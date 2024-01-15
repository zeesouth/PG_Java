package programmers.lv3.n77886;

import java.util.*;

class Solution {
    public String[] solution(String[] s) {
        String[] answer = new String[s.length];

        for (int i = 0; i < s.length; i++) {

            Stack<Character> stack = new Stack<>();
            String str = s[i];
            int cnt = 0;

            // 110의 개수 세기
            for (int j = 0; j < str.length(); j++) {
                stack.push(str.charAt(j));

                if (stack.size() >= 3) {
                    char first = stack.pop();
                    if (first != '0') {
                        stack.push(first);
                        continue;
                    }
                    char second = stack.pop();
                    if (second != '1') {
                        stack.push(second);
                        stack.push(first);
                        continue;
                    }
                    char third = stack.pop();
                    if (third != '1') {
                        stack.push(third);
                        stack.push(second);
                        stack.push(first);
                        continue;
                    }
                    cnt++;
                }
            }

            StringBuilder sb = new StringBuilder();
            int insertIdx = -1, idx = stack.size() - 1;
            while (!stack.isEmpty()) {
                char val = stack.pop();
                // 스택은 가장 앞에 있는게 뒤에 있는 것
                sb.insert(0, val);

                // 가장 뒤에 있으면서 0을 마주친다면 이곳이 110의 삽입 위치
                if (insertIdx == -1 && val == '0') {
                    insertIdx = idx;
                }
                idx--;
            }

            insertIdx = insertIdx == -1 ? 0 : insertIdx + 1;
            while (cnt-- > 0) {
                sb.insert(insertIdx, "110");
            }
            answer[i] = sb.toString();
        }

        return answer;
    }
}
