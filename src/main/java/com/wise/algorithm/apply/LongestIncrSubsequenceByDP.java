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

        for (int i = 0; i < len; i++) {
            rdp(i, i, sequence, mem);
        }

        // 获取最长递增子序列
        int index = 0;
        for (int i = 1; i < len; i++) {
            if (mem[i] > mem[index]) {
                index = i;
            }
        }

        System.out.println("最长递增子序列长度：" + mem[index]);
    }

    public static int rdp(int t, int i, int[] sequence, int[] mem) {
        if (i == 0) {
            return 0;
        }

        int result;
        if (sequence[t] >= sequence[i - 1]) {
            if (mem[i - 1] > 0) {
                result = mem[i - 1] + 1;
            } else {
                result = rdp(i - 1, i - 1, sequence, mem) + 1;
            }
        } else {
            result = rdp(t,i - 1, sequence, mem);
        }
        mem[t] = result;

        return result;
    }

    /**
     * 结果
     */
    static class ResultDTO {
        /** 最长子序列长度 */
        public int longestLength = Integer.MIN_VALUE;

        /** 最优解序列 */
        public LinkedList<Point> longestSubsequenceList = new LinkedList<>();

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "longestLength=" + longestLength +
                    ", longestSubsequenceList=" + longestSubsequenceList +
                    '}';
        }
    }

}
