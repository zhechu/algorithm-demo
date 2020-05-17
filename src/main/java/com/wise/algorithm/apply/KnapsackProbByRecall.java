package com.wise.algorithm.apply;

import java.util.Arrays;

/**
 * 求解0-1背包问题（回溯）
 *
 * @author lingyuwang
 * @date 2020-05-15 20:41
 * @since 1.0.4
 */
public class KnapsackProbByRecall {

    public static void main(String[] args) {
        // 每个物品的重量
        int[] itmes = new int[]{20, 15, 40, 10, 65, 80, 35};
        // 背包可承受重量
        int weight = 100;

        ResultDTO resultDTO = getResult(itmes, weight);
        System.out.println(resultDTO);
    }

    /**
     * 获取能装入背包的物品结果
     * @param items 每个物品的重量
     * @param weight 背包可承受重量
     * @return
     */
    public static ResultDTO getResult(int[] items, int weight) {
        int len = items.length;
        ResultDTO resultDTO = new ResultDTO(len);

        // 临时解
        int[] tempAnswer = new int[len];

        // 备忘录
        boolean[][] mem = new boolean[len][weight];

        cal(resultDTO, tempAnswer, mem, 0,0, items, len, weight);

        return resultDTO;
    }

    /**
     *
     * @param resultDTO 结果
     * @param i 物品索引
     * @param cw 当前已经装进去的物品的重量和
     * @param items 每个物品的重量
     * @param n 物品个数
     * @param w 背包重量
     */
    public static void cal(ResultDTO resultDTO, int[] tempAnswer, boolean[][] mem, int i, int cw, int[] items, int n, int w) {
        // cw==w 表示装满了; i==n 表示已经考察完所有的物品
        if (cw == w || i == n) {
            // 打印当前方案
            print(items, tempAnswer);

            if (cw > resultDTO.maxW) {
                resultDTO.maxW = cw;

                for (int j = 0; j < n; j++) {
                    resultDTO.bestAnswer[j] = tempAnswer[j];
                }
            }
            return;
        }

        // 重复状态
        if (mem[i][cw]) {
            return;
        }
        // 记录(i, cw)这个状态
        mem[i][cw] = true;

        // 回溯（回溯法的关键步骤，先调用，后计算）
        cal(resultDTO, tempAnswer, mem, i + 1, cw, items, n, w);

        // 未超过背包可以承受的重量才继续装
        if (cw + items[i] <= w) {
            // 装入背包的物品设置为 1
            tempAnswer[i] = 1;
            cal(resultDTO, tempAnswer, mem, i + 1,cw + items[i], items, n, w);
        }

        // 清除已有状态，重新回到未设置状态
        tempAnswer[i] = 0;
    }

    private static void print(int[] items, int[] bestAnswer) {
        int bestPrice = 0;
        System.out.print("路径为：");
        for (int i = 0; i < bestAnswer.length; i++) {
            bestPrice += bestAnswer[i] == 1 ? items[i] : 0;
            System.out.print(bestAnswer[i]);
        }
        System.out.println(" 价值为" + bestPrice);
    }

}

/**
 * 结果
 */
class ResultDTO {
    /** 存储背包中物品总重量的最大值 */
    public int maxW = Integer.MIN_VALUE;

    /** 最优解 */
    public int[] bestAnswer;

    public ResultDTO(int length) {
        this.bestAnswer = new int[length];
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "maxW=" + maxW +
                ", bestAnswer=" + Arrays.toString(bestAnswer) +
                '}';
    }
}
