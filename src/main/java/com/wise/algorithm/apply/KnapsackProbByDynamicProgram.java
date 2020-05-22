package com.wise.algorithm.apply;

/**
 * 求解0-1背包问题（动态规划）
 *
 * 问题：
 * 假设购物车中有 n 个（n>100）想买的商品，要求从里面选几个，
 * 在凑够满减条件的前提下，让选出来的商品价格总和最大程度地接
 * 近满减条件（200 元），这样就可以极大限度地“薅羊毛”
 *
 * @author lingyuwang
 * @date 2020-05-15 20:41
 * @since 1.0.4
 */
public class KnapsackProbByDynamicProgram {

    public static void main(String[] args) {
        // 每个商品的价格
        int[] itmes = new int[]{120, 215, 140, 310, 65, 80, 35};
        // 满减价格
        int weight = 200;

        knapsack(itmes, weight);
    }

    /**
     * 求解
     * @param items 每个商品的价格
     * @param weight 满减价格
     */
    public static void knapsack(int[] items, int weight) {
        int len = items.length;
        // 可承受价格上限
        int maxWeight = weight * 3;

        boolean[][] states = new boolean[len][maxWeight + 1];
        // 第一行的数据要特殊处理
        states[0][0] = true;
        if (items[0] <= maxWeight) {
            states[0][items[0]] = true;
        }

        // 动态规划
        for (int i = 1; i < len; ++i) {
            // 不购买第i个商品（记录所有可能的组合值）
            for (int j = 0; j <= maxWeight; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j] = states[i - 1][j];
                }
            }

            // 购买第i个商品（记录所有可能的组合值）
            for (int j = 0; j <= maxWeight - items[i]; ++j) {
                if (states[i - 1][j] == true) {
                    states[i][j + items[i]] = true;
                }
            }
        }

        int j;
        for (j = weight; j < maxWeight + 1; ++j) {
            // 输出结果大于等于 weight 的最小值
            if (states[len - 1][j] == true) {
                break;
            }
        }

        // 没有可行解
        if (j == maxWeight + 1) {
            return;
        }

        // i表示二维数组中的行，j表示列（从 states 状态数组推断）
        for (int i = len - 1; i >= 1; --i) {
            // j - items[i] >= 0 是为了防止数组越界
            if (j - items[i] >= 0 && states[i - 1][j - items[i]] == true) {
                // 购买这个商品
                System.out.print(items[i] + " ");
                j = j - items[i];
            } // else 没有购买这个商品，j不变
        }

        if (j != 0) {
            System.out.print(items[0]);
        }
    }

}

