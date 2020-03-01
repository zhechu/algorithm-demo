package com.wise.algorithm.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * 求解简单的加减乘除四则运算表达式
 * 1.从左向右遍历表达式
 * 2.遇到数字，直接压入操作数栈
 * 3.遇到运算符，则与运算符栈的栈顶元素进行比较
 * 4.若比运算符栈顶元素的优先级高，则将当前运算符压入栈
 * 5.若比运算符栈顶元素的优先级低或者相等，则从运算符栈中取栈顶运算符，从操作数栈的栈顶取 2 个操作数
 * 6.将取出的操作数和运算符结合进行计算，再把计算完的结果压入操作数栈，然后将还未入栈的运算符回到第3步执行
 * 7.最后计算还在栈中的操作数即可
 */
public class StackExpression {
	
	/**
	 * 运算符之间的优先级,其顺序是 #、+、-、*、/，其中大于号表示优先级高，
	 * 小于号表示优先级低，等号表示优先级相同。
	 * #不参与运算，用作哨兵，简化编程
	 */
	public static final char[][] relation = {
			{'=','<','<','<','<'},
			{'>','=','=','<','<'},
			{'>','=','=','<','<'},
			{'>','>','>','=','='},
			{'>','>','>','=','='}
	};

	/** 连续几个字符是否是数字，用于支持多位数运算 */
	public static boolean flag = false;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		while (true) {
			flag = false;
			System.out.println("请输入要计算的表达式：");
			try {
				String exp = input.next();
				System.out.println(calc(exp));
			} catch(ArithmeticException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析并运算
	 * @param exp
	 * @return
	 */
	private static int calc(String exp) {
		// 操作数栈
		Stack<Integer> num = new Stack<>();
		// 操作符栈
		Stack<Character> op = new Stack<>();
		// 用作哨兵，简化编程
		op.push('#');

		int len = exp.length();
		for (int i = 0; i < len; i++) {
			calcDetail(num, op, exp.charAt(i));
		}

		// 最后一个运算符运算
		calcAndPushNum(num, op);

		return num.peek();
	}

	public static void calcDetail(Stack<Integer> num, Stack<Character> op, char ch) {
		// 操作数
		if (ch >= '0' && ch <= '9') {
			if (flag) {
				int tmp = num.pop();
				num.push(tmp * 10 + Integer.valueOf(String.valueOf(ch)));
			} else {
				num.push(Integer.valueOf(String.valueOf(ch)));
			}
			flag = true;
		}
		// 运算符
		else {
			flag = false;
			switch (getRelation(ch, op.peek())) {
				// 当前运算符比栈顶运算符优先级低
				case '<':
					calcAndPushNum(num, op);
					// 递归回溯计算
					calcDetail(num, op, ch);
					break;
				// 当前运算符与栈顶运算符优先级相等
				case '=':
					calcAndPushNum(num, op);
					// 递归回溯计算
					calcDetail(num, op, ch);
					break;
				// 当前运算符比栈顶运算符优先级高
				case '>':
					op.push(ch);
					break;
				default:
					throw new ArithmeticException("输入的表达式格式错误");
			}
		}
	}

	/**
	 * 计算和计算结果入栈
	 * @param num
	 * @param op
	 */
	private static void calcAndPushNum(Stack<Integer> num, Stack<Character> op) {
		int num2 = num.pop();
		int num1 = num.pop();
		num.push(operate(num1, op.pop(), num2));
	}

	/**
	 * 获取运算符优先关系
	 * @param ch1 运算符符1
	 * @param ch2 运算符符2
	 * @return
	 */
	private static char getRelation(char ch1, char ch2) {
		return relation[getIndex(ch1)][getIndex(ch2)];
	}

	/**
	 * 获取运算符索引
	 * @param ch
	 * @return
	 */
	private static int getIndex(char ch) {
		int index = -1;
		switch (ch) {
		case '#':
			index = 0;
			break;
		case '+':
			index = 1;
			break;
		case '-':
			index = 2;
			break;
		case '*':
			index = 3;
			break;
		case '/':
			index = 4;
			break;
		}
		return index;
	}

	/**
	 * 运算
	 * @param num1 操作数1
	 * @param ch 运算符
	 * @param num2 操作数2
	 * @return
	 */
	private static int operate(int num1, char ch, int num2) {
		int result = 0;
		switch (ch) {
		case '+':
			result = num1 + num2;
			break;
		case '-':
			result = num1 - num2;
			break;
		case '*':
			result = num1 * num2;
			break;
		case '/':
			result = num1 / num2;
			break;
		}
		return result;
	}

}