package programmers.lv3.n131129;

class Solution {
    public int[] solution(int target) {
        int dp[][] = new int[target + 1][2];

        for (int i = 1; i <= target; i++) {
            dp[i][0] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= target; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 20; k++) {
                    if (i - j * k < 0) continue;

                    if (dp[i - j * k][0] + 1 < dp[i][0]) {
                        dp[i][0] = dp[i - j * k][0] + 1;
                        if (j == 1) dp[i][1] = dp[i - j * k][1] + 1;
                        else dp[i][1] = dp[i - j * k][1];
                    } else if (dp[i - j * k][0] + 1 == dp[i][0]) {
                        if (j == 1 && dp[i][1] < dp[i - j * k][1] + 1) dp[i][1] = dp[i - j * k][1] + 1;
                        else if (j != 1 && dp[i][1] < dp[i - j * k][1]) dp[i][1] = dp[i - j * k][1];
                    }

                }
            }

            if (i - 50 < 0) continue;
            if (dp[i - 50][0] + 1 < dp[i][0]) {
                dp[i][0] = dp[i - 50][0] + 1;
                dp[i][1] = dp[i - 50][1] + 1;
            } else if (dp[i - 50][0] + 1 == dp[i][0]) {
                if (dp[i][1] < dp[i - 50][1] + 1) dp[i][1] = dp[i - 50][1] + 1;
            }

        }

        return dp[target];
    }
}