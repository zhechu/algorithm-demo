package com.wise.algorithm.list;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 模拟实现 Redis SortedSet
 * 实现：TreeSet + HashMap
 */
public class SortedSet<V extends Comparable> {

  private final TreeSet<TypedTuple<V>> treeSet = new TreeSet<>((o1, o2) -> {
    if (o1.getScore().compareTo(o2.getScore()) == 0) {
      if (o1.getValue().equals(o2.getValue())) {
        return 0;
      }

      return o1.getValue().compareTo(o2.getValue());
    }

    return o1.getScore() > o2.getScore() ? 1 : -1;
  });

  private final Map<V, Double> sortedMap = new HashMap<>();

  /**
   * 添加元素
   * @param typedTuple 元数据
   * @return 如果此集合尚未包含指定的元素，则为true
   */
  public boolean add(TypedTuple<V> typedTuple) {
    boolean result = treeSet.add(typedTuple);
    sortedMap.put(typedTuple.getValue(), typedTuple.getScore());
    return result;
  }

  /**
   * 范围查询
   * @param start
   * @param end
   * @return 集合
   */
  public List<TypedTuple<V>> range(long start, long end) {
    if (end < 0) {
      end = Long.MAX_VALUE;
    }

    return treeSet.stream()
      .skip(start)
      .limit(end)
      .collect(Collectors.toList());
  }

  /**
   * 移除
   * @param vs
   * @return 成功移除个数
   */
  public long remove(V... vs) {
    long result = 0;

    for (V v : vs) {
      Double score = sortedMap.get(v);
      TypedTuple typedTuple = new TypedTuple(v, score);
      boolean remove = treeSet.remove(typedTuple);
      if (remove) {
        sortedMap.remove(v);
        result++;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    SortedSet<String> sortedSet = new SortedSet<>();

    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      double score = random.nextDouble();
      TypedTuple<String> typedTuple = new TypedTuple<>(String.valueOf(i), score);
      sortedSet.add(typedTuple);

//      typedTuple = new TypedTuple<>(String.valueOf((i + 1)*100), score);
//      sortedSet.add(typedTuple);
//
//      typedTuple = new TypedTuple<>(String.valueOf((i + 1)*200), score);
//      sortedSet.add(typedTuple);
    }

    int page = 1;
    int pageSize = 10;
    List<TypedTuple<String>> resultList = sortedSet.range((page - 1) * pageSize, pageSize);
    resultList.forEach(System.out::println);

    System.out.println("=================================");

    sortedSet.remove(resultList.get(0).getValue());
    sortedSet.range((page - 1) * pageSize, pageSize).forEach(System.out::println);

    System.out.println("=================================");

    TypedTuple<String> typedTupleTemp = resultList.get(1);
    typedTupleTemp.setScore(0.0001);
    sortedSet.add(typedTupleTemp);
    sortedSet.range((page - 1) * pageSize, pageSize).forEach(System.out::println);

    System.out.println("=================================");
  }

}

/**
 * 缓存数据
 */
class TypedTuple<V> {

  private final V value;

  private double score;

  public TypedTuple(V value, Double score) {
    this.value = value;
    this.score = score;
  }

  public V getValue() {
    return value;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  @Override
  public String toString() {
    return "TypedTuple{" +
      "score='" + score + '\'' +
      ", value=" + value +
      '}';
  }

}