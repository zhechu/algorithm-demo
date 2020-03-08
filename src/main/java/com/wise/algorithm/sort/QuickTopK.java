package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序（求解第 k 大元素）
 * @author lingyuwang
 * @date 2019-07-13 10:55
 */
public class QuickTopK {

    public static void main(String[] args) {
        // 查找第 3 大的数
        int k = 3;

        // 对数器
        int testTime = 100;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int resultIndex = findByQuickSort(arr1, k);
            Arrays.sort(arr2);
            arr2 = reserve(arr2);
            // 检查结果是否相等
            if (arr1.length >= k && (resultIndex < 0 || arr1[resultIndex] != arr2[resultIndex])) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        int[] arr = generateRandomArray(maxSize, maxValue);
        int[] arr2 = copyArray(arr);
        Arrays.sort(arr2);
        arr2 = reserve(arr2);

        // 查找前
        printArray(arr);
        // 排序后
        printArray(arr2);

        // 开始查找
        int result = findByQuickSort(arr, k);
        if (result >= 0) {
            System.out.println("第" + k + "大的元素是：" + arr[result] + ", 索引是：" + result);
        } else {
            System.out.println("不存在第" + k + "大的元素");
        }
    }

    /**
     * 通过快速排序查找
     * @param arr
     */
    public static int findByQuickSort(int[] arr, int k) {
        if (arr == null || k < 1) {
            return -1;
        }
        if (arr.length == 1) {
            return k == 1 ? 0 : -1;
        }
        if (arr.length < k) {
            return -1;
        }

        return findByQuickSort(arr, k, 0, arr.length - 1);
    }

    /**
     * 快速排序算法骨架方法，用于递归
     * @param arr
     * @param left
     * @param right
     */
    public static int findByQuickSort(int[] arr, int k, int left, int right) {
        if (left == right) {
            return left;
        }

        int p = partition(arr, left, right);

        // 目标已查到
        if (p == k - 1) {
            return p;
        }

        // 目标在左边
        if (p > k - 1) {
            return findByQuickSort(arr, k, left, p-1);
        }

        // 目标在右边
        return findByQuickSort(arr, k, p + 1, right);
    }

    /**
     * 以最后一个元素作为分区点进行分区
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public static int partition(int[] arr, int left, int right) {
        // 记录分区指针所在的位置
        int p = left;
        for (int j = left; j < right; j++) {
            if (arr[j] > arr[right]) {
                swap(arr, p, j);
                p++;
            }
        }

        // 此时，p 要么是与 right 相等（原数组已有序），要么是右边第一个比 right 元素值小的数的指针
        swap(arr, p, right);
        return p;
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
     * 数组反转
     * @param arr
     * @return
     */
    public static int[] reserve(int[] arr){
        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++){
            temp[i] = arr[arr.length - i - 1];
        }
        return temp ;
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
