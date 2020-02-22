package com.wise.algorithm;

/**
 * 位运算
 * @author lingyuwang
 * @date 2019-07-13 9:58
 */
public class BitOperation {

    public static void main(String[] args){
        int a = 2;
        int b = 5;
//        sumAndDivide2(a, b);
        sumAndDivide2Enhance(a, b);
    }

    /**
     * 求和再除以2：先加后移
     * @param a
     * @param b
     */
    public static void sumAndDivide2(int a, int b) {
        int result = (a + b) >> 1;
        System.out.println(result); // 3
    }

    /**
     * 求和再除以2：先减后移再减，安全，保证不会溢出
     * @param a
     * @param b
     */
    public static void sumAndDivide2Enhance(int a, int b) {
        int result = a + ((b - a) >> 1);
        System.out.println(result); // 3
    }

}
