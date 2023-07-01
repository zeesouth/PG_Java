package swea.n5648;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    static int T, N;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static int map[][] = new int[4001][4001];
    static List<Atom> atom;
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/n5648.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());
        atom = new ArrayList<>();
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            for(int i=0;i<N;i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = (Integer.parseInt(st.nextToken())+1000)*2;
                int y = (Integer.parseInt(st.nextToken())+1000)*2;
                int d = Integer.parseInt(st.nextToken());
                int K = Integer.parseInt(st.nextToken());
                atom.add(new Atom(x, y, d, K));
                map[x][y] = K;
            }
            sb.append("#").append(t).append(" ").append(simulate()).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    static int simulate() {
        int sum = 0;
        while(!atom.isEmpty()) {
            for(int i=0;i<atom.size();i++) {
                Atom a = atom.get(i);
                map[a.x][a.y] = 0;
                a.x += dx[a.d];
                a.y += dy[a.d];
                if(a.x > 4000 || a.x < 0 || a.y > 4000 || a.y < 0) {
                    atom.remove(i);
                    i--;
                    continue;
                }
                map[a.x][a.y] += a.e;
            }
            for(int i=0;i<atom.size();i++) {
                Atom a = atom.get(i);
                if(map[a.x][a.y] != a.e) {
                    sum += map[a.x][a.y];
                    map[a.x][a.y] = 0;
                    atom.remove(i);
                    i--;
                }
            }
        }
        return sum;
    }

    static class Atom {
        int x, y, d, e;
        Atom(int x, int y, int d, int e) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.e = e;
        }
    }
}

/*
* 문제의 접근법
* 1. 원자들의 위치를 map에 할당한다.
* 2. 원자들이 이동하면 이동된 map에 에너지값을 더해준다.
* 3. 모든 원자들을 이동한 후 map의 에너지값이 해당 원자의 에너직값보다 큰 경우, 충돌한 것이므로 sum에 더해주고 해당 위치인 map에 0으로 초기화해줌. 그 다음 원자 삭제
* 4. 원자의 위치인 map의 에너지값이 0일 경우 충돌하였으므로 해당 원자를 삭제
* */