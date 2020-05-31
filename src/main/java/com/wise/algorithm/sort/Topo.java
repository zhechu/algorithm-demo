package com.wise.algorithm.sort;

import java.util.LinkedList;

/**
 * 拓扑排序
 *
 * @author lingyuwang
 * @date 2020-05-30 23:31
 * @since 1.0.4
 */
public class Topo {

    public static void main(String[] args) {
        Graph graph = new Graph(10);

        // 0 -> 1 -> 3 -> 4
        // 1 -> 4 -> 5 -> 7
        // 2 -> 3 -> 8 -> 9

        graph.addEdge(0, 1);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 8);
        graph.addEdge(4, 5);
        graph.addEdge(5, 7);
        graph.addEdge(8, 9);

        LinkedList<Integer> topoSortByKahnList = graph.topoSortByKahn();
        System.out.println("基于Kahn算法拓扑排序后：" + topoSortByKahnList);
        // 拓扑排序后：[0, 2, 6, 1, 3, 4, 8, 5, 9, 7]

        LinkedList<Integer> topoSortByDFSList = graph.topoSortByDFS();
        System.out.println("基于DFS算法拓扑排序后：" + topoSortByDFSList);
        // 拓扑排序后：[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    static class Graph {
        /** 顶点的个数 */
        private int v;
        /** 邻接表 */
        private LinkedList<Integer>[] adj;

        public Graph(int v) {
            this.v = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                adj[i] = new LinkedList<>();
            }
        }

        /**
         * s先于t，边s->t
         * @param s
         * @param t
         */
        public void addEdge(int s, int t) {
            adj[s].add(t);
        }

        /**
         * 基于Kahn算法拓扑排序
         *
         * 思路：
         * 先从图中，找出一个入度为 0 的顶点，将其输出到拓扑排序的结果序列中（对应代码中就是把它打印出来），
         * 并且把这个顶点从图中删除（也就是把这个顶点可达的顶点的入度都减 1）。循环执行以上过程，直到所有的
         * 顶点都被输出。最后输出的序列，就是满足局部依赖关系的拓扑排序
         *
         * 时间复杂度：
         * 每个顶点被访问了一次，每个边也都被访问了一次，时间复杂度就是 O(V+E)（V 表示顶点个数，E 表示边的个数）
         */
        public LinkedList<Integer> topoSortByKahn() {
            LinkedList<Integer> result = new LinkedList<>();

            // 统计每个顶点的入度
            int[] inDegree = new int[v];
            for (int i = 0; i < v; ++i) {
                int adjSize = adj[i].size();
                for (int j = 0; j < adjSize; ++j) {
                    // i->w
                    int w = adj[i].get(j);
                    inDegree[w]++;
                }
            }

            // 将入度为0的顶点放入队列
            LinkedList<Integer> queue = new LinkedList<>();
            for (int i = 0; i < v; ++i) {
                if (inDegree[i] == 0) {
                    queue.add(i);
                }
            }

            // 广度优先遍历
            while (!queue.isEmpty()) {
                int i = queue.remove();
                result.add(i);
                int adjSize = adj[i].size();
                for (int j = 0; j < adjSize; ++j) {
                    int k = adj[i].get(j);
                    inDegree[k]--;
                    if (inDegree[k] == 0) {
                        queue.add(k);
                    }
                }
            }

            return result;
        }

        /**
         * 基于DFS算法拓扑排序
         *
         * 思路：
         * 1.通过邻接表构造逆邻接表
         * 2.递归处理每个顶点：先将vertex依赖的所有顶点放入链表，然后再放入自身
         *
         * 时间复杂度：
         * 每个顶点被访问两次，每条边都被访问一次，时间复杂度也是 O(V+E)
         */
        public LinkedList<Integer> topoSortByDFS() {
            LinkedList<Integer> result = new LinkedList<>();

            // 先构建逆邻接表，边s->t表示，s依赖于t，t先于s
            LinkedList<Integer>[] inverseAdj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                // 申请空间
                inverseAdj[i] = new LinkedList<>();
            }

            // 通过邻接表生成逆邻接表
            for (int i = 0; i < v; ++i) {
                int adjSize = adj[i].size();
                for (int j = 0; j < adjSize; ++j) {
                    // i->w
                    int w = adj[i].get(j);
                    // w->i
                    inverseAdj[w].add(i);
                }
            }

            boolean[] visited = new boolean[v];
            for (int i = 0; i < v; ++i) {
                if (visited[i]) {
                    continue;
                }

                visited[i] = true;

                // 深度优先遍历
                dfs(result, i, inverseAdj, visited);
            }

            return result;
        }

        /**
         * 深度优先遍历
         * @param result 结果列表
         * @param vertex 当前顶点
         * @param inverseAdj 逆邻接表
         * @param visited 是否访问过顶点的备忘录
         */
        private void dfs(LinkedList<Integer> result, int vertex, LinkedList<Integer>[] inverseAdj, boolean[] visited) {
            int inverseAdjSize = inverseAdj[vertex].size();
            for (int i = 0; i < inverseAdjSize; ++i) {
                int w = inverseAdj[vertex].get(i);
                if (visited[w]) {
                    continue;
                }

                visited[w] = true;
                dfs(result, w, inverseAdj, visited);
            }

            // 先将vertex依赖的所有顶点放入链表，然后再放入自身
            result.add(vertex);
        }
    }

}

