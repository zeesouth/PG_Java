package programmers.lv3.n42884;
import java.util.*;
class Solution {
    public int solution(int[][] routes) {
        Arrays.sort(routes,
                (o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);

        int answer = 0;
        int temp = -30001;  // 지난 마지막 지점
        for(int i=0;i<routes.length;i++) {
            int start = routes[i][0];
            int end = routes[i][1];

            if(temp < start) {
                answer++;
                temp = end;
            } else if (temp > end) temp = end;

        }
        return answer;
    }
}
