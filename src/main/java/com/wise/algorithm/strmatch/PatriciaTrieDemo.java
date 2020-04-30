package com.wise.algorithm.strmatch;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trie树的应用
 * 1.统计词频（PatriciaTrie 继承 AbstractMap，拥有 Map 特性）
 * 2.搜索框提示（prefixMap 方法）
 * 3.根据前缀范围搜索（subMap 方法）
 */
public class PatriciaTrieDemo {

    public static void main(String[] args) {
        PatriciaTrie<Double> patriciaTrie = new PatriciaTrie<Double>();

        // key 为字符串，value 为存储的值（非常适合存储词频）
        patriciaTrie.put("ronak", 100.0);
        patriciaTrie.put("ronald", 90.0);
        patriciaTrie.put("rat", 50.0);
        patriciaTrie.put("robert", 200.0);
        patriciaTrie.put("bat", 44.0);
        patriciaTrie.put("batman", 440.0);

        // 是否包含某前缀的字符
        System.out.println(patriciaTrie.containsKey("ronak"));
        // true
        System.out.println(patriciaTrie.containsKey("onak"));
        // false

        // 根据 key 获取存储的值（需精确匹配）
        System.out.println(patriciaTrie.get("robert"));
        // 200.0

        // 获取按字典排序最接近的字符
        System.out.println(patriciaTrie.selectKey("ro"));
        // robert

        // 获取匹配前缀的所有字符（按字典排序）
        System.out.println(patriciaTrie.prefixMap("r"));
        // {rat=50.0, robert=200.0, ronak=100.0, ronald=90.0}

        // 获取匹配前缀的所有字符（按值顺序排序）
        Map<String, Double> roMap = patriciaTrie.prefixMap("ro").entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2, LinkedHashMap::new));
        System.out.println(roMap);
        // {ronald=90.0, ronak=100.0, robert=200.0}

        // 获取匹配前缀的所有字符（按值倒序排序）
        Map<String, Double> roReverseOrderMap = patriciaTrie.prefixMap("ro").entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2, LinkedHashMap::new));
        System.out.println(roReverseOrderMap);
        // {robert=200.0, ronak=100.0, ronald=90.0}

        System.out.println(patriciaTrie.prefixMap("ron"));
        // {ronak=100.0, ronald=90.0}

        // 获取按字典顺序排序的第一个字符
        System.out.println(patriciaTrie.firstKey());
        // bat

        // 获取按字典顺序排序的最后一个字符
        System.out.println(patriciaTrie.lastKey());
        // ronald

        // 获取比 ro（不包含） 前缀小的字符
        System.out.println(patriciaTrie.headMap("ro"));
        // {bat=44.0, batman=440.0, rat=50.0}

        // 获取在 ra（包含） 和 ro（不包含） 前缀之间的字符
        System.out.println(patriciaTrie.subMap("ra", "ro"));
        // {rat=50.0}

        // 获取比 ra（包含） 前缀大的字符
        System.out.println(patriciaTrie.tailMap("ra"));
        // {rat=50.0, robert=200.0, ronak=100.0, ronald=90.0}

        // 按字典顺序列出所有 key
        Set<String> keySet = patriciaTrie.keySet();
        for (String key : keySet) {
            System.out.println("key ---> " + key);
        }
        /*
        key ---> bat
        key ---> batman
        key ---> rat
        key ---> robert
        key ---> ronak
        key ---> ronald
        */

        // 按 key 的字典顺序列出其所有 value
        Collection<Double> values = patriciaTrie.values();
        for (Double value : values) {
            System.out.println("value ---> " + value);
        }
        /*
        value ---> 44.0
        value ---> 440.0
        value ---> 50.0
        value ---> 200.0
        value ---> 100.0
        value ---> 90.0
        */
    }

}
