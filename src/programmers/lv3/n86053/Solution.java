package programmers.lv3.n86053;

class Solution {
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        long answer = -1;

        // '소요 시간'을 기준으로 이분탐색
        long left = 1, right = 400_000_000_000_000L;
        long mid;
        int len = s.length;

        while (left <= right) {
            mid = (left + right) / 2;

            // 모든 도시 순회
            int gold = 0, silver = 0, sum = 0;
            for (int i = 0; i < len; i++) {
                int weight = w[i];
                int time = t[i];

                // mid시간 동안 몇 번 왕복 가능한지
                long cnt = mid / (time * 2);
                if ((mid % (time * 2)) >= time) { // 편도로 한번 더 운반 가능한지?
                    cnt++;
                }

                gold += Math.min(g[i], weight * cnt);
                silver += Math.min(s[i], weight * cnt);
                sum += Math.min(g[i] + s[i], weight * cnt);
            }

            // 현재 시간에서 운반 가능하면 시간 줄이기
            if (gold >= a && silver >= b && sum >= a + b) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }
}
