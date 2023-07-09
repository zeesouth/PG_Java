package programmers.lv3.n12927;
import java.util.*;
class Solution {
    public long solution(int n, int[] works) {
        // 작업량을 내림차순 정렬
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2-o1);

        for(int i=0;i<works.length;i++) pq.add(works[i]);

        // 가장 시간이 큰 작업량을 뽑은 후, -1을 해준 후 다시 pq에 넣기
        // 작업량 간 편차를 줄여주고 균등한 분포를 이루게 함
        while(n-- > 0) {
            int work = pq.poll();
            if(work == 0) break;
            pq.offer(--work);
        }

        long ans = 0;
        for(int work : pq) {
            ans += work * work;
        }

        return ans;
    }
}