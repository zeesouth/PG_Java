package codetree.samsung.n2022_1_2_2;
import java.io.*;
import java.util.*;

public class Main {
    static class Pair extends Object{
        int y, x;

        @Override
        public int hashCode() {
            return (this.y*100) + this.x;
        }

        @Override
        public boolean equals(Object o) {
            return this.y == ((Pair)o).y && this.x == ((Pair)o).x;
        }
        Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final int MAX_N = 20;
    // 나무 + 벽 + 빈칸
    static int map[][] = new int[MAX_N][MAX_N];
    // 제초제 {시작시간, 끝나는 시간}
    static int rMap[][] = new int[MAX_N][MAX_N];
    // 현재 턴 나무
    static Set<Pair> tree = new HashSet<>();
    // N: 격자 가로&세로, M: 박멸 진행년, K: 제초제 확산 범위, C: 제초제가 남아있는 년
    static int N, M, K, C;
    // 성장, 번식
    static int dy1[] = {1, 0, -1, 0};
    static int dx1[] = {0, 1, 0, -1};
    // 박멸
    static int dy2[] = {1, 1, -1, -1};
    static int dx2[] = {-1, 1, -1, 1};
    public static void main(String[] args) throws Exception {
        init();
        int ans = 0;
        for(int m=1;m<=M;m++) {
            grow();
            breed(m);
            ans += removeTree(m);
        }
        System.out.println(ans);
        br.close();
    }
    // 초기화
    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] > 0) tree.add(new Pair(i, j));
            }
        }
    }
    // 1. 나무 성장
    static void grow() {
        for(Pair t : tree) {
            int g = 0;
            for(int j=0;j<4;j++) {
                int currY = t.y+dy1[j];
                int currX = t.x+dx1[j];
                if(!isValid(currY, currX)) continue;
                if(map[currY][currX] <= 0) continue;
                g++;
            }
            map[t.y][t.x] += g;
        }
    }
    // 2. 나무 번식
    static void breed(int time) {
        Set<Pair> newTree = new HashSet<>();
        int changedMap[][] = new int[N][N];
        for(Pair t : tree) {
            int cnt = 0;
            Pair pos[] = new Pair[4];
            for(int j=0;j<4;j++) {
                int currY = t.y+dy1[j];
                int currX = t.x+dx1[j];
                if(!isValid(currY, currX)) continue;
                if(map[currY][currX] != 0) continue;
                if(rMap[currY][currX] >= time) continue;
                pos[cnt++] = new Pair(currY, currX);
            }
            if(cnt == 0 || map[t.y][t.x] / cnt == 0) continue;
            for(int j=0;j<cnt;j++) {
                Pair p = pos[j];
                changedMap[p.y][p.x] += map[t.y][t.x]/cnt;
                newTree.add(p);
            }
        }
        for(Pair np : newTree) {
            map[np.y][np.x] = changedMap[np.y][np.x];
            tree.add(np);
        }
    }
    // 3. 제초제 위치 선정 및 박멸
    static int removeTree(int time) {
        int max = 0;
        int targetY = -1;
        int targetX = -1;
        Set<Pair> targetList = new HashSet<>();
        // 제초 나무 선정
        for(Pair t : tree) {
            int val = map[t.y][t.x];
            Set<Pair> rmList = new HashSet<>();
            rmList.add(t);
            for(int j=0;j<4;j++) {
                int currY = t.y;
                int currX = t.x;
                int k = 0;
                while(k < K) {
                    k++;
                    currY += dy2[j];
                    currX += dx2[j];
                    if(!isValid(currY, currX)) break;
                    rmList.add(new Pair(currY, currX));
                    if(map[currY][currX] <= 0) break;
                    val += map[currY][currX];
                }
            }
            if(val > max) {
                max = val;
                targetList = rmList;
                targetY = t.y;
                targetX = t.x;
            } else if(val == max) {
                if(targetY > t.y) {
                    targetList = rmList;
                    targetY = t.y;
                    targetX = t.x;
                } else if(targetY == t.y) {
                    if(targetX > t.x) {
                        targetList = rmList;
                        targetX = t.x;
                    }
                }
            }
        }
        // 나무 제거
        for(Pair r : targetList) {
            if(map[r.y][r.x] > 0) {
                tree.remove(r);
                map[r.y][r.x] = 0;
            }
            rMap[r.y][r.x] = time+C;
        }
        return max;
    }
    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}