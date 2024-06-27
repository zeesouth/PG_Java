package programmers.lv2.n152996;

import java.util.*;

class Solution {
    public long solution(int[] weights) {
        Arrays.sort(weights);

        long answer = 0;

        Map<Double, Integer> map = new HashMap<>();

        for (double w : weights) {
            double a = w * 1.0;
            double b = w * 2.0 / 3.0;
            double c = w * 1.0 / 2.0;
            double d = w * 3.0 / 4.0;

            if (map.containsKey(a)) answer += map.get(a);
            if (map.containsKey(b)) answer += map.get(b);
            if (map.containsKey(c)) answer += map.get(c);
            if (map.containsKey(d)) answer += map.get(d);

            map.put(w, map.getOrDefault(w, 0) + 1);
        }

        return answer;
    }
}
