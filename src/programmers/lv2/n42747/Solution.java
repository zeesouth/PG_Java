package programmers.lv2.n42747;

// 1 <= n <= 1000
// 0 <= h <= 10000

class Solution {
    public int solution(int[] citations) {
        int answer = 0;
        int indexMin = Integer.MAX_VALUE;
        int indexMax = Integer.MIN_VALUE;


        for(int i=0;i<citations.length;i++) {
            indexMin = Math.min(citations[i], indexMin);
            indexMax = Math.max(citations[i], indexMax);
        }

        for(int i=indexMax;i>=indexMin;i--) {
            int count = 0;
            for(int j=0;j<citations.length;j++){
                if(citations[j] >= i) count++;
            }

            if(count > i) {
                return i;
            }

            if(count < i && i == indexMin) {
                return count;
            }

            if(count == i) {
                answer = count;
                break;
            }
        }



        return answer;
    }
}