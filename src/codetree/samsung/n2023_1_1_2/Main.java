package codetree.samsung.n2023_1_1_2;
// 삼성 공채 2023 상반기 오전 2번
import java.io.*;
import java.util.*;

public class Main {

    // 상 하 좌 우
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, -1, 0, 1};

    static long totalScore = 0;
    static int N, M, P;
    static StringTokenizer st;

    static Map<Integer, Rabbit> rabbits;
    static Queue<Rabbit> rabbitPQ;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());
        for(int i=0;i<Q;i++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            if(order == 100) init();
            else if (order == 200) totalScore += (play());
            else if (order == 300) replace();
            else if (order == 400) System.out.println(best());
        }
        br.close();
    }

    static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        rabbits = new HashMap<>(P);
        rabbitPQ = new PriorityQueue<>(P);
        for(int i=0;i<P;i++) {
            int pid = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Rabbit newRabbit = new Rabbit(pid, d);
            rabbits.put(pid, newRabbit);
            rabbitPQ.add(newRabbit);
        }
    }

    static int play() {
        int K = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());

        int total = 0;
        Set<Rabbit> selected = new HashSet<>();
        while(K-->0) {
            Rabbit rabbit = rabbitPQ.poll();
            total += rabbit.move();
            rabbitPQ.add(rabbit);
            selected.add(rabbit);
        }

        Rabbit max = null;
        for(Rabbit r : selected) {
            if(max == null || max.compareLoc(r) < 0) max = r;
        }
        max.score += S;
        return total;
    }

    static void replace() {
        int pid = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        rabbits.get(pid).mulDist(L);
    }

    static long best() {
        long maxScore = -1;
        for(int id : rabbits.keySet()) {
            Rabbit r = rabbits.get(id);
            maxScore = Math.max(maxScore, r.score + totalScore);
        }
        return maxScore;
    }

    static class Pair {
        int y, x;
        Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Rabbit implements Comparable<Rabbit> {
        int y=0, x = 0, cnt = 0, id, dist;
        long score = 0;

        Rabbit(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        int move() {
            Pair to = nextPos();
            int mScore = to.y+to.x + 2;
            this.score -= mScore;
            this.y = to.y;
            this.x = to.x;
            cnt += 1;
            return mScore;
        }

        private Pair nextPos() {
            Pair positions[] = findAllNextPos();

            Arrays.sort(positions, (o1, o2) -> {
                if((o1.y+o1.x) != (o2.y+o2.x)) return o2.y+o2.x - (o1.y+o1.x);
                if (o1.y != o2.y) return o2.y-o1.y;
                return o2.x-o1.x;
            });

            return positions[0];
        }

        private Pair[] findAllNextPos() {
            Pair res[] = new Pair[4];
            for(int i=0;i<4;i++) res[i] = goTo(i);
            return res;
        }

        private Pair goTo(int d) {
            int remain = this.dist;

            // 벽에 도달할 때까지 필요한 거리
            int toEnd = 0;
            int end = 0;

            if(d == 0) {
                toEnd = this.y;
                end = 0;
            } else if(d == 2) {
                toEnd = N-this.y-1;
                end = N-1;
            } else if(d == 1) {
                toEnd = this.x;
                end = 0;
            } else {
                toEnd = M - this.x - 1;
                end = M - 1;
            }

            if(toEnd >= remain) return new Pair(this.y+remain * dy[d], this.x + remain*dx[d]);

            remain -= toEnd;
            d = (d+2)%4;

            if((d&1) == 0) {
                if(((remain/(N-1)) & 1) != 0) {
                    d = (d+2)%4;
                    end = (end == 0) ? N - 1 : 0;
                }
                remain = remain % (N-1);
            } else {
                if(((remain/(M-1)) & 1) != 0) {
                    d = (d+2)%4;
                    end = (end == 0) ? M - 1 : 0;
                }
                remain = remain % (M-1);
            }

            if((d&1) == 0) return new Pair(end+remain*dy[d], this.x);
            else return new Pair(this.y, end+remain*dx[d]);
        }

        void mulDist(int L) {this.dist *= L; }

        int compareLoc(Rabbit o) {
            if ((this.y + this.x) != (o.y + o.x)) return (this.y + this.x) - (o.y + o.x);
            if (this.y != o.y) return this.y - o.y;
            if (this.x != o.x) return this.x - o.x;
            return this.id - o.id;
        }

        public int compareTo(Rabbit o) {
            if(this.cnt != o.cnt) return this.cnt-o.cnt;
            return compareLoc(o);
        }

        public int hashCode() {return this.id;}

        public boolean equals(Object o) {return ((Rabbit) o).id == this.id; }
    }
}

/*
100 경주 시작 준비

200 경주 진행

300 이동 거리 변경

400 최고 토끼 선정
15683544885
17005810932
*/