package programmers.lv3.n258709;
// 풀이 출처: https://school.programmers.co.kr/questions/66170

import java.util.*;

class Solution {
    int n;
    boolean[] visited;
    ArrayList<int[]> diceComb;
    ArrayList<Integer> scoreA;
    ArrayList<Integer> scoreB;

    public int[] solution(int[][] dice) {
        n = dice.length;
        int[] answer = new int[n/2];
        visited = new boolean[n];
        diceComb = new ArrayList<>();

        // 1. A가 뽑을 수 있는 주사위 조합 구하기
        permutation(0, 0, new int[n/2]);

        // 2. 주사위 조합 별로 승률 계산
        int max = 0;
        for(int[] combA : diceComb) {
            int[] combB = new int[n/2];
            boolean[] other = new boolean[n];

            int idx = 0;
            for(int choice : combA) {
                other[choice] = true;
            }

            for(int i=0;i<other.length;i++) {
                if(other[i]) continue;
                combB[idx] = i;
                idx++;
            }

            scoreA = new ArrayList<>(); // A가 선택한 주사위 모든 조합
            scoreB = new ArrayList<>(); // B가 선택한 주사위 모든 조합

            combDice(0, combA, dice, 0, scoreA);
            combDice(0, combB, dice, 0, scoreB);

            Collections.sort(scoreA);
            Collections.sort(scoreB);

            // 3. 이분 탐색으로 승리 카운트 수를 찾는다.
            int totalWinCount = 0;

            for(int a : scoreA) {
                int left = 0;
                int right = scoreB.size();

                while(left + 1 < right) {
                    int mid = (left + right) / 2;

                    if(a > scoreB.get(mid)) {
                        left = mid;
                    } else {
                        right = mid;
                    }
                }

                totalWinCount += left;
            }

            if(totalWinCount > max) {
                max = totalWinCount;
                answer = combA;
            }
        }

        for(int i=0;i<answer.length;i++) {
            answer[i]++;
        }

        return answer;
    }

    void combDice(int idx, int[] dices, int[][] originDices, int sum, ArrayList<Integer> team) {
        if(idx == dices.length) {
            team.add(sum);
            return;
        }

        for(int i=0;i<6;i++) {
            combDice(idx + 1, dices, originDices, sum + originDices[dices[idx]][i], team);
        }
    }

    void permutation(int depth, int idx, int[] arr) {
        if(depth == n/2) {
            diceComb.add(arr.clone());
            return;
        }

        for(int i=idx;i<n;i++) {
            if(visited[i]) continue;

            visited[i] = true;
            arr[depth] = i;
            permutation(depth+1, i+1, arr);
            visited[i] = false;
        }
    }
}