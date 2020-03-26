package com.wise.algorithm.heap;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通过构建堆结构求解 TopK 问题
 *
 * @param <E>
 */
public class HeapTopK<E> {

    public static void main(String[] args) {
        // 对数器检查自定义算法是否正确
//        check();

        // 构造一个 范围为 [0, 2^30] 的 Integer 流，通过limit可以控制大小
        final int upLimit = 1 << 30;
        List<Integer> originList = Stream.generate(Math::random)
                .map(d -> d * upLimit)
                .map(d -> (int) Math.round(d))
                .limit(10000000)
                .collect(Collectors.toList());

        // 将 (o1, o2) -> (o1 - o2) 换成 (o1, o2) -> (o2 - o1) 可以求解 top K 小
        HeapTopK<Integer> heapTopK = new HeapTopK<>(10, (o1, o2) -> (o2 - o1));

        long startTime = System.currentTimeMillis();

        List<Integer> results = heapTopK.getTopK(originList);

        long endTime = System.currentTimeMillis();

//        System.out.println("origin: " + originList);
        System.out.println("results: " + results);
        System.out.println("cost: " + (endTime - startTime));
        // 108 101 113
    }

    /**
     * 检查自定义算法是否正确
     */
    public static void check() {
        // 对数器
        int testTime = 100;
        boolean succeed = true;
        List<Comparator<Integer>> comparatorList = new ArrayList<>(2);
        comparatorList.add((o1, o2) -> (o1 - o2));
        comparatorList.add((o1, o2) -> (o2 - o1));
        for (int i = 0; i < testTime; i++) {
            // 构造一个 范围为 [0, 2^30] 的 Integer 流，通过limit可以控制大小
            final int upLimit = 1 << 20;
            List<Integer> originList = Stream.generate(Math::random)
                    .map(d -> d * upLimit)
                    .map(d -> (int) Math.round(d))
                    .limit(100000)
                    .collect(Collectors.toList());
            int boundary = 10;
            Comparator<Integer> comparator = comparatorList.get(testTime % 2);
            HeapTopK<Integer> heapTopK = new HeapTopK<>(boundary, comparator);
            List<Integer> headTopKResult = heapTopK.getTopK(originList);
            PriorityQueueTopK<Integer> priorityQueueTopK = new PriorityQueueTopK<>(boundary, comparator);
            List<Integer> priorityQueueTopKResult = priorityQueueTopK.getTopK(originList);

            if (headTopKResult.size() != boundary || headTopKResult.size() != priorityQueueTopKResult.size()) {
                System.out.println("size check fail, origin: " + originList);
                System.out.println("size check fail, headTopKResult: " + headTopKResult);
                System.out.println("size check fail, priorityQueueTopKResult: " + priorityQueueTopKResult);
                succeed = false;
                break;
            }

            for (int j = 0; j < boundary; j++) {
                if (!headTopKResult.get(j).equals(priorityQueueTopKResult.get(j))) {
                    System.out.println("result check fail, origin: " + originList);
                    System.out.println("result check fail, headTopKResult: " + headTopKResult);
                    System.out.println("result check fail, priorityQueueTopKResult: " + priorityQueueTopKResult);
                    succeed = false;
                    break;
                }
            }

            if (!succeed) {
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");
    }

    /** 堆的边界，Top K 问题中的 K */
    private final int boundary;

    /** TopK 数据 */
    transient Object[] queue;

    /** 比较器 */
    private final Comparator<? super E> comparator;

    public HeapTopK(int boundary, Comparator<? super E> comparator) {
        this.boundary = boundary;
        this.queue = new Object[boundary];
        this.comparator = comparator;
    }

    /**
     * 求解数据流中的 top K
     * @param c 原始数据
     * @return top K 结果
     */
    public List<E> getTopK(Collection<? extends E> c) {
        Object[] array = c.toArray();

        for (int i = 0; i < boundary; i++) {
            queue[i] = array[i];
        }

        // 堆化
        buildHeap(queue);

        int length = array.length;
        for (int j = boundary; j < length; j++) {
            add(array[j]);
        }

        List<E> result = new ArrayList<>(boundary);
        Object[] es = queue;
        int len = boundary;
        while (len > 0) {
            result.add((E) es[0]);
            es[0] = es[--len];
            heapify(es, 0, len);
        }

        return result;
    }

    /**
     * 自上往下堆化
     * @param array
     * @param index
     * @param length
     */
    private void heapify(Object[] array, int index, int length) {
        // 左节点指针
        int left = (index << 1) + 1;
        // 右节点指针
        int right = (index << 1) + 2;
        // 最小节点指针
        int least = index;

        // 检查左节点
        if (left < length && comparator.compare((E) array[left], (E) array[least]) < 0) {
            least = left;
        }

        // 检查右节点
        if (right < length && comparator.compare((E) array[right], (E) array[least]) < 0) {
            least = right;
        }

        // 若最小节点不是父节点，则交换并继续调整
        if (index != least) {
            swap(array, least, index);
            // 继续向下调整
            heapify(array, least, length);
        }
    }

    /**
     * 两数交换
     * @param array
     * @param i
     * @param j
     */
    public static void swap(Object[] array, int i, int j) {
        if (i == j) {
            return;
        }

        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 建立小根堆
     * @param array
     */
    private void buildHeap(Object[] array) {
        int length = array.length;
        // 自最后一个非叶子节点开始堆化
        for (int i = (length - 2) >> 1; i >= 0; i--) {
            heapify(array, i, length);
        }
    }

    /**
     * 向有界堆中添加元素的帮助方法
     * @param t 待添加数据
     */
    private void add(Object t) {
        if (comparator.compare((E) t, (E) queue[0]) > 0) {
            queue[0] = t;
            heapify(queue, 0, boundary);
        }
    }

}