package com.wise.algorithm.strmatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * AC自动机算法实现
 */
public class ACAutomaton {

    public static void main(String[] args) {
        String[] patterns = {"at", "art", "oars", "soar"};
        String text = "soarsoarsat";
        System.out.println(match(text, patterns));
        /*
        匹配起始下标0; 长度4
        匹配起始下标1; 长度4
        匹配起始下标4; 长度4
        匹配起始下标5; 长度4
        匹配起始下标9; 长度2
        true
        */

        String[] patterns2 = {"Fxtec Pro1", "谷歌Pixel"};
        String text2 = "一家总部位于伦敦的公司Fxtex在MWC上就推出了一款名为Fxtec Pro1的手机，该机最大的亮点就是采用了侧滑式全键盘设计。DxOMark年度总榜发布 华为P20 Pro/谷歌Pixel 3争冠";
        System.out.println(match(text2, patterns2));
        /*
        匹配起始下标29; 长度10
        匹配起始下标88; 长度7
        true
        */
    }

    public static boolean match(String text, String[] patterns) {
        ACAutomaton acAutomaton = new ACAutomaton();

        // 构建 Trie 树
        for (String pattern: patterns) {
            acAutomaton.insert(pattern);
        }

        // 构建失效指针
        acAutomaton.buildFailurePointer();

        // 返回匹配结果
        return acAutomaton.match(text);
    }

    /** 存储无意义字符 */
    private ACNode root;

    public ACAutomaton() {
        this.root = new ACNode("/");
    }

    /**
     * 将输入内容与多模式串匹配
     * @param text 输入内容
     * @return
     */
    private boolean match (String text) {
        boolean result = false;

        ACNode root = this.root;
        ACNode p = root;

        int n = text.length();
        for (int i = 0; i < n; i++) {
            String c = String.valueOf(text.charAt(i));
            while (Objects.isNull(p.children.get(c)) && p != root) {
                p = p.fail;
            }

            p = p.children.get(c);
            if (Objects.isNull(p)) {
                p = root;
            }

            ACNode tmp = p;
            while ( tmp != root) {
                if (tmp.isEndingChar == true) {
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配起始下标" + pos + "; 长度" + tmp.length);

                    result = true;
                }
                tmp = tmp.fail;
            }
        }

        return result;
    }

    /**
     * 构建 Trie 树
     * @param pattern
     */
    private void insert (String pattern) {
        ACNode node = this.root;
        int len = pattern.length();
        for (int i = 0; i < len; i++) {
            String c = String.valueOf(pattern.charAt(i));
            if (Objects.isNull(node.children.get(c))) {
                node.children.put(c, new ACNode(c));
            }
            node = node.children.get(c);
        }

        node.isEndingChar = true;
        node.length = pattern.length();
    }

    /**
     * 构建失效指针
     */
    private void buildFailurePointer() {
        ACNode root = this.root;

        // 按层遍历 Trie 树的辅助队列
        LinkedList<ACNode> queue = new LinkedList<>();

        // 初始时先加入 root 节点到队列
        queue.add(root);

        // 按层遍历
        while (!queue.isEmpty()) {
            ACNode p = queue.pop();

            // 循环遍历子节点
            for (ACNode pc : p.children.values()) {
                if (Objects.isNull(pc)) {
                    continue;
                }

                // 若父节点是 root，则失败指针指向 root
                if (p == root) {
                    pc.fail = root;
                } else {
                    // 获取父节点的失败指针
                    ACNode q = p.fail;

                    // root 节点的失败指针指向 null，找不到失败指针则会退出循环
                    while (Objects.nonNull(q)) {
                        ACNode qc = q.children.get(pc.data);

                        // 父节点的失败指针指向的节点的子节点值等于当前节点的值
                        if (Objects.nonNull(qc)) {
                            pc.fail = qc;
                            break;
                        }

                        q = q.fail;
                    }

                    if (Objects.isNull(q)) {
                        pc.fail = root;
                    }
                }

                // 将子节点放入队列，等待下一轮计算
                queue.add(pc);
            }
        }
    }

    /**
     * 节点类
     */
    public class ACNode {

        /** 节点存储的数据 */
        private String data;

        /** 子节点列表 */
        private Map<String, ACNode> children;

        /** 是否为结尾字符 */
        private boolean isEndingChar;

        /** 当 isEndingChar=true 时，记录模式串长度 */
        private int length;

        /** 失效指针 */
        private ACNode fail;

        public ACNode(String data) {
            this.data = data;
            this.children = new HashMap<>();
            this.isEndingChar = false;
            this.length = 0;
            this.fail = null;
        }

    }

}