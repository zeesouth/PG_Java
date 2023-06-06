package programmers.lv3.n49189;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
// DFS (깊이 구하기)
// 1번 노드로부터 멀리 떨어진 곳 (start)

class Solution {
    private ArrayList<Integer>[] adj;
    private int[] visit;
    private int depth = 0;

    public int solution(int n, int[][] edge) {
        int answer = 0;
        visit = new int[n+1];
        adj = new ArrayList[n+1];
        for(int i=1; i<=n; i++) adj[i] = new ArrayList<>();
        for(int i=0; i<edge.length; i++){
            adj[edge[i][0]].add(edge[i][1]);
            adj[edge[i][1]].add(edge[i][0]);
        }

        bfs(1, 1);

        for(int i=1; i<=n; i++){
            if(depth == visit[i]) answer+=1;
        }

        return answer;
    }

    private void bfs(int start, int count){
        Queue<Integer> q = new LinkedList();
        q.add(start);
        q.add(count);
        visit[start] = count;

        while(!q.isEmpty()){
            int node = q.poll();
            int n = q.poll();

            if(depth < n) depth = n;
            for(int i=0; i<adj[node].size(); i++){
                int next = adj[node].get(i);

                if(visit[next] != 0) continue;
                visit[next] = n+1;
                q.add(next);
                q.add(n+1);
            }
        }
    }
}