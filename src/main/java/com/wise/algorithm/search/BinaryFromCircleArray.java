package com.wise.algorithm.search;

/**
 * 循环有序数组二分查找
 * @author lingyuwang
 * @date 2019-07-13 11:01
 */
public class BinaryFromCircleArray {

    public static void main(String[] args) {
        int[] arr = {8, 9, 10, 11, 12, 13, 3, 4, 5, 6};

        int target = 3;
        int targetIndex = search(arr, target);
        System.out.println("目标索引：" + targetIndex);
        // 6

        target = 8;
        targetIndex = search(arr, target);
        System.out.println("目标索引：" + targetIndex);
        // 0

        target = 6;
        targetIndex = search(arr, target);
        System.out.println("目标索引：" + targetIndex);
        // 9
    }

    /**
     * 循环有序数组二分查找
     * @param arr
     * @param value
     * @return
     */
    public static int search(int[] arr, int value) {
        // 数组长度
        int len = arr.length;

        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (arr[mid] == value) {
                return mid;
            }

            // 转折点在右边
            if (arr[mid] > arr[low]) {
                if (arr[low] == value) {
                    return low;
                }
                if (arr[low] < value && value < arr[mid]) {
                    high = mid - 1;
                    // low 已被检查过，不需再次检查
                    low++;
                } else {
                    low = mid + 1;
                }
            }
            // 转折点在左边
            else {
                if (arr[high] == value) {
                    return high;
                }
                if (arr[mid] < value && value < arr[high]) {
                    low = mid + 1;
                    // hight 已被检查过，不需再次检查
                    high--;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

}
