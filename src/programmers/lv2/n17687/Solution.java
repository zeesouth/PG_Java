package programmers.lv2.n17687;

class Solution {
    final char[] numToChar = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    public String solution(int n, int t, int m, int p) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        int next = 1;
        int cnt = 0;
        int turn = 0;
        int idx = 0;

        int[] stack = new int[t];
        while (cnt < t) {
            if (num < n) {
                stack[idx++] = num;
                while (idx-- > 0) {
                    if (turn + 1 == p) {
                        sb.append(numToChar[stack[idx]]);
                        cnt++;
                        if (cnt == t) break;
                    }
                    turn = (turn + 1) % m;
                }
                idx = 0;
                num = next++;
            } else {
                stack[idx++] = num % n;
                num /= n;
            }
        }

        return sb.toString();
    }
}