package com.wise.algorithm.apply;

/**
 * 正则表达式匹配（回溯法）
 */
public class Pattern {

    public static void main(String[] args) {
//        String regEx = "http://*.html";
        String regEx = "http://www.?aidu.com";
        Pattern pattern = new Pattern(regEx);
        boolean match = pattern.match("http://www.baidu.com");
        System.out.println("是否匹配：" + match);

        match = pattern.match("http://www.baidu.com/index.html");
        System.out.println("是否匹配：" + match);
    }

    /** 是否匹配 */
    private boolean matched = false;

    /** 正则表达式 */
    private char[] pattern;

    /** 正则表达式长度 */
    private int plen;

    public Pattern(String regEx) {
        this.pattern = regEx.toCharArray();
        this.plen = pattern.length;
    }

    /**
     * 文本串
     * @param textStr
     * @return
     */
    public boolean match(String textStr) {
        char[] text = textStr.toCharArray();
        int tlen = text.length;
        matched = false;
        rmatch(0, 0, text, tlen);
        return matched;
    }

    /**
     * 回溯法匹配
     * @param ti 文本索引
     * @param pj 正则索引
     * @param text 文本
     * @param tlen 文本长度
     */
    private void rmatch(int ti, int pj, char[] text, int tlen) {
        // 若已匹配，则直接返回
        if (matched) {
            return;
        }

        // 正则表达式已到结尾
        if (pj == plen) {
            // 文本串已到结尾
            if (ti == tlen) {
                matched = true;
            }
            return;
        }

        // *匹配任意个字符
        if (pattern[pj] == '*') {
            for (int k = 0; k <= tlen - ti; ++k) {
                rmatch(ti + k, pj + 1, text, tlen);
            }
        }
        // ?匹配0个或者1个字符
        else if (pattern[pj] == '?') {
            rmatch(ti, pj + 1, text, tlen);
            rmatch(ti + 1, pj + 1, text, tlen);
        }
        // 纯字符匹配
        else if (ti < tlen && pattern[pj] == text[ti]) {
            rmatch(ti + 1, pj + 1, text, tlen);
        }
    }

}