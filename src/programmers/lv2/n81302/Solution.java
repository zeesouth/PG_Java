package programmers.lv2.n81302;

import java.util.*;

class Solution {
    final int size = 5;
    final int dy[] = {1, 0, -1, 0};
    final int dx[] = {0, 1, 0, -1};
    boolean visited[][];

    public int[] solution(String[][] places) {
        int answer[] = new int[size];
        for (int i = 0; i < size; i++) {
            if (play(places[i])) answer[i] = 1;
        }
        return answer;
    }

    boolean play(String[] place) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (place[i].charAt(j) != 'P') continue;
                visited = new boolean[size][size];
                if (!dfs(i, j, 0, false, place)) return false;
            }
        }
        return true;
    }

    // y,x: 현재 위치, dist: 시작지점으로부터 거리, part: 파티션 여부
    boolean dfs(int y, int x, int dist, boolean part, String[] place) {
        visited[y][x] = true;
        if (dist == 1 && place[y].charAt(x) == 'P') return false;

        if (dist == 2) {
            if (place[y].charAt(x) == 'P' && !part) return false;
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nextY = y + dy[i];
            int nextX = x + dx[i];
            if (!isRange(nextY, nextX)) continue;
            if (visited[nextY][nextX]) continue;
            boolean flag = false;
            if (place[nextY].charAt(nextX) == 'X')
                flag = dfs(nextY, nextX, dist + 1, true, place);
            else
                flag = dfs(nextY, nextX, dist + 1, part, place);

            visited[nextY][nextX] = false;
            if (!flag) return false;
        }

        return true;
    }

    boolean isRange(int y, int x) {
        return y >= 0 && y < size && x >= 0 && x < size;
    }
}

/*
P: 응시자가 앉아있는 자리
O: 빈테이블
X: 파티션
*/