package codetree.samsung_re.n2022_2_1_1;

import java.io.*;
import java.util.*;

public class Main {
    static final int dy[] = {-1, 0, 1, 0}; // ↑, →, ↓, ←
    static final int dx[] = {0, 1, 0, -1};
    static final int MAX_N = 20, MAX_M = 900;
    static ArrayList<Integer> gun_map[][] = new ArrayList[MAX_N][MAX_N];
    static int player_map[][] = new int[MAX_N][MAX_N];
    static int player[][] = new int[MAX_M + 1][6];  // y, x, d, s, g, point

    static int N, M, K;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;

    public static void main(String[] args) throws Exception {
        init();
        while (K-- > 0) {
            simulate();
            // printMap(player_map);
            // printPlayer();
        }
        print();
    }

    private static void printPlayer() {
        for(int i=1;i<=M;i++) {
            System.out.println(i+" : "+player[i][0]+", "+player[i][1]+", "+player[i][2]+", "+player[i][3]+", "+player[i][4]+", "+player[i][5]);

        }
    }

    private static void printMap(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= M; i++) {
            sb.append(player[i][5]).append(" ");
        }

        System.out.println(sb);
    }

    private static void simulate() {

        for (int i = 1; i <= M; i++) {
            int y = player[i][0];
            int x = player[i][1];
            int d = player[i][2];
            int s = player[i][3];
            int g = player[i][4];

            int nextY = y + dy[d];
            int nextX = x + dx[d];

            if (!isRange(nextY, nextX)) {
                d = (d + 2) % 4;
                nextY = y + dy[d];
                nextX = x + dx[d];
            }

            player_map[y][x] = 0;
            player[i][0] = nextY;
            player[i][1] = nextX;
            player[i][2] = d;

            // 총 정렬
            Collections.sort(gun_map[nextY][nextX]);


            if (player_map[nextY][nextX] == 0) {
                player_map[nextY][nextX] = i;

                if (gun_map[nextY][nextX].size() == 0) continue;

                int max_gun = gun_map[nextY][nextX].get(gun_map[nextY][nextX].size() - 1);

                // 총 바꾸기 : 플레이어가 가진 총보다 현재 위치에서 가장 큰 총의 수보다 작은 경우
                if (g < max_gun) {
                    player[i][4] = max_gun;
                    gun_map[nextY][nextX].remove(gun_map[nextY][nextX].size() - 1);
                    gun_map[nextY][nextX].add(g);
                }

            } else {
                // fight
                int e = player_map[nextY][nextX];
                int win = i;
                int lose = e;
                if (s + g < player[e][3] + player[e][4]) {
                    win = e;
                    lose = i;
                } else if (s + g == player[e][3] + player[e][4]) {
                    if(s < player[e][3]) {
                        win = e;
                        lose = i;
                    }
                }

                // 이긴사람 포인트 획득
                player[win][5] += player[win][3] + player[win][4] - player[lose][3] - player[lose][4];


                // 진사람 총 내려놓기
                gun_map[nextY][nextX].add(player[lose][4]);
                player[lose][4] = 0;

                Collections.sort(gun_map[nextY][nextX]);
                if(player[win][4] < gun_map[nextY][nextX].get(gun_map[nextY][nextX].size()-1)) {
                    int tmp = player[win][4];
                    player[win][4] = gun_map[nextY][nextX].remove(gun_map[nextY][nextX].size()-1);
                    gun_map[nextY][nextX].add(tmp);
                }

                // 진사람 이동하기
                for (int j = 0; j < 4; j++) {
                    int moveY = nextY + dy[(player[lose][2] + j) % 4];
                    int moveX = nextX + dx[(player[lose][2] + j) % 4];
                    if (!isRange(moveY, moveX)) continue;
                    if (player_map[moveY][moveX] > 0) continue;

                    player[lose][0] = moveY;
                    player[lose][1] = moveX;
                    player[lose][2] = (player[lose][2] + j) % 4;

                    if (lose == i) {
                        player_map[moveY][moveX] = i;
                    } else {
                        player_map[moveY][moveX] = e;
                        player_map[nextY][nextX] = i;
                    }

                    if (gun_map[moveY][moveX].size() > 0) {
                        Collections.sort(gun_map[moveY][moveX]);
                        int max_gun = gun_map[moveY][moveX].get(gun_map[moveY][moveX].size() - 1);
                        player[lose][4] = max_gun;
                        gun_map[moveY][moveX].remove(gun_map[moveY][moveX].size() - 1);
                    }

                    break;
                }


            }
        }
    }

    private static boolean isRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                gun_map[i][j] = new ArrayList<>();
                int gun = Integer.parseInt(st.nextToken());
                if (gun > 0) gun_map[i][j].add(gun);
            }
        }

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            player_map[y][x] = i;
            player[i][0] = y;
            player[i][1] = x;
            player[i][2] = d;
            player[i][3] = s;
        }

        br.close();
    }

}

/*
- 빨간 숫자 : 총 공격력, 플레이어 초기 능력치
- 노란 숫자 : 플레이어 번호
-

 */