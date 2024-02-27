package programmers.lv2.n42578;

import java.util.*;

class Solution {
    HashSet<String>[] clo = new HashSet[30];
    HashMap<String, Integer> map = new HashMap<>();
    int id = 0;

    public int solution(String[][] clothes) {
        for (int i = 0; i < clothes.length; i++) {
            String a = clothes[i][0];
            String b = clothes[i][1];

            if (!map.containsKey(b)) {
                clo[id] = new HashSet<>();
                map.put(b, id++);
            }

            clo[map.get(b)].add(a);
        }

        int ans = 1;
        for (int i = 0; i < id; i++) {
            ans *= (clo[i].size() + 1);
        }

        return ans - 1;
    }
}