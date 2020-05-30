package com.wise.algorithm.apply;

import java.util.LinkedList;

/**
 *
 * @author lingyuwang
 * @date 2020-05-30 19:28
 * @since 1.0.4
 */
public class LongestIncrSubsequenceByDP {

    public static void main(String[] args) {
        int[] sequence = {2, 9, 3, 6, 5, 1, 7};

        dp(sequence);
    }

    public static void dp(int[] sequence) {
        int len = sequence.length;
        int[] mem = new int[len];

        // 回溯加备忘录
        for (int i = 0; i < len; i++) {
            for (int j = i; j >= 0; j--) {
                rdp(i, j, sequence, mem);
            }
        }

        // 获取最长递增子序列（不包含本身节点）
        int index = 0;
        for (int i = 1; i < len; i++) {
            if (mem[i] > mem[index]) {
                index = i;
            }
        }

        System.out.println("最长递增子序列长度：" + (mem[index] + 1));

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

        System.out.println("最长递增子序列：" + longestSubsequenceList);
    }

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
