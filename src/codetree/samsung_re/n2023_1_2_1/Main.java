package codetree.samsung_re.n2023_1_2_1;

import com.sun.security.jgss.GSSUtil;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int[] dy = {1, -1, 0, 0};
    static final int[] dx = {0, 0, 1, -1};
    static final int MAX_N = 10, MAX_M = 10;
    static int map[][] = new int[MAX_N][MAX_M];
    static int pMap[][] = new int[MAX_N][MAX_M];
    static HashSet<Integer> person = new HashSet<>();
    static int N, M, K, ey, ex, sum;

    public static void main(String[] args) throws Exception {
        init();

        while (K-- > 0) {
            simulate();
        }
        System.out.println(getAns());
    }

    private static void print(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String getAns() {
        StringBuilder sb = new StringBuilder();
        sb.append(sum).append("\n");
        sb.append(ey + 1).append(" ").append(ex + 1);
        return sb.toString();
    }

    private static void simulate() {
        // 참가자 이동
        move();

        if (person.size() == 0) return;

        // 회전
        rotate();
    }

    private static void rotate() {
        int minSize = N - 1;
        int ly = N - 1; // 좌상단 r
        int lx = N - 1; // 좌상단 c

        for (int key : person) {
            int py = key / 100;
            int px = key % 100;

            int maxY = Math.max(py, ey);
            int maxX = Math.max(px, ex);


            int size = Math.max(Math.abs(py - ey), Math.abs(px - ex));

            int minY = maxY - size;
            int minX = maxX - size;

            if (minY < 0) minY = 0;
            if (minX < 0) minX = 0;

            if (size < minSize) {
                minSize = size;
                ly = minY;
                lx = minX;
            } else if (size == minSize) {
                if (ly > minY) {
                    ly = minY;
                    lx = minX;
                } else if (ly == minY) {
                    if (lx > minX) {
                        lx = minX;
                    }
                }
            }
        }

        int[][] newMap = new int[minSize + 1][minSize + 1];
        int[][] newpMap = new int[minSize + 1][minSize + 1];

        if (minSize == N - 1) {
            ly = 0;
            lx = 0;
        }

        int r = ly;
        for (int j = minSize; j >= 0; j--) {
            int c = lx;
            for (int i = 0; i <= minSize; i++) {
                if (map[r][c] > 0)
                    newMap[i][j] = map[r][c] - 1;
                else
                    newMap[i][j] = map[r][c];

                if (pMap[r][c] > 0) {
                    person.remove(r * 100 + c);
                    newpMap[i][j] = pMap[r][c];
                }
                c++;
            }
            r++;
        }

        r = ly;
        for (int i = 0; i <= minSize; i++) {
            int c = lx;
            for (int j = 0; j <= minSize; j++) {
                map[r][c] = newMap[i][j];
                pMap[r][c] = newpMap[i][j];

                if (pMap[r][c] > 0) person.add(r * 100 + c);

                if (map[r][c] == -1) {
                    ey = r;
                    ex = c;
                }
                c++;
            }
            r++;
        }
    }

    private static void move() {
        HashSet<Integer> newPerson = new HashSet<>();
        int[][] newpMap = new int[N][N];
        for (int key : person) {
            int currY = key / 100;
            int currX = key % 100;

            int minDist = Math.abs(currY - ey) + Math.abs(currX - ex);

            int y = -1;
            int x = -1;

            for (int j = 0; j < 4; j++) {
                int nextY = currY + dy[j];
                int nextX = currX + dx[j];
                if (!isRange(nextY, nextX)) continue;
                if (map[nextY][nextX] > 0) continue;

                int dist = Math.abs(nextY - ey) + Math.abs(nextX - ex);

                if (dist >= minDist) continue;
                y = nextY;
                x = nextX;

                break;
            }

            int num = pMap[currY][currX];

            if (y == -1 && x == -1) {
                newpMap[currY][currX] += num;
                newPerson.add(key);
                continue;
            }
            else if (!(y == ey && x == ex)) {
                newpMap[y][x] += num;
                newPerson.add(y * 100 + x);
            }
            sum += num;
        }
        person = newPerson;
        pMap = newpMap;

    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= M + 1; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            if (i <= M) {
                int idx = y * 100 + x;
                if (!person.contains(idx)) {
                    person.add(idx);
                }
                pMap[y][x]++;
            } else {
                ey = y;
                ex = x;
                map[y][x] = -1;
            }
        }
    }
}
