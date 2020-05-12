package com.wise.algorithm.apply;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 求解平面最接近点对
 *
 * @author lingyuwang
 * @date 2020-05-12 23:45
 * @since 1.0.4
 */
public class MinDis {
 
    public static void main(String[] args) {
        // 测试用例
        Point[] points = new Point[7];
 
        points[0] = new Point(1, 1);
        points[1] = new Point(1, 9);
        points[2] = new Point(2, 5);
        points[3] = new Point(3, 1);
        points[4] = new Point(4, 4);
        points[5] = new Point(5, 8);
        points[6] = new Point(6, 2);
 
        // 预处理，基于x轴坐标排序，便于实施分治法
        Arrays.sort(points, (p1, p2) -> (p1.x > p2.x) ? 1 : (p1.x == p2.x) ? 0 : -1);

        // 测试
        System.out.println(cal(points));
    }

    /**
     * 计算
     * @param points
     * @return
     */
    public static PointResult cal(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }

        return divide(0, points.length - 1, points);
    }

    /**
     * 求平面上距离最近的两个点
     */
    private static PointResult divide(int left, int right, Point[] points) {
        // 若只有一个点，则不存在最近两点距离，返回无穷大
        if (left == right) {
            return new PointResult(points[left], null, Double.MAX_VALUE);
        }

        // 若只有两个点，则直接求解
        if (left + 1 == right) {
            return new PointResult(points[left], points[right], distance(points[left], points[right]));
        }
 
        // 分治法：第一步：分区，并求取左右分区最小两点距离
        // 通过右移运算除2，对区域进行合理的划分，使得左右两边保持大致相等个数点
        int middle = (left + right) >> 1;

        PointResult leftPointResult = divide(left, middle, points);
        PointResult rightPointResult = divide(middle + 1, right, points);

        PointResult curMinPointResult = leftPointResult;
        if (rightPointResult.distance < leftPointResult.distance) {
            curMinPointResult = rightPointResult;
        }

        // 分治法：第二步：假设距离最近的两点分别在左右分区中，x轴搜索范围[middle - curMinDis, middle + curMinDis]
        curMinPointResult = getMinPointResult(left, middle, right, curMinPointResult, points);

        return curMinPointResult;
    }

    /**
     * 获取结果
     * @param left
     * @param middle
     * @param right
     * @param curMinPointResult
     * @param points
     * @return
     */
    private static PointResult getMinPointResult(int left, int middle, int right, PointResult curMinPointResult, Point[] points) {
        // 记录搜索区间内的点的索引，便于进一步计算最小距离
        List<Integer> validPointIndexList = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[middle].x - points[i].x) <= curMinPointResult.distance) {
                validPointIndexList.add(i);
            }
        }

        // 基于索引，进一步计算区间内最小两点距离
        int size = validPointIndexList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                // 若区间内的两点y轴距离大于curMinDis，则表明其距离肯定大于curMinDis
                if (Math.abs(points[validPointIndexList.get(i)].y - points[validPointIndexList.get(j)].y) > curMinPointResult.distance) {
                    continue;
                }

                Point pi = points[validPointIndexList.get(i)];
                Point pj = points[validPointIndexList.get(j)];
                double tempDis = distance(pi, pj);
                if (tempDis < curMinPointResult.distance) {
                    curMinPointResult = new PointResult(pi, pj, tempDis);
                }
            }
        }

        return curMinPointResult;
    }

    /**
     * 计算两点间的距离
     */
    private static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.y - p1.y, 2) + Math.pow(p2.x - p1.x, 2));
    }

}

/**
 * 定义点
 */
class Point {
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

/**
 * 结果
 */
class PointResult {
    public Point p1;
    public Point p2;
    public double distance;

    public PointResult(Point p1, Point p2, double distance) {
        this.p1 = p1;
        this.p2 = p2;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PointResult{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", distance=" + distance +
                '}';
    }
}