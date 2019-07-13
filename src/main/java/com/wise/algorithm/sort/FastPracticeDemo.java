package com.wise.algorithm.sort;

/**
 * 快速排序使用示例：荷兰国旗问题，给定一个数组arr和一个数num，请将小于num的数放在数组的左边，
 *                 等于num的数放在数组的中间，大于num的数放在数组的右边，并返回等于num区域的索引区间
 * @author lingyuwang
 * @date 2019-07-13 10:59
 */
public class FastPracticeDemo {

    public static void main(String[] args) {
        // 荷兰国旗问题
        int[] test = generateArray();

        printArray(test);
        int[] res = partition(test, 0, test.length - 1, 1);
        printArray(test);
        // 返回等于区域的索引区间
        System.out.println(res[0]);
        System.out.println(res[1]);
    }

    public static int[] partition(int[] arr, int l, int r, int num) {
        int less = l - 1;
        int more = r + 1;
        int current = l;
        while (current < more) {
            if (arr[current] < num) {
                swap(arr, ++less, current++);
            } else if (arr[current] > num) {
                swap(arr, --more, current);
            } else {
                current++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    // for test
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
        }
        return arr;
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
