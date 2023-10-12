package codetree.samsung_re.n2022_2_2_1;

// https://www.codetree.ai/training-field/frequent-problems/problems/codetree-mon-bread/description?page=1&pageSize=20
// https://evecomcom.tistory.com/36 - 해설

import java.io.*;
import java.util.*;

public class Main {
    static final int[] dy = {-1, 0, 0, 1};
    static final int[] dx = {0, -1, 1, 0};
    static final int MAX_N = 15, MAX_M = 30;

    static int map[][] = new int[MAX_N][MAX_N];
    static boolean visited[][] = new boolean[MAX_N][MAX_N];
    static int end[][] = new int[MAX_M][2];    // 목적지
    static Queue<int[]> people = new LinkedList<>(); // 현재 조사할 사람들의 큐

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, T;

    public static void main(String[] args) throws Exception {
        init();
        while (true) {
            if (people.size() == 0 && T > 0) break;
            simulate(people.size());
            T++;
        }
        System.out.println(T);
    }

    private static void simulate(int size) {
        while (size-- > 0) {
            int[] t = people.poll();
            int[] add = bfs(t[0], t[1], t[2]);
            if (add[1] == end[t[0]][0] && add[2] == end[t[0]][1])
                visited[add[1]][add[2]] = true;
            else {
                people.offer(add);
            }
        }

        if (T < M) {
            people.offer(findHouse(T, end[T][0], end[T][1]));
        }
    }

    private static int[] bfs(int id, int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y, x, -1, -1});
        boolean[][] check = new boolean[N][N];
        check[y][x] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            if (curr[0] == end[id][0] && curr[1] == end[id][1]) {
                return new int[]{id, curr[2], curr[3]};
            }

            for (int i = 0; i < 4; i++) {
                int nextY = curr[0] + dy[i];
                int nextX = curr[1] + dx[i];

                if (!isRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (check[nextY][nextX]) continue;

                check[nextY][nextX] = true;

                if (curr[2] == -1) {
                    q.offer(new int[]{nextY, nextX, nextY, nextX});
                } else {
                    q.offer(new int[]{nextY, nextX, curr[2], curr[3]});
                }
            }
        }
        return null;
    }

    private static int[] findHouse(int id, int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x});
        boolean[][] check = new boolean[N][N];
        check[y][x] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            if (map[curr[0]][curr[1]] == 1) {
                visited[curr[0]][curr[1]] = true;
                return new int[]{id, curr[0], curr[1]};
            }

            for (int i = 0; i < 4; i++) {
                int nextY = curr[0] + dy[i];
                int nextX = curr[1] + dx[i];

                if (!isRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (check[nextY][nextX]) continue;

                check[nextY][nextX] = true;
                q.add(new int[]{nextY, nextX});
            }
        }

        return null;
    }


    static boolean isRange(int y, int x) {
        return y >= 0 && y < N & x >= 0 && x < N;
    }

    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // 사람 수 및 편의점 수
        M = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            end[i][0] = y;
            end[i][1] = x;
        }
    }
}
