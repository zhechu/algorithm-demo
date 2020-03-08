package com.wise.algorithm.queue;

/**
 * 循环队列
 */
public class LoopQueue<E> {

    public static void main(String[] args) {
        int capacity = 5;
        LoopQueue<String> loopQueue = new LoopQueue<>(capacity);

        // 入队
        System.out.println(loopQueue.enqueue("张三")); // true
        System.out.println(loopQueue.enqueue("李四")); // true
        System.out.println(loopQueue.enqueue("王五")); // true
        System.out.println(loopQueue.enqueue("赵六")); // true
        System.out.println(loopQueue.enqueue("牛七")); // true
        System.out.println(loopQueue.enqueue("三八")); // false

        // 出队
        for (int i = 0; i < capacity; i++) {
            System.out.println(loopQueue.dequeue());
        }

        System.out.println("==============================");

        // 入队
        System.out.println(loopQueue.enqueue("张三")); // true
        System.out.println(loopQueue.enqueue("李四")); // true
        System.out.println(loopQueue.enqueue("王五")); // true
        System.out.println(loopQueue.enqueue("赵六")); // true
        System.out.println(loopQueue.enqueue("牛七")); // true
        System.out.println(loopQueue.enqueue("三八")); // false
        // 出队
        System.out.println(loopQueue.dequeue()); // 张三
        // 入队
        System.out.println(loopQueue.enqueue("洪九")); // true

        // 出队
        for (int i = 0; i < capacity; i++) {
            System.out.println(loopQueue.dequeue());
        }
    }

    /** 数据 */
    private Object[] items;
    /** 容量 */
    private int capacity;
    /** 现有元素个数 */
    private int count;
    /** 头指针 */
    private int head;
    /** 尾指针 */
    private int tail;

    public LoopQueue() {
        this(10);
    }

    public LoopQueue(int capacity) {
        this.capacity = capacity;
        items = new Object[capacity];
        head = tail = 0;
        count = 0;
    }

    /**
     * 入队
     * @param e
     * @return
     */
    public boolean enqueue(E e) {
        if (count == capacity) {
            return false;
        }

        items[tail++] = e;

        if (tail == capacity) {
            tail = 0;
        }

        count++;

        return true;
    }

    /**
     * 出队
     * @return
     */
    public E dequeue() {
        if (count == 0) {
            return null;
        }

        E e = (E) items[head++];

        if (head == capacity) {
            head = 0;
        }

        count--;

        return e;
    }

}
