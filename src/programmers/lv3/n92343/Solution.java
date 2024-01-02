package programmers.lv3.n92343;

import java.util.*;

class Solution {
    int answer = 0;
    ArrayList<Integer> tree[];

    public int solution(int[] info, int[][] edges) {
        tree = new ArrayList[info.length];
        for (int i = 0; i < info.length; i++) tree[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; i++) {
            int x = edges[i][0];
            int y = edges[i][1];
            tree[x].add(y);
        }

        List<Integer> next = new LinkedList<>();
        next.add(0);

        dfs(info, next, 0, 0, 0);

        return answer;
    }


    void dfs(int[] info, List<Integer> list, int node, int sheep, int wolf) {
        if (info[node] == 0) sheep++;
        else wolf++;

        if (sheep <= wolf) return;

        answer = Math.max(answer, sheep);

        List<Integer> next = new LinkedList<>(list);
        next.remove(Integer.valueOf(node));
        next.addAll(tree[node]);

        for (int n : next) {
            dfs(info, next, n, sheep, wolf);
        }
    }
}
