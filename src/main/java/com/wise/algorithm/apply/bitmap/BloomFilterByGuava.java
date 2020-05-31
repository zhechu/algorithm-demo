package com.wise.algorithm.apply.bitmap;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.*;

/**
 * 问题：
 * 有1亿个随机数，其范围在1到1亿之间。要求将1到1亿之间没有在随机数中的数求出来？
 *
 * 方案：
 * Guava布隆过滤器（不在集合的一定不在，在的不一定在）
 *
 * @author lingyuwang
 * @date 2020-05-26 20:47
 * @since 1.0.4
 */
public class BloomFilterByGuava {

    public static void main(String[] args) {
        int length = 10;
        // 包含 minValue 和 maxValue
        int minValue = 1;
        int maxValue = 10;
        int[] arr = generateRandomArray(length, minValue, maxValue);

        // 打印
        printArray(arr);

        BloomFilter<Integer> filter = BloomFilter.create(
                // 存放 Integer 类型数据
                Funnels.integerFunnel(),
                // 预计长度
                length,
                // 预计误报率
                0.001);
        for (int i = 0; i < length; i++) {
            filter.put(arr[i]);
        }

        // 获取不在随机数中的数
        List<Integer> result = new ArrayList<>();
        for (int k = minValue; k <= maxValue; k++) {
            // 判断是否在随机数的数组中
            if (!filter.mightContain(k)) {
                result.add(k);
            }
        }
        System.out.println(minValue + "~" + maxValue + "不在上述随机数中的数有：" + result.size() + "个");
        System.out.println(result);

    }

    /**
     * 随机生成数组
     * @param length
     * @param minValue
     * @param maxValue
     * @return
     */
    public static int[] generateRandomArray(int length, int minValue, int maxValue) {
        int[] arr = new int[length];

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }
        return arr;
    }

    /**
     * 数组打印
     * @param arr
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
