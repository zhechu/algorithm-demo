package com.wise.algorithm.sort;

import java.util.Arrays;
import java.util.Stack;

/**
 * 随机快速排序循环实现
 * @author lingyuwang
 * @date 2019-07-13 10:57
 */
public class FastLoop {

    public static void main(String[] args) {
        // 快速排序
        int testTime = 1;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        quickSort(arr);
        printArray(arr);

    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        Stack<Integer[]> stack = new Stack<>();
        stack.push(new Integer[]{0, arr.length - 1});

        while (!stack.isEmpty()) {
            Integer[] boundary = stack.pop();
            int l = boundary[0];
            int r = boundary[1];

            if (l < r) {
                swap(arr, l + (int) (Math.random() * (r - l + 1)), r); // 增加随机性
                int[] p = partition(arr, l, r);

                stack.push(new Integer[]{l, p[0] - 1});
                stack.push(new Integer[]{p[1] + 1, r});
            }
        }
    }

    public static int[] partition(int[] arr, int l, int r) {
        int less = l - 1;
        int more = r;
        int current = l; // current 可以用 l 变量替换，这里为了方便理解才加多的一个变量
        while (current < more) {
            if (arr[current] < arr[r]) {
                swap(arr, ++less, current++);
            } else if (arr[current] > arr[r]) {
                swap(arr, --more, current);
            } else {
                current++;
            }
        }
        swap(arr, current, r);
        return new int[] { less + 1, more };
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
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

    // for test
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

    // for test
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
