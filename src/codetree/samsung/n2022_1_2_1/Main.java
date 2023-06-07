package codetree.samsung.n2022_1_2_1;
// 삼성 공채 2022 상반기 오후 1번
import java.io.*;
import java.util.*;
public class Main {
    static final int MAX_N = 20, MAX_M = 5;
    static int map[][] = new int[MAX_N][MAX_N];
    static int tMap[][] = new int[MAX_N][MAX_N];
    static ArrayList<Team> teamArr = new ArrayList<>();
    // 0x +, N-1y -, N-1x -, 0y +
    static int[] dy = {0, -1, 0, 1};
    static int[] dx = {1, 0, -1, 0};
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, K;
    static int tCnt;
    public static void main(String[] args) throws Exception {
        init();
        for(int k=0;k<K;k++) {
            int round = (k)%(4*N);
            move(round);
        }
        int ans = 0;
        for(Team t : teamArr) {
            ans += t.score;
        }
        System.out.println(ans);
        br.close();
    }
    static void print(int[][] arr) {
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++)
                System.out.print(arr[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }
    static void init() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        for(int i=0;i<N;i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<N;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 1) {
                    Team team = new Team(++tCnt, i, j);
                    tMap[i][j] = tCnt;
                    teamArr.add(team);
                }
            }
        }
        int tId = 0;
        for(Team t : teamArr) {
            ++tId;

            boolean visited[][] = new boolean[N][N];
            Queue<int[]> q = new LinkedList<>();
            q.add(new int[]{t.hy, t.hx});
            while(!q.isEmpty()) {
                int cy = q.peek()[0];
                int cx = q.poll()[1];
                if(map[cy][cx] == 3) {
                    t.ty = cy;
                    t.tx = cx;
                }
                visited[cy][cx] = true;
                for(int i=0;i<4;i++) {
                    int currY = cy+dy[i];
                    int currX = cx+dx[i];
                    if(!isValid(currY, currX)) continue;
                    if(visited[currY][currX]) continue;
                    if(map[currY][currX] == 0 || map[currY][currX] == 4) continue;

                    tMap[currY][currX] = tId;
                    q.add(new int[]{currY, currX});
                    break;
                }
            }
        }
    }
    static void move(int round) {
        // 화살표 이동 방향
        int[] check = checkInfo(round);
        int d = check[0];
        int startY = check[1];
        int startX = check[2];
        int endY = check[3];
        int endX = check[4];
        int currY = startY;
        int currX = startX;
        int visitTeam = -1;

        for(Team t : teamArr) t.move();
        // print(map);
        // print(tMap);
        while(true) {
            if(tMap[currY][currX] != 0) {
                int score = getOrder(tMap[currY][currX], currY, currX);
                // System.out.println(score);
                teamArr.get(tMap[currY][currX]-1).score += score;
                visitTeam = tMap[currY][currX];
                break;
            }
            if(currY == endY && currX == endX) break;
            currY += dy[d];
            currX += dx[d];
        }
        if(visitTeam != -1) teamArr.get(visitTeam-1).changeHeadTail();
    }
    static int getOrder(int tId, int targetY, int targetX) {

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{teamArr.get(tId-1).hy, teamArr.get(tId-1).hx});
        boolean visited[][] = new boolean[N][N];
        int cnt = 0;
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            cnt++;
            if(targetY == curr[0] && targetX == curr[1]) return cnt*cnt;
            visited[curr[0]][curr[1]] = true;
            for(int i=0;i<4;i++) {
                int currY = curr[0]+dy[i];
                int currX = curr[1]+dx[i];
                if(isValid(currY, currX) && !visited[currY][currX]) {
                    if(map[currY][currX] == 2) q.add(new int[]{currY, currX});
                }
            }
        }
        cnt++;
        return cnt * cnt;
    }
    static int[] checkInfo(int round) {
        // 우(1~N) 상(N+1~2N) 좌(2N+1~3N) 하(3N+1~4N)
        // 화살표 방향, 시작 인덱스
        int direct = (round)/N;
        int add = (round)%N;
        // 0, 0x +, N-1y -, N-1x -, 0y +
        int startY = 0, startX = 0, endY = 0, endX = 0;

        if(direct == 0) {
            startY = endY = add;
            startX = 0;
            endX = N-1;
        } else if(direct == 1) {
            startY = N-1;
            endY = 0;
            startX = endX = add;
        } else if(direct == 2) {
            endX = 0;
            startX = N-1;
            startY = endY = N-1-add;
        } else if(direct == 3) {
            startY = 0;
            endY = N-1;
            startX = endX = N-1-add;
        }
        // System.out.println(direct+", "+startY+", "+startX +", "+endY+", "+endX);
        return new int[] {direct, startY, startX, endY, endX};
    }
    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
    static class Team {
        int id, hy, hx, ty, tx, score;
        Team(int id, int hy, int hx) {
            this.id = id;
            this.hy = hy;
            this.hx = hx;
            this.score = 0;
        }
        void addScore(int score) {
            this.score += score;
        }
        void changeHeadTail() {
            int nhy = this.hy;
            int nhx = this.hx;
            int nty = this.ty;
            int ntx = this.tx;

            this.hy = nty;
            this.hx = ntx;
            this.ty = nhy;
            this.tx = nhx;
            map[this.hy][this.hx] = 1;
            map[this.ty][this.tx] = 3;
        }
        void move() {
            int bhy = hy;
            int bhx = hx;
            // 머리 이동
            for(int i=0;i<4;i++) {
                int currY = hy+dy[i];
                int currX = hx+dx[i];
                if(!isValid(currY, currX) || map[currY][currX] == 2 || map[currY][currX] == 0) continue;
                map[currY][currX] = 1;
                tMap[currY][currX] = id;
                map[hy][hx] = 2;
                hy = currY;
                hx = currX;
                break;
            }
            // 꼬리 이동
            for(int i=0;i<4;i++) {
                int currY = ty+dy[i];
                int currX = tx+dx[i];
                if(!isValid(currY, currX) || map[currY][currX] == 4 || map[currY][currX] == 0) continue;
                if(currY == bhy && currX == bhx) continue;
                map[currY][currX] = 3;

                if(!(ty == hy && tx == hx)){
                    tMap[ty][tx] = 0;
                    map[ty][tx] = 4;
                }
                ty = currY;
                tx = currX;
                break;
            }
        }
    }
}
