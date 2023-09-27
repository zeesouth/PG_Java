package programmers.lv3.n214288;

import java.util.*;

class Solution {

    int[][] time;
    int max;
    int answer = Integer.MAX_VALUE;
    int K, N;

    public int solution(int k, int n, int[][] reqs) {

        max = n - k + 1;
        time = new int[k][max + 1];
        K = k;
        N = n;

        ArrayList<int[]> arr[] = new ArrayList[k];

        for (int i = 0; i < k; i++) {
            arr[i] = new ArrayList<>();
        }

        for (int i = 0; i < reqs.length; i++) {
            int start = reqs[i][0];
            int end = start + reqs[i][1];
            int type = reqs[i][2] - 1;
            arr[type].add(new int[]{start, end});
        }

        for (int i = 0; i < k; i++) {
            Collections.sort(arr[i], (
                    (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]
            ));
        }

        for (int i = 0; i < k; i++) {
            for (int j = 1; j <= max; j++) {
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                int num = 0;
                for (int r = 0; r < arr[i].size(); r++) {
                    int curr[] = arr[i].get(r);
                    if (pq.size() == j) {
                        int e = pq.poll();
                        if (e > curr[0]) {
                            num += e - curr[0];
                            pq.add(e + curr[1] - curr[0]);
                        } else pq.add(curr[1]);
                    } else pq.add(curr[1]);
                }
                time[i][j] = num;
            }
        }

        dfs(0, 0, 0);

        return answer;
    }

    void dfs(int type, int cnt, int sum) {
        if (type == K) {
            if (cnt != N) return;
            answer = Math.min(answer, sum);
            return;
        }

        for (int i = 1; i <= max; i++) {
            dfs(type + 1, cnt + i, sum + time[type][i]);
        }
    }
}
