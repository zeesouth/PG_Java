package programmers.lv3.n42628;

import java.util.PriorityQueue;
import java.util.StringTokenizer;
class Solution {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // 최소힙
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(); // 최대힙
    public int[] solution(String[] operations) {

        for(int i=0;i<operations.length;i++) {
            StringTokenizer st = new StringTokenizer(operations[i]);
            char order = st.nextToken().charAt(0);
            int value = Integer.parseInt(st.nextToken());

            if(order == 'I') {
                minHeap.add(value);
                maxHeap.add(-value);
            } else {
                // 최댓값 삭제
                if(value == 1 && !maxHeap.isEmpty()) {
                    minHeap.remove(-maxHeap.poll());
                } // 최솟값 삭제
                else if (value == -1 && !minHeap.isEmpty()){
                    maxHeap.remove(-minHeap.poll());
                }
            }
        }


        int[] answer = {0, 0};
        if(!minHeap.isEmpty()) {
            answer[0] = -maxHeap.poll();
            answer[1] = minHeap.poll();
        }

        return answer;
    }
}