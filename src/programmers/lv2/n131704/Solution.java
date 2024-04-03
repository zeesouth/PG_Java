package programmers.lv2.n131704;

import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0;

        Stack<Integer> stack = new Stack<>();
        int num = 1;
        int i = 0;

        while (num <= order.length) {
            if (num == order[i]) {
                answer++;
                i++;
                num++;
            } else {
                if (!stack.isEmpty() && stack.peek() == order[i]) {
                    stack.pop();
                    i++;
                    answer++;
                } else {
                    stack.add(num);
                    num++;
                }
            }
        }

        while (!stack.isEmpty() && i < order.length) {
            int curr = stack.pop();
            if (order[i] != curr) break;
            i++;
            answer++;
        }

        return answer;
    }
}
