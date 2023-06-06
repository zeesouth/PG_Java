package programmers.lv3.n12987;

import java.util.PriorityQueue;
class Solution {
    public int solution(int[] A, int[] B) {
        PriorityQueue<Integer> a = new PriorityQueue<>();
        PriorityQueue<Integer> b = new PriorityQueue<>();

        for(int i=0;i<A.length;i++) a.add(A[i]);
        for(int i=0;i<B.length;i++) b.add(B[i]);

        int ans = 0;
        while(!a.isEmpty()) {
            int currA = a.peek();
            if(b.isEmpty()) break;
            else if(currA < b.poll()) {
                ans++;
                a.poll();
            }
        }
        return ans;
    }
}
