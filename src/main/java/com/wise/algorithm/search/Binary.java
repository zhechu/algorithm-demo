package com.wise.algorithm.search;

/**
 * 二分查找
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class Binary {

    public static void main(String[] args) {
//        int[] arr = {1, 2, 2, 3, 3, 4, 5, 6};
        int[] arr = {1, 2, 3, 4, 6, 6, 6, 7, 8, 9, 0};

//        int target = 3;
        int target = 6;
        int targetIndex = searchFirst(arr, target);
        System.out.println("第一个值等于给定值的索引：" + targetIndex);
        // 2

        targetIndex = searchFirstForOptimize(arr, target);
        System.out.println("第一个值等于给定值的索引：" + targetIndex);
        // 2
    }

    /**
     * 查找第一个值等于给定值的元素
     * @param arr
     * @param value
     * @return
     */
    public static int searchFirst(int[] arr, int value) {
        // 数组长度
        int len = arr.length;

        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (arr[mid] > value) {
                high = mid - 1;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (arr[mid - 1] != value)) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 查找第一个值等于给定值的元素（优化）
     * @param arr
     * @param value
     * @return
     */
    public static int searchFirstForOptimize(int[] arr, int value) {
        // 数组长度
        int len = arr.length;

        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            // 若目标值在右边，则指针不断往左移
            if (arr[mid] >= value) {
                high = mid - 1;
            }
            // 若刚好错过目标值，已跑到左边去检索，在后面的检索中指针也会移回来
            else {
                low = mid + 1;
            }
        }

        // 检查
        if (low < len && arr[low] == value) {
            return low;
        }

        return -1;
    }

}
