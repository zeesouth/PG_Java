package codetree.samsung.n2023_2_1_1;

import java.util.*;
import java.io.*;

// 복기
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static final int dy[] = {-1, 0, 1, 0};
    static final int dx[] = {0, 1, 0, -1};

    static final int MAX_L = 40, MAX_N = 30;
    static int[][] map = new int[MAX_L][MAX_L];
    static int[][] rMap = new int[MAX_L][MAX_L];
    static Robot robot[] = new Robot[MAX_N + 1];
    static int L, N, Q;

    public static void main(String[] args) throws Exception {
        init();
        while (Q-- > 0) {
            simulate();
        }
        System.out.println(getAns());
    }

    static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < L; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                rMap[i][j] = 0;
            }
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            robot[i] = new Robot(y, x, h, w, k);

            for (int r = y; r < y + h; r++) {
                for (int c = x; c < x + w; c++) {
                    rMap[r][c] = i;
                }
            }
        }
    }

    static void simulate() throws Exception {
        st = new StringTokenizer(br.readLine());
        int i = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        if (robot[i].d >= robot[i].k) return;

        ArrayList<Integer> mv = move(i, d);

        if (mv == null || mv.size() == 0) return;

        damage(mv, i, d);
    }

    static ArrayList<Integer> move(int id, int d) {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(id);

        boolean visited[] = new boolean[N + 1];
        Queue<Integer> q = new LinkedList<>();
        visited[id] = true;
        q.add(id);

        while (!q.isEmpty()) {
            int currID = q.poll();
            Robot currR = robot[currID];

            int y = currR.y;
            int x = currR.x;
            int h = currR.h;
            int w = currR.w;

            if (d == 1) {
                x += w - 1;
            } else if (d == 2) {
                y += h - 1;
            }

            y += dy[d];
            x += dx[d];

            // 상하
            if (d % 2 == 0) {
                for (int j = x; j < x + w; j++) {
                    if (!isRange(y, j)) return null;

                    if (rMap[y][j] > 0) {
                        int nextR = rMap[y][j];
                        if (visited[nextR]) continue;
                        visited[nextR] = true;
                        q.add(nextR);
                        arr.add(nextR);
                    }
                }
            }
            // 좌우
            else {
                for (int i = y; i < y + h; i++) {
                    if (!isRange(i, x)) return null;

                    if (rMap[i][x] > 0) {
                        int nextR = rMap[i][x];
                        if (visited[nextR]) continue;
                        visited[nextR] = true;
                        q.add(nextR);
                        arr.add(nextR);
                    }
                }
            }

        }

        return arr;
    }

    static void damage(ArrayList<Integer> arr, int id, int d) {
        // 맵 이동하기
        for (int i = arr.size() - 1; i >= 0; i--) {
            Robot currR = robot[arr.get(i)];
            int currID = arr.get(i);

            if (d % 2 == 0) {
                for (int x = currR.x; x < currR.x + currR.w; x++) {
                    // 위
                    if (d == 0) {
                        rMap[currR.y - 1][x] = currID;
                        rMap[currR.y + currR.h - 1][x] = 0;
                    } else {
                        rMap[currR.y + currR.h][x] = currID;
                        rMap[currR.y][x] = 0;
                    }
                }
            } else {
                for (int y = currR.y; y < currR.y + currR.h; y++) {
                    // 오른
                    if (d == 1) {
                        rMap[y][currR.x + currR.w] = currID;
                        rMap[y][currR.x] = 0;
                    } else {
                        rMap[y][currR.x - 1] = currID;
                        rMap[y][currR.x + currR.w - 1] = 0;
                    }
                }
            }

            currR.y += dy[d];
            currR.x += dx[d];
        }


        // 데미지 구하기
        for (int i = 1; i < arr.size(); i++) {
            Robot currR = robot[arr.get(i)];

            for (int y = currR.y; y < currR.y + currR.h; y++) {
                for (int x = currR.x; x < currR.x + currR.w; x++) {
                    if (map[y][x] == 1) {
                        currR.d++;
                    }
                }
            }
        }

        // 로봇 제거하기
        for (int i = 0; i < arr.size(); i++) {
            Robot currR = robot[arr.get(i)];

            if (currR.d < currR.k) continue;

            for (int y = currR.y; y < currR.y + currR.h; y++) {
                for (int x = currR.x; x < currR.x + currR.w; x++) {
                    rMap[y][x] = 0;
                }
            }
        }

    }

    static int getAns() {
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            if (robot[i].d < robot[i].k) sum += robot[i].d;
        }
        return sum;
    }

    static boolean isRange(int y, int x) {
        return y >= 0 && y < L && x >= 0 && x < L && map[y][x] != 2;
    }

    static class Robot {
        int y, x, h, w, k, d;

        Robot(int y, int x, int h, int w, int k) {
            this.y = y;
            this.x = x;
            this.h = h;
            this.w = w;
            this.k = k;
        }
    }
}
