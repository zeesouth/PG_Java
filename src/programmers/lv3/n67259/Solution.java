package programmers.lv3.n67259;
import java.util.*;
class Solution {

    int dp[][][];
    int dy[] = {-1, 1, 0, 0};
    int dx[] = {0, 0, -1, 1};
    int N, answer = Integer.MAX_VALUE;

    public int solution(int[][] board) {
        N = board.length;
        dp = new int[N][N][4];

        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                for(int k=0;k<4;k++)
                    dp[i][j][k] = Integer.MAX_VALUE;
            }
        }

        for(int i=0;i<4;i++) dp[0][0][i] = 0;

        bfs(0, 0, board);

        return answer;
    }

    private void bfs(int y, int x, int[][] board) {
        Deque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{y, x, 0});

        while(!dq.isEmpty()) {
            int[] pos = dq.poll();
            y = pos[0]; x = pos[1];
            int preDir = pos[2];

            for(int i=0;i<4;i++) {
                int currY = y+dy[i];
                int currX = x+dx[i];

                if(currY < 0 || currY >= N || currX < 0 || currX >= N) continue;
                if(board[currY][currX] == 1) continue;

                int newCost = dp[y][x][preDir];
                if((y == 0 && x == 0) || i == preDir) {
                    newCost += 100;
                } else {
                    newCost += 600;
                }

                if(dp[currY][currX][i] >= newCost) {
                    dp[currY][currX][i] = newCost;
                    dq.add(new int[]{currY, currX, i});
                    if(currY == N-1 && currX == N-1) {
                        answer = Math.min(newCost, answer);
                    }
                }
            }
        }
    }
}