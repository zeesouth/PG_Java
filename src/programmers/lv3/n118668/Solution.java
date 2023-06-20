package programmers.lv3.n118668;
import java.util.*;
class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        int alp_max = 0, cop_max = 0;

        // 모든 문제를 푸는데 필요한 최대 알고력, 최대 코딩력 구하기
        for(int[] p : problems) {
            alp_max = Math.max(alp_max, p[0]);
            cop_max = Math.max(cop_max, p[1]);
        }

        // 모든 문제를 풀 수 있는 경우 함수 종료
        if(alp >= alp_max && cop >= cop_max) return 0;

        // 처음 설정된 알고력이 최종 목표값보다 높은 경우
        if(alp >= alp_max) alp = alp_max;

        // 처음 설정된 코딩력이 최종 목표값보다 높은 경우
        if(cop >= cop_max) cop = cop_max;

        int dp[][] = new int[alp_max+2][cop_max+2];
        for(int i=alp;i<=alp_max;i++) {
            for(int j=cop;j<=cop_max;j++) dp[i][j] = Integer.MAX_VALUE;
        }

        dp[alp][cop] = 0;
        for(int i=alp;i<=alp_max;i++) {
            for(int j=cop;j<=cop_max;j++) {
                // 알고리즘 공부
                dp[i+1][j] = Math.min(dp[i+1][j], dp[i][j]+1);
                // 코딩 공부
                dp[i][j+1] = Math.min(dp[i][j+1], dp[i][j]+1);

                for(int[] p : problems) {
                    // 현재 풀 수 있는 문제인 경우
                    if(i >= p[0] && j >= p[1]) {
                        // 알고력, 코딩력이 목표 수준을 넘긴 경우
                        if(i+p[2] > alp_max && j+p[3] > cop_max) {
                            dp[alp_max][cop_max] = Math.min(dp[alp_max][cop_max], dp[i][j] + p[4]);
                        } // 알고력만 목표 수준을 넘긴 경우
                        else if(i+p[2] > alp_max) {
                            dp[alp_max][j+p[3]] = Math.min(dp[alp_max][j+p[3]], dp[i][j] + p[4]);
                        } // 코딩력만 목표 수준을 넘긴 경우
                        else if(j+p[3] > cop_max) {
                            dp[i+p[2]][cop_max] = Math.min(dp[i+p[2]][cop_max], dp[i][j] + p[4]);
                        } // 알고력, 코딩력이 목표 수준을 넘기지 못한 경우
                        else {
                            dp[i+p[2]][j+p[3]] = Math.min(dp[i+p[2]][j+p[3]], dp[i][j] + p[4]);
                        }
                    }
                }
            }
        }

        return dp[alp_max][cop_max];
    }
}
