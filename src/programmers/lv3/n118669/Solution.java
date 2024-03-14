package programmers.lv3.n118669;

import java.util.*;

class Solution {
    int[] answer = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    ArrayList<int[]>[] graph;
    int dist[], gates[], summits[];
    HashSet<Integer> sSet = new HashSet<>();
    HashSet<Integer> eSet = new HashSet<>();

    int n;
    PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] == o2[2] ? o1[0] - o2[0] : o1[2] - o2[2]);

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        Arrays.sort(summits);

        this.n = n;
        this.gates = gates;
        this.summits = summits;
        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < paths.length; i++) {
            graph[paths[i][0]].add(new int[]{paths[i][1], paths[i][2]});
            graph[paths[i][1]].add(new int[]{paths[i][0], paths[i][2]});
        }

        for (int i = 0; i < gates.length; i++) sSet.add(gates[i]);
        for (int i = 0; i < summits.length; i++) eSet.add(summits[i]);

        dijkstra();

        return answer;
    }

    void dijkstra() {
        dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 0; i < gates.length; i++) {
            dist[gates[i]] = 0;
            pq.add(new int[]{gates[i], gates[i], 0});
        }

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            if (dist[curr[1]] < curr[2]) continue;

            for (int next[] : graph[curr[1]]) {
                if (next[0] != curr[0] && sSet.contains(next[0])) continue;
                if (dist[next[0]] <= Math.max(curr[2], next[1])) continue;

                dist[next[0]] = Math.max(curr[2], next[1]);
                if (eSet.contains(next[0])) continue;
                pq.add(new int[]{curr[0], next[0], dist[next[0]]});
            }
        }

        for (int i = 0; i < summits.length; i++) {
            if (answer[1] > dist[summits[i]]) {
                answer[0] = summits[i];
                answer[1] = dist[summits[i]];
            }
        }
    }
}
