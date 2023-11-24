package programmers.lv1.n118666;

import java.util.*;

class Solution {
    public String solution(String[] survey, int[] choices) {
        int[] type = new int[4];

        int length = choices.length;

        for (int i = 0; i < length; i++) {
            char a = survey[i].charAt(0);
            char b = survey[i].charAt(1);
            int choice = choices[i] - 4;

            if (a > b) {
                char tmp = a;
                a = b;
                b = tmp;
                choice = -choice;
            }

            if (a == 'R') {
                type[0] += choice;
            } else if (a == 'C') {
                type[1] += choice;
            } else if (a == 'J') {
                type[2] += choice;
            } else if (a == 'A') {
                type[3] += choice;
            }

        }

        StringBuilder ans = new StringBuilder();
        if (type[0] <= 0) ans.append('R');
        else ans.append('T');
        if (type[1] <= 0) ans.append('C');
        else ans.append('F');
        if (type[2] <= 0) ans.append('J');
        else ans.append('M');
        if (type[3] <= 0) ans.append('A');
        else ans.append('N');
        return ans.toString();
    }


}