package com.wise.algorithm.apply;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计数组的正/逆序对个数
 */
public class OrderPair {

    public static void main(String[] args) {
        int[] arr = generateRandomArray(10, 10);
        int[] arr2 = copyArray(arr);
        int[] arrBak = copyArray(arr);
        printArray(arr);

        // 逆序对个数
        int reverseOrderPairCount = getOrderPairCount(arr, true);
        System.out.println("数组长度为:" + arrBak.length + ", 逆序对个数为:" + reverseOrderPairCount + "个");

        System.out.println("--------------------------------分割线-----------------------------------");

        // 正序对个数
        int orderPairCount = getOrderPairCount(arr2, false);
        System.out.println("数组长度为:" + arrBak.length + ", 正序对个数为:" + orderPairCount + "个");
    }

    /**
     * 获取正/逆序对个数
     * @param arr 数组
     * @param reverse 是否获取逆序对
     * @return
     */
    public static int getOrderPairCount(int[] arr, boolean reverse) {
        AtomicInteger count = new AtomicInteger(0);

        int len = arr.length;
        mergeSortCounting(arr, 0, len - 1, reverse, count);

        return count.get();
    }

    private static void mergeSortCounting(int[] arr, int p, int r, boolean reverse, AtomicInteger count) {
        if (p >= r) {
            return;
        }

        int q = (p + r) / 2;

        mergeSortCounting(arr, p, q, reverse, count);
        mergeSortCounting(arr, q + 1, r, reverse, count);
        merge(arr, p, q, r, reverse, count);
    }

    private static void merge(int[] arr, int p, int q, int r, boolean reverse, AtomicInteger count) {
        int i = p, j = q + 1, k = 0;
        int[] tmp = new int[r - p + 1];
        while (i <= q && j <= r) {
            // 逆序对
            if (reverse) {
                if (arr[i] <= arr[j]) {
                    tmp[k++] = arr[i++];
                } else {
                    // 统计 p - q 之间，比 a[j] 大的元素个数
                    count.addAndGet(q - i + 1);

                    tmp[k++] = arr[j++];
                }
            }
            // 正序对
            else {
                if (arr[i] <= arr[j]) {
                    // 统计 r - q 之间，比 a[j] 大的元素个数
                    count.addAndGet(r - j + 1);

                    tmp[k++] = arr[i++];
                } else {
                    tmp[k++] = arr[j++];
                }
            }
        }

        // 处理剩下的
        while (i <= q) {
            tmp[k++] = arr[i++];
        }

        // 处理剩下的
        while (j <= r) {
            tmp[k++] = arr[j++];
        }

        // 从 tmp 拷贝回 arr
        for (i = 0; i <= r - p; ++i) {
            arr[p + i] = tmp[i];
        }
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
