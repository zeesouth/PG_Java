package codetree.samsung_re.n2023_1_1_2;

import java.io.*;
import java.util.*;

public class Main {
    static final int dy[] = {1, 0, -1, 0};
    static final int dx[] = {0, 1, 0, -1};
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static HashMap<Integer, Rabbit> rabbitMap = new HashMap<>();
    static int N, M, P, turn = 0;

    static PriorityQueue<Rabbit> pq1 = new PriorityQueue<>(
            (o1, o2) -> {
                if (o1.count != o2.count) return o1.count - o2.count;
                if (o1.y + o1.x != o2.y + o2.x) return (o1.y + o1.x) - (o2.y + o2.x);
                if (o1.y != o2.y) return o1.y - o2.y;
                if (o1.x != o2.x) return o1.x - o2.x;
                return o1.id - o2.id;
            }
    );

    static PriorityQueue<Rabbit> pq2 = new PriorityQueue<>(
            (o1, o2) -> {
                if (o1.turn != o2.turn) return o2.turn - o1.turn;
                if (o1.y + o1.x != o2.y + o2.x) return (o2.y + o2.x) - (o1.y + o1.x);
                if (o1.y != o2.y) return o2.y - o1.y;
                if (o1.x != o2.x) return o2.x - o1.x;
                return o2.id - o1.id;
            }
    );

    public static void main(String[] args) throws Exception {
        int Q = Integer.parseInt(br.readLine());
        while (Q-- > 0) {
            simulate();
        }
    }

    private static long best() {
        long max = 0;

        for (Rabbit r : pq1) {
            max = Math.max(r.score, max);
        }

        return max;
    }

    private static void update() {
        int pid = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        if (L == 1) return;
        Rabbit r = rabbitMap.get(pid);
        r.dist *= L;
    }

    private static void run() {
        int K = Integer.parseInt(st.nextToken());
        Long S = Long.parseLong(st.nextToken());
        turn++;

        while (K-- > 0) {
            Rabbit r = pq1.peek();
            r.count++;
            r.turn = turn;

            int dist = r.dist;

            int y = 0;
            int x = 0;
            for (int i = 0; i < 4; i++) {
                int move = 0;
                if (dy[i] == 0) {
                    move = dist % (2 * (M - 1));
                } else {
                    move = dist % (2 * (N - 1));
                }

                int currY = r.y;
                int currX = r.x;

                int nextY = currY + move * dy[i];
                int nextX = currX + move * dx[i];

                int cnt = 0;
                while (!isRange(nextY, nextX)) {
                    if (nextY >= N) {
                        move -= N - 1 - currY;
                        currY = N - 1;
                    } else if (nextY < 0) {
                        move -= currY;
                        currY = 0;
                    } else if (nextX >= M) {
                        move -= M - 1 - currX;
                        currX = M - 1;
                    } else if (nextX < 0) {
                        move -= currX;
                        currX = 0;
                    }

                    if (cnt % 2 == 0) {
                        nextY = currY + move * dy[(i + 2) % 4];
                        nextX = currX + move * dx[(i + 2) % 4];
                    } else {
                        nextY = currY + move * dy[i];
                        nextX = currX + move * dx[i];
                    }
                    cnt++;
                }

                if (y + x < nextY + nextX) {
                    y = nextY;
                    x = nextX;
                } else if (y + x == nextY + nextX) {
                    if (y < nextY) {
                        y = nextY;
                        x = nextX;
                    } else if (y == nextY) {
                        if (x < nextX) {
                            x = nextX;
                        }
                    }
                }
            }

            r.y = y;
            r.x = x;

            for (Rabbit rb : pq1) {
                if (rb.id == r.id) continue;
                rb.score += y + x + 2;
            }
        }

        Rabbit r2 = pq2.peek();
        r2.score += S;
    }


    private static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        for (int i = 0; i < P; i++) {
            int pid = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Rabbit newRabbit = new Rabbit(pid, d);
            rabbitMap.put(pid, newRabbit);
            pq1.add(newRabbit);
            pq2.add(newRabbit);
        }

    }

    static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    static void simulate() throws Exception {
        st = new StringTokenizer(br.readLine());

        int order = Integer.parseInt(st.nextToken());

        if (order == 100) init();
        else if (order == 200) run();
        else if (order == 300) update();
        else System.out.println(best());
    }

    static class Rabbit {
        int y, x, turn;
        int id, count, dist;
        long score;

        Rabbit(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }
}
