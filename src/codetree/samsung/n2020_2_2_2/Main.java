package codetree.samsung.n2020_2_2_2;

import java.io.*;
import java.util.*;

public class Main {
    static final int dy[] = {-1, 0, 1, 0};
    static final int dx[] = {0, -1, 0, 1};
    static int map[][];
    static boolean visited[][];
    static int N, Q, ans1, ans2;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        init();
        while (Q-- > 0) {
            int L = Integer.parseInt(st.nextToken());
            if (L > 0) rotate(L);
            melt();
        }
        cntArea();
        sb.append(ans1).append("\n").append(ans2);
        System.out.println(sb);
    }

    private static void cntArea() {
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) continue;
                if (visited[i][j]) continue;

                ans2 = Math.max(ans2, bfs(i, j));
            }
        }
    }

    private static int bfs(int y, int x) {
        int cnt = 1;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x});
        visited[y][x] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextY = curr[0] + dy[i];
                int nextX = curr[1] + dx[i];

                if (!isRange(nextY, nextX)) continue;
                if (map[nextY][nextX] == 0) continue;
                if (visited[nextY][nextX]) continue;

                visited[nextY][nextX] = true;
                q.add(new int[]{nextY, nextX});
                cnt++;
            }
        }

        return cnt;
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = 1 << Integer.parseInt(st.nextToken());
        map = new int[N][N];

        Q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                ans1 += (map[i][j] = Integer.parseInt(st.nextToken()));
            }
        }

        st = new StringTokenizer(br.readLine());
        br.close();
    }

    static void melt() {
        int[][] newMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) continue;

                int ice = 0;

                for (int d = 0; d < 4; d++) {
                    int ny = i + dy[d];
                    int nx = j + dx[d];
                    if (!isRange(ny, nx)) continue;
                    if (map[ny][nx] > 0) ice++;
                }

                newMap[i][j] = map[i][j];

                if (ice < 3) {
                    newMap[i][j]--;
                    ans1--;
                }
            }
        }
        map = newMap;
    }

    // 회전
    static void rotate(int level) {
        int[][] newMap = new int[N][N];
        int size = 1 << level;
        int half_size = 1 << (level - 1);

        for (int sy = 0; sy < N; sy += size) {
            for (int sx = 0; sx < N; sx += size) {

                // 1. 좌상 -> 우상
                int y = sy;
                int x = -1;
                for (int i = sy; i < sy + half_size; i++) {
                    x = sx + half_size;
                    for (int j = sx; j < sx + half_size; j++) {
                        newMap[y][x] = map[i][j];
                        x++;
                    }
                    y++;
                }

                // 2. 우상 -> 우하
                y = sy + half_size;
                for (int i = sy; i < sy + half_size; i++) {
                    x = sx + half_size;
                    for (int j = sx + half_size; j < sx + half_size * 2; j++) {
                        newMap[y][x] = map[i][j];
                        x++;
                    }
                    y++;
                }


                // 3. 우하 -> 좌하
                y = sy + half_size;
                for (int i = sy + half_size; i < sy + half_size * 2; i++) {
                    x = sx;
                    for (int j = sx + half_size; j < sx + half_size * 2; j++) {
                        newMap[y][x] = map[i][j];
                        x++;
                    }
                    y++;
                }

                // 4. 좌하 -> 좌상
                y = sy;
                for (int i = sy + half_size; i < sy + half_size * 2; i++) {
                    x = sx;
                    for (int j = sx; j < sx + half_size; j++) {
                        newMap[y][x] = map[i][j];
                        x++;
                    }
                    y++;
                }

            }
        }

        map = newMap;
    }

    static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

}
