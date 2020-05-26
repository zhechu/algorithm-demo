package com.wise.algorithm.apply;

import java.util.LinkedList;

/**
 * 问题：
 * 假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。棋子起始位置在左上角，终止位置在右下角。
 * 我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位。从左上角到右下角，会有很多不同的路径可以
 * 走。我们把每条路径经过的数字加起来看作路径的长度。那从左上角移动到右下角的最短路径长度是多少？
 *
 * 方案：
 * 回溯法
 *
 * @author lingyuwang
 * @date 2020-05-26 20:47
 * @since 1.0.4
 */
public class MatrixMinDistBT {

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
        LinkedList<Point> routeList = new LinkedList<>();

        minDistBT(resultDTO, routeList, 0, 0, 0, w, w.length);

        return resultDTO;
    }

    /**
     * 回溯法
     * @param resultDTO
     * @param routeList
     * @param i
     * @param j
     * @param dist
     * @param w
     * @param n
     */
    public static void minDistBT(ResultDTO resultDTO, LinkedList<Point> routeList, int i, int j, int dist, int[][] w, int n) {
        // 到达了 n-1, n-1 这个位置
        if (i == (n - 1) && j == (n - 1)) {
            routeList.add(new Point(i, j));
            dist += w[i][j];

            if (dist < resultDTO.minDist) {
                resultDTO.minDist = dist;
                resultDTO.minRouteList.clear();
                resultDTO.minRouteList.addAll(routeList);
            }

            routeList.removeLast();
            return;
        }

        // 往下走，更新 i=i+1, j=j
        if (i < n - 1) {
            routeList.add(new Point(i, j));
            minDistBT(resultDTO, routeList,i + 1, j, dist + w[i][j], w, n);
            routeList.removeLast();
        }

        // 往右走，更新 i=i, j=j+1
        if (j < n - 1) {
            routeList.add(new Point(i, j));
            minDistBT(resultDTO, routeList, i,j + 1, dist + w[i][j], w, n);
            routeList.removeLast();
        }
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
