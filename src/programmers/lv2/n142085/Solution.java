package programmers.lv2.n142085;

import java.util.*;

class Solution {
    public int solution(int n, int k, int[] enemy) {

        int answer = enemy.length;

        // 무적권의 수가 병사의 수보다 같거나 큰 경우
        if (k >= enemy.length) return answer;

        // 최대 힙
        Queue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (int i = 0; i < enemy.length; i++) {
            n -= enemy[i];
            pq.add(enemy[i]);

            if (n < 0) {
                if (k > 0 && !pq.isEmpty()) {
                    n += pq.poll();
                    k--;
                } else {
                    answer = i;
                    break;
                }
            }
        }

        return answer;
    }
}