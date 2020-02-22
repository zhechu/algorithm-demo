package com.wise.algorithm.sort;

/**
 * 选择排序
 * @author lingyuwang
 * @date 2019-07-13 10:46
 */
public class Select {

    public static void main(String[] args) {
        // 选择排序
        int[] array = {49,38,65,97,76,13,27,49};
        selectionSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void selectionSort(int[] array) {
        int minIndex = 0;
        int len = array.length;
        for (int i = 0; i < len - 1; i++) {
            // 无序区的最小数据数组下标
            minIndex = i;
            for (int j = i + 1; j < len; j++) {
                // 在无序区中找到最小数据并保存其数组下标
                if (array[j] < array[minIndex]) minIndex = j;
            }

            // 若最小数不在正确位置，则交换
            if (minIndex != i) {
                array[i] = array[i] ^ array[minIndex];
                array[minIndex] = array[i] ^ array[minIndex];
                array[i] = array[i] ^ array[minIndex];
            }
        }
    }

}
