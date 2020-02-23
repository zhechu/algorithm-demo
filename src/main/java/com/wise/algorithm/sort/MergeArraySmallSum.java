package com.wise.algorithm.sort;

/**
 * 归并排序使用示例：在一个数组中， 每一个数左边比当前数小的数累加起来， 叫做这个数组的小和。 求一个数组 的小和
 * @author lingyuwang
 * @date 2019-07-13 10:51
 */
public class MergeArraySmallSum {

    public static void main(String[] args) {
        // 对数器
        int testTime = 1;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "检查成功" : "检查失败");
    }

    /**
     * 求数组的小和
     * @param arr
     * @return
     */
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * 归并排序
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public static int mergeSort(int[] arr, int left, int right) {
        // 序列中只有一个元素，表示默认已排好序，不需处理，直接返回
        if (left == right) {
            return 0;
        }

        // 结果
        int res = 0;

        // 使用加减法和位运算以避免乘除法运算，提高效率
        int mid = left + ((right - left) >> 1);
        // 左边序列排序
        res += mergeSort(arr, left, mid);
        // 右边序列排序
        res += mergeSort(arr, mid + 1, right);
        // 两序列归并
        res += merge(arr, left, mid, right);

        return res;
    }

    /**
     * 归并
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @return
     */
    public static int merge(int[] arr, int left, int mid, int right) {
        // 两序列合并后的辅助数组
        int[] help = new int[right - left + 1];
        int i = 0;
        // 左序列当前指针
        int p1 = left;
        // 右序列当前指针
        int p2 = mid + 1;
        // 记录结果
        int res = 0;

        while (p1 <= mid && p2 <= right) {
            // 若左边序列当前指针的值比右序列当前指针的值小，则前者进入辅助数组，反之则反
            if (arr[p1] < arr[p2]) {
                // 右边序列所有元素都比 p1 指针的元素大，所以都需+1（用乘法实现）
                res += (right - p2 + 1) * arr[p1];
                help[i++] = arr[p1++];
            } else {
                help[i++] = arr[p2++];
            }
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

        return res;
    }

    /**
     * 对数器
     * @param arr
     * @return
     */
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
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
