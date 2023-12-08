package codetree.samsung.n2023_2_2_1;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 상 우 하 좌
    static final int[] dy = {-1, 0, 1, 0, 1, 1, -1, -1};
    static final int[] dx = {0, 1, 0, -1, 1, -1, 1, -1};
    static final int MAX_N = 50, MAX_P = 30;
    static int map[][] = new int[MAX_N][MAX_N]; // 산타가 있는 맵 정보
    static int pArr[][] = new int[MAX_P + 1][4];
    static int N, M, P, C, D, ry, rx, cnt;

    public static void main(String[] args) throws Exception {
        init();
        for (int m = 1; m <= M; m++) {
            simulate(m);
            if (cnt == 0) break;
        }
        getAns();
    }

    private static void getAns() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= P; i++) {
            sb.append(pArr[i][3]).append(" ");
        }
        System.out.println(sb);
    }


    private static void simulate(int t) {
        // 루돌프 움직임
        moveR(t);

        // 산타 움직임
        moveS(t);

        // 점수 얻기
        getScore();

        // print();

    }

    private static void print() {
        System.out.println(ry + ", " + rx);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void getScore() {
        for (int i = 1; i <= P; i++) {
            if (pArr[i][2] == -1) continue;
            pArr[i][3]++;
        }
    }

    private static void moveS(int t) {
        for (int i = 1; i <= P; i++) {
            if (pArr[i][2] == -1 || pArr[i][2] + 1 == t || pArr[i][2] == t) continue;

            int ny = pArr[i][0];
            int nx = pArr[i][1];
            int nd = -1;
            int dist = getDist(ny, nx, ry, rx);

            for (int d = 0; d < 4; d++) {
                int nextY = pArr[i][0] + dy[d];
                int nextX = pArr[i][1] + dx[d];

                if (!isRange(nextY, nextX)) continue;
                if (map[nextY][nextX] > 0) continue;

                int currD = getDist(nextY, nextX, ry, rx);
                if (currD < dist) {
                    ny = nextY;
                    nx = nextX;
                    nd = d;
                    dist = currD;
                }
            }

            if (nd == -1) continue;

            map[pArr[i][0]][pArr[i][1]] = 0;

            if (ny == ry && nx == rx) {
                int nextY = ny - D * dy[nd];
                int nextX = nx - D * dx[nd];

                pArr[i][3] += D;
                pArr[i][2] = t;
                int currPI = i;

                while (isRange(nextY, nextX) && map[nextY][nextX] > 0) {
                    int nextPI = map[nextY][nextX];
                    map[nextY][nextX] = currPI;
                    pArr[currPI][0] = nextY;
                    pArr[currPI][1] = nextX;
                    currPI = nextPI;
                    nextY -= dy[nd];
                    nextX -= dx[nd];
                }

                if (!isRange(nextY, nextX)) {
                    pArr[currPI][2] = -1;
                    cnt--;
                } else {
                    map[nextY][nextX] = currPI;
                    pArr[currPI][0] = nextY;
                    pArr[currPI][1] = nextX;
                }
            } else {
                pArr[i][0] = ny;
                pArr[i][1] = nx;
                map[ny][nx] = i;
            }

        }
    }

    private static void moveR(int t) {
        int dist = Integer.MAX_VALUE;
        int r = ry;
        int c = rx;
        int pi = 0;
        for (int i = 1; i <= P; i++) {
            if (pArr[i][2] == -1) continue;
            int[] currP = pArr[i];

            int currD = getDist(ry, rx, currP[0], currP[1]);
            if (currD < dist) {
                r = currP[0];
                c = currP[1];
                dist = currD;
                pi = i;
            } else if (currD == dist) {
                if (r < currP[0]) {
                    r = currP[0];
                    c = currP[1];
                    pi = i;
                } else if (r == currP[0]) {
                    if (c < currP[1]) {
                        c = currP[1];
                        pi = i;
                    }
                }
            }
        }

        int ny = 0;
        int nx = 0;
        int nd = 0;

        for (int d = 0; d < 8; d++) {
            int nextY = ry + dy[d];
            int nextX = rx + dx[d];
            if (!isRange(nextY, nextX)) continue;

            int currD = getDist(nextY, nextX, r, c);
            if (currD < dist) {
                ny = nextY;
                nx = nextX;
                dist = currD;
                nd = d;
            }
        }

        if (r == ny && c == nx && pArr[pi][2] != t + 1) {
            int nextY = r + C * dy[nd];
            int nextX = c + C * dx[nd];

            map[r][c] = 0;
            pArr[pi][3] += C;
            pArr[pi][2] = t;

            int currPI = pi;
            while (isRange(nextY, nextX) && map[nextY][nextX] > 0) {
                int nextPI = map[nextY][nextX];
                map[nextY][nextX] = currPI;
                pArr[currPI][0] = nextY;
                pArr[currPI][1] = nextX;
                currPI = nextPI;
                nextY += dy[nd];
                nextX += dx[nd];
            }

            if (!isRange(nextY, nextX)) {
                pArr[currPI][2] = -1;
                cnt--;
            } else {
                map[nextY][nextX] = currPI;
                pArr[currPI][0] = nextY;
                pArr[currPI][1] = nextX;
            }
        }

        ry = ny;
        rx = nx;
    }


    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }


    private static int getDist(int y1, int x1, int y2, int x2) {
        int subY = y1 - y2;
        int subX = x1 - x2;
        return subY * subY + subX * subX;
    }

    private static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cnt = P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        ry = Integer.parseInt(st.nextToken()) - 1;
        rx = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int pi = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;

            pArr[pi][0] = y;
            pArr[pi][1] = x;
            pArr[pi][2] = -2;
            map[y][x] = pi;
        }
    }
}
