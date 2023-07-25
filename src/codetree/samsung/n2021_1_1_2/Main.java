package codetree.samsung.n2021_1_1_2;

import java.io.*;
import java.util.*;

public class Main {
    // 상 하 좌 우
    static final int dy[] = {1, -1, 0, 0};
    static final int dx[] = {0, 0, -1, 1};
    static final int MAX_N = 20;
    static int map[][];
    static boolean flag, visited[][];

    static int T, D;
    static int tempArea[][] = new int[MAX_N * MAX_N][2];
    static int selectedArea[][] = new int[MAX_N * MAX_N][2];

    // size +, redSize -, r +, c -;
    static int tempInfo[];
    static int selectedInfo[];

    static int N, M, ans;

    public static void main(String[] args) throws Exception {
        init();
        while (true) {
            if (!deleteBombs()) break;
            gravity();
            rotate();
            gravity();
        }
        System.out.println(ans);
    }

    private static void print(String title) {
        System.out.println(title);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static boolean deleteBombs() {
        flag = false;
        visited = new boolean[N][N];
        selectedInfo = new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE};

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] <= 0) continue;    // 돌멩이 혹은, 빈칸, 빨간 칸
                if (visited[i][j]) continue;    // 이미 방문한 칸
                tempInfo = new int[]{0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE};
                T = 0;
                dfs(i, j, map[i][j]);
                resetVisit();
                if (T <= 1) continue; // 가능한 영역이 1이라면 불가능
                checkArea();
                flag = true;
            }
        }

        if (!flag) return false;

        // delete
        for (int i = 0; i < D; i++) {
            int y = selectedArea[i][0];
            int x = selectedArea[i][1];
            map[y][x] = -2;
        }

        ans += D * D;
        D = 0;

        return true;
    }

    private static void resetVisit() {
        for (int i = 0; i < T; i++) {
            int y = tempArea[i][0];
            int x = tempArea[i][1];
            if (map[y][x] == 0) visited[y][x] = false;
        }
    }

    private static void checkArea() {
        int idx = -1;
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                if (selectedInfo[i] == tempInfo[i]) continue;
                else if (selectedInfo[i] < tempInfo[i]) idx = i;
                break;
            } else {
                if (selectedInfo[i] == tempInfo[i]) continue;
                else if (selectedInfo[i] > tempInfo[i]) idx = i;
                break;
            }
        }

        if (idx == -1) return;

        for (int i = idx; i < 4; i++) selectedInfo[i] = tempInfo[i];

        for (int i = 0; i < T; i++) {
            selectedArea[i][0] = tempArea[i][0];
            selectedArea[i][1] = tempArea[i][1];
        }

        D = T;
    }

    private static void dfs(int y, int x, int target) {
        visited[y][x] = true;
        tempInfo[0]++;
        if (map[y][x] == 0) tempInfo[1]++;
        else {
            tempInfo[2] = Math.max(tempInfo[2], y);
            tempInfo[3] = Math.min(tempInfo[3], x);
        }

        tempArea[T][0] = y;
        tempArea[T++][1] = x;

        for (int i = 0; i < 4; i++) {
            int nextY = y + dy[i];
            int nextX = x + dx[i];
            if (!isRange(nextY, nextX)) continue;
            if (visited[nextY][nextX]) continue;
            if (map[nextY][nextX] < 0) continue;
            if (map[nextY][nextX] == 0 || map[nextY][nextX] == target) {
                dfs(nextY, nextX, target);
            }
        }
    }

    // 2, 4. 중력 작용
    private static void gravity() {
        for (int j = 0; j < N; j++) {
            int i = 0;
            while (i < N) {
                int k = i;
                int blank = 0;
                int d = 0;
                int data[] = new int[N];
                for (; k < N; k++) {
                    if (map[k][j] == -1) break;
                    if (map[k][j] == -2) blank++;
                    else data[d++] = map[k][j];
                }

                for (int idx = i, dd = 0; idx < k && dd < d; idx++) {
                    if (blank-- > 0) map[idx][j] = -2;
                    else map[idx][j] = data[dd++];
                }

                i = k + 1;
            }
        }
    }

    // 3. 회전
    private static void rotate() {
        int newMap[][] = new int[N][N];

        int b = 0;
        for (int i = 0; i < N; i++) {
            int a = N - 1;
            for (int j = 0; j < N; j++) {
                newMap[a][b] = map[i][j];
                a--;
            }
            b++;
        }
        map = newMap;
    }


    private static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.close();
    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}
/*
-1 : 검은색 돌
0 : 빨간색 폭탄
1~m : 서로 다른 색의 폭탄

1) 폭탄 묶음 선택
1. 크기가 같다면 빨간색 폭탄이 가장 적게 포함
2. 1번의 조건이 같다면, 가장 큰 행이 포함된 묶음
3. 2번의 조건이 같다면, 가장 작은 열이 포함된 묶음

2) 선택한 폭탄 묶음 제거

3) 중력 작용
- 돌 위에 있는 폭탄은 움직이지 않음
- 그렇지 않은 폭탄들은 아래에 돌을 만날 때까지 아래로 이동

4) 반시계 방향으로 90도만큼 격자 판에 회전이 일어남

5) 다시 중력이 작용

-> 터진 폭탄의 점수를 +
 */