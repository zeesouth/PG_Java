package programmers.lv3.n68646;
class Solution {
    public int solution(int[] a) {
        if (a.length <= 2) return a.length;

        int leftMin = a[0];
        int[] rightMin = new int[a.length];
        rightMin[a.length - 1] = a[a.length - 1];

        for (int i = a.length - 2; i > 0; i--)
            rightMin[i] = Math.min(rightMin[i + 1], a[i]);

        int answer = 2;
        for (int i = 1; i < a.length - 1; i++) {
            if (leftMin >= a[i] || rightMin[i] >= a[i]) answer++;
            leftMin = Math.min(leftMin, a[i]);
        }
        return answer;
    }
}