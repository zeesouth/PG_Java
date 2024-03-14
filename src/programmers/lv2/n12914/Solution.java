package programmers.lv2.n12914;

class Solution {
    public long solution(int n) {
        long[] dp = new long[n + 1];

        dp[0] = 1;

        for (int i = 1; i <= n; i++) {
            dp[i] += dp[i - 1];
            if (i - 2 >= 0) dp[i] += dp[i - 2];
            dp[i] %= 1234567;
        }

        return dp[n];
    }
}