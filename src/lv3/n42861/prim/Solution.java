package lv3.n42861.prim;

// MST + prim
import java.util.*;
class Solution {
    public int solution(int n, int[][] costs) {
        List<Edge> list[] = new ArrayList[n];
        for(int i=0;i<n;i++) list[i] = new ArrayList<>();

        for(int i=0;i<costs.length;i++) {
            int v1 = costs[i][0];
            int v2 = costs[i][1];
            int cost = costs[i][2];
            list[v1].add(new Edge(v2, cost));
            list[v2].add(new Edge(v1, cost));
        }

        boolean visited[] = new boolean[n];
        int dist[] = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        int answer = 0;
        int cnt = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>(
                (o1, o2) -> o1.cost-o2.cost
        );
        pq.add(new Edge(0, dist[0]));
        while(true) {
            Edge curr = pq.poll();

            if(visited[curr.to]) continue;
            visited[curr.to] = true;
            answer += curr.cost;
            cnt++;

            if(cnt == n) break;

            for(Edge v : list[curr.to]) {
                if(!visited[v.to] && dist[v.to] > v.cost) {
                    dist[v.to] = v.cost;
                    pq.add(new Edge(v.to, dist[v.to]));
                }
            }
        }
        return answer;
    }

    class Edge {
        int to, cost;
        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

}