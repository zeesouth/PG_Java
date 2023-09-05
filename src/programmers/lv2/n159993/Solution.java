package programmers.lv2.n159993;

import java.util.*;

class Solution {
    int dy[] = {0, 0, -1, 1};
    int dx[] = {1, -1, 0, 0};
    int N, M, sy, sx;
    String[] map;
    boolean visited[][];

    public int solution(String[] maps) {
        init(maps);

        visited = new boolean[N][M];
        int dist1 = bfs(sy, sx, 0, 'L');
        if (dist1 == 0) return -1;

        visited = new boolean[N][M];
        int dist2 = bfs(sy, sx, dist1, 'E');
        if (dist2 == 0) return -1;

        return dist2;
    }

    private int bfs(int y, int x, int dist, char target) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x, dist});
        visited[y][x] = true;
        int result = 0;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            if (map[curr[0]].charAt(curr[1]) == target) {
                sy = curr[0];
                sx = curr[1];
                result = curr[2];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextY = dy[i] + curr[0];
                int nextX = dx[i] + curr[1];

                if (!isRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (map[nextY].charAt(nextX) == 'X') continue;

                q.add(new int[]{nextY, nextX, curr[2] + 1});
                visited[nextY][nextX] = true;
            }
        }

        return result;
    }

    private void init(String[] maps) {
        map = maps;
        N = maps.length;
        M = maps[0].length();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (maps[i].charAt(j) == 'S') {
                    sy = i;
                    sx = j;
                    return;
                }
            }
        }
    }

    private boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }
}

