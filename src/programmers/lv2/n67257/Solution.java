package programmers.lv2.n67257;

import java.util.*;

class Solution {
    char[] top = {'-', '*', '+'};
    long ans;

    void swap(int a, int b) {
        char k = top[a];
        top[a] = top[b];
        top[b] = k;
    }

    long calculate(ArrayList<Long> operand, ArrayList<Character> operator) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < operator.size(); j++) {
                if (operator.get(j) == top[i]) {
                    if (top[i] == '-')
                        operand.add(j, operand.remove(j) - operand.remove(j));
                    else if (top[i] == '*')
                        operand.add(j, operand.remove(j) * operand.remove(j));
                    else
                        operand.add(j, operand.remove(j) + operand.remove(j));
                    operator.remove(j--);
                }
            }
        }
        return Math.abs(operand.get(0));
    }

    void perm(ArrayList<Long> operand, ArrayList<Character> operator, int depth) {
        if (depth == 3) {
            long tmp = calculate(
                    (ArrayList<Long>) operand.clone(),
                    (ArrayList<Character>) operator.clone()
            );
            if (tmp > ans) ans = tmp;
            return;
        }

        for (int i = depth; i < 3; i++) {
            swap(i, depth);
            perm(operand, operator, depth + 1);
            swap(i, depth);
        }
    }


    public long solution(String expression) {
        ArrayList<Long> operand = new ArrayList<>();
        ArrayList<Character> operator = new ArrayList<>();

        StringBuilder num = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                num.append(expression.charAt(i));
            } else {
                operand.add(Long.parseLong(num.toString()));
                num = new StringBuilder();
                operator.add(expression.charAt(i));
            }
        }
        operand.add(Long.parseLong(num.toString()));
        perm(operand, operator, 0);
        return ans;
    }
}