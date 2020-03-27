package com.wise.algorithm.heap;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1.建立一个大根堆和一个小根堆，用一个临时变量（count）来统计数据流的个数
 * 2.当插入的数字个数为奇数时，使小根堆的个数比大根堆多1；当插入的数字个数为偶数时，使大根堆和小根堆的个数一样多
 * 3.当总的个数为奇数时，中位数就是小根堆的堆顶；当总的个数为偶数时，中位数就是两个堆顶的值相加除以2
 */
public class MedianFinder {

    public static void main(String[] args) {
        // 构造一个 范围为 [0, 2^8] 的 Integer 流，通过limit可以控制大小
        final int upLimit = 1 << 8;
        List<Integer> originList = Stream.generate(Math::random)
                .map(d -> d * upLimit)
                .map(d -> (int) Math.round(d))
                .limit(9)
                .collect(Collectors.toList());

        MedianFinder medianFinder = new MedianFinder();
        originList.forEach(medianFinder::add);

        originList.sort((o1, o2) -> (o1 - o2));
        System.out.println("originList: " + originList);
        double median = medianFinder.getMedian();
        System.out.println("median: " + median);
    }

    private PriorityQueue<Integer> min = new PriorityQueue<>((o1, o2) -> (o1 - o2));

    private PriorityQueue<Integer> max = new PriorityQueue<>((o1, o2) -> (o2 - o1));

    /** 统计数据流的个数 */
    private int count = 0;

    /**
     * 确保小根堆里面的数 > 大根堆里面的数
     * @param num
     */
    public void add(int num) {
        count++;
        if (count % 2 == 1) {
            // 若小于大根堆的堆顶，则先从大根堆过滤一遍
            if (max.peek() != null && num < max.peek()) {
                max.add(num);
                min.add(max.poll());
            }
            // 直接放入小根堆
            else {
                min.add(num);
            }
        } else {
            // 若大于小根堆的堆顶，则先从小顶堆过滤一遍
            if (min.peek() != null && num > min.peek() ) {
                min.add(num);
                max.add(min.poll());
            }
            // 直接放入大根堆
            else {
                max.add(num);
            }
        }
    }

    /**
     * 获取中位数
     * @return
     */
    public double getMedian() {
        if (count == 0) {
            throw new RuntimeException("队列大小为空");
        }

        if (count % 2 == 0) {
            return (min.peek() + max.peek()) / 2.0;
        }

        return min.peek();
    }

}