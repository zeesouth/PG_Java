package programmers.lv2.n1829;

import java.util.*;

class Solution {
    int[] dy = {1, 0, -1, 0};
    int[] dx = {0, 1, 0, -1};
    boolean visited[][];
    int M, N;
    int map[][];

    public int[] solution(int m, int n, int[][] picture) {
        M = m;
        N = n;
        map = picture;

        int a = 0;  // 몇개의 영역이 있는지
        int b = 0;  // 가장 큰 영역의 크기

        visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (visited[i][j]) continue;
                if (map[i][j] == 0) continue;
                a++;
                b = Math.max(bfs(i, j), b);
            }
        }

        return new int[]{a, b};
    }

    int bfs(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x});
        visited[y][x] = true;

        int cnt = 1;
        int t = map[y][x];
        while (!q.isEmpty()) {
            int curr[] = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextY = curr[0] + dy[i];
                int nextX = curr[1] + dx[i];

                if (!isRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (t != map[nextY][nextX]) continue;
                visited[nextY][nextX] = true;
                cnt++;
                q.add(new int[]{nextY, nextX});
            }
        }

        return cnt;
    }

    boolean isRange(int y, int x) {
        return y >= 0 && y < M && x >= 0 && x < N;
    }
}
