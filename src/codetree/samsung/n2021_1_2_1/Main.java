package codetree.samsung.n2021_1_2_1;

import java.io.*;
import java.util.*;

public class Main {
    static final int dy[] = {0, -1, -1, -1, 0, 1, 1, 1};
    static final int dx[] = {1, 1, 0, -1, -1, -1, 0, 1};
    static final int MAX_N = 15, MAX_M = MAX_N * MAX_N;
    static int map[][] = new int[MAX_N][MAX_N];
    static int leaf[][] = new int[MAX_M][2];
    static boolean visited[][] = new boolean[MAX_N][MAX_N];
    static int N, M, L, ans;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;

    public static void main(String[] args) throws Exception {
        init();
        while (M-- > 0) {
            move();
            grow();
            cutAndChange();
        }
        System.out.println(ans);
        br.close();
    }

    private static void cutAndChange() {
        L = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j]) {
                    visited[i][j] = false;
                    continue;
                }

                if (map[i][j] >= 2) {
                    leaf[L][0] = i;
                    leaf[L][1] = j;
                    map[i][j] -= 2;
                    ans -= 2;
                    L++;
                }
            }
        }

    }

    private static void grow() {

        int plus[] = new int[L];

        for (int i = 0; i < L; i++) {
            int y = leaf[i][0];
            int x = leaf[i][1];

            for (int j = 1; j < 8; j += 2) {
                int sideY = y + dy[j];
                int sideX = x + dx[j];

                if (!isRange(sideY, sideX)) continue;
                if (map[sideY][sideX] == 0) continue;
                plus[i]++;
                ans++;
            }
        }

        for (int i = 0; i < L; i++) {
            int y = leaf[i][0];
            int x = leaf[i][1];
            map[y][x] += plus[i];
            visited[y][x] = true;
        }

    }

    private static void move() throws Exception {
        st = new StringTokenizer(br.readLine());
        int d = Integer.parseInt(st.nextToken()) - 1;
        int p = Integer.parseInt(st.nextToken());

        for (int i = 0; i < L; i++) {
            int nextY = (leaf[i][0] + dy[d] * p + N) % N;
            int nextX = (leaf[i][1] + dx[d] * p + N) % N;
            map[nextY][nextX] += 1;
            leaf[i][0] = nextY;
            leaf[i][1] = nextX;
            ans += 1;
        }

    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                ans += (map[i][j] = Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = N - 1; i >= N - 2; i--) {
            for (int j = 0; j < 2; j++) {
                leaf[L][0] = i;
                leaf[L][1] = j;
                L++;
            }
        }
    }
}
