package com.wise.algorithm.apply.shortestpath;

import java.util.LinkedList;

/**
 * 有向有权图
 *
 * @author lingyuwang
 * @date 2020-06-08 22:00
 * @since 1.0.4
 */
public class Graph {

    /** 邻接表 */
    private LinkedList<Edge> adj[];

    /** 顶点个数 */
    private int v;

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            this.adj[i] = new LinkedList<>();
        }
    }

    /**
     * 添加一条边
     * @param s
     * @param t
     * @param w
     */
    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));
    }

    private class Edge {
        /** 边的起始顶点编号 */
        public int sid;
        /** 边的终止顶点编号 */
        public int tid;
        /** 权重 */
        public int w;

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    /**
     * dijkstra实现用
     */
    private class Vertex {
        /** 顶点编号ID */
        public int id;
        /** 从起始顶点到这个顶点的距离 */
        public int dist;

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    private class PriorityQueue { 
        /** 根据vertex.dist构建小顶堆 */
        private Vertex[] nodes;  
        private int count;  
        public PriorityQueue(int v) {
            this.nodes = new Vertex[v+1];
            this.count = v;
        }
        public Vertex poll() {
            return nodes[0];
        }

        public void add(Vertex vertex) { }

        /**
         * 更新结点的值，并且从下往上堆化，重新符合堆的定义
         * @param vertex
         */
        public void update(Vertex vertex) {

        }

        public boolean isEmpty() {
            return true;
        }
    }

}