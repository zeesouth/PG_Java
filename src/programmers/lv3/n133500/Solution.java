package programmers.lv3.n133500;

import java.util.*;

class Solution {
    int answer = 0;
    ArrayList<Integer> graph[];

    public int solution(int n, int[][] lighthouse) {
        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < lighthouse.length; i++) {
            int a = lighthouse[i][0];
            int b = lighthouse[i][1];

            graph[a].add(b);
            graph[b].add(a);
        }

        dfs(1, 0);

        return answer;
    }

    int dfs(int curr, int before) {
        // 리프 노드일 경우 (불을 켤 필요가 없음)
        if (graph[curr].size() == 1 && graph[curr].get(0) == before)
            return 1;

        //리프 노드가 아닐 경우
        int cnt = 0;
        for (int next : graph[curr]) {
            // 이전에 방문한 노드일 경우
            if (next == before) continue;
            cnt += dfs(next, curr);
        }

        // 불 꺼진 노드들이 없다면 (안켜도됨)
        if (cnt == 0) return 1;

        // 불 꺼진 노드들이 있다면 (켜야됨)
        answer++;
        return 0;
    }

}