package programmers.lv1.n150370;

import java.util.*;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int t = strToInt(today, 0);

        Map<Character, Integer> map = new HashMap<>();

        for (String s : terms) {
            String[] data = s.split(" ");
            char d1 = data[0].charAt(0);
            int d2 = Integer.parseInt(data[1]);
            map.put(d1, d2);
        }

        int ans[] = new int[privacies.length];
        int idx = 0;

        for (int i = 0; i < privacies.length; i++) {
            String[] curr = privacies[i].split(" ");
            int tt = map.get(curr[1].charAt(0));
            int d = strToInt(curr[0], tt);

            if (d <= t) ans[idx++] = i + 1;
        }

        int answer[] = Arrays.copyOfRange(ans, 0, idx);

        return answer;
    }

    int strToInt(String s, int plus) {
        String[] d = s.split("\\.");
        int year = Integer.parseInt(d[0]);
        int month = Integer.parseInt(d[1]);
        int day = Integer.parseInt(d[2]);

        if (plus != 0) {
            int y = plus / 12;
            int m = plus % 12;
            year += y;

            if (month + m > 12) {
                year++;
                month = (month + m) % 12;
            } else {
                month += m;
            }
        }

        int res = year * 10000 + month * 100 + day;
        // System.out.println(res);
        return res;
    }
}
