package codetree.samsung.n2019_2_2_2;

import java.util.*;

public class Main {
    static final int MAX_TURN = 10;
    static int[] move = new int[MAX_TURN];
    static final int START = 0, END = 32;
    // data, to(black idx), to(red idx);
    static int[][] map = new int[END + 1][3];
    static int[][] area = new int[4][2];   // 각 말의 위치, 0,1 (red인지 아닌지)
    static boolean[] flag = new boolean[END + 1];   // 방문할 수 있는지 확인

    static int ans;

    public static void main(String[] args) {
        init();
        play();
        System.out.println(ans);
    }

    private static void init() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < MAX_TURN; i++) move[i] = sc.nextInt();

        map[START][0] = 0;
        map[START][1] = 1;
        map[START][2] = -1;

        map[END][0] = 0;
        map[END][1] = -1;
        map[END][2] = -1;

        for (int i = 1; i <= 19; i++) {
            map[i][0] = i * 2;
            map[i][1] = i + 1;
            map[i][2] = -1;
        }

        map[20][0] = 40;
        map[20][1] = END;
        map[20][2] = -1;

        map[5][2] = 21;
        int d = 13;
        for (int i = 21; i <= 23; i++) {
            map[i][0] = d;
            if (i != 23) {
                map[i][1] = i + 1;
            } else map[i][1] = 29;
            map[i][2] = -1;
            d += 3;
        }

        map[15][2] = 24;
        d = 28;
        for (int i = 24; i <= 26; i++) {
            map[i][0] = d;
            if (i != 26) {
                map[i][1] = i + 1;
            } else map[i][1] = 29;
            map[i][2] = -1;
            d -= 1;
        }

        map[10][2] = 27;
        d = 22;
        for (int i = 27; i <= 28; i++) {
            map[i][0] = d;
            if (i != 28) map[i][1] = i + 1;
            else map[i][1] = 29;
            map[i][2] = -1;
            d += 2;
        }

        d = 25;
        for (int i = 29; i <= 31; i++) {
            map[i][0] = d;
            if (i != 31) map[i][1] = i + 1;
            else map[i][1] = 20;
            map[i][2] = -1;
            d += 5;
        }
    }


    private static void play() {
        dfs(0, START);
    }

    private static void dfs(int turn, int sum) {

        if (turn == MAX_TURN) {
            ans = Math.max(sum, ans);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (area[i][0] == END) continue;

            int beforeIdx = area[i][0];
            int beforeRed = area[i][1];
            int nextIdx = area[i][0];
            for (int j = 0; j < move[turn]; j++) {
                if (nextIdx == END) break;
                if (j == 0 && area[i][1] == 1) nextIdx = map[nextIdx][2];
                else nextIdx = map[nextIdx][1];
            }

            if (flag[nextIdx]) continue;

            area[i][0] = nextIdx;
            flag[beforeIdx] = false;
            if(nextIdx != END) {
                flag[nextIdx] = true;
                if(map[nextIdx][2] != -1) area[i][1] = 1;
                else area[i][1] = 0;
            }

            dfs(turn + 1, sum + map[nextIdx][0]);

            flag[nextIdx] = false;
            flag[beforeIdx] = true;
            area[i][0] = beforeIdx;
            area[i][1] = beforeRed;
        }
    }

}

/*
22
5
6
33개


                    20
                    31
                    30

//        21 22 23  29  26 25 24
                    28
                    27
                    10

*/

/*
5 3 5 3 1 5 2 3 5 3
222
 */