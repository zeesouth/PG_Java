package programmers.lv2.n155651;

import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        int[][] time = new int[book_time.length][2];

        for (int i = 0; i < book_time.length; i++) {
            time[i][0] = StringToInt(book_time[i][0]);
            time[i][1] = StringToInt(book_time[i][1]) + 10;
        }

        Arrays.sort(time, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < time.length; i++) {
            if (!pq.isEmpty() && pq.peek() <= time[i][0]) {
                pq.poll();
            }
            pq.add(time[i][1]);
        }

        return pq.size();
    }

    int StringToInt(String time) {
        String t[] = time.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }

}
