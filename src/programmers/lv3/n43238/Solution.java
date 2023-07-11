package programmers.lv3.n43238;
import java.util.*;
class Solution {
    public long solution(int n, int[] times) {
        Arrays.sort(times);
        long ans = 0;
        long left = 1, right = (long) times[times.length-1] * n;

        while(left <= right) {
            long mid = (left + right) / 2;
            long people = 0;
            for(int time : times) {
                // people : 모든 심사관들이 mid분 동안 심사한 사람의 수
                people += (mid / time);
                // 모든 심사관을 거치지 않아도 mid분 동안 N명 이상의 심사를 할 수 있다면 나간다.
                if(people >= n) break;
            }

            // 심사한 사람의 수가 심사 받아야 할 수보다 같거나 많은 경우 : 정답 갱신, 오른쪽 범위 -1
            if(people >= n) {
                ans = mid;
                right = mid-1;
            } else {
                left = mid+1;
            }
        }
        return ans;
    }
}