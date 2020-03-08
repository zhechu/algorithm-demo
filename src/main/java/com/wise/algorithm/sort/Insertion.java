package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class Insertion {

    public static void main(String[] args) {
        // 对数器
        int testTime = 100;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            insertionSort(arr1);
            Arrays.sort(arr2);
            // 检查经插入排序的数组是否已有序，若有序，则检查成功
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        // 插入排序
        int[] arr = generateRandomArray(maxSize, maxValue);
        // 排序前
        printArray(arr);

        // 开始排序
        insertionSort(arr);

        // 排序后
        printArray(arr);
    }

    /**
     * 插入排序
     * @param arr
     * @return
     */
    public static int[] insertionSort(int[] arr){
        int len = arr.length;

        // 获取最小值的索引，作为哨兵
        int minIndex = 0;
        for (int i = 0; i < len; i++) {
            if (arr[minIndex] > arr[i]) {
                minIndex = i;
            }
        }
        swap(arr, minIndex, 0);

        // 假设第1个元素已排好序，从第2个元素开始
        for (int i = 1; i < len; i++) {
            for (int j = i; arr[j] < arr[j - 1]; j--) {
                swap(arr, j, j - 1);
            }
        }
        return arr;
    }

    /**
     * 两数交换
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }

        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
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
