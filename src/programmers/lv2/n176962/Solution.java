package programmers.lv2.n176962;

import java.util.*;

class Solution {
    public String[] solution(String[][] plans) {

        int len = plans.length;

        int newPlan[][] = new int[len][3];
        for (int i = 0; i < len; i++) {
            int start = strToInt(plans[i][1]);
            newPlan[i][0] = i;
            newPlan[i][1] = start;
            newPlan[i][2] = start + Integer.parseInt(plans[i][2]);
        }

        Arrays.sort(newPlan,
                (o1, o2) -> o1[1] == o2[1] ? o2[2] - o1[2] : o1[1] - o2[1]
        );

        int size = 0;
        int[][] stack = new int[len][2];
        int a = 0;
        String[] answer = new String[len];
        int e = newPlan[0][2], idx = newPlan[0][0];
        for (int i = 1; i < len; i++) {
            if (e <= newPlan[i][1]) {
                answer[a++] = plans[idx][0];
                int rest = newPlan[i][1] - e;
                while (rest > 0 && size > 0) {
                    if (rest >= stack[size - 1][1]) {
                        answer[a++] = plans[stack[size - 1][0]][0];
                        rest -= stack[size - 1][1];
                        size--;
                    } else {
                        stack[size - 1][1] -= rest;
                        rest = 0;
                    }
                }
            } else {
                stack[size][0] = idx;
                stack[size++][1] = e - newPlan[i][1];
            }
            e = newPlan[i][2];
            idx = newPlan[i][0];
        }

        answer[a++] = plans[idx][0];

        for (int i = size - 1; i >= 0; i--) {
            answer[a++] = plans[stack[i][0]][0];
        }

        return answer;
    }

    int strToInt(String s) {
        String[] split = s.split(":");
        int h = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        return h * 60 + m;
    }
}
