package programmers.lv3.n258705;

public class Solution {
    private final int MOD = 10007;

    public int solution(int n, int[] tops) {
        int[][] dp = new int[n + 1][2];
        dp[0][1] = 1;
        // dp[i][0]: '\' 모양 마름모로 끝남
        // dp[i][1]: 끝이 '/' 모양 마름모로 끝남 or 삼각형으로 끝남 + tops = 1인 경우 끝이 위로 향하는 마름모 모양으로 끝남

        for (int i = 0; i < n; i++) {
            dp[i + 1][0] = (dp[i][0] + dp[i][1]) % MOD;

            // 위에 삼각형이 없는 경우
            if (tops[i] == 0) {
                dp[i + 1][1] = (dp[i][0] + dp[i][1] * 2) % MOD;
            }
            // 위에 삼각형이 있는 경우
            else {
                dp[i + 1][1] = (dp[i][0] * 2 + dp[i][1] * 3) % MOD;
            }
        }

        return (dp[n][0] + dp[n][1]) % MOD;
    }
}
