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
        // 构造一个 范围为 [0, 2^30] 的 Integer 流，通过limit可以控制大小
        final int upLimit = 1 << 30;
        List<Integer> originList = Stream.generate(Math::random)
                .map(d -> d * upLimit)
                .map(d -> (int) Math.round(d))
                .limit(100000000)
                .collect(Collectors.toList());

        // 将 (o1, o2) -> (o1 - o2) 换成 (o1, o2) -> (o2 - o1) 可以求解 top K 小
        PriorityQueueTopK<Integer> priorityQueueTopK = new PriorityQueueTopK<>(10, (o1, o2) -> (o1 - o2));

        long startTime = System.currentTimeMillis();

        List<Integer> results = priorityQueueTopK.getTopK(originList);

        long endTime = System.currentTimeMillis();

//        System.out.println("origin: " + originList);
        System.out.println("results: " + results);
        System.out.println("cost: " + (endTime - startTime));
    }

    /** 堆的边界，Top K 问题中的 K */
    private final int boundary;

    /** 优先队列，构造一个有界的堆 */
    private final PriorityQueue<T> boundaryHeap;

    /**
     * 求解 top K 问题
     * @param boundary 边界 K
     * @param comparator 数据比较器
     */
    public PriorityQueueTopK(int boundary, Comparator<T> comparator) {
        this.boundary = boundary;
        boundaryHeap = new PriorityQueue<>(boundary, comparator);
    }

    /**
     * 求解数据流中的 top K
     * @param originList 原始数据
     * @return top K 结果
     */
    public List<T> getTopK(List<T> originList) {
        List<T> result = new ArrayList<>(boundary);

        originList.forEach(this::add);

        while (!boundaryHeap.isEmpty()) {
            result.add(boundaryHeap.poll());
        }

        // 排序反转
        Collections.reverse(result);

        return result;
    }

    /**
     * 向有界堆中添加元素的帮助方法
     * @param t 待添加数据
     */
    private void add(T t) {
        boundaryHeap.add(t);
        if (boundaryHeap.size() > boundary) {
            boundaryHeap.poll();
        }
    }

}