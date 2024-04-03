package programmers.lv2.n148652;

class Solution {
    public int solution(int n, long l, long r) {
        return dfs(n, l, r, 1);
    }

    public int dfs(int n, long s, long e, long idx) {
        if (n == 0) {
            return 1;
        }

        int cnt = 0;
        long size = (long) Math.pow(5, n - 1);

        for (int i = 0; i < 5; i++) {
            long min = idx + size * i;
            long max = idx + size * (i + 1) - 1;

            // 가운데 부분이거나, 범위에 겹치지 않는다면
            if (i == 2 || e < min || max < s) continue;

            cnt += dfs(n - 1, s, e, min);
        }

        return cnt;
    }
}
