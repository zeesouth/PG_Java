package programmers.lv3.n214289;

import java.util.*;

// 참고 : https://velog.io/@hyungmin96/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EC%97%90%EC%96%B4%EC%BB%A8
class Solution {
    int INF = 987654321;

    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int answer = INF;
        int[][] dp = new int[onboard.length][50 + 2];

        // 탐색하지 않은 상태로 초기화
        for (int i = 0; i < onboard.length; i++)
            Arrays.fill(dp[i], INF);

        // 영하온도가 입력되어도 인덱스를 벗어나지 않기 위해 처리
        temperature += 10;
        t1 += 10;
        t2 += 10;

        // 초기값 설정
        dp[0][temperature] = 0;

        // i분 일때
        for (int i = 0; i < onboard.length - 1; i++) {
            // 온도가 j일 경우
            for (int j = 0; j <= 50; j++) {
                // 도달할수없는 경우의 [시간][온도]일 경우 건너뛰기
                if (dp[i][j] == INF) continue;

                // 승객이 탑승한 시간에 t1 ~ t2범위의 온도가 아닐경우 건너뛰기
                if (onboard[i] == 1 && (j < t1 || j > t2)) continue;

                // 에어컨을 사용하여 온도 올리기
                dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + a);
                // 에어컨을 사용하여 온도 낮추기
                if (j - 1 >= 0)
                    dp[i + 1][j - 1] = Math.min(dp[i + 1][j - 1], dp[i][j] + a);
                // 에어컨을 사용하여 온도 유지하기
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + b);

                // 에어컨 끄기
                int next_tmp = j;

                // 실외온도가 실내온도보다 높아 에어컨을 종료할 경우 온도를 올려준다.
                if (j < temperature) {
                    next_tmp++;
                }

                // 실내온도가 높을경우 온도를 낮춰준다.
                if (j > temperature) {
                    next_tmp--;
                }

                if (next_tmp >= 0)
                    // i + 1분에 에어컨이 종료됨으로써 변경된 실내온도의 값에 최소값을 갱신한다.
                    dp[i + 1][next_tmp] = Math.min(dp[i + 1][next_tmp], dp[i][j]);
            }
        }

        for (int i = 0; i <= 50; i++) {
            // 종료되는 시점에 사람이 탑승해있고 적당한 온도가 아닐경우는 제외
            if (onboard[onboard.length - 1] == 1 && (i < t1 || i > t2)) continue;
            answer = Math.min(answer, dp[onboard.length - 1][i]);
        }
        return answer;
    }
}