package programmers.lv2.n42626;

import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        if (K == 0) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < scoville.length; i++) pq.add(scoville[i]);

        int ans = 0;

        while (pq.size() >= 2) {
            int a = pq.poll();
            int b = pq.poll();

            if (a >= K) return ans;

            pq.add(a + (b * 2));
            ans++;
        }

        return pq.peek() >= K ? ans : -1;
    }
}
