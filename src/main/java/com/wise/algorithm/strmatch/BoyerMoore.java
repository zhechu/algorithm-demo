package com.wise.algorithm.strmatch;

/**
 * BM 算法实现字符串匹配
 */
public class BoyerMoore {

    public static void main(String[] args) {
        // 主串
        String a = "ababaeabac";
        // 模式串
        String b = "abac";

        // 获取模式串匹配主串的第一个索引
        int result = bm(a.toCharArray(), b.toCharArray());
        System.out.println(result);
        // 6

        // 等价于
        result = a.indexOf(b);
        System.out.println(result);
        // 6
    }

    /**
     * BM 算法
     * @param a 主串
     * @param b 模式串
     * @return
     */
    public static int bm(char[] a, char[] b) {
        // 主串长度
        int n = a.length;
        // 模式串长度
        int m = b.length;

        // 构建坏字符哈希表
        int[] bc = generateBC(b);

        // 计算 suffix 和 prefix
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        generateGS(b, suffix, prefix);

        // 表示主串与模式串对齐的第一个字符的下标
        int i = 0;
        while (i <= n - m) {
            int j;

            // 模式串从后往前匹配
            for (j = m - 1; j >= 0; --j) {
                if (a[i + j] != b[j]) {
                    // 坏字符对应模式串中的下标是 j
                    break;
                }
            }

            // 匹配成功，返回主串与模式串匹配的第一个字符的下标
            if (j < 0) {
                return i;
            }

            int x = j - bc[a[i + j]];
            int y = 0;

            // 若存在好后缀
            if (j < m-1) {
                y = moveByGS(j, m, suffix, prefix);
            }

            // 往后滑动的位数
            i = i + Math.max(x, y);
        }

        return -1;
    }

    /**
     * 获取模式串中的字符对应下标的散列表（坏字符哈希表）
     * @param b 模式串
     * @return
     */
    private static int[] generateBC(char[] b) {
        // ASCII 字符数
        int size = 256;
        int[] bc = new int[size];

        // 初始化bc
        for (int i = 0; i < size; ++i) {
            bc[i] = -1;
        }

        int m = b.length;
        for (int i = 0; i < m; ++i) {
            // 记录字符在模式串的下标
            // 若在模式串有相同的字符，则后面的会覆盖前面的字符下标
            // b[i] 为对应字符串的 ASCII 值
            bc[b[i]] = i;
        }

        return bc;
    }

    /**
     * 计算 suffix 和 prefix
     * @param b 模式串
     * @param suffix
     * @param prefix
     */
    private static void generateGS(char[] b, int[] suffix, boolean[] prefix) {
        // 模式串长度
        int m = b.length;

        // 初始化
        for (int i = 0; i < m; ++i) {
            suffix[i] = -1;
            prefix[i] = false;
        }

        for (int i = 0; i < m - 1; ++i) {
            int j = i;

            // 公共后缀子串长度
            int k = 0;
            // 与 b[0, m-1] 求公共后缀子串
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                suffix[++k] = --j + 1;
            }

            // 公共后缀子串也是模式串的前缀子串
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }

    /**
     * 计算移动长度
     * @param j 坏字符对应的模式串中的字符下标
     * @param m 模式串长度
     * @param suffix
     * @param prefix
     * @return
     */
    private static int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // 好后缀长度
        int k = m - 1 - j;

        // 存在好后缀匹配子串
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }

        // 在好后缀的后缀子串中查找最长的能跟模式串前缀子串匹配的后缀子串
        for (int r = j + 2; r <= m - 1; r++) {
            if (prefix[m - r] == true) {
                return r;
            }
        }

        return m;
    }

}