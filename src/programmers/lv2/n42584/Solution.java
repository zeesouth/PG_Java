package programmers.lv2.n42584;

class Solution {
    public int[] solution(int[] prices) {
        int N = prices.length;
        int id = 0;
        int stack[][] = new int[N][2];

        int[] answer = new int[N];

        for (int i = 0; i < N; i++) {
            while (id >= 1 && stack[id - 1][1] > prices[i]) {
                id--;
                answer[stack[id][0]] = i - stack[id][0];
            }

            stack[id][0] = i;
            stack[id][1] = prices[i];
            id++;
        }

        while (id >= 1) {
            id--;
            answer[stack[id][0]] = (N - 1) - stack[id][0];
        }

        return answer;
    }
}
