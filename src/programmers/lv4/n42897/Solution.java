package programmers.lv4.n42897;
class Solution {
    public int solution(int[] money) {
        int dp1[] = new int[money.length];
        int dp2[] = new int[money.length];

        dp1[0] = money[0];
        dp1[1] = Math.max(money[0], money[1]);

        dp2[1] = money[1];

        int ans = 0;
        for(int i=2;i<money.length;i++) {
            if(i < money.length - 1) dp1[i] = Math.max(dp1[i-1], dp1[i-2]+money[i]);
            dp2[i] = Math.max(dp2[i-1], dp2[i-2]+money[i]);
            ans = Math.max(ans, Math.max(dp1[i], dp2[i]));
        }
        return ans;
    }
}