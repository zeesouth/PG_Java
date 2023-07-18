package codetree.samsung.n2021_2_1_2;

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_N = 20;
    static final int MAX_M = 400;
    // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    // 0,    2,    4,    6
    static final int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
    static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int map[][] = new int[MAX_N][MAX_N];
    static int aircon[][] = new int[MAX_M][3];
    static boolean isOffice[][] = new boolean[MAX_N][MAX_N];
    static boolean isWall[][][] = new boolean[MAX_N][MAX_N][4];
    static boolean visited[][];
    static int N, M, K, A, O, t;


    public static void main(String[] args) throws Exception {
        init();
        while (!check() && t <= 100) {
            t++;
            spread();
            mix();
            minus();
        }
        System.out.println(t > 100 ? -1 : t);
    }

    static void print(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int data = Integer.parseInt(st.nextToken());
                if (data >= 2) {
                    aircon[A++] = new int[]{i, j, (data - 2) * 2};
                } else if (data == 1) {
                    isOffice[i][j] = true;
                    O++;
                }
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            // 0: 위, 1: 왼쪽
            int s = Integer.parseInt(st.nextToken());
            if (s == 0) {
                isWall[y][x][1] = true;
                if (isRange(y - 1, x)) {
                    isWall[y - 1][x][3] = true;
                }
            } else {
                isWall[y][x][0] = true;
                if (isRange(y, x - 1)) {
                    isWall[y][x - 1][2] = true;
                }
            }
        }
        br.close();
    }

    // 1. 에어컨 시원함 전파
    static void spread() {
        for (int a = 0; a < A; a++) {
            int[] currA = aircon[a];
            if (!isRange(currA[0] + dy[currA[2]], currA[1] + dx[currA[2]])) continue;
            visited = new boolean[N][N];

            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{currA[0] + dy[currA[2]], currA[1] + dx[currA[2]], 5});

            while (!q.isEmpty()) {
                int[] curr = q.poll();
                map[curr[0]][curr[1]] += curr[2];
                visited[curr[0]][curr[1]] = true;

                if (curr[2] == 1) continue;

                for (int i = -1; i <= 1; i++) {
                    int d = (currA[2] + i + 8) % 8;
                    int nextY = curr[0] + dy[d];
                    int nextX = curr[1] + dx[d];
                    if (!isRange(nextY, nextX)) continue;
                    if (visited[nextY][nextX]) continue;

                    // 이동하려는 방향에 벽이 있는지 검사
                    boolean flag = false;
                    if (i == 0)
                        flag = isWall[curr[0]][curr[1]][d / 2];
                    else {
                        int nd = ((d + i + 8) % 8);
                        flag = isWall[curr[0]][curr[1]][nd / 2]
                                || isWall[curr[0] + dy[nd]][curr[1] + dx[nd]][currA[2] / 2];
                    }

                    if (flag) continue;
                    if (curr[2] > 1) {
                        visited[nextY][nextX] = true;
                        q.add(new int[]{nextY, nextX, curr[2] - 1});
                    }
                }
            }

        }

    }

    // 2. 공기 섞기
    static void mix() {
        int[][] distMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k += 2) {
                    int nearY = i + dy[k];
                    int nearX = j + dx[k];
                    if (!isRange(nearY, nearX)) continue;
                    if (isWall[i][j][k / 2]) continue;
                    if (map[i][j] == map[nearY][nearX]) continue;
                    int rDist = map[i][j] - map[nearY][nearX];
                    int uDist = Math.abs(rDist) / 4;
                    if (rDist > 0) {
                        distMap[nearY][nearX] += uDist;
                        distMap[i][j] -= uDist;
                    } else {
                        distMap[nearY][nearX] -= uDist;
                        distMap[i][j] += uDist;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) map[i][j] += (distMap[i][j] / 2);
        }

    }

    // 3. 외벽에 있는 시원함 감소
    static void minus() {
        for (int i = 0; i < N; i++) {
            if (map[0][i] > 0) map[0][i]--;
            if (map[N - 1][i] > 0) map[N - 1][i]--;
        }

        for (int i = 1; i < N - 1; i++) {
            if (map[i][0] > 0) map[i][0]--;
            if (map[i][N - 1] > 0) map[i][N - 1]--;
        }
    }

    // 4. 사무실 체크
    static boolean check() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (isOffice[i][j] && map[i][j] >= K) cnt++;
            }
        }
        if (cnt == O) return true;
        else return false;
    }


    static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}