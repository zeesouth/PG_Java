package programmers.lv3.n12971;
class Solution {
    public int solution(int sticker[]) {
        if(sticker.length == 1) return sticker[0];

        int N = sticker.length;

        // 첫 번째 스티커를 뜯은 경우와 그렇지 않은 경우
        int dp1[] = new int[N], dp2[] = new int[N];
        dp1[1] = dp1[0] = sticker[0];

        for(int i=2;i<N-1;i++) dp1[i] = Math.max(dp1[i-1], dp1[i-2]+sticker[i]);

        for(int i=1;i<N;i++) dp2[i] = Math.max(dp2[i-1], dp2[((i-2)+N)%N]+sticker[i]);

        return Math.max(dp1[N-2], dp2[N-1]);
    }
}