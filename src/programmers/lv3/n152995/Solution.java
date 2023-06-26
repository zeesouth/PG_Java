package programmers.lv3.n152995;
import java.util.*;
class Solution {
    public int solution(int[][] scores) {
        int wScore = scores[0][0] + scores[0][1];
        Score arr[] = new Score[scores.length];
        for(int i=0;i<scores.length;i++)
            arr[i] = new Score(i, scores[i][0], scores[i][1]);
        Arrays.sort(arr);

        int ans = 1;
        int peerPoint = 0;  // 동료 점수의 임계값
        for(int i=0;i<arr.length;i++) {
            // 인센티브를 받지 못하는 경우
            if(arr[i].b < peerPoint) {
                // 원호가 인센티브를 받지 못하는 경우
                if(arr[i].id == 0) return -1;
            } else {
                peerPoint = Math.max(arr[i].b, peerPoint);
                if(wScore < arr[i].a+arr[i].b) ans++;
            }
        }

        return ans;
    }
}

class Score implements Comparable<Score> {
    int id, a, b;
    Score(int id, int a, int b) {
        this.id = id;
        this.a = a;
        this.b = b;
    }

    public int compareTo(Score s) {
        // 근무 태도 내림차순
        if(this.a != s.a) return s.a-this.a;
        // 동료 평가 점수 오름차순
        return this.b-s.b;
    }
}

/*
풀이 접근법 : 근무 태도 (a) 내림차순 정렬 -> 동료 평가 (b) 오름차순 정렬
- 반복문 수행 시 근무태도는 어쩌피 이전 팀원보다 낮거나 같음
1) 동료 점수가 낮은지, 높은지 체크하면 제외 대상을 뽑을 수 있음!
2) 점수의 합이 원호보다 높다면 원호의 등수가 낮아짐 (ans 1 증가)
 */