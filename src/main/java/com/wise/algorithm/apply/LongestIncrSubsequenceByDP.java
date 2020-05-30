package com.wise.algorithm.apply;

import java.util.LinkedList;

/**
 * 动态规划求最长递增子序列
 *
 * @author lingyuwang
 * @date 2020-05-30 19:28
 * @since 1.0.4
 */
public class LongestIncrSubsequenceByDP {

    public static void main(String[] args) {
        int[] sequence = {2, 9, 3, 6, 5, 1, 7};

        LinkedList<Integer> longestSubsequenceList = dp(sequence);

        System.out.println("最长递增子序列长度：" + longestSubsequenceList.size());
        System.out.println("最长递增子序列：" + longestSubsequenceList);
    }

    /**
     * 状态转移方程法
     * @param sequence 原序列
     * @return 最长递增子序列
     */
    public static LinkedList<Integer> dp(int[] sequence) {
        int len = sequence.length;
        int[] mem = new int[len];

        // 回溯加备忘录
        for (int i = 0; i < len; i++) {
            for (int j = i; j >= 0; j--) {
                rdp(i, j, sequence, mem);
            }
        }

        // 获取最长递增子序列备忘录索引（不包含本身节点）
        int index = 0;
        for (int i = 1; i < len; i++) {
            if (mem[i] > mem[index]) {
                index = i;
            }
        }

        // 推断最长子序列
        LinkedList<Integer> longestSubsequenceList = new LinkedList<>();
        longestSubsequenceList.addFirst(sequence[index]);
        int tempSequence = sequence[index];
        int tempLen = mem[index] - 1;
        for (int k = index - 1; k >=0; k--) {
            if (sequence[k] <= tempSequence && mem[k] == tempLen) {
                tempSequence = sequence[k];
                tempLen = mem[k] - 1;
                longestSubsequenceList.addFirst(sequence[k]);
            }
        }

        return longestSubsequenceList;
    }

    /**
     * 递归调用
     * @param t 目标索引
     * @param i 回溯索引
     * @param sequence 原序列
     * @param mem 备忘录
     * @return 以 t 为参照节点，返回 0 到 i 的递增子序列的长度
     */
    public static int rdp(int t, int i, int[] sequence, int[] mem) {
        if (i == 0) {
            return 0;
        }

        int result;
        if (sequence[i - 1] <= sequence[t]) {
            if (mem[i - 1] > 0) {
                result = mem[i - 1] + 1;
            } else {
                result = rdp(i - 1, i - 1, sequence, mem) + 1;
            }
        } else {
            result = rdp(t,i - 1, sequence, mem);
        }

        // 记录最长序列长度
        if (result > mem[t]) {
            mem[t] = result;
        }

        return result;
    }

}
