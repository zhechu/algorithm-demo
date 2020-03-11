package com.wise.algorithm.list;

/**
 * 跳表中存储的是正整数，并且存储的是不重复的
 */
public class SkipList {

    public static void main(String[] args) {
        /**
         * 示例：
         * 									    null:15-------
         * 									    null:14-------
         * 									    null:13-------
         * 									    null:12-------
         * 									    null:11-------
         * 									    null:10-------
         * 										   5:9-------
         * 										   5:8-------
         * 										   5:7-------
         * 										   5:6-------
         * 										   5:5-------
         * 										   5:4-------					 8:4-------
         * 							     4:3-------5:3-------6:3-------7:3-------8:3-------
         * 1:2-------2:2-------		     4:2-------5:2-------6:2-------7:2-------8:2-------
         * 1:1-------2:1-------3:1-------4:1-------5:1-------6:1-------7:1-------8:1-------
         * 1:0-------2:0-------3:0-------4:0-------5:0-------6:0-------7:0-------8:0-------
         * { data: 1; levels: 3 } { data: 2; levels: 3 } { data: 3; levels: 2 } { data: 4; levels: 4 }
         * { data: 5; levels: 10 } { data: 6; levels: 4 } { data: 7; levels: 4 } { data: 8; levels: 5 }
         */

        SkipList list = new SkipList();
        list.insert(1);
        list.insert(2);
        list.insert(6);
        list.insert(7);
        list.insert(8);
        list.insert(3);
        list.insert(4);
        list.insert(5);
        System.out.println();
        list.printAllWithBeautiful();
        System.out.println();
        list.printAll();

        int targetValue = 4;
        Node targetNode = list.find(targetValue);
        System.out.println(targetValue + "的节点是：" + targetNode);

        list.delete(targetValue);

        System.out.println("===================删除节点" + targetValue + "=====================");
        list.printAllWithBeautiful();
        System.out.println();
        list.printAll();

        int newValue = 6;
        System.out.println("===================添加节点" + newValue + "=====================");
        list.insert(newValue);
        list.printAllWithBeautiful();
        System.out.println();
        list.printAll();
    }

    /** 每层晋升概率 */
    private static final double SKIPLIST_P = 0.5d;
    /** 最大层 */
    private static final int MAX_LEVEL = 16;
    /** 当前层数 */
    private int levelCount = 1;

    /** 带头链表 */
    private Node head = new Node(MAX_LEVEL);

    /**
     * 查找数据所在的节点
     * @param value
     * @return
     */
    public Node find(int value) {
        Node p = head;
        // 从最大层开始查找，找到前一节点，通过--i，移动到下层再开始查找
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && value > p.forwards[i].data) {
                // 找到前一节点
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        }

        return null;
    }

    /**
     * 插入
     *
     * @param value 值
     */
    public void insert(int value) {
        int level = head.forwards[0] == null ? 1 : randomLevel();
        // 每次只增加一层，如果条件满足
        if (level > levelCount) {
            level = ++levelCount;
        }
        Node newNode = new Node(level);
        newNode.data = value;
        Node p = head;
        // 从最大层开始查找，找到前一节点，通过--i，移动到下层再开始查找
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && value > p.forwards[i].data) {
                // 找到前一节点
                p = p.forwards[i];
            }
            if (i < level) {
                if (p.forwards[i] == null) {
                    p.forwards[i] = newNode;
                } else {
                    Node next = p.forwards[i];
                    p.forwards[i] = newNode;
                    newNode.forwards[i] = next;
                }
            }
        }
    }

    /**
     * 删除
     * @param value
     */
    public void delete(int value) {
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && value > p.forwards[i].data) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // 不存在则直接返回
        if (p.forwards[0] == null || p.forwards[0].data != value) {
            return;
        }

        for (int i = levelCount - 1; i >= 0; --i) {
            if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                update[i].forwards[i] = update[i].forwards[i].forwards[i];
            }
        }
    }

    /**
     * 理论来讲，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
     * 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
     *         50%的概率返回 1
     *         25%的概率返回 2
     *       12.5%的概率返回 3 ...
     * @return
     */
    private int randomLevel() {
        int level = 1;

        while (Math.random() < SKIPLIST_P && level < MAX_LEVEL) {
            level++;
        }

        return level;
    }

    public void printAll() {
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.print(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    /**
     * 打印所有数据
     */
    public void printAllWithBeautiful() {
        int maxLevel = head.forwards.length;
        for (int i = maxLevel - 1; i >= 0; i--) {
            Node[] d = head.forwards;
            do {
                System.out.print((d[i] != null ? d[i].data : null) + ":" + i + "-------");
            } while (d[i] != null && (d = d[i].forwards)[i] != null);
            System.out.println();
        }
    }

    /**
     * 跳表的节点，每个节点记录了当前节点数据和所在层数数据
     */
    public class Node {
        private int data = -1;
        /**
         * 表示当前节点位置的下一个节点所有层的数据，从上层切换到下层，就是数组下标-1，
         * forwards[3]表示当前节点在第三层的下一个节点。
         */
        private Node[] forwards;

        public Node(int level) {
            forwards = new Node[level];
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(data);
            builder.append(" }");
            return builder.toString();
        }
    }

}
