package codetree.samsung_re.boj_13640;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int MAX = 10;
    static final int dy[] = {1, 0, -1, 0};
    static final int dx[] = {0, 1, 0, -1};
    static char map[][] = new char[MAX][MAX];
    static int N, M, ans = Integer.MAX_VALUE;
    static int ry, rx, by, bx, ey, ex;
    static boolean visited[][][][] = new boolean[MAX][MAX][MAX][MAX];

    public static void main(String[] args) throws Exception {
        init();
        System.out.println(bfs());
    }

    private static int bfs() {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, ry, rx, by, bx});
        visited[ry][rx][by][bx] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            int cnt = curr[0];

            if (cnt >= 10) return -1;

            for (int i = 0; i < 4; i++) {
                int nextRy = curr[1];
                int nextRx = curr[2];
                int nextBy = curr[3];
                int nextBx = curr[4];

                boolean isHole = false;
                while (isRange(nextBy + dy[i], nextBx + dx[i])) {
                    nextBy += dy[i];
                    nextBx += dx[i];
                    if (nextBy == ey && nextBx == ex) isHole = true;
                }

                if (isHole) continue;

                while (isRange(nextRy + dy[i], nextRx + dx[i])) {
                    nextRy += dy[i];
                    nextRx += dx[i];
                    if (nextRy == ey && nextRx == ex) {
                        return cnt + 1;
                    }
                }

                if (nextRy == nextBy && nextRx == nextBx) {
                    // 아래
                    if (i == 0) {
                        if (curr[1] < curr[3]) {
                            nextRy -= 1;
                        } else {
                            nextBy -= 1;
                        }
                    }
                    // 오른
                    else if (i == 1) {
                        if (curr[2] < curr[4]) {
                            nextRx -= 1;
                        } else {
                            nextBx -= 1;
                        }
                    }
                    // 위
                    else if (i == 2) {
                        if (curr[1] > curr[3]) {
                            nextRy += 1;
                        } else {
                            nextBy += 1;
                        }
                    }
                    // 왼
                    else {
                        if (curr[2] > curr[4]) {
                            nextRx += 1;
                        } else {
                            nextBx += 1;
                        }
                    }
                }

                if (visited[nextRy][nextRx][nextBy][nextBx]) continue;
                visited[nextRy][nextRx][nextBy][nextBx] = true;
                q.add(new int[]{cnt + 1, nextRy, nextRx, nextBy, nextBx});
            }
        }
        return -1;
    }

    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'R') {
                    ry = i;
                    rx = j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'B') {
                    by = i;
                    bx = j;
                    map[i][j] = '.';
                } else if (map[i][j] == 'O') {
                    ey = i;
                    ex = j;
                }
            }
        }
    }

    static boolean isRange(int y, int x) {
        return y >= 1 && y < N - 1 && x >= 1 && x < M - 1 && map[y][x] != '#';
    }
}
