package programmers.lv3.n12979;
class Solution {
    public int solution(int n, int[] stations, int w) {
        int ans = 0;
        int start = 1;
        int end = -1;
        for(int i=0;i<=stations.length;i++) {
            if(i == stations.length) end = n+1;
            else end = stations[i]-w;
            int len = end-start;
            if(len > 0) ans += len/(w*2+1) + (len%(w*2+1) == 0 ? 0 : 1);

            if(i != stations.length) start = stations[i]+w+1;
        }
        return ans;
    }
}