package com.wise.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class Radix {

    public static void main(String[] args) {
        // 对数器
        int testTime = 100;
        int maxSize = 100;
        int maxValue = 10000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            Arrays.sort(arr2);
            // 检查经基数排序的数组是否已有序，若有序，则检查成功
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");

        // 基数排序
        int[] arr = generateRandomArray(maxSize, maxValue);
        // 排序前
        printArray(arr);

        // 开始排序
        radixSort(arr);

        // 排序后
        printArray(arr);
    }

    /**
     * 随机数排序
     * @param arr
     */
    public static void radixSort(int[] arr) {
        // 数组长度
        int len = arr.length;
        if (len < 2) {
            return;
        }

        int mod = 10;
        int dev = 1;

        // 计数数组长度
        int countLen = 19;
        // 计数数组索引填充
        int countAppend = 9;

        // 最大位数
        int maxDigit = getMaxDigit(arr);

        for (int i = 0; i < maxDigit; i++, dev *= 10, mod *= 10) {
            // 申请一个计数数组，考虑负数的情况，下标大小[0, 19]
            int[] countArr = new int[countLen];

            // 计算每个元素的个数，放入计数数组中
            for (int j = 0; j < len; j++) {
                int bucket = ((arr[j] % mod) / dev) + countAppend;
                countArr[bucket]++;
            }

            // 求小于或等于自身的元素个数并记录到对应的索引位置
            for (int k = 1; k < countLen; k++) {
                countArr[k] = countArr[k - 1] + countArr[k];
            }

            int[] result = new int[len];
            // 从后往前扫描原数组并将结果记录到结果数组
            for (int r = len - 1; r >= 0; r--) {
                int bucket = ((arr[r] % mod) / dev) + countAppend;
                // 计数数组对应的计数值即为元素排序后的位置
                int index = countArr[bucket] - 1;
                result[index] = arr[r];
                // 原数组元素进入结果数组后计数数组对应的计数值需要-1
                countArr[bucket]--;
            }

            // 将结果拷贝到原始数组
            for (int r = 0; r < len; r++) {
                arr[r] = result[r];
            }
        }
    }

    /**
     * 获取最高位数
     */
    public static int getMaxDigit(int[] arr) {
        int maxAbsValue = getMaxAbsValue(arr);
        return getNumLenght(maxAbsValue);
    }

    /**
     * 获取绝对值的最大值
     * @param arr
     * @return
     */
    public static int getMaxAbsValue(int[] arr) {
        int maxAbsValue = Math.abs(arr[0]);
        for (int value : arr) {
            int absValue = Math.abs(value);
            if (maxAbsValue < absValue) {
                maxAbsValue = absValue;
            }
        }
        return maxAbsValue;
    }

    /**
     * 获取数值的长度
     * @param num
     * @return
     */
    public static int getNumLenght(int num) {
        if (num == 0) {
            return 1;
        }
        int lenght = 0;
        for (int temp = num; temp != 0; temp /= 10) {
            lenght++;
        }
        return lenght;
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
