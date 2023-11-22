package programmers.lv3.n150365;

import java.util.*;

class Solution {

    final int[] dy = {1, 0, 0, -1};
    final int[] dx = {0, -1, 1, 0};

    final char[] d = {'d', 'l', 'r', 'u'};

    int N, M, K;
    int ey, ex;
    char[] ans;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        N = n;
        M = m;
        ey = r;
        ex = c;
        K = k;
        ans = new char[k];
        int diff = Math.abs(r - x) + Math.abs(c - y);
        if (dfs(x, y, k, diff)) return new String(ans);
        else return "impossible";
    }


    boolean dfs(int y, int x, int res, int diff) {
        if (res == 0 && diff == 0) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nextY = y + dy[i];
            int nextX = x + dx[i];
            if (!isRange(nextY, nextX)) continue;
            if (diff > res) continue;
            if ((diff % 2 == 0 && res % 2 == 0) || (diff % 2 == 1 && res % 2 == 1)) {
                ans[K - res] = d[i];
                int newDiff = Math.abs(ey - nextY) + Math.abs(ex - nextX);
                if (dfs(nextY, nextX, res - 1, newDiff)) return true;
            }
        }

        return false;
    }

    boolean isRange(int y, int x) {
        return y >= 1 && y <= N && x >= 1 && x <= M;
    }
}