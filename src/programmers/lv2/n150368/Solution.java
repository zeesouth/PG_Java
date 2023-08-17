package programmers.lv2.n150368;

class Solution {
    int answer[] = new int[2];
    int discnt[] = {10, 20, 30, 40};
    int userPay[];

    public int[] solution(int[][] users, int[] emoticons) {
        userPay = new int[users.length];
        dfs(0, emoticons, users, 0, 0);
        return answer;
    }

    void dfs(int idx, int[] emoticons, int[][] users, int p, int e) {
        if (idx == emoticons.length) {
            if (answer[0] < e) {
                answer[0] = e;
                answer[1] = p;
            } else if (answer[0] == e) {
                if (answer[1] < p) answer[1] = p;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            int[] beforeUser = new int[userPay.length];
            int price = emoticons[idx] * (100 - discnt[i]) / 100;
            int plusP = 0;
            int plusE = 0;

            for (int j = 0; j < userPay.length; j++) {
                beforeUser[j] = userPay[j];
                if (users[j][0] > discnt[i]) continue;
                if (userPay[j] != -1) {
                    if (userPay[j] + price < users[j][1]) {
                        userPay[j] += price;
                        plusP += price;
                    } else {
                        plusP -= userPay[j];
                        userPay[j] = -1;
                        plusE++;
                    }
                }
            }

            dfs(idx + 1, emoticons, users, p + plusP, e + plusE);
            userPay = beforeUser;
        }

    }
}
