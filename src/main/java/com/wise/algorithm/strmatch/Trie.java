package com.wise.algorithm.strmatch;

public class Trie {

    /** 存储无意义字符 */
    private TrieNode root = new TrieNode('/');

    /**
     * 插入一个字符串
     * @param text
     */
    public void insert(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    /**
     * 查找一个字符串
     * @param pattern
     * @return
     */
    public boolean find(char[] pattern) {
        TrieNode p = root;
        for (int i = 0; i < pattern.length; ++i) {
            int index = pattern[i] - 'a';

            // 不存在pattern
            if (p.children[index] == null) {
                return false;
            }

            p = p.children[index];
        }

        // 不能完全匹配，只是前缀
        if (p.isEndingChar == false) {
            return false;
        }
        // 找到pattern
        else {
            return true;
        }
    }

    /**
     * Trie 树节点
     */
    public class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;
        public TrieNode(char data) {
            this.data = data;
        }
    }

}