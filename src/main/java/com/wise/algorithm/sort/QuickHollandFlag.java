package com.wise.algorithm.sort;

/**
 * 快速排序使用示例：荷兰国旗问题，给定一个数组arr和一个数num，请将小于num的数放在数组的左边，
 *                 等于num的数放在数组的中间，大于num的数放在数组的右边，并返回等于num区域的索引区间
 * @author lingyuwang
 * @date 2019-07-13 10:59
 */
public class QuickHollandFlag {

    public static void main(String[] args) {
        // 生成数组
        int[] test = generateArray();

        printArray(test);
        // num 必须在数组中
        int[] res = partition(test, 0, test.length - 1, 1);
        printArray(test);

        // 返回等于区域的索引区间
        System.out.println(res[0]);
        System.out.println(res[1]);
    }

    /**
     * 分区
     * @param arr
     * @param left
     * @param right
     * @param num
     * @return
     */
    public static int[] partition(int[] arr, int left, int right, int num) {
        // 分区左指针
        int pLeft = left - 1;
        // 分区右指针
        int pRight = right + 1;
        // 当前指针
        int current = left;
        while (current < pRight) {
            // current 应进入左分区（小于num）
            if (arr[current] < num) {
                pLeft++;
                swap(arr, pLeft, current);
                current++;
            }
            // current 应进入右分区（大于num）
            else if (arr[current] > num) {
                pRight--;
                swap(arr, pRight, current);
            }
            // current 应进入中间分区
            else {
                current++;
            }
        }
        return new int[] { pLeft + 1, pRight - 1 };
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
     * 生成数组
     * @return
     */
    public static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
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
