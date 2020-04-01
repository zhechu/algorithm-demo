package com.wise.algorithm.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 求图中两节点间的有效路径
 */
public class GraphOfAdjacency {

    public static void main(String[] args) {
        GraphOfAdjacency graphOfAdjacency = new GraphOfAdjacency(8);
        graphOfAdjacency.addEdge(0,1);
        graphOfAdjacency.addEdge(0,3);
        graphOfAdjacency.addEdge(1,2);
        graphOfAdjacency.addEdge(1,4);
        graphOfAdjacency.addEdge(2,5);
        graphOfAdjacency.addEdge(4,5);
        graphOfAdjacency.addEdge(4,6);
        graphOfAdjacency.addEdge(5,7);
        graphOfAdjacency.addEdge(6,7);

        // 广度优先（最短路径）
        graphOfAdjacency.bfs(0,6);

        System.out.println();

        // 深度优先（不保证最短路径）
        graphOfAdjacency.dfs(0, 6);
    }

    /** 顶点个数 */
    private int vertexSize;
    /** 邻接表 */
    private List<Integer>[] adj;

    public GraphOfAdjacency(int vertexSize) {
        this.vertexSize = vertexSize;
        adj = new LinkedList[vertexSize];
        for (int i = 0; i < vertexSize; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加边
     *
     * @param s 顶点
     * @param t 顶点
     */
    public void addEdge(int s, int t) {
        // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }

    /**
     * 广度优先搜索
     * @param s
     * @param t
     */
    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }

        // 记录已经被访问的顶点，避免顶点被重复访问
        boolean[] visited = new boolean[vertexSize];
        visited[s] = true;

        // 存储已经被访问、但相连的顶点还没有被访问的顶点
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        // 记录搜索路径
        int[] prev = new int[vertexSize];
        for (int i = 0; i < vertexSize; ++i) {
            prev[i] = -1;
        }

        while (queue.size() != 0) {
            int w = queue.poll();
            for (int q : adj[w]) {
                if (visited[q]) {
                    continue;
                }

                prev[q] = w;
                if (q == t) {
                    print(prev, s, t);
                    return;
                }
                visited[q] = true;
                queue.add(q);
            }
        }
    }

    /**
     * 递归打印 s->t 的路径
     * @param prev
     * @param s
     * @param t
     */
    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    /** 全局变量或者类成员变量 */
    private boolean found = false;

    /**
     * 深度优先搜索
     * @param s
     * @param t
     */
    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[vertexSize];
        int[] prev = new int[vertexSize];
        for (int i = 0; i < vertexSize; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    /**
     * 深度递归
     * @param w
     * @param t
     * @param visited
     * @param prev
     */
    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) {
            return;
        }

        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }

        for (int q : adj[w]) {
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }

}