package programmers.lv2.n76502;

import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = 0;
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            stack.clear();
            boolean flag = true;

            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt((i + j) % s.length());

                if (c == '{' || c == '(' || c == '[') {
                    stack.push(c);
                } else {
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }

                    if (c == '}') {
                        if (stack.pop() != '{') {
                            flag = false;
                            break;
                        }
                    } else if (c == ']') {
                        if (stack.pop() != '[') {
                            flag = false;
                            break;
                        }
                    } else {
                        if (stack.pop() != '(') {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if (!stack.isEmpty()) continue;

            if (flag) answer++;
        }

        return answer;
    }
}
