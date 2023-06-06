package programmers.lv1.n86491;

class Solution {
    public int solution(int[][] sizes) {

        int wMax = Integer.MIN_VALUE;
        int hMax = Integer.MIN_VALUE;

        for(int i=0;i<sizes.length;i++) {

            int w = sizes[i][0], h = sizes[i][1];

            if(w < h) {
                int tmp = w;
                w = h;
                h = tmp;
            }

            wMax = Math.max(w, wMax);
            hMax = Math.max(h, hMax);

        }

        return wMax*hMax;
    }
}
