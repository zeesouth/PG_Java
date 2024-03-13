package programmers.lv2.n12899;

class Solution {
    public String solution(int n) {
        StringBuilder sb = new StringBuilder();

        int num[] = {1, 2, 4};

        n--;
        while (n / 3 != 0) {
            int curr = n % 3;
            sb.insert(0, num[curr]);
            n = n / 3 - 1;
        }

        sb.insert(0, num[n]);

        return sb.toString();
    }
}