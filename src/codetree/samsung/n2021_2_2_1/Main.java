package codetree.samsung.n2021_2_2_1;

import java.io.*;
import java.util.*;

public class Main {
    static final int N = 4;

    // ↑, ↖, ←, ↙, ↓, ↘, →, ↗
    static final int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int dx[] = {0, -1, -1, -1, 0, 1, 1, 1};

    static int aliveMonsterCnt[][] = new int[N][N];
    static int aliveMonsterDirectCnt[][][] = new int[N][N][8];
    static int eggMonsterCnt[][][] = new int[N][N][8];
    static int deadMonsterTime[][] = new int[N][N];
    static boolean visited[][] = new boolean[N][N];
    static int py, px;
    static int M, T, t, dieM, direct[];

    public static void main(String[] args) throws Exception {
        init();
        for (t = 1; t <= T; t++) {
            duplicateAliveMonster();
            moveAliveMonster();
            movePacman();
            birthEggMonster();
        }
        System.out.println(M);
    }


    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        py = Integer.parseInt(st.nextToken()) - 1;
        px = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int my = Integer.parseInt(st.nextToken()) - 1;
            int mx = Integer.parseInt(st.nextToken()) - 1;
            int md = Integer.parseInt(st.nextToken()) - 1;
            aliveMonsterCnt[my][mx]++;
            aliveMonsterDirectCnt[my][mx][md]++;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) deadMonsterTime[i][j] = -2;
        }
        br.close();
    }

    static void print(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    static void duplicateAliveMonster() {
        // System.out.println(1 + " : " + M);
        M *= 2;
        // System.out.println(2 + " : " + M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (aliveMonsterCnt[i][j] == 0) continue;
                for (int k = 0; k < 8; k++) {
                    if (aliveMonsterDirectCnt[i][j][k] == 0) continue;
                    eggMonsterCnt[i][j][k] = aliveMonsterDirectCnt[i][j][k];
                }
            }
        }
    }

    static void moveAliveMonster() {
        int newAliveMonsterCnt[][] = new int[N][N];
        int newAliveMonsterDirectCnt[][][] = new int[N][N][8];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (aliveMonsterCnt[i][j] == 0) continue;
                for (int k = 0; k < 8; k++) {
                    if (aliveMonsterDirectCnt[i][j][k] == 0) continue;
                    boolean flag = false;
                    for (int d = 0; d < 8; d++) {
                        int nd = (k + d) % 8;
                        int nextY = i + dy[nd];
                        int nextX = j + dx[nd];
                        if (!isRange(nextY, nextX)) continue;
                        if (nextY == py && nextX == px) continue;
                        if (t - deadMonsterTime[nextY][nextX] <= 2) continue;
                        newAliveMonsterCnt[nextY][nextX] += aliveMonsterDirectCnt[i][j][k];
                        newAliveMonsterDirectCnt[nextY][nextX][nd] += aliveMonsterDirectCnt[i][j][k];
                        flag = true;
                        break;
                    }
                    if(!flag) {
                        newAliveMonsterCnt[i][j] += aliveMonsterDirectCnt[i][j][k];
                        newAliveMonsterDirectCnt[i][j][k] += aliveMonsterDirectCnt[i][j][k];
                    }

                }
            }
        }
        aliveMonsterCnt = newAliveMonsterCnt;
        aliveMonsterDirectCnt = newAliveMonsterDirectCnt;

    }

    static void movePacman() {
        direct = new int[]{6, 6, 6};
        dieM = 0;

        dfs(new int[]{6, 6, 6}, py, px, 0, 0);

        // System.out.print("ans : ");
        for (int i = 0; i < 3; i++) {
            // System.out.print(direct[i]+" ");
            py += dy[direct[i]];
            px += dx[direct[i]];
            if (aliveMonsterCnt[py][px] == 0) continue;
            for (int j = 0; j < 8; j++) {
                aliveMonsterDirectCnt[py][px][j] = 0;
            }
            deadMonsterTime[py][px] = t;
            aliveMonsterCnt[py][px] = 0;
        }
        // System.out.println();

        M -= dieM;
    }

    static void dfs(int[] dir, int y, int x, int move, int cnt) {
        if (move == 3) {
            // System.out.println(cnt+": "+dir[0]+", "+dir[1]+", "+dir[2]);
            if (cnt > dieM) {
                direct[0] = dir[0];
                direct[1] = dir[1];
                direct[2] = dir[2];
                dieM = cnt;
            } else if (cnt == dieM) {
                if (direct[0] > dir[0]) {
                    direct[0] = dir[0];
                    direct[1] = dir[1];
                    direct[2] = dir[2];
                } else if (direct[0] == dir[0]) {
                    if (direct[1] > dir[1]) {
                        direct[1] = dir[1];
                        direct[2] = dir[2];
                    } else if (direct[1] == dir[1]) {
                        if (direct[2] > dir[2]) {
                            direct[2] = dir[2];
                        }
                    }
                }
            }
            return;
        }

        for (int i = 0; i < 8; i += 2) {
            int nextY = y + dy[i];
            int nextX = x + dx[i];

            if (!isRange(nextY, nextX)) continue;

            dir[move] = i;

            if (visited[nextY][nextX]) dfs(dir, nextY, nextX, move + 1, cnt);
            else {
                visited[nextY][nextX] = true;
                dfs(dir, nextY, nextX, move + 1, cnt + aliveMonsterCnt[nextY][nextX]);
                visited[nextY][nextX] = false;
            }

        }

    }


    static void birthEggMonster() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 8; k++) {
                    if (eggMonsterCnt[i][j][k] == 0) continue;
                    aliveMonsterDirectCnt[i][j][k] += eggMonsterCnt[i][j][k];
                    aliveMonsterCnt[i][j] += eggMonsterCnt[i][j][k];
                    eggMonsterCnt[i][j][k] = 0;
                }
            }
        }
        // System.out.println(3 + " : " + M);
    }


    static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}

