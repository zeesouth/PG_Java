package programmers.lv3.n42861.kruskal;
// MST + kruskal

import java.util.Arrays;
class Solution {
    int parents[];
    int answer = 0;
    public int solution(int n, int[][] costs) {
        parents = new int[n];
        for(int i=0;i<n;i++) parents[i] = i;

        Arrays.sort(costs, (o1, o2) -> o1[2]-o2[2]);

        int cnt = 0;
        for(int i=0;i<costs.length;i++) {
            if(union(costs[i][0], costs[i][1])) {
                answer += costs[i][2];
                cnt++;
            }
            if(cnt == n-1) break;
        }
        return answer;
    }

    int find(int n) {
        if(parents[n] == n) return n;
        return parents[n] = find(parents[n]);
    }

    boolean union(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);

        if(parentA != parentB) {
            if(parentA < parentB) parents[parentB] = parentA;
            else parents[parentA] = parentB;

            return true;
        }
        return false;
    }

}

