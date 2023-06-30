package programmers.lv3.n64062;
// ref : https://github.com/bong6981/algorithms_websites/commit/bb6710c80384749c3cec0cb22ac423b1c22968a7

import java.util.*;
class Solution {
    /*
    // 1. 슬라이딩 윈도우, Deque 사용
    public int solution(int[] stones, int k) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        int ans = 200000000;

        for(int i=0;i<stones.length;i++) {
            if(!dq.isEmpty() && dq.peekFirst() == i-k) dq.pollFirst();

            while(!dq.isEmpty()) {
                if(stones[dq.peekLast()] < stones[i]) dq.pollLast();
                else break;
            }

            dq.offerLast(i);
            if(i >= k-1) ans = Math.min(ans, stones[dq.peekFirst()]);
        }
        return ans;
    }
    */

    // 2. 이분탐색 이용
    public int solution(int[] stones, int k) {
        int start = 1;
        int end = Math.max(stones.length, Arrays.stream(stones).max().getAsInt());
        int answer = 1;

        // 최소~최댓값
        while(start <= end) {
            int mid = (start + end) / 2;
            if(check(stones, mid, k)) {
                answer = Math.max(answer, mid);
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return answer;
    }

    private boolean check(int[] stones, int m, int k) {
        int cnt = 0;
        for(int stone : stones) {
            if(stone < m) {
                cnt += 1;
            } else {
                cnt = 0;
            }

            if(cnt == k) {
                return false;
            }
        }
        return true;
    }
}