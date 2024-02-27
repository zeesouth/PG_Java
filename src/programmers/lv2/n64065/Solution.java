package programmers.lv2.n64065;

import java.util.*;

class Solution {
    int cnt[][] = new int[100_000][2];

    public int[] solution(String s) {
        s = s.replaceAll("\\{\\{", "").replaceAll("\\}\\}", "");

        String[] arr = s.split("\\},\\{");

        for (int i = 0; i < arr.length; i++) {
            String[] split = arr[i].split(",");
            for (int j = 0; j < split.length; j++) {
                int num = Integer.parseInt(split[j]);
                cnt[num][0] = num;
                cnt[num][1]++;
            }
        }

        Arrays.sort(cnt, (o1, o2) -> o2[1] - o1[1]);

        int[] answer = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            answer[i] = cnt[i][0];
        }

        return answer;
    }
}