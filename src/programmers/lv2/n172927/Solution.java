package programmers.lv2.n172927;

import java.util.*;

public class Solution {
    String[] m;
    final int INF = Integer.MAX_VALUE;
    HashMap<String, Integer> map = new HashMap<>();

    public int solution(int[] picks, String[] minerals) {
        m = minerals;
        map.put("diamond", 0);
        map.put("iron", 1);
        map.put("stone", 2);

        return dfs(picks[0], picks[1], picks[2], 0);
    }

    int dfs(int d, int i, int s, int idx) {
        if (d == 0 && i == 0 && s == 0) return 0;
        if (idx >= m.length) return 0;

        int res = INF;
        int tmp;

        if (d > 0) {
            tmp = Math.min(5, m.length - idx);
            res = Math.min(dfs(d - 1, i, s, idx + 5) + tmp, res);
        }

        if (i > 0) {
            tmp = 0;
            for (int j = idx; j < Math.min(idx + 5, m.length); j++) {
                if (map.get(m[j]) == 0) tmp += 5;
                else tmp += 1;
            }

            res = Math.min(dfs(d, i - 1, s, idx + 5) + tmp, res);
        }

        if (s > 0) {
            tmp = 0;
            for (int j = idx; j < Math.min(idx + 5, m.length); j++) {
                if (map.get(m[j]) == 0) tmp += 25;
                else if (map.get(m[j]) == 1) tmp += 5;
                else tmp += 1;
            }

            res = Math.min(dfs(d, i, s - 1, idx + 5) + tmp, res);
        }

        return res;
    }
}
