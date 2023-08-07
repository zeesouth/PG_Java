package programmers.lv4.n43236;

import java.util.*;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        Arrays.sort(rocks);
        int answer = 0;

        int left = 1, right = distance;
        while (left <= right) {
            int mid = (left + right) / 2;

            int start = 0;
            int end = distance;
            int cnt = 0;
            for (int i = 0; i < rocks.length; i++) {
                if (rocks[i] - start < mid) cnt++;
                else start = rocks[i];
            }

            // 바뀐 기준의 바위 지점에 대해서도 제거할 수 있는 돌인지 확인
            if (end - start < mid) cnt++;

            if (n < cnt) right = mid - 1;
            else {
                answer = Math.max(mid, answer);
                left = mid + 1;
            }
        }

        return answer;
    }
}

/*
접근법: 지점 간 최소 거리를 이분 탐색하면서 최대 값을 찾는 것
 */
