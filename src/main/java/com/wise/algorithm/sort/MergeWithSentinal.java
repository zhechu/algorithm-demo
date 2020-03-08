package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序（使用哨兵优化）
 * @author lingyuwang
 * @date 2019-07-13 10:48
 */
public class MergeWithSentinal {

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
        mergeWithSentinal(arr, left, mid, right);
    }

    /**
     * 合并局部有序的两段序列（使用哨兵优化）
     * @param arr
     * @param left
     * @param mid
     * @param right
     */
    public static void mergeWithSentinal(int[] arr, int left, int mid, int right) {
        int leftLen = mid - left + 1;
        int rightLen = right - mid;
        int[] leftTemp = new int[leftLen + 1];
        int[] rightTemp = new int[rightLen + 1];

        // 左部分
        int i = 0;
        while (i < leftLen) {
            leftTemp[i] = arr[left + i++];
        }

        // 右部分
        int j = 0;
        while (j < rightLen) {
            rightTemp[j] = arr[mid + 1 + j++];
        }

        // 使用哨兵减少比较次数
        leftTemp[leftLen] = Integer.MAX_VALUE;
        rightTemp[rightLen] = Integer.MAX_VALUE;

        // 比较大小，并把有序数据放回原数组
        int k = left;
        i = j = 0;
        while (k <= right) {
            arr[k++] = leftTemp[i] < rightTemp[j] ? leftTemp[i++] : rightTemp[j++];
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
