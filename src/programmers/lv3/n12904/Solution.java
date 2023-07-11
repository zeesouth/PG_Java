package programmers.lv3.n12904;
class Solution
{
    public int solution(String s)
    {
        int answer = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];

        for(int i=0;i<s.length();i++) {
            dp[i][i] = true;
        }
        answer = 1;

        for(int i=0;i<s.length()-1;i++) {
            if(s.charAt(i) == s.charAt(i+1))
                dp[i][i+1] = true;
        }

        for(int len = 3; len <= s.length(); len++) {
            for(int i=0;i+len <= s.length();i++) {
                int j = i+len-1;
                if(s.charAt(i) == s.charAt(j) && dp[i+1][j-1]) {
                    dp[i][j] = true;
                    answer = len;
                }
            }
        }
        return answer;
    }
}
