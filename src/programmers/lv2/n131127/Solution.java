package programmers.lv2.n131127;

import java.util.*;

class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < want.length; i++) {
            map.put(want[i], i);
        }

        int[][] sum = new int[discount.length + 1][want.length];

        for (int i = 1; i <= discount.length; i++) {
            Integer id = map.get(discount[i - 1]);
            if (id == null) continue;

            for (int j = 0; j < want.length; j++) {
                if (id == j) sum[i][j] = sum[i - 1][j] + 1;
                else sum[i][j] = sum[i - 1][j];
            }
        }

        int ans = 0;
        for (int i = discount.length; i >= 10; i--) {
            boolean flag = true;

            for (int j = 0; j < want.length; j++) {
                if (sum[i][j] - sum[i - 10][j] != number[j]) {
                    flag = false;
                    break;
                }
            }

            if (flag) ans++;
        }

        return ans;
    }
}

