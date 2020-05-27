package com.wise.algorithm.apply;

import java.util.LinkedList;

/**
 * 问题：
 * 假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上角，终止位置在右下角。
 * 我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位。从左上角到右下角，会有很多不同的路径可以
 * 走。我们把每条路径经过的数字加起来看作路径的长度。那从左上角移动到右下角的最短路径长度是多少？
 *
 * 方案：
 * 状态转移表法
 *
 * @author lingyuwang
 * @date 2020-05-26 20:47
 * @since 1.0.4
 */
public class MatrixMinDistDP {

    public static void main(String[] args) {
        int[][] w = {
                {1, 3, 5, 9},
                {2, 1, 3, 4},
                {5, 2, 6, 7},
                {6, 8, 4, 3}
        };

        ResultDTO resultDTO = minDistDP(w);

        System.out.println(resultDTO);
    }

    /**
     * 状态转移表法
     * @param matrix
     */
    public static ResultDTO minDistDP(int[][] matrix) {
        ResultDTO resultDTO = new ResultDTO();

        int len = matrix.length;

        int[][] states = new int[len][len];
        int sum = 0;

        // 初始化states的第一行数据
        for (int j = 0; j < len; ++j) {
            sum += matrix[0][j];
            states[0][j] = sum;
        }

        // 重置求和临时变量
        sum = 0;

        // 初始化states的第一列数据
        for (int i = 0; i < len; ++i) {
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        for (int i = 1; i < len; ++i) {
            for (int j = 1; j < len; ++j) {
                states[i][j] = matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }

        // 最小距离
        resultDTO.minDist = states[len - 1][len - 1];

        // 从 states 状态数组推断
        int i = len - 1;
        int j = len - 1;
        while (true) {
            resultDTO.minRouteList.addFirst(new Point(i, j));

            int tempDist = states[i][j] - matrix[i][j];
            // 已回溯完毕
            if (tempDist == 0) {
                break;
            }

            // 回溯到上一个节点
            if (tempDist == states[i - 1][j]) {
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
