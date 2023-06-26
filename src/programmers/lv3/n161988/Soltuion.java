package programmers.lv3.n161988;
class Solution {
    public long solution(int[] sequence) {

        // -1, 1, ...
        long s1[] = new long[sequence.length];
        // 1, -1, ...
        long s2[] = new long[sequence.length];

        int m1 = -1, m2 = 1;
        for(int i=0;i<sequence.length;i++) {
            int data = sequence[i];
            s1[i] = m1 * data;
            s2[i] = m2 * data;
            m1 *= -1;
            m2 *= -1;
        }

        long dp1[] = new long[sequence.length];
        long dp2[] = new long[sequence.length];
        dp1[0] = s1[0];
        dp2[0] = s2[0];
        long ans = Math.max(dp1[0], dp2[0]);

        for(int i=1;i<sequence.length;i++) {
            dp1[i] = Math.max(dp1[i-1] + s1[i], s1[i]);
            dp2[i] = Math.max(dp2[i-1] + s2[i], s2[i]);
            ans = Math.max(ans, Math.max(dp1[i], dp2[i]));
        }

        return ans;
    }
}