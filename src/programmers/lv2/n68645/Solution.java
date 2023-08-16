package programmers.lv2.n68645;

class Solution {
    int N;

    public int[] solution(int n) {
        if (n == 1) return new int[]{1};

        N = n;

        int[][] temp = new int[n][n];

        int dy[] = {1, 0, -1};
        int dx[] = {0, 1, -1};
        int y = 0, x = 0, d = 0, num = 1;
        while (true) {
            if (temp[y][x] != 0) break;
            temp[y][x] = num;
            if (!isValid(y + dy[d], x + dx[d]) || temp[y + dy[d]][x + dx[d]] != 0) {
                d = (d + 1) % 3;
            }
            y += dy[d];
            x += dx[d];
            num++;
        }

        int[] answer = new int[num - 1];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) answer[idx++] = temp[i][j];
        }
        return answer;
    }

    boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}