package programmers.lv2.n86052;

import java.util.*;

class Solution {
    String[] map;
    int R, C;
    boolean visited[][][];
    int[] dy = {1, 0, -1, 0};
    int[] dx = {0, 1, 0, -1};

    ArrayList<Integer> ans = new ArrayList<>();

    public int[] solution(String[] grid) {
        map = grid;
        R = grid.length;
        C = grid[0].length();
        visited = new boolean[R][C][4];

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                for (int d = 0; d < 4; d++) {
                    if (visited[i][j][d]) continue;
                    bfs(i, j, d);
                }
            }
        }

        int[] answer = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            answer[i] = ans.get(i);
        }
        Arrays.sort(answer);

        return answer;
    }

    void bfs(int y, int x, int d) {

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{y, x, d, 0});

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int cy = curr[0];
            int cx = curr[1];
            int cd = curr[2];
            int cn = curr[3];

            if (visited[cy][cx][cd]) {
                ans.add(cn);
                break;
            }

            visited[cy][cx][cd] = true;

            int ny = (cy + dy[cd] + R) % R;
            int nx = (cx + dx[cd] + C) % C;
            int nd = cd;

            if (map[ny].charAt(nx) == 'L') {
                nd = (nd + 1 + 4) % 4;
            } else if (map[ny].charAt(nx) == 'R') {
                nd = (nd - 1 + 4) % 4;
            }

            q.add(new int[]{ny, nx, nd, cn + 1});
        }
    }
}
