package codetree.samsung.n2021_2_1_1;

import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int MAX_N = 25;
    static final int dy[] = {0, 1, 0, -1};
    static final int dx[] = {1, 0, -1, 0};

    static StringTokenizer st;
    static int map[][] = new int[MAX_N][MAX_N];
    // 미로의 다음 방향을 나타냄
    static int next[][][] = new int[MAX_N][MAX_N][2];
    // 미로의 재비치된 배열 내용
    static int reArr[] = new int[MAX_N * MAX_N];
    static int N, M, mCnt, ans;

    public static void main(String[] args) throws Exception {
        init();
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            attack(d, p);
            while (delete()) ;
            relocate();
            initDirect(true);
        }
        System.out.println(ans);

        br.close();
    }

    private static void relocate() {
        mCnt = 0;
        int y = N / 2 + 1;
        int x = N / 2 + 1;
        int type = 0, cnt = 0;
        while (true) {
            int nextY = next[y][x][0];
            int nextX = next[y][x][1];
            if (!isRange(nextY, nextX)) break;
            if (type != map[nextY][nextX]) {
                if (type != 0) {
                    reArr[mCnt++] = cnt;
                    reArr[mCnt++] = type;
                }
                type = map[nextY][nextX];
                cnt = 0;
            } else {
                cnt++;
            }
        }
    }

    private static boolean delete() {
        boolean flag = false;

        int y = N / 2 + 1;
        int x = N / 2 + 1;
        int type = 0, cnt = 0;
        int sy = y, sx = x;
        while (true) {
            int nextY = next[y][x][0];
            int nextX = next[y][x][1];
            if (!isRange(nextY, nextX)) break;
            if (type != map[nextY][nextX]) {
                if (cnt >= 4) {
                    ans += type * cnt;
                    next[sy][sx][0] = nextY;
                    next[sy][sx][1] = nextX;
                    if (!flag) flag = true;
                }
                type = map[nextY][nextX];
                cnt = 0;
                sy = nextY;
                sx = nextX;
            } else {
                cnt++;
            }
        }
        return flag;
    }

    private static void attack(int d, int p) {
        int y = N / 2 + 1 + dy[d];
        int x = y + dx[d];

        while (isRange(y, x) && p-- > 0) {
            ans += map[y][x];
            map[y][x] = 0;
        }
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }

        initDirect(false);
    }

    private static void initDirect(boolean flag) {
        // 좌 하 우 상
        int l = 0;
        int L = 1;
        int d = 3; // --

        int i = 0;
        int y = N / 2 + 1;
        int x = N / 2 + 1;

        int m = 0;

        while (true) {
            if ((!flag && map[next[y][x][0]][next[y][x][1]] == 0) ||
            flag && m == mCnt) {
                next[y][x][0] = -1;
                next[y][x][1] = -1;
                break;
            }

            next[y][x][0] = y + dy[d];
            next[y][x][1] = x + dx[d];

            y = next[y][x][0];
            x = next[y][x][1];

            if (!isRange(y, x)) break;

            if (flag) map[y][x] = reArr[m++];

            l++;
            if (l == L) {
                i++;
                d = (d - 1 + 4) % 4;
                l = 0;
                if (i % 2 == 0) L++;
            }
        }
    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}
