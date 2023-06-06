package programmers.lv2.n1844;

import java.util.LinkedList;
import java.util.Queue;

class Solution {

    static int answer;
    static int xLen, yLen;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static boolean[][] visited;

    public int solution(int[][] maps) {
        xLen = maps.length;
        yLen = maps[0].length;
        visited = new boolean[xLen][yLen];

        answer = bfs(maps);

        return answer;
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && x < xLen && y >= 0 && y < yLen;
    }

    public int bfs(int[][] maps) {

        Queue<int[]> q = new LinkedList<>();
        // start
        q.add(new int[]{0, 0, 1});
        visited[0][0] = true;
        int count = Integer.MAX_VALUE;
        boolean flag = false;

        while(!q.isEmpty()) {
            int[] curr = q.poll();

            if(curr[0] == xLen-1 && curr[1] == yLen-1) {
                count = Math.min(count, curr[2]);
                flag = true;
            }

            for(int i=0;i<4;i++) {
                int currX = curr[0]+dx[i];
                int currY = curr[1]+dy[i];

                if(isValid(currX, currY) && maps[currX][currY] != 0) {
                    if(!visited[currX][currY]) {
                        visited[currX][currY] = true;
                        q.add(new int[]{currX, currY, curr[2]+1});
                    }
                }
            }
        }

        return flag ? count : -1;

    }

}

