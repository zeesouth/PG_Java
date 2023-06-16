package programmers.lv3.n72413;
import java.util.*;
class Solution {
    int N;
    ArrayList<Node> graph[];

    public int solution(int n, int s, int a, int b, int[][] fares) {
        N = n;
        graph = new ArrayList[n+1];
        for(int i=1;i<=n;i++) graph[i] = new ArrayList<>();

        for(int i=0;i<fares.length;i++) {
            int from  = fares[i][0];
            int to  = fares[i][1];
            int value  = fares[i][2];
            graph[from].add(new Node(to, value));
            graph[to].add(new Node(from, value));
        }

        int[] both = dijkstra(s);
        int answer = Integer.MAX_VALUE;
        for(int i=1;i<=n;i++) {
            int[] alone = dijkstra(i);
            answer = Math.min(answer, both[i]+alone[a]+alone[b]);
        }

        return answer;
    }

    int[] dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(
                (o1, o2) -> o1.value-o2.value);

        boolean visited[] = new boolean[N+1];
        int dist[] = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        pq.add(new Node(start, 0));

        while(!pq.isEmpty()) {
            Node curr = pq.poll();
            if(visited[curr.to]) continue;
            visited[curr.to] = true;

            for(Node next : graph[curr.to]) {
                if(dist[next.to] > dist[curr.to] + next.value) {
                    dist[next.to] = dist[curr.to] + next.value;
                    pq.add(new Node(next.to, dist[next.to]));
                }
            }
        }

        return dist;
    }
}

class Node {
    int to, value;
    Node(int to, int value) {
        this.to = to;
        this.value = value;
    }
}