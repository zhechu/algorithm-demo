package com.wise.algorithm.strmatch;

/**
 * KMP 算法实现字符串匹配
 */
public class Kmp {

    public static void main(String[] args) {
        // 主串
        String a = "ababaeabac";
        // 模式串
        String b = "abac";

        // 获取模式串匹配主串的第一个索引
        int result = kmp(a.toCharArray(), b.toCharArray());
        System.out.println(result);
        // 6
    }

    /**
     * a, b分别是主串和模式串
     * @param a
     * @param b
     * @return
     */
    public static int kmp(char[] a, char[] b) {
        // 主串长度
        int n = a.length;
        // 模式串长度
        int m = b.length;

        // 计算 next 数组（存储模式串中每个前缀的最长可匹配前缀子串的结尾字符下标）
        int[] next = getNexts(b, m);
        int j = 0;
        for (int i = 0; i < n; ++i) {
            // 一直找到 a[i] 和 b[j]
            while (j > 0 && a[i] != b[j]) {
                j = next[j - 1] + 1;
            }

            if (a[i] == b[j]) {
                ++j;
            }

            // 找到匹配模式串的了
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    /**
     * b表示模式串，m表示模式串的长度
     * @param b
     * @param m
     * @return
     */
    private static int[] getNexts(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                k = next[k];
            }

            if (b[k + 1] == b[i]) {
                ++k;
            }

            next[i] = k;
        }
        return next;
    }

}