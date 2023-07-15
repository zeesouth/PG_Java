package codetree.samsung.n2022_1_1_1;
// https://www.codetree.ai/training-field/frequent-problems/problems/hide-and-seek/description?page=3&pageSize=20

import java.io.*;
import java.util.*;

public class Main {
    static final int dy[] = {0, 1, 0, -1};
    static final int dx[] = {1, 0, -1, 0};
    static final int MAX_N = 99, MAX_M = 99 * 99;
    static int fMap[][] = new int[MAX_N + 1][MAX_N + 1];
    static int dMap[][] = new int[MAX_M * 2 - 2][3];
    static boolean tMap[][] = new boolean[MAX_N + 1][MAX_N + 1];
    static int N, M, H, K;
    static int ay, ax, by, bx;
    static Pair tagger, fugitive[] = new Pair[MAX_M + 1];
    static int ans = 0;

    public static void main(String[] args) throws Exception {
        init();
        for (int k = 1; k <= K; k++) {
            // print();
            moveF();
            // print();
            moveT(k);
            // print();
        }
        System.out.println(ans);
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // map 가로, 세로 사이즈
        M = Integer.parseInt(st.nextToken());   // 도망자 수
        H = Integer.parseInt(st.nextToken());   // 나무 수
        K = Integer.parseInt(st.nextToken());   // 테스트 수

        tagger = new Pair(N / 2 + 1, N / 2 + 1, 3);

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) - 1;
            fugitive[i] = new Pair(y, x, d);
            fMap[y][x]++;
        }

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            tMap[y][x] = true;
        }

        br.close();

        // 술래의 이동 방향 채워넣기 - 정방향
        int idx = 0;
        int turn = 1;
        int len = 1;
        int y = tagger.y, x = tagger.x, d = tagger.d;
        int ny = y + len * dy[d], nx = x + len * dx[d];

        while (!(y == 1 && x == 1)) {
            if (y == ny && x == nx) {
                d = (d + 1) % 4;
                turn++;
                ny = y + len * dy[d];
                nx = x + len * dx[d];
                if (turn % 2 == 0) len++;
            }
            dMap[idx] = new int[]{y, x, d};
            y += dy[d];
            x += dx[d];
            idx++;
        }


        // 술래의 이동 방향 채워놓기 - 역방향
        y = 1;
        x = 1;
        d = 1;
        ny = N;
        nx = 1;
        while (!(y == N / 2 + 1 && x == N / 2 + 1)) {
            if (y == ny && x == nx) {
                d = (d - 1 + 4) % 4;
                turn++;
                if (turn % 2 == 0) len--;
                ny = y + len * dy[d];
                nx = x + len * dx[d];
            }
            dMap[idx] = new int[]{y, x, d};
            y += dy[d];
            x += dx[d];
            idx++;
        }

//        for (int i = 0; i < N * N * 2 - 2; i++) {
//            System.out.println(dMap[i][0] + ", " + dMap[i][1] + ", " + dMap[i][2]);
//        }

    }

    // 1. 도망자 움직이기
    static void moveF() {

        for (int i = 1; i <= M; i++) {
            Pair currF = fugitive[i];
            // 이미 잡힌 도망자는 넘기기
            if (currF == null) continue;
            else {
                if (currF.y >= ay && currF.x >= ax && currF.y <= by && currF.x <= bx) {
                    if (!tMap[currF.y][currF.x]) {
                        fugitive[i] = null;
                        continue;
                    }
                }
            }

            // 술래와의 거리가 3 초과라면 넘기기
            if (getDist(tagger.y, tagger.x, currF.y, currF.x) > 3) continue;

            int nextY = currF.y + dy[currF.d];
            int nextX = currF.x + dx[currF.d];

            if (!isRange(nextY, nextX)) {
                // 격자 밖을 벗어나면 반대 방향으로 전환하기
                currF.d = (currF.d + 2) % 4;
                nextY = currF.y + dy[currF.d];
                nextX = currF.x + dx[currF.d];
            }

            // 술래와 같은 위치라면 이동하지 않고 넘기기
            if (tagger.equals(nextY, nextX)) continue;

            fMap[currF.y][currF.x]--;
            currF.y = nextY;
            currF.x = nextX;
            fMap[nextY][nextX]++;
        }
    }

    // 2. 술래 움직이기
    static void moveT(int t) {

        int mv = t % (N * N * 2 - 2);
        tagger.y = dMap[mv][0];
        tagger.x = dMap[mv][1];
        tagger.d = dMap[mv][2];
        catchF(t);
    }

    static void catchF(int t) {
        int sum = 0;
        int y = tagger.y;
        int x = tagger.x;
        // System.out.println(y+","+x+","+tagger.d);

        ay = Math.min(y, y + 2 * dy[tagger.d]);
        ax = Math.min(x, x + 2 * dx[tagger.d]);
        by = Math.max(y, y + 2 * dy[tagger.d]);
        bx = Math.max(x, x + 2 * dx[tagger.d]);

        for (int i = 1; i <= 3; i++) {
            if (!tMap[y][x]) {
                if (fMap[y][x] > 0) {
                    sum += fMap[y][x];
                    fMap[y][x] = 0;
                }
            }
            y += dy[tagger.d];
            x += dx[tagger.d];
            if (!isRange(y, x)) break;
        }
        ans += (sum * t);
        // System.out.println(sum+", "+t+", "+ans);
    }

    static int getDist(int y1, int x1, int y2, int x2) {
        return Math.abs(y1 - y2) + Math.abs(x1 - x2);
    }

    static boolean isRange(int y, int x) {
        return y >= 1 && y <= N && x >= 1 && x <= N;
    }

    static class Pair {
        int y, x, d;

        Pair(int y, int x, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }

        public boolean equals(int y, int x) {
            return this.y == y && this.x == x;
        }
    }


    static void print() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) System.out.print(fMap[i][j] + "(" + tMap[i][j] + ")" + "\t\t");
            System.out.println();
        }
        System.out.println();
    }

}

/*
(1턴)
1. m명의 도망자가 먼저 움직인다. (단, 술래와의 거리가 3 이하인 도망자)
    1-1. 현재 바라보고 있는 방향으로 1칸 움직였을 때, 격자를 벗어나지 않는 경우
         -> 움직일 칸에 술래가 있다면 움직이지 않음
         -> 움직일 칸에 술래가 없으면 해당 칸으로 이동. 나무가 있어도 괜찮음
    1-2. 현재 바라보고 있는 방향으로 1칸 움직였을 때, 격자를 벗어나는 경우
         -> 방향을 반대로 틀어주기.
            이후, 바라보고 있는 방향으로 1칸 움직있다 했을 때, 해당 위치에 술래가 없다면 1칸 이동
2. 술래가 움직인다.
    처음엔 윗 방향으로 시작하여 달팽이 모양 (시계방향)으로 움직임
    만약 다시 끝에 도달하게 되면, 거꾸로 이동하여 중심으로 이동

    + 그리고 이동 후, 만약 방향이 틀어지는 지점이라면 바로 방향 바꾸어주기.

      술래의 방향 시야 내 도망자가 있다면 잡아주기, 술래의 시야는 현재 칸을 포함하여 3칸
      하지만 나무가 놓여있는 칸이라면 잡을 수 없음

거리 = |x1 - x2| + |y1 - y2|


 */
