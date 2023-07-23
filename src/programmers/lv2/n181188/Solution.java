package programmers.lv2.n181188;
import java.util.*;
class Solution {
    public int solution(int[][] targets) {
        // 끝나는 지점을 기준으로 오름차순 정렬
        Arrays.sort(targets, (o1, o2) -> o1[1] - o2[1]);
        int answer = 0;
        int end = -1;
        for (int[] target : targets) {
            // 현재 시작점과 끝점 사이에 end가 포함된다면 pass
            if (end >= target[0] && end <= target[1]) continue;

            // 그렇지 않다면 정답+1, end갱신
            answer++;
            end = target[1] - 1;
        }
        return answer;
    }
}