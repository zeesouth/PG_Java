package programmers.lv2.n132265;

class Solution {
    public int solution(int[] topping) {
        int answer = 0;

        int[] a = new int[10000 + 1];
        int aCnt = 0;
        int[] b = new int[10000 + 1];
        int bCnt = 0;

        for (int i = 0; i < topping.length; i++) {
            if (a[topping[i]] == 0) {
                aCnt++;
            }
            a[topping[i]]++;
        }

        for (int i = 0; i < topping.length; i++) {
            if (b[topping[i]] == 0) {
                bCnt++;
            }
            b[topping[i]]++;

            if (a[topping[i]] > 0) {
                a[topping[i]]--;
                if (a[topping[i]] == 0) aCnt--;
            }

            if (aCnt == bCnt) answer++;
        }

        return answer;
    }
}