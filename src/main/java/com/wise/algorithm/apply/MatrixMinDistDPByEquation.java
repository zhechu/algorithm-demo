package com.wise.algorithm.apply;

import java.util.LinkedList;

/**
 * 问题：
 * 假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上角，终止位置在右下角。
 * 我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位。从左上角到右下角，会有很多不同的路径可以
 * 走。我们把每条路径经过的数字加起来看作路径的长度。那从左上角移动到右下角的最短路径长度是多少？
 *
 * 方案：
 * 状态转移方程法
 *
 * @author lingyuwang
 * @date 2020-05-26 20:47
 * @since 1.0.4
 */
public class MatrixMinDistDPByEquation {

    public static void main(String[] args) {
        int[][] w = {
                {1, 3, 5, 9},
                {2, 1, 3, 4},
                {5, 2, 6, 7},
                {6, 8, 4, 3}
        };

        ResultDTO resultDTO = minDist(w);

        System.out.println(resultDTO);
    }

    public static ResultDTO minDist(int[][] w) {
        ResultDTO resultDTO = new ResultDTO();

        int len = w.length;
        int[][] mem = new int[len][len];

        resultDTO.minDist = minDistDP(len - 1, len - 1, w, mem);

        // 从备忘录数组推断
        int i = len - 1;
        int j = len - 1;
        while (true) {
            resultDTO.minRouteList.addFirst(new Point(i, j));

            int tempDist = mem[i][j] - w[i][j];
            // 已回溯完毕
            if (tempDist == 0) {
                break;
            }

            // 回溯到上一个节点
            if (tempDist == mem[i - 1][j]) {
                i--;
            }
            // 回溯到左一个节点
            else {
                j--;
            }
        }

        return resultDTO;
    }

    /**
     * 状态转移方程法
     * @param i
     * @param j
     * @param matrix
     * @param mem
     * @return
     */
    public static int minDistDP(int i, int j, int[][] matrix, int[][] mem) {
        // 备忘录已有，不需重复计算
        if (mem[i][j] > 0) {
            return mem[i][j];
        }

        if (i == 0 && j == 0) {
            mem[i][j] = matrix[0][0];
            return matrix[0][0];
        }

        int minLeft = Integer.MAX_VALUE;
        if (j - 1 >= 0) {
            minLeft = minDistDP(i, j - 1, matrix, mem);
        }

        int minUp = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            minUp = minDistDP(i - 1, j, matrix, mem);
        }

        int currMinDist = matrix[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = currMinDist;

        return currMinDist;
    }

    /**
     * 结果
     */
    static class ResultDTO {
        /** 最小距离 */
        public int minDist = Integer.MAX_VALUE;

        /** 最优解路径 */
        public LinkedList<Point> minRouteList = new LinkedList<>();

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "minDist=" + minDist +
                    ", minRouteList=" + minRouteList +
                    '}';
        }
    }

    /**
     * 定义点
     */
    static class Point {
        public int x;
        public int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
