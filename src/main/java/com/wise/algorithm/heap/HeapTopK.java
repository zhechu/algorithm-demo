package com.wise.algorithm.heap;


public class HeapTopK {

    public static void main(String[] args) {
        HeapTopK heapTopK = new HeapTopK(4);

        int[] nums = {3, 1, 6, 12, 9, 10, 2};
        int[] res = heapTopK.getTopK(nums);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + ", ");
        }
    }

    /** 堆的边界，Top K 问题中的 K */
    private final int boundary;

    /** TopK 数据 */
    private final int[] top;

    public HeapTopK(int boundary) {
        this.boundary = boundary;
        top = new int[boundary];
    }

    /**
     * 求解数据流中的 top K
     * @param array 原始数据
     * @return top K 结果
     */
    public int[] getTopK(int[] array) {
        for (int i = 0; i < boundary; i++) {
            top[i] = array[i];
        }

        // 堆化
        buildHeap(array);

        int length = array.length;
        for (int j = boundary; j < length; j++) {
            add(array[j]);
        }

        return top;
    }

    /**
     * 自上往下堆化
     * @param array
     * @param index
     * @param length
     */
    private void heapify(int[] array, int index, int length) {
        // 左节点指针
        int left = (index << 1) + 1;
        // 右节点指针
        int right = (index << 1) + 2;
        // 最小节点指针
        int least = index;

        // 检查左节点是否小于父节点
        if (left < length && array[left] < array[least]) {
            least = left;
        }

        // 检查右节点是否小于最小节点
        if (right < length && array[right] < array[least]) {
            least = right;
        }

        // 若最小节点不是父节点，则交换并继续调整
        if (index != least) {
            swap(array, least, index);
            // 继续向下调整
            heapify(array, least, length);
        }
    }

    /**
     * 两数交换
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        if (i == j) {
            return;
        }

        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }

    /**
     * 建立小根堆
     * @param array
     */
    private void buildHeap(int[] array) {
        int length = array.length;
        // 自最后一个非叶子节点开始堆化
        for (int i = (length - 2) >> 1; i >= 0; i--) {
            heapify(array, i, length);
        }
    }

    /**
     * 向有界堆中添加元素的帮助方法
     * @param t 待添加数据
     */
    private void add(int t) {
        if (t > top[0]) {
            top[0] = t;
            heapify(top, 0, boundary);
        }
    }

}