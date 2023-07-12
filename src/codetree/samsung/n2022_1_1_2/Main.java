package codetree.samsung.n2022_1_1_2;

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_N = 29;
    static int map[][] = new int[MAX_N][MAX_N];
    static int group[][];
    static boolean visited[][];
    static int gSum[];
    static final int dy[] = {1, 0, -1, 0};
    static final int dx[] = {0, 1, 0, -1};
    static int N, gCnt;
    static int T = 3, ans = 0;

    public static void main(String[] args) throws Exception {
        init();
        getScore();

        while (T-- > 0) {
            rotate();
            getScore();
        }

        System.out.println(ans);
    }

    // 0. 초기화
    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }
        br.close();
    }

    // 1. 예술 점수 구하기
    static void getScore() {
        visited = new boolean[N][N];
        group = new int[N][N];
        gSum = new int[N * N + 1];
        gCnt = 0;

        // 1-1. 그룹 나누기
        divideGroup();

        // 1-2. 점수 계산하기
        calculateScore();
    }

    static void divideGroup() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    gCnt++;
                    visited[i][j] = true;
                    group[i][j] = gCnt;
                    gSum[gCnt] = 1;
                    dfs(i, j);
                }
            }
        }
    }

    static void dfs(int y, int x) {
        for (int i = 0; i < 4; i++) {
            int currY = dy[i] + y;
            int currX = dx[i] + x;

            if (isValid(currY, currX) && !visited[currY][currX] && map[y][x] == map[currY][currX]) {
                visited[currY][currX] = true;
                group[currY][currX] = gCnt;
                gSum[gCnt]++;
                dfs(currY, currX);
            }
        }
    }

    static void calculateScore() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 4; k++) {
                    int currY = i + dy[k];
                    int currX = j + dx[k];
                    if (isValid(currY, currX) && map[i][j] != map[currY][currX]) {
                        int g1 = group[i][j], g2 = group[currY][currX];
                        int num1 = map[i][j], num2 = map[currY][currX];
                        int cnt1 = gSum[g1], cnt2 = gSum[g2];
                        sum += (cnt1 + cnt2) * num1 * num2;
                    }
                }
            }
        }
        ans += sum / 2;
    }


    // 2. 회전
    static void rotate() {
        int beforeMap[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            beforeMap[i] = Arrays.copyOfRange(map[i], 0, N);
        }

        // 가운데 십자 모양 : 시계 반대 방향으로 90도 회전
        int targetIdx = N / 2;
        for (int i = 0; i < N; i++) map[N - 1 - i][targetIdx] = beforeMap[targetIdx][i];
        for (int i = 0; i < N; i++) map[targetIdx][i] = beforeMap[i][targetIdx];

        // 나머지 4개의 사각형 : 시계 방향으로 90도 회전
        targetIdx = N / 2 + 1;
        int L = N / 2;

        int[] startY = {0, targetIdx, 0, targetIdx};
        int[] startX = {0, 0, targetIdx, targetIdx};

        for (int t = 0; t < 4; t++) {
            int b = startX[t] + L - 1;
            for (int i = startY[t]; i < startY[t] + L; i++) {
                int a = startY[t];
                for (int j = startX[t]; j < startX[t] + L; j++) {
                    map[a][b] = beforeMap[i][j];
                    a++;
                }
                b--;
            }
        }
    }

    static void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) System.out.print(map[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}
