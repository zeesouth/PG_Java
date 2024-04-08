package programmers.lv3.n258707;

import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        int answer = 0;
        int n = cards.length;
        HashSet<Integer> before = new HashSet<>();
        HashSet<Integer> after = new HashSet<>();

        int id = n / 3;
        for (int i = 0; i < id; i++) {
            before.add(cards[i]);
        }

        while (true) {
            answer++;

            if (id >= n) break;

            after.add(cards[id]);
            after.add(cards[id + 1]);
            id += 2;

            boolean flag = false;

            // 1. 기존 카드로 사용할 수 있는가
            for (int i : before) {
                if (before.contains(n + 1 - i)) {
                    before.remove(n + 1 - i);
                    before.remove(i);
                    flag = true;
                    break;
                }
            }
            if (flag) continue;

            // 2. 카드 하나를 사용해야 하는가
            if (coin <= 0) break;
            for (int i : before) {
                if (after.contains(n + 1 - i)) {
                    after.remove(n + 1 - i);
                    before.remove(i);
                    coin -= 1;
                    flag = true;
                    break;
                }
            }
            if (flag) continue;

            // 3. 카드 두개를 사용해야 하는가
            if (coin <= 1) break;
            for (int i : after) {
                if (after.contains(n + 1 - i)) {
                    after.remove(n + 1 - i);
                    after.remove(i);
                    coin -= 2;
                    flag = true;
                    break;
                }
            }

            if (!flag) break;
        }

        return answer;
    }
}