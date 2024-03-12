package programmers.lv3.n1837;

import java.util.*;

class Solution {
    public int solution(int n, int m, int[][] edge_list, int k, int[] gps_log) {
        HashSet<Integer> graph[] = new HashSet[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new HashSet<>();
            graph[i].add(i);
        }

        for (int i = 0; i < m; i++) {
            int a = edge_list[i][0] - 1;
            int b = edge_list[i][1] - 1;

            graph[a].add(b);
            graph[b].add(a);
        }

        int dp[][] = new int[k][n];
        int INF = Integer.MAX_VALUE;

        for (int i = 0; i < k; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][gps_log[0] - 1] = 0;

        for (int i = 0; i < k - 1; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == INF) continue;

                for (int next : graph[j]) {
                    if (next == gps_log[i + 1] - 1)
                        dp[i + 1][next] = Math.min(dp[i + 1][next], dp[i][j]);
                    else
                        dp[i + 1][next] = Math.min(dp[i + 1][next], dp[i][j] + 1);
                }
            }
        }

        return dp[k - 1][gps_log[k - 1] - 1] == INF ? -1 : dp[k - 1][gps_log[k - 1] - 1];
    }
}
