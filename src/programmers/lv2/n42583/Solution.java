package programmers.lv2.n42583;

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {

        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> time = new LinkedList<>();
        queue.add(0);
        time.add(1);
        int next = 1;
        int sum = truck_weights[0];
        int currTime = 1;

        while(!queue.isEmpty()) {

            // 다리를 건너고 있는 트럭이 다리를 지날 수 있는지
            // -> 시간이 다 된 첫번째 트럭
            if(currTime-time.peek() >= bridge_length) {
                int w = truck_weights[queue.poll()];
                time.poll();
                sum-=w;
            }

            // 대기하고 있는 트럭이 다리를 건널 수 있는지
            // 최대 무게 - 다리에 있는 트럭의 합 >= 트럭 무게 라면 가능
            while(next != truck_weights.length && sum+truck_weights[next] <= weight) {
                queue.add(next);
                time.add(currTime);
                sum+=truck_weights[next];
                next++;
            }


            currTime+=1;
        }



        return currTime-1;
    }
}