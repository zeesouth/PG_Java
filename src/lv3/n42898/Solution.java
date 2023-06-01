package lv3.n42898;
class Solution {
    int MOD = 1_000_000_007;
    public int solution(int m, int n, int[][] puddles) {
        boolean puddle[][] = new boolean[n+1][m+1];
        for(int i=0;i<puddles.length;i++) {
            int x = puddles[i][0];
            int y = puddles[i][1];
            puddle[y][x] = true;
        }

        int dp[][] = new int[n+1][m+1];
        dp[0][1] = 1;

        for(int i=1;i<=n;i++) {
            for(int j=1;j<=m;j++) {
                if(!puddle[i][j]) {
                    dp[i][j] += dp[i][j-1] + dp[i-1][j];
                    dp[i][j] %= MOD;
                }
            }
        }
        return dp[n][m];
    }
}