package programmers.lv3.n132266;

import java.util.*;

class Solution {
    ArrayList<Integer> graph[];
    int dist[];

    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        graph = new ArrayList[n + 1];
        dist = new int[n + 1];

        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < roads.length; i++) {
            int a = roads[i][0];
            int b = roads[i][1];

            graph[a].add(b);
            graph[b].add(a);
        }

        dijkstra(destination);

        int answer[] = new int[sources.length];
        for (int i = 0; i < sources.length; i++) {
            if (dist[sources[i]] == Integer.MAX_VALUE) answer[i] = -1;
            else answer[i] = dist[sources[i]];
        }
        return answer;
    }

    void dijkstra(int start) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> q = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        q.add(new int[]{start, dist[start]});

        while (!q.isEmpty()) {
            int curr[] = q.poll();

            for (int next : graph[curr[0]]) {
                if (curr[1] + 1 < dist[next]) {
                    dist[next] = curr[1] + 1;
                    q.add(new int[]{next, dist[next]});
                }
            }
        }
    }
}