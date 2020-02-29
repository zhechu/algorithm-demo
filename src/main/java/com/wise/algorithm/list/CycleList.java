package com.wise.algorithm.list;

/**
 * 链表环检测：定义两指针，慢指针步长为1，快指针步长为2，若两指针相遇，则表示存在环
 *
 * @author lingyuwang
 * @date 2020-02-29 22:21
 * @since 1.0.4
 **/
public class CycleList {

    public static void main(String[] args) {
        System.out.println("是否存在环:" + hascycle(test1())); // true
        System.out.println("是否存在环:" + hascycle(test2())); // false
    }

    /**
     * 是否存在环
     * @param head
     * @return
     */
    public static boolean hascycle(Node head){
        // 快慢指针
        Node slow, fast;
        slow = fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
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
        node7.setNext(node3);
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

}
