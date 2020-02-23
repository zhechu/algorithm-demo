package com.wise.algorithm.queue;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 无锁非阻塞队列
 */
public class LockFreeQueue {

    public static void main(String[] args) throws InterruptedException {
        LockFreeQueue lockFreeQueue = new LockFreeQueue(15);

        // 生产者
        Thread product1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lockFreeQueue.add(i);
            }
        });
        Thread product2 = new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                lockFreeQueue.add(i);
            }
        });
        Thread product3 = new Thread(() -> {
            for (int i = 200; i < 300; i++) {
                lockFreeQueue.add(i);
            }
        });

        // 消费者
        Thread customer1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lockFreeQueue.poll();
            }
        });
        Thread customer2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lockFreeQueue.poll();
            }
        });
        Thread customer3 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                lockFreeQueue.poll();
            }
        });

        product1.start();
        product2.start();
        product3.start();
        customer1.start();
        customer2.start();
        customer3.start();

        TimeUnit.SECONDS.sleep(5);

        lockFreeQueue.print();
    }

    /** 存储数据 */
    private AtomicReferenceArray<Integer> atomicReferenceData;
    /** 数组长度 */
    private int len;

    /** 代表为空，没有元素 */
    private static final  Integer EMPTY = null;

    /** 头指针 */
    AtomicInteger head;

    /** 尾指针 */
    AtomicInteger tail;


    public LockFreeQueue(int size) {
        // 数组需要多出一个空元素，方便判断队列是否已满
        atomicReferenceData = new AtomicReferenceArray(new Integer[size + 1]);
        len = atomicReferenceData.length();
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
    }

    /**
     * 入队
     * @param element
     * @return
     */
    public boolean add(Integer element) {
        int index;

        do {
            index = (tail.get() + 1) % len;
            if (index == head.get() % len) {
                System.out.println("当前队列已满," + element + "无法入队!");
                return false;
            }
        } while (!atomicReferenceData.compareAndSet(index, EMPTY, element));

        // 移动尾指针
        tail.incrementAndGet();

        System.out.println(element + "入队成功!");

        return true;
    }

    /**
     * 出队
     * @return
     */
    public Integer poll() {
        int index;
        Integer element;

        do {
            if (head.get() == tail.get()) {
                System.out.println("当前队列为空!");
                return null;
            }

            index = (head.get() + 1) % len;
            element = atomicReferenceData.get(index);
        } while (element == null || !atomicReferenceData.compareAndSet(index, element, EMPTY));

        // 移动头指针
        head.incrementAndGet();

        System.out.println(element + "出队成功!");

        return element;
    }

    /**
     * 打印队列存储状态
     * @return
     */
    public void print() {
       StringBuffer buffer = new StringBuffer("[");
       for (int i = 0; i < len ; i++){
           if (i == head.get() || atomicReferenceData.get(i) == null){
               continue;
           }
           buffer.append(atomicReferenceData.get(i) + ",");
       }
       buffer.deleteCharAt(buffer.length() - 1);
       buffer.append("]");

       System.out.println("队列内容:" + buffer);
    }

}
