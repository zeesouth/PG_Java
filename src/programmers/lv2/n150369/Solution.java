package programmers.lv2.n150369;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        long d = 0, p = 0;

        // 가장 먼 곳부터 이동
        for (int i = n - 1; i >= 0; i--) {
            d += deliveries[i];
            p += pickups[i];

            // 배달해야 하거나 픽업해야 할 것 들이 있다면 무조건 이동
            while (d > 0 || p > 0) {
                d -= cap;
                p -= cap;
                answer += (i + 1) * 2;
            }
        }
        return answer;
    }
}
