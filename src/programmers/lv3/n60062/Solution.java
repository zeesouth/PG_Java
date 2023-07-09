package programmers.lv3.n60062;
class Solution {
    int[] weak, dist;
    int[][] weak_cases;
    int n, ans;
    public int solution(int n, int[] weak, int[] dist) {
        weak_cases = new int[weak.length][weak.length];
        this.weak = weak;
        this.dist = dist;
        this.ans = dist.length+1;
        this.n = n;

        makeWeakCases();
        makeDistCases(new boolean[dist.length], new int[dist.length], 0);
        if(ans > dist.length) return -1;
        return ans;
    }

    private void makeWeakCases() {
        for(int i=0;i<weak_cases.length;i++) {
            for(int j=0;j<weak_cases.length;j++) {
                if(i+j < weak.length)
                    weak_cases[i][j] = weak[(j+i)%weak.length];
                else
                    weak_cases[i][j] = weak[(j+i)%weak.length]+n;
            }
        }
    }

    private void makeDistCases(boolean[] dist_visit, int[] dist_case, int idx) {
        if(idx == dist.length) {
            for(int[] weak_case : weak_cases) check(dist_case, weak_case);
        }

        for(int i=0;i<dist.length;i++) {
            if(!dist_visit[i]) {
                dist_visit[i] = true;
                dist_case[idx] = dist[i];
                makeDistCases(dist_visit, dist_case, idx+1);
                dist_case[idx] = 0;
                dist_visit[i] = false;
            }
        }
    }

    private void check(int[] dist_case, int[] weak_case) {
        int cur = 0, next;
        int dist_idx = 0;
        while(cur < weak_case.length && dist_idx < dist_case.length) {
            next = cur+1;
            while(next < weak_case.length &&
                    weak_case[cur] + dist_case[dist_idx] >= weak_case[next]) {
                next++;
            }
            cur = next;
            dist_idx++;
        }

        if(cur == weak_case.length && dist_idx < ans) ans = dist_idx;
    }
}