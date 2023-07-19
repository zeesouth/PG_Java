package programmers.lv3.n49191;

// 플로이드 와샬
class Solution {
    public int solution(int n, int[][] res) {
        int board[][] = new int[n][n];
        for (int i = 0; i < res.length; i++) {
            int data[] = res[i];
            board[data[0] - 1][data[1] - 1] = 1;    // win
            board[data[1] - 1][data[0] - 1] = -1;   // lose
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 0) { // i, j의 승/패 여부를 모르는 경우
                        if (board[i][k] == 1 && board[k][j] == 1)   // i->k 이고 k->j 이면 i->j (`->`은 win 또는 lose)
                            board[i][j] = 1;
                        else if (board[i][k] == -1 && board[k][j] == -1)
                            board[i][j] = -1;
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) if (board[i][j] != 0) cnt++;
            if (cnt == n - 1) ans++;    // 모든 다른 노드와의 승패 여부를 아는 노드일 경우
        }
        return ans;
    }
}