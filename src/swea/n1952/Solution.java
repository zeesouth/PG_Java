package swea.n1952;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int T;
    static int[] ticket = {0, 0, 0, 2, 11};
    static int[] price; // 1일, 1달, 3달, 1년 -> 달, 년 이용권은 1일부터 시작/1년 이용권은 1월 1일부터 시작
    static int[] scd;
    static int[] dp;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/n1952.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            price = new int[4 + 1];
            scd = new int[12 + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= 4; i++) price[i] = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= 12; i++) scd[i] = Integer.parseInt(st.nextToken());

            dp = new int[12 + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);

            dp[0] = 0;
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 4; j++) {
                    if (j == 1) dp[i] = Math.min(dp[i - 1] + price[j] * scd[i], dp[i]);
                    else {
                        if (i <= 12 - ticket[j]) dp[i + ticket[j]] = Math.min(dp[i - 1] + price[j], dp[i + ticket[j]]);
                        else dp[12] = Math.min(dp[i - 1] + price[j], dp[12]);
                    }

                }
            }

            sb.append("#").append(t).append(" ").append(dp[12]).append("\n");
        }
        System.out.println(sb);
        br.close();
    }
}

/*
 * 연속된 3달 이용 가능 : 대신 결제한 해의 달만 사용 가능
 * 1년동안 이용 가능 : 1년 이용권은 매월 1월 1일부터 시작
 *
 * */
