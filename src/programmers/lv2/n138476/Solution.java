package programmers.lv2.n138476;

import java.util.*;

class Solution {
    Map<Integer, Integer> map = new HashMap<>();

    public int solution(int k, int[] tangerine) {

        for (int i = 0; i < tangerine.length; i++) {
            if (!map.containsKey(tangerine[i])) map.put(tangerine[i], 0);
            map.put(tangerine[i], map.get(tangerine[i]) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int key : map.keySet()) {
            pq.add(map.get(key));
        }

        int rest = tangerine.length - k;

        while (!pq.isEmpty() && rest != 0) {
            int size = pq.poll();
            if (rest < size) {
                pq.add(rest - k);
                break;
            }
            rest -= size;
        }

        return pq.size();
    }
}