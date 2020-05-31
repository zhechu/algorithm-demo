package com.wise.algorithm.apply.bitmap;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 问题：
 * 有1亿个不同的数，其范围在1到1亿之间。要求将1到1亿之间的数进行有序输出？
 *
 * 方案：
 * 位图法
 *
 * @author lingyuwang
 * @date 2020-05-26 20:47
 * @since 1.0.4
 */
public class LinearSortByBitSet {

    public static void main(String[] args) {
        int length = 10;
        // 包含 minValue 和 maxValue
        int minValue = 1;
        int maxValue = 20;
        int[] arr = generateRandomArray(length, minValue, maxValue);

        // 打印
        printArray(arr);

        // BitSet 索引从0开始，因此需浪费一个位
        BitSet bitSet = new BitSet(maxValue + 1);
        for (int i = 0; i < length; i++) {
            bitSet.set(arr[i]);
        }

        System.out.println("有序输出：");
        for (int k = minValue; k <= maxValue; k++) {
            // 判断是否在位图中
            if (bitSet.get(k)) {
                System.out.print(k + " ");
            }
        }
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

        Set<Integer> set = new HashSet<>(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int temp;
            do {
                temp = random.nextInt(maxValue - minValue + 1) + minValue;
            } while (set.contains(temp));

            set.add(temp);
            arr[i] = temp;
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
