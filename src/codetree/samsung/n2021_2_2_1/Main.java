package codetree.samsung.n2021_2_2_1;
// 2021 삼성 하반기 오후 2번

import java.io.*;
import java.util.*;
public class Main {
    static final int MAX_N = 100;
    static int[][] pizza = new int[MAX_N][MAX_N];
    static ArrayList<Peice> peice = new ArrayList<>();
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    static int N, K, cnt = 0;
    public static void main(String[] args) throws Exception {
        init();
        while(getABS(max, min) > K) {
            cnt++;
            plus();
            roll();
            pressDown();
            fold();
            pressDown();
        }
        System.out.println(cnt);
    }
    static void print(int[][] map) {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) System.out.print(map[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }
    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++) {
            pizza[N-1][i] = Integer.parseInt(st.nextToken());
            max = Math.max(pizza[N-1][i], max);
            min = Math.min(pizza[N-1][i], min);
            peice.add(new Peice(N-1, i));
        }
        br.close();
    }
    // 1. 밀가루 양이 가장 작은 위치에 밀가루 1만큼 더 넣어주기
    static void plus() {
        for(Peice p : peice) {
            if(pizza[p.y][p.x] == min) pizza[p.y][p.x] += 1;
        }
        min++;
    }
    // 2. 도우 말기
    static void roll() {
        int startY = N-1;
        int startX = 0;
        int sizeY = 1, sizeX = 1;
        while(N-(startX+sizeX) >= sizeY) {
            int jj = startX+sizeY+sizeX-1;
            for(int i=startY;i<startY+sizeY;i++) {
                int ii = N-1-sizeX;
                for(int j=startX;j<startX+sizeX;j++) {
                    pizza[ii][jj] = pizza[i][j];
                    pizza[i][j] = 0;
                    peice.remove(new Peice(i, j));
                    peice.add(new Peice(ii, jj));
                    ii++;
                }
                jj--;
            }

            if(sizeY == sizeX) {
                sizeY++;
                startY--;
                startX += sizeX;
            }
            else {
                startX += sizeX;
                sizeX++;
            }
        }
        /*
        1*1
        2*1
        2*2
        3*2
        3*3
        */
    }
    // 3. 도우 누르기
    static void pressDown() {
        int[][] newMap = new int[N][N];
        for(Peice p : peice) {
            for(int i=0;i<4;i++) {
                int currY = p.y + dy[i];
                int currX = p.x + dx[i];
                if(!isValid(currY, currX)) continue;
                if(pizza[currY][currX] == 0) continue;
                int a = pizza[currY][currX];
                int b = pizza[p.y][p.x];
                int temp = getABS(a, b) / 5;
                if(temp == 0) continue;
                if(a > b) {
                    newMap[currY][currX] -= temp;
                    newMap[p.y][p.x] += temp;
                } else {
                    newMap[currY][currX] += temp;
                    newMap[p.y][p.x] -= temp;
                }
            }
        }
        Collections.sort(peice, (o1, o2) -> o1.x == o2.x ? o2.y-o1.y : o1.x-o2.x);
        int newPizza[][] = new int[N][N];
        int j = 0;
        int newMin = Integer.MAX_VALUE;
        int newMax = Integer.MIN_VALUE;
        for(Peice p : peice) {
            pizza[p.y][p.x] += newMap[p.y][p.x]/2;
            newPizza[N-1][j] = pizza[p.y][p.x];
            newMin = Math.min(newPizza[N-1][j], newMin);
            newMax = Math.max(newPizza[N-1][j], newMax);
            p.y = N-1;
            p.x = j;
            j++;
        }
        min = newMin;
        max = newMax;
        pizza = newPizza;
    }
    // 4. 도우 두번 반 접기
    static void fold() {
        // print(pizza);
        int C = N/2;
        int y = N-2;
        int x = N-1;
        for(Peice p : peice) {
            pizza[y][x] = pizza[p.y][p.x];
            pizza[p.y][p.x] = 0;
            p.y = y;
            p.x = x;
            C--;
            x--;
            if(C==0) break;
        }
        Collections.sort(peice, (o1, o2) -> o1.y == o2.y ? o1.x-o2.x : o1.y-o2.y);

        C = N/2 + N/4;
        y = N-3;
        x = N-1;
        for(Peice p : peice) {
            C--;
            if(C/(N/4) != 1) {
                pizza[y][x] = pizza[p.y][p.x];
                pizza[p.y][p.x] = 0;
                p.y = y;
                p.x = x;
            }
            if(C==0) break;

            if((C) == N/4) {
                y--;
                x = N-1;
            } else x--;
        }

    }
    // 5. 도우 누르기 (3번과 동일)
    static int getABS(int y, int x) {
        return Math.abs(y-x);
    }
    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >=0 &&x < N;
    }
    static class Peice extends Object {
        int y, x;
        Peice(int y, int x) {
            this.y = y;
            this.x = x;
        }
        @Override
        public int hashCode() {
            return this.y * 1000 + this.x;
        }
        @Override
        public boolean equals(Object o) {
            return ((Peice)o).y == this.y && ((Peice)o).x == this.x;
        }
    }
}
