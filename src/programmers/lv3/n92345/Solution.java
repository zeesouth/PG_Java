package programmers.lv3.n92345;
import java.util.*;
class Solution {
    final int INF = 987654321;

    // dy, dx
    int dy[] = {0, 0, 1, -1};
    int dx[] = {1, -1, 0, 0};

    // map 세로, 가로 길이
    int N, M;

    // board
    int map[][];

    int dfs(int ay, int ax, int by, int bx, boolean turn) {
        int y = turn ? ay : by;
        int x = turn ? ax : bx;

        // 최적의 경로 찾기
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<4;i++) {
            int currY = y + dy[i];
            int currX = x + dx[i];

            if(!isValid(currY, currX) || map[currY][currX] == 0) continue;

            // 현재 상대가 같은 발판에 있는 경우 (움직일 경우 바로 끝)
            if(ay == by && ax == bx) {
                list.add(1);
                continue;
            }

            map[y][x] = 0;
            int res;
            if(turn) res = -dfs(currY, currX, by, bx, !turn);
            else res = -dfs(ay, ax, currY, currX, !turn);

            if(res >= 0) res++;
            else res--;

            list.add(res);
            map[y][x] = 1;
        }

        int pMax = -INF, pMin = INF;
        int mMax = -INF, mMin = INF;

        for(int i=0;i<list.size();i++) {
            int num = list.get(i);
            if(num > 0) {
                pMax = Math.max(pMax, num);
                pMin = Math.min(pMin, num);
            } else {
                mMax = Math.max(mMax, num);
                mMin = Math.min(mMin, num);
            }
        }

        if(pMax == -INF && mMax == -INF) return 0;
        else if(pMax == -INF) return mMin;
        else if(pMax != -INF) return pMin;
        else return 0;

    }

    boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    public int solution(int[][] board, int[] aloc, int[] bloc) {

        N = board.length;
        M = board[0].length;
        map = board;

        return Math.abs(dfs(aloc[0], aloc[1], bloc[0], bloc[1], true));
    }
}