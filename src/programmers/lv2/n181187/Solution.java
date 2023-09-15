package programmers.lv2.n181187;

class Solution {
    public long solution(int r1, int r2) {
        long tmp = 0;
        for (int i = 0; i < r2; i++) {
            double a = Math.floor(Math.sqrt((long) r2 * r2 - (long) i * i));
            double b = i < r1 ? Math.ceil(Math.sqrt((long) r1 * r1 - (long) i * i)) : 0;
            tmp += (a - b) + (i < r1 ? 1 : 0);
        }
        return tmp * 4;
    }
}
