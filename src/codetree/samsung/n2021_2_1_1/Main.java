package codetree.samsung.n2021_2_1_1;
// https://www.codetree.ai/training-field/frequent-problems/problems/cube-rounding-again/description?page=2&pageSize=20

import java.io.*;
import java.util.*;

public class Main {
    private static final int[] dy = {0, 1, 0, -1};
    private static final int[] dx = {1, 0, -1, 0};
    private static final int MAX_N = 20;
    private static int map[][] = new int[MAX_N][MAX_N];
    private static boolean visited[][];
    private static int dice[] = {0, 1, 2, 3, 4, 5, 6};  // 상 (남 동 서 북) 하
    private static int N, M, Y, X, D, ans;

    /*
          5
        4 1 3
          2
          6
     */

    private static void moveDice(int type) {
        int temp = 0;
        switch (type) {
            case 0:     // 오른쪽
                temp = dice[1];
                dice[1] = dice[4];
                dice[4] = dice[6];
                dice[6] = dice[3];
                dice[3] = temp;
                break;
            case 1:     // 아래쪽
                temp = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[6];
                dice[6] = dice[2];
                dice[2] = temp;
                break;
            case 2:     // 왼쪽
                temp = dice[1];
                dice[1] = dice[3];
                dice[3] = dice[6];
                dice[6] = dice[4];
                dice[4] = temp;
                break;
            case 3:     // 위쪽
                temp = dice[1];
                dice[1] = dice[2];
                dice[2] = dice[6];
                dice[6] = dice[5];
                dice[5] = temp;
                break;
        }
    }


    public static void main(String[] args) throws Exception {
        init();
        while (M-- > 0) {
            move();
            getScore();
            compareNumber();
        }
        System.out.println(ans);
    }

    private static void compareNumber() {
        if(dice[6] == map[Y][X]) return;

        if (dice[6] > map[Y][X]) {
            D = (D + 1) % 4;
        } else {
            D = (D - 1 + 4) % 4;
        }
    }

    private static void getScore() {
        visited = new boolean[N][N];
        dfs(Y, X, map[Y][X]);
    }

    private static void dfs(int y, int x, int target) {
        visited[y][x] = true;
        ans += target;

        for (int i = 0; i < 4; i++) {
            int nextY = y + dy[i];
            int nextX = x + dx[i];

            if (!isRange(nextY, nextX)) continue;
            if (visited[nextY][nextX]) continue;
            if (map[nextY][nextX] != target) continue;

            dfs(nextY, nextX, target);
        }
    }

    private static void move() {
        if (!isRange(Y + dy[D], X + dx[D])) D = (D + 2) % 4;

        moveDice(D);

        Y += dy[D];
        X += dx[D];
    }

    private static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }
    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}
