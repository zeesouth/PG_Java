package programmers.lv2.n42586;

import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int N = progresses.length;
        int[] answer = new int[N];

        int id = 0;
        int max = 0;
        for (int i = 0; i < N; i++) {
            int rest = ((100 - progresses[i]) / speeds[i]) + ((100 - progresses[i]) % speeds[i] != 0 ? 1 : 0);
            if (max < rest) {
                answer[id]++;
                id++;
                max = rest;
            } else {
                answer[id - 1]++;
            }

        }

        return Arrays.copyOf(answer, id);
    }
}

