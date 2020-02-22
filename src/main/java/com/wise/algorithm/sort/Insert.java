package com.wise.algorithm.sort;

/**
 * 插入排序
 * @author lingyuwang
 * @date 2019-07-13 10:47
 */
public class Insert {

    public static void main(String[] args) {
        // 直接插入排序
        int[] array = {49,38,65,97,76,13,27,49};
        array = insertionSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static int[] insertionSort(int[] array){
        int len = array.length;
        // 假设第1个元素已排好序，从第2个元素开始
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    array[j - 1] = array[j - 1] ^ array[j];
                    array[j] = array[j - 1] ^ array[j];
                    array[j - 1] = array[j - 1] ^ array[j];
                } else {
                    break;
                }
            }
        }
        return array;
    }

}
