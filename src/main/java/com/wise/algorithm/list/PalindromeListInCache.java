package com.wise.algorithm.list;

import java.util.Stack;

/**
 * 判断链表是否为回文链表（不会改变原结构，时间复杂度O(n)，空间复杂度O(n)）
 *
 * 1.前半部分缓存在栈中
 * 2.分两指针以中间节点为中心分别向前（栈）和向后（原链表）移动
 * 3.依次比较两指针的节点是否相等，若有节点不等，则为非回文链
 */
public class PalindromeListInCache {

    public static void main(String[] args) {
        Node head = test1();
        System.out.println("是否为回文链表:" + isPalindrome(head)); // true
        printList(head);

        head = test2();
        System.out.println("是否为回文链表:" + isPalindrome(head)); // true
        printList(head);

        head = test3();
        System.out.println("是否为回文链表:" + isPalindrome(head)); // false
        printList(head);

        head = test4();
        System.out.println("是否为回文链表:" + isPalindrome(head)); // false
        printList(head);

        head = test5();
        System.out.println("是否为回文链表:" + isPalindrome(head)); // false
        printList(head);
    }

    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.data == head.next.data;
        }

        // 栈，用于存储队列前半部分节点
        Stack<Node> stack = new Stack<>();
        stack.push(head);

        // 慢指针
        Node slow = head.next;
        // 快指针
        Node fast = head.next.next;

        // 找到中间节点
        while (fast.next != null && fast.next.next != null) {
            stack.push(slow);
            slow = slow.next;
            fast = fast.next.next;
        }

        // 链表总节点数为偶数
        if (fast.next != null) {
            // 后半部分头指针
            fast = slow.next;
            // 前半部分头指针为当前 slow 指针
        }
        // 链表总节点数为奇数
        else {
            // 后半部分头指针
            fast = slow.next;
            // 前半部分头指针（此时原半部分队列已翻转）
            slow = stack.pop();
        }

        // 头尾两部分节点依次比较
        while (true) {
            if (slow.data != fast.data) {
                return false;
            }
            if (stack.empty()) {
                break;
            }
            slow = stack.pop();
            fast = fast.next;
        }

        return true;
    }

    /**
     * 节点
     */
    static class Node {

        /** 节点数据 */
        int data;

        /** 下一个节点指针 */
        Node next = null;

        Node(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    public static Node test1() {
        Node node7 = new Node(1);
        Node node6 = new Node(2);
        Node node5 = new Node(3);
        Node node4 = new Node(4);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);
        node6.setNext(node7);
        node5.setNext(node6);
        node4.setNext(node5);
        node3.setNext(node4);
        node2.setNext(node3);
        node1.setNext(node2);

        return node1;
    }

    public static Node test2() {
        Node node7 = new Node(1);
        Node node6 = new Node(2);
        Node node5 = new Node(3);
        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);
        node6.setNext(node7);
        node5.setNext(node6);
        node3.setNext(node5);
        node2.setNext(node3);
        node1.setNext(node2);

        return node1;
    }

    public static Node test3() {
        Node node7 = new Node(1);
        Node node6 = new Node(2);
        Node node5 = new Node(3);
        Node node2 = new Node(1);
        Node node1 = new Node(1);
        node6.setNext(node7);
        node5.setNext(node6);
        node2.setNext(node5);
        node1.setNext(node2);

        return node1;
    }

    public static Node test4() {
        Node node7 = new Node(1);
        Node node6 = new Node(1);
        Node node5 = new Node(3);
        Node node2 = new Node(2);
        Node node1 = new Node(1);
        node6.setNext(node7);
        node5.setNext(node6);
        node2.setNext(node5);
        node1.setNext(node2);

        return node1;
    }

    public static Node test5() {
        Node node7 = new Node(2);
        Node node6 = new Node(1);
        Node node2 = new Node(2);
        Node node1 = new Node(1);
        node6.setNext(node7);
        node2.setNext(node6);
        node1.setNext(node2);

        return node1;
    }

    public static void printList(Node head) {
        System.out.println("=============================================");
        while (head != null) {
            System.out.println(head);
            head = head.next;
        }
        System.out.println("=============================================");
    }

}