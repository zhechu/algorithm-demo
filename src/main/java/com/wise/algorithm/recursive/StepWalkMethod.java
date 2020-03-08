package com.wise.algorithm.recursive;

import java.util.HashMap;
import java.util.Map;

/**
 * 有 n 个台阶，每次可以跨 1 个台阶或者 2 个台阶，计算其有多少种走法
 * 递推公式：f(n) = f(n-1) + f(n-2)
 * 终止条件：f(1) = 1, f(2) = 2
 */
public class StepWalkMethod {

    public static void main(String[] args) {
        int n = 7;
        int result = method(n);
        System.out.println(n + "个台阶一共有" + result + "种走法");

        result = optimizeMethod(n, new HashMap<>());
        System.out.println(n + "个台阶一共有" + result + "种走法");
        // 21

        result = nonRecursiveMethod(n);
        System.out.println(n + "个台阶一共有" + result + "种走法");
        // 21
    }

    /**
     * 计算台阶走法
     * @param n
     * @return
     */
    public static int method(int n) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        return method(n - 1) + method(n -2);
    }

    /**
     * 计算台阶走法（缓存已求解的子结果）
     * @param n
     * @return
     */
    public static int optimizeMethod(int n, Map<Integer, Integer> cacheMap) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        Integer resultTemp = cacheMap.get(n);
        if (resultTemp != null) {
            return resultTemp;
        }

        int result = method(n - 1) + method(n -2);

        cacheMap.put(n, result);

        return result;
    }

    /**
     * 计算台阶走法（非递归）
     * @param n
     * @return
     */
    public static int nonRecursiveMethod(int n) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        int result = 0;
        int pre = 2;
        int prepre = 1;

        for (int i = 3; i <= n; i++) {
            result = pre + prepre;
            prepre = pre;
            pre = result;
        }

        return result;
    }

}
