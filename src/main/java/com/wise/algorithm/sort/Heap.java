package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 堆排序（大根堆）
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class Heap {

    public static void main(String[] args) {
        // 对数器
        int testTime = 1;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            Arrays.sort(arr2);
            // 检查经冒泡排序的数组是否已有序，若有序，则检查成功
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        // 堆排序
        int[] arr = generateRandomArray(maxSize, maxValue);
        // 排序前
        printArray(arr);

        // 开始排序
        heapSort(arr);

        // 排序后
        printArray(arr);
    }

    /**
     * 大根堆排序
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;

        // 建立大根堆
        buildMaxHeap(arr, len);

        // 最后一个元素与堆顶交换，并堆化，直到堆中只剩一个元素（i == 0）
        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0, i);
            // 堆元素-1
            len--;
            heapify(arr, 0, len);
        }
    }

    /**
     * 建立大根堆
     * @param arr
     * @param len
     */
    public static void buildMaxHeap(int[] arr, int len) {
        // 自最后一个非叶子节点开始堆化（(len - 1) >> 1 不一定是非叶子节点，但也没关系）
        for (int i = (len - 1) >> 1; i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    /**
     * 自上往下堆化
     * @param arr
     * @param index
     * @param len
     */
    public static void heapify(int[] arr, int index, int len) {
        // 左节点指针
        int left = (index << 1) + 1;
        // 右节点指针
        int right = (index << 1) + 2;
        // 最大节点指针
        int largest = index;

        // 检查左节点是否大于父节点
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        // 检查右节点是否大于最大节点
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        // 若最大节点不是父节点，则交换并继续调整
        if (largest != index) {
            swap(arr, index, largest);
            heapify(arr, largest, len);
        }
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
