package com.wise.algorithm.heap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 通过 PriorityQueue 求解 TopK 问题
 * @param <T>
 */
public class PriorityQueueTopK<T> {

    public static void main(String[] args) {
        // 对数器检查通过 PriorityQueue 实现的算法是否正确
//        check();

        // 构造一个 范围为 [0, 2^30] 的 Integer 流，通过limit可以控制大小
        final int upLimit = 1 << 30;
        List<Integer> originList = Stream.generate(Math::random)
                .map(d -> d * upLimit)
                .map(d -> (int) Math.round(d))
                .limit(100000000)
                .collect(Collectors.toList());

        // 将 (o1, o2) -> (o1 - o2) 换成 (o1, o2) -> (o2 - o1) 可以求解 top K 小
        PriorityQueueTopK<Integer> priorityQueueTopK = new PriorityQueueTopK<>(10, (o1, o2) -> (o2 - o1));

        long startTime = System.currentTimeMillis();

        List<Integer> results = priorityQueueTopK.getTopK(originList);

        long endTime = System.currentTimeMillis();

//        System.out.println("origin: " + originList);
        System.out.println("results: " + results);
        System.out.println("cost: " + (endTime - startTime));
        // 510 655 518 513 667
        // average: 572
    }

    /**
     * 检查通过 PriorityQueue 实现的算法是否正确
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

    /** 优先队列，构造一个有界的堆 */
    private final PriorityQueue<T> boundaryHeap;

    /** 比较器 */
    private final Comparator<T> comparator;

    /**
     * 求解 top K 问题
     * @param boundary 边界 K
     * @param comparator 数据比较器
     */
    public PriorityQueueTopK(int boundary, Comparator<T> comparator) {
        this.boundary = boundary;
        boundaryHeap = new PriorityQueue<>(boundary, comparator);
        this.comparator = comparator;
    }

    /**
     * 求解数据流中的 top K
     * @param originList 原始数据
     * @return top K 结果
     */
    public List<T> getTopK(List<T> originList) {
        int size = originList.size();
        if (size <= boundary) {
            return originList;
        }

        List<T> result = new ArrayList<>(boundary);

        // 堆化
        for (int i = 0; i < boundary; i++) {
            buildHeap(originList.get(i));
        }

        // 向已经堆化的堆插入数据
        for (int i = boundary; i < size; i++) {
            add(originList.get(i));
        }

        while (!boundaryHeap.isEmpty()) {
            result.add(boundaryHeap.poll());
        }

        return result;
    }

    /**
     * 堆化
     * @param t
     */
    private void buildHeap(T t) {
        boundaryHeap.add(t);
    }

    /**
     * 向有界堆中添加元素的帮助方法
     * @param t 待添加数据
     */
    private void add(T t) {
        if (comparator.compare(t, boundaryHeap.peek()) > 0) {
            boundaryHeap.poll();
            boundaryHeap.add(t);
        }
    }

}