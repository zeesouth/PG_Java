package programmers.lv3.n12946;

import java.util.*;

class Solution {
    private static List<int[]> ansList;

    public int[][] solution(int n) {
        ansList = new ArrayList<>();
        hanoi(n, 1, 3, 2);

        int answer[][] = new int[ansList.size()][2];
        for (int i = 0; i < ansList.size(); i++) {
            answer[i] = ansList.get(i);
        }

        return answer;
    }

    private static void hanoi(int n, int start, int to, int mid) {
        if (n == 0) return;

        // 1번 기둥의 n-1개를 3번에 걸쳐 2번으로 이동
        hanoi(n - 1, start, mid, to);

        // 가장 큰 n을 1에서 3으로 이동
        ansList.add(new int[]{start, to});

        // 2번의 기둥의 n-1개를 1번을 걸쳐 3번으로 이동
        hanoi(n - 1, mid, to, start);
    }
}