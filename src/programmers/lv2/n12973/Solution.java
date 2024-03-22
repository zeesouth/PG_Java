package programmers.lv2.n12973;

import java.util.*;

class Solution {
    public int solution(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (stack.isEmpty()) {
                stack.add(c);
            } else {
                if (stack.peek() == c) {
                    stack.pop();
                    continue;
                }
                stack.add(c);
            }
        }

        return stack.isEmpty() ? 1 : 0;
    }
}
