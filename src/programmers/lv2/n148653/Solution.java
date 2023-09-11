package programmers.lv2.n148653;

class Solution {
    public int solution(int storey) {
        int num = storey;
        int answer1 = 0;
        while (num > 0) {
            if (num % 10 >= 5) {
                answer1 += (10 - (num % 10));
                num += (10 - (num % 10));
            } else {
                answer1 += num % 10;
            }
            num /= 10;
        }

        num = storey;
        int answer2 = 0;
        while (num > 0) {
            if (num % 10 > 5) {
                answer2 += (10 - (num % 10));
                num += (10 - (num % 10));
            } else {
                answer2 += num % 10;
            }
            num /= 10;
        }

        return Math.min(answer1, answer2);
    }
}
