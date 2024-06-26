package programmers.lv2.n154540;

import java.util.*;

class Solution {
    boolean visited[][];
    int N, M;
    int[] dy = {1, 0, -1, 0};
    int[] dx = {0, 1, 0, -1};
    String[] map;

    public int[] solution(String[] maps) {
        int idx = 0;
        N = maps.length;
        M = maps[0].length();
        visited = new boolean[N][M];
        int[] answer = new int[N * M];
        map = maps;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char c = maps[i].charAt(j);
                if (c == 'X') continue;
                if (visited[i][j]) continue;
                answer[idx++] = dfs(i, j);
            }
        }

        if (idx == 0) answer[idx++] = -1;
        Arrays.sort(answer, 0, idx);
        return Arrays.copyOfRange(answer, 0, idx);
    }

    int dfs(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y, x});
        visited[y][x] = true;

        int answer = map[y].charAt(x) - '0';

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextY = curr[0] + dy[i];
                int nextX = curr[1] + dx[i];

                if (!isRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (map[nextY].charAt(nextX) == 'X') continue;

                answer += map[nextY].charAt(nextX) - '0';
                visited[nextY][nextX] = true;
                q.offer(new int[]{nextY, nextX});
            }
        }

        return answer;
    }

    boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }
}

