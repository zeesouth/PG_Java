package codetree.samsung.n2021_1_1_1;

import java.io.*;
import java.util.*;

public class Main {
    static final int[] dy = {-1, 0, 1, 0};
    static final int[] dx = {0, -1, 0, 1};
    static final int MAX_N = 20, MAX_M = 400;
    static int[][] map = new int[MAX_N + 1][MAX_N + 1];
    static boolean[][] isLike = new boolean[MAX_M + 1][MAX_M + 1];
    static int N, ans;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(br.readLine());
        simulate();
        calculate();
        System.out.println(ans);
        br.close();
    }

    private static void calculate() {

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int cnt = 0;
                for (int d = 0; d < 4; d++) {
                    int ny = i + dy[d];
                    int nx = j + dx[d];
                    if (!isRange(ny, nx)) continue;
                    if (isLike[map[i][j]][map[ny][nx]]) cnt++;
                }

                if (cnt == 0) continue;

                if (cnt == 1) ans += 1;
                else if (cnt == 2) ans += 10;
                else if (cnt == 3) ans += 100;
                else ans += 1000;
            }
        }

    }

    private static void simulate() throws Exception {
        for (int i = 1; i <= N * N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            for (int j = 0; j < 4; j++) {
                int like = Integer.parseInt(st.nextToken());
                // idx가 좋아하는 친구 : like
                isLike[idx][like] = true;
            }

            if (i == 1) {
                map[2][2] = idx;
                continue;
            }

            findPos(idx);
        }
    }

    private static void findPos(int idx) {

        int like = 0;
        int blank = 0;
        int y = N;
        int x = N;

        // 1. 좋아하는 친구가 많은 위치를 찾기
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // 빈 칸인 것만 탑승할 수 있음
                if (map[i][j] != 0) continue;

                int currLike = 0;
                int currBlank = 0;

                for (int d = 0; d < 4; d++) {
                    int ny = i + dy[d];
                    int nx = j + dx[d];
                    if (!isRange(ny, nx)) continue;
                    if (map[ny][nx] == 0) currBlank++;
                    else if (isLike[idx][map[ny][nx]]) currLike++;
                }

                if (like < currLike) {
                    like = currLike;
                    blank = currBlank;
                    y = i;
                    x = j;
                } else if (like == currLike) {
                    if (blank < currBlank) {
                        blank = currBlank;
                        y = i;
                        x = j;
                    }
                    else if (blank == currBlank) {
                        if (y > i) {
                            y = i;
                            x = j;
                        } else if (y == i) {
                            if (x > j) {
                                x = j;
                            }
                        }
                    }

                }

            }
        }

        map[y][x] = idx;
    }

    private static boolean isRange(int y, int x) {
        return y >= 1 && y <= N && x >= 1 & x <= N;
    }

    private static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
1. 격자를 벗어나지 않는 4방향으로 인접한 칸 중 좋아하는 친구 수가 가장 많은 위치
2. 인접한 칸 중 비어있는 칸의 수가 가장 많은 위치
   -> 격자를 벗어나는 칸은 비어있는 칸으로 간주 X
3. 행 번호가 가장 작은 위치
4. 열 번호가 가장 작은 위치

 */
