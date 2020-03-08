package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 计数排序（假设数组中存储的都是非负整数）
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class Counting {

    public static void main(String[] args) {
        // 对数器
        int testTime = 100;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            countingSort(arr1);
            Arrays.sort(arr2);
            // 检查经计数排序的数组是否已有序，若有序，则检查成功
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        // 计数排序
        int[] arr = generateRandomArray(maxSize, maxValue);
        // 排序前
        printArray(arr);

        // 开始排序
        countingSort(arr);

        // 排序后
        printArray(arr);
    }

    /**
     * 计数排序
     * @param arr
     */
    public static void countingSort(int[] arr) {
        int len = arr.length;
        if (len <= 1) {
            return;
        }

        // 查找数组中数据的范围
        int max = arr[0];
        for (int i = 1; i < len; ++i) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        // 申请一个计数数组，下标大小[0, max]
        int[] countArr = new int[max + 1];

        // 计算每个元素的个数，放入计数数组中
        for (int i = 0; i < len; ++i) {
            countArr[arr[i]]++;
        }

        // 求小于或等于自身的元素个数并记录到对应的索引位置
        for (int i = 1; i <= max; ++i) {
            countArr[i] = countArr[i - 1] + countArr[i];
        }

        int[] result = new int[len];
        // 从后往前扫描原数组并将结果记录到结果数组
        for (int i = len - 1; i >= 0; --i) {
            // 计数数组对应的计数值即为元素排序后的位置
            int index = countArr[arr[i]] - 1;
            result[index] = arr[i];
            // 原数组元素进入结果数组后计数数组对应的计数值需要-1
            countArr[arr[i]]--;
        }

        // 将结果拷贝到原始数组
        for (int i = 0; i < len; ++i) {
            arr[i] = result[i];
        }
    }

    /**
     * 随机生成数组
     * @param maxSize
     * @param maxValue
     * @return
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (maxValue * Math.random());
        }
        return arr;
    }

    /**
     * 拷贝数组
     * @param arr
     * @return
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * 两数组比较
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
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
