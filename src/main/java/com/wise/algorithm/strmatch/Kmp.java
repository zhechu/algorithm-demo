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

        // 等价于
        result = a.indexOf(b);
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

        // 一个字符是不存在前缀和后缀的
        next[0] = -1;

        // k 表示 next 的值
        int k = -1;

        // 计算 next 值
        for (int i = 1; i < m; ++i) {
            // 此时的 k 表示上一个 next 的值

            // 若上一个 next 的值存在，则再检查是否有复用的可能
            while (k != -1 && b[k + 1] != b[i]) {
                // 取上一个 next 的值作为此次 next 的参考值
                k = next[k];
            }

            // 指针新字符检查是否匹配
            if (b[k + 1] == b[i]) {
                ++k;
            }

            // 最终得到 next 的值
            next[i] = k;
        }

        return next;
    }

}