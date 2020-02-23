package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 * @author lingyuwang
 * @date 2019-07-13 10:48
 */
public class Merge {

    public static void main(String[] args) {
        // 对数器
        int testTime = 1;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            Arrays.sort(arr2);
            // 检查经冒泡排序的数组是否已有序，若有序，则检查成功
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        // 归并排序
        int[] arr = generateRandomArray(maxSize, maxValue);
        // 排序前
        printArray(arr);

        // 开始排序
        mergeSort(arr);

        // 排序后
        printArray(arr);
    }

    /**
     * 归并排序主入口
     * @param arr
     */
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序算法骨架方法，用于递归
     * @param arr
     * @param left
     * @param right
     */
    public static void mergeSort(int[] arr, int left, int right) {
        // 序列中只有一个元素，表示默认已排好序，不需处理，直接返回
        if (left == right) {
            return;
        }

        // 使用加减法和位运算以避免乘除法运算，提高效率
        int mid = left + ((right - left) >> 1);
        // 左边序列排序
        mergeSort(arr, left, mid);
        // 右边序列排序
        mergeSort(arr, mid + 1, right);
        // 两序列归并
        merge(arr, left, mid, right);
    }

    /**
     * 合并局部有序的两段序列
     * @param arr
     * @param left
     * @param mid
     * @param right
     */
    public static void merge(int[] arr, int left, int mid, int right) {
        // 两序列合并后的辅助数组
        int[] help = new int[right - left + 1];
        int i = 0;
        // 左序列当前指针
        int p1 = left;
        // 右序列当前指针
        int p2 = mid + 1;

        while (p1 <= mid && p2 <= right) {
            // 若左边序列当前指针的值比右序列当前指针的值小，则前者进入辅助数组，反之则反
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 检查左边序列是否还有元素未进入辅助数组
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }

        // 检查右边序列是否还有元素未进入辅助数组
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }

        // 将辅助数组整理到原数组
        for (i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
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
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
