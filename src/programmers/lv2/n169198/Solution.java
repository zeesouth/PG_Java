package programmers.lv2.n169198;

class Solution {
    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        int[] answer = new int[balls.length];


        for (int i = 0; i < balls.length; i++) {
            int[] ball = balls[i];

            int diffX = startX - ball[0]; // X차이
            int diffY = startY - ball[1]; // Y차이

            int left = getDist(startX + ball[0], diffY);      // 왼쪽 쿠션
            int right = getDist(m - startX + m - ball[0], diffY); // 오른쪽 쿠션
            int top = getDist(diffX, n - startY + n - ball[1]);   // 위쪽 쿠션
            int bottom = getDist(diffX, startY + ball[1]);    // 아래쪽 쿠션

            int res = 0;
            // x축이 같은 선상인 경우
            if (diffX == 0) {
                // 아래쪽 쿠션 안됨
                if (diffY > 0) res = Math.min(left, Math.min(right, top));
                    // 위쪽 쿠션 안됨
                else res = Math.min(left, Math.min(right, bottom));
            }
            // y축이 같은 선상인 경우
            else if (diffY == 0) {
                // 왼쪽 쿠션 안됨
                if (diffX > 0) res = Math.min(right, Math.min(top, bottom));
                    // 오른쪽 쿠션 안됨
                else res = Math.min(left, Math.min(top, bottom));
            }
            // 모두 다른 경우
            else {
                res = Math.min(left, Math.min(right, Math.min(top, bottom)));
            }
            answer[i] = res;
        }

        return answer;
    }


    // 거리는 거리의 제곱으로 계산
    int getDist(int diff1, int diff2) {
        return diff1 * diff1 + diff2 * diff2;
    }
}