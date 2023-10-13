package codetree.samsung_re.n2023_1_1_1;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static final int dy[] = {0, 1, 0, -1, 1, 1, -1, -1};
    static final int dx[] = {1, 0, -1, 0, 1, -1, 1, -1};
    static final int MAX = 10;
    static int map[][] = new int[MAX][MAX];
    static int backY[][] = new int[MAX][MAX];
    static int backX[][] = new int[MAX][MAX];
    static int time[][] = new int[MAX][MAX];
    static boolean visited[][] = new boolean[MAX][MAX];
    static int N, M, K;
    static ArrayList<Pair> arr = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        init();
        for (int i = 1; i <= K; i++) {
            simulate(i);
            if (arr.size() <= 1) break;
        }

        getAnswer();

    }

    private static void getAnswer() {
        Collections.sort(arr);
        int answer = 0;
        if (arr.size() != 0) {
            Pair strong = arr.get(arr.size() - 1);
            answer = map[strong.y][strong.x];
        }
        System.out.println(answer);
    }

    private static void simulate(int t) {
        Collections.sort(arr);

        Pair weak = arr.get(0);
        Pair strong = arr.get(arr.size() - 1);

        if(map[weak.y][weak.x] + N + M <= 5000) map[weak.y][weak.x] += N + M;
        else map[weak.y][weak.x] = 5000;
        time[weak.y][weak.x] = t;
        visited[weak.y][weak.x] = true;

        boolean res = laser(weak.y, weak.x, strong.y, strong.x);
        if (!res) potan(weak.y, weak.x, strong.y, strong.x);

        reset();

        ArrayList<Pair> newArr = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            Pair curr = arr.get(i);
            if (map[curr.y][curr.x] > 0) {
                newArr.add(curr);
            }
        }

        arr = newArr;

        // print(map);

    }

    private static void print(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void reset() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j]) {
                    if (map[i][j] > 0 && map[i][j] < 5000) map[i][j] += 1;
                } else visited[i][j] = false;
            }
        }
    }

    private static void potan(int y1, int x1, int y2, int x2) {

        map[y2][x2] -= map[y1][x1];
        visited[y2][x2] = true;


        for (int i = 0; i < 8; i++) {
            int nextY = (y2 + N + dy[i]) % N;
            int nextX = (x2 + M + dx[i]) % M;

            if (nextY == y1 && nextX == x1) continue;
            if (map[nextY][nextX] <= 0) continue;

            visited[nextY][nextX] = true;
            map[nextY][nextX] -= map[y1][x1] / 2;
        }

    }

    private static boolean laser(int y1, int x1, int y2, int x2) {

        boolean check[][] = new boolean[N][M];
        Queue<Pair> q = new LinkedList<>();
        check[y1][x1] = true;
        q.add(new Pair(y1, x1));

        boolean isCan = false;

        while (!q.isEmpty()) {
            Pair curr = q.poll();

            if (curr.y == y2 && curr.x == x2) {
                isCan = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextY = (curr.y + dy[i] + N) % N;
                int nextX = (curr.x + dx[i] + M) % M;

                if (check[nextY][nextX]) continue;
                if (map[nextY][nextX] <= 0) continue;

                check[nextY][nextX] = true;
                backY[nextY][nextX] = curr.y;
                backX[nextY][nextX] = curr.x;
                q.add(new Pair(nextY, nextX));
            }
        }

        if (isCan) {
            map[y2][x2] -= map[y1][x1];
            visited[y2][x2] = true;

            int currY = backY[y2][x2];
            int currX = backX[y2][x2];

            while (!(currY == y1 && currX == x1)) {
                map[currY][currX] -= map[y1][x1] / 2;
                visited[currY][currX] = true;

                int nextY = backY[currY][currX];
                int nextX = backX[currY][currX];

                currY = nextY;
                currX = nextX;
            }
        }


        return isCan;
    }

    private static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > 0) arr.add(new Pair(i, j));
            }
        }
    }

    static class Pair implements Comparable<Pair> {
        int y, x;

        Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Pair o) {
            if (map[this.y][this.x] != map[o.y][o.x]) return map[this.y][this.x] - map[o.y][o.x];
            if (time[this.y][this.x] != time[o.y][o.x]) return time[o.y][o.x] - time[this.y][this.x];
            if (this.y + this.x != o.y + o.x) return (o.y + o.x) - (this.y + this.x);
            return o.x - this.x;
        }
    }
}
