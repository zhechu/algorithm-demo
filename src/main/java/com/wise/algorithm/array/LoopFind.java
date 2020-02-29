package com.wise.algorithm.array;

/**
 * 利用哨兵优化循环判断
 *
 **/
public class LoopFind {

    public static void main(String[] args) {
        int[] array = {4, 2, 3, 5, 9, 6};

        int result1 = find(array, 9);
        System.out.println(result1); // 4

        int result2 = find(array, 3);
        System.out.println(result2); // 2

        System.out.println("=======================================");

        int result3 = findByGuard(array, 9);
        System.out.println(result3); // 4

        int result4 = findByGuard(array, 3);
        System.out.println(result4); // 2

    }

    /**
     * 在数组array中，查找key，返回key所在的位置
     * @param array
     * @param key
     * @return
     */
    public static int find(int[] array, int key) {
        int len;
        // 边界条件处理，如果array为空，或者len<=0，说明数组中没有数据，就不用while循环比较了
        if (array == null || (len = array.length) <= 0) {
            return -1;
        }

        int i = 0;
        // 这里有两个比较操作：i<len 和 array[i]==key.
        while (i < len) {
            if (array[i] == key) {
                return i;
            }
            ++i;
        }

        return -1;
    }

    /**
     * 在数组array中，查找key，返回key所在的位置（使用哨兵优化）
     * @param array
     * @param key
     * @return
     */
    public static int findByGuard(int[] array, int key) {
        int len;
        // 边界条件处理，如果array为空，或者len<=0，说明数组中没有数据，就不用while循环比较了
        if (array == null || (len = array.length) <= 0) {
            return -1;
        }

        // 这里因为要将array[len-1]的值替换成key，所以要特殊处理这个值
        if (array[len-1] == key) {
            return len - 1;
        }

        // 把array[len-1]的值临时保存在变量tmp中，以便之后恢复。tmp=6。
        // 之所以这样做的目的是：希望findByGuard()代码不要改变array数组中的内容
        int tmp = array[len - 1];
        // 把key的值放到array[len-1]中
        array[len - 1] = key;

        int i = 0;
        // while 循环比起代码一，少了i<len这个比较操作
        while (array[i] != key) {
            ++i;
        }

        // 恢复array[len-1]原来的值
        array[len-1] = tmp;

        if (i == len - 1) {
            // 如果i == len-1说明，在0...len-2之间都没有key，所以返回-1
            return -1;
        }

        // 否则，返回i，就是等于key值的元素的下标
        return i;
    }

}
