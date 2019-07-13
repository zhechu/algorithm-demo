package com.wise.algorithm;

/**
 * 欧几里得算法（辗转相除法）：求两个正整数最大公约数
 * @author lingyuwang
 * @date 2019-07-13 9:53
 */
public class Euclid {

    public static void main(String[] args) {
        long result = divisor(123, 321);
        System.out.println(result); // 3
    }

    public static long divisor(long m, long n) {
        while (n != 0) {
            m = m % n;
            m = m ^ n;
            n = n ^ m;
            m = n ^ m;
        }

        return m;
    }

}
