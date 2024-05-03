package programmers.lv2.n135807;

import java.util.*;

class Solution {
    public int solution(int[] arrayA, int[] arrayB) {
        int gcd1 = arrayA[0];
        int gcd2 = arrayB[0];

        for (int i = 1; i < arrayA.length; i++) {
            gcd1 = getGCD(gcd1, arrayA[i]);
            gcd2 = getGCD(gcd2, arrayB[i]);
        }

        int answer = 0;
        boolean flag = true;
        // A 비교
        for (int i = 0; i < arrayA.length; i++) {
            if (arrayA[i] % gcd2 == 0) {
                flag = false;
                break;
            }
        }

        if (flag) {
            answer = gcd2;
        }

        flag = true;

        // B 비교
        for (int i = 0; i < arrayB.length; i++) {
            if (arrayB[i] % gcd1 == 0) {
                flag = false;
                break;
            }
        }

        if (flag) {
            answer = Math.max(gcd1, answer);
        }

        return answer;
    }


    int getGCD(int num1, int num2) {
        if (num1 % num2 == 0) {
            return num2;
        }
        return getGCD(num2, num1 % num2);
    }
}
