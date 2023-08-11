package softeer.n582;

import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static int dist[];
    static ArrayList<Pair>[] graph;

    public static void main(String args[]) throws Exception {
        init();
        dijkstra(0);
        print();
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());
            graph[a].add(new Pair(b, c));
            graph[b].add(new Pair(a, c));
        }
    }

    static void dijkstra(int start) {
        dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Pair> pq = new PriorityQueue<>((o1, o2) -> o1.value - o2.value);
        pq.add(new Pair(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            if (curr.value > dist[curr.to]) continue;

            for (Pair next : graph[curr.to]) {
                int val = Math.max(next.value, curr.value);
                if (dist[next.to] > val) {
                    dist[next.to] = val;
                    pq.add(new Pair(next.to, dist[next.to]));
                }
            }
        }
    }

    static void print() {
        for (int i = dist[N - 1] + 1; i < dist[N - 1] * 2; i++) {
            if (i % 2 == 0) continue;
            boolean pn = true;
            for (int j = 2; j < (int) (Math.pow(i, 0.5)) + 1; j++) {
                if (i % j == 0) {
                    pn = false;
                    break;
                }
            }

            if (pn) {
                System.out.println(i);
                break;
            }
        }
    }

    static class Pair {
        int to, value;

        Pair(int to, int value) {
            this.to = to;
            this.value = value;
        }
    }
}