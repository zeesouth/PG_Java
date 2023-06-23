package programmers.lv3.n92344;
class Solution {
    int N, M;
    public int solution(int[][] board, int[][] skill) {
        N = board.length;
        M = board[0].length;

        int change[][] = new int[N][M];

        for(int s = 0;s<skill.length;s++) {
            int currS[] = skill[s];
            int type = currS[0];
            int r1 = currS[1];
            int c1 = currS[2];
            int r2 = currS[3];
            int c2 = currS[4];
            int degree = currS[5];
            if(type == 1) {
                change[r1][c1] -= degree;
                if(isValid(r2+1, c1)) change[r2+1][c1] += degree;
                if(isValid(r1, c2+1)) change[r1][c2+1] += degree;
                if(isValid(r2+1, c2+1)) change[r2+1][c2+1] -= degree;
            } else {
                change[r1][c1] += degree;
                if(isValid(r2+1, c1)) change[r2+1][c1] -= degree;
                if(isValid(r1, c2+1)) change[r1][c2+1] -= degree;
                if(isValid(r2+1, c2+1)) change[r2+1][c2+1] += degree;
            }
        }

        for(int i=0;i<N;i++) {
            for(int j=1;j<M;j++) change[i][j] += change[i][j-1];
        }

        for(int j=0;j<M;j++) {
            for(int i=1;i<N;i++) change[i][j] += change[i-1][j];
        }

        int alive = 0;
        for(int i=0;i<N;i++) {
            for(int j=0;j<M;j++) {
                board[i][j] += change[i][j];
                if(board[i][j] > 0) alive++;
            }
        }
        return alive;
    }

    boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }
}