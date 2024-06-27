package programmers.lv2.n92342;

import java.util.*;

class Solution {
    int a = 0, max = -1;
    int[] answer = {-1}, arr;

    public int[] solution(int n, int[] info) {
        for (int i = 0; i < info.length; i++) {
            if (info[i] > 0) a += (10 - i);
        }
        arr = info;

        dfs(10, 0, n, new int[11]);

        return answer;
    }

    void dfs(int idx, int sum, int cnt, int[] list) {
        if (cnt == 0 || idx == -1) {
            if (a >= sum) return;

            if (sum - a > max) {
                answer = Arrays.copyOf(list, 11);
                if (cnt > 0) answer[11] += cnt;
                max = sum - a;
            }

            return;
        }

        for (int i = cnt; i >= 0; i--) {
            if (cnt - i < 0) continue;
            list[idx] = i;
            boolean flag = arr[idx] < list[idx] && arr[idx] != 0;
            if (flag) a -= 10 - idx;

            dfs(idx - 1, sum + (arr[idx] < list[idx] ? 10 - idx : 0), cnt - i, list);

            if (flag) a += 10 - idx;
            list[idx] = 0;
        }
    }
}