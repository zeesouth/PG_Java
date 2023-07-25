package codetree.samsung.n2021_1_2_2;

import java.io.*;
import java.util.*;

public class Main {
    static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int MAX_N = 25;
    static final int dy[] = {0, 1, 0, -1};
    static final int dx[] = {1, 0, -1, 0};

    static StringTokenizer st;
    static int map[][] = new int[MAX_N][MAX_N];
    // 실제 몬스터
    static Pair monsters[] = new Pair[MAX_N * MAX_N];

    // 끝 인덱스
    static int ey, ex;
    static int N, M, ans, mCnt;

    public static void main(String[] args) throws Exception {
        init();
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            attack(d, p);
            while (delete()) ;
            resetDirect();
        }
        System.out.println(ans);

        br.close();
    }

    private static void resetDirect() {

        int new_mCnt = 1;
        int arr[] = new int[MAX_N * MAX_N * 2];

        int idx = 0;
        int type = -1, cnt = 0;
        while (true) {
            idx = monsters[idx].next;
            if (idx == -1) {
                arr[new_mCnt++] = cnt;
                arr[new_mCnt++] = type;
                break;
            }
            if (type != monsters[idx].data) {
                if (type != -1) {
                    arr[new_mCnt++] = cnt;
                    arr[new_mCnt++] = type;

                }
                type = monsters[idx].data;
                cnt = 1;
            } else {
                cnt++;
            }
        }


        int newMap[][] = new int[N][N];

        int y = N / 2;
        int x = N / 2;

        int i = 0;
        int a = 0;
        int b = 1;
        int d = 2;

        mCnt = 0;

        do {
            monsters[mCnt] = new Pair(mCnt, y, x, arr[mCnt]);
            newMap[y][x] = mCnt;
            y += dy[d];
            x += dx[d];
            a++;
            if (a == b) {
                d = (d - 1 + 4) % 4;
                a = 0;
                i++;
                if (i % 2 == 0) b++;
            }
            mCnt++;
        } while (isRange(y, x) && mCnt < new_mCnt);

        monsters[0].before = -1;
        monsters[mCnt - 1].next = -1;
        map = newMap;
    }

    private static boolean delete() {
        boolean flag = false;

        int idx = 0;
        int start = 0;
        int type = 0, cnt = 0;

        while (true) {
            idx = monsters[idx].next;
            if (idx == -1) {
                if (cnt >= 4) {
                    ans += type * cnt;
                    monsters[monsters[start].before].next = idx;
                }
                break;
            }
            if (type != monsters[idx].data) {
                if (cnt >= 4) {
                    ans += type * cnt;
                    monsters[monsters[start].before].next = idx;
                    monsters[idx].before = monsters[start].before;
                    if (!flag) flag = true;
                }
                start = idx;
                type = monsters[idx].data;
                cnt = 1;
            } else cnt++;
        }
        return flag;
    }

    private static void attack(int d, int p) {
        int y = N / 2 + dy[d];
        int x = N / 2 + dx[d];
        do {
            int idx = map[y][x];
            ans += monsters[idx].data;
            // 현재 이전의 다음은, 현재 다음을 가리킴
            monsters[monsters[idx].before].next = monsters[idx].next;
            // 현재 다음의 이전은, 현재 이전을 가리킴
            if (monsters[idx].next != -1)
                monsters[monsters[idx].next].before = monsters[idx].before;
            y += dy[d];
            x += dx[d];
        } while (isRange(y, x) && --p > 0 && map[y][x] != 0);
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }

        initDirect();
    }

    private static void initDirect() {
        int y = N / 2;
        int x = N / 2;

        int i = 0;
        int a = 0;
        int b = 1;
        int d = 2;

        mCnt = 0;
        do {
            monsters[mCnt] = new Pair(mCnt, y, x, map[y][x]);
            map[y][x] = mCnt;
            y += dy[d];
            x += dx[d];
            a++;
            if (a == b) {
                d = (d - 1 + 4) % 4;
                a = 0;
                i++;
                if (i % 2 == 0) b++;
            }
            mCnt++;
        } while (isRange(y, x) && map[y][x] != 0);

        monsters[0].before = -1;
        monsters[mCnt - 1].next = -1;
    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private static void print(int arr[][]) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) System.out.print(arr[i][j] + " ");
            System.out.println();
        }
    }

    static class Pair {
        int id, before, next;
        int y, x, data;

        Pair(int id, int y, int x, int data) {
            this.y = y;
            this.x = x;
            this.data = data;
            this.id = id;
            this.before = id - 1;
            this.next = id + 1;
        }

    }
}