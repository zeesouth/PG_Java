package swea.n2112;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static int T, D, W, K, film[][], record[][], ans;
    static boolean[] check;
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/n2112.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());
        for(int t=1;t<=T;t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            film = new int[D][W];
            record = new int[D][W];
            check = new boolean[W];
            for(int i=0;i<D;i++) {
                st = new StringTokenizer(br.readLine());
                for(int j=0;j<W;j++) film[i][j] = Integer.parseInt(st.nextToken());
            }
            record = new int[D][W];
            ans = D;
            if(K == 1) ans = 0;
            else dfs(0, 0);
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    static void dfs(int cnt, int depth) {

        if(depth == D) {
            for(int i=0;i<W;i++) {
                if(!check[i]) return;
            }
            ans = Math.min(cnt, ans);
            return;
        }

        // int[] temp = Arrays.copyOf(film[depth], W);
        boolean[] flag = Arrays.copyOf(check, W);
        // A 약품 투입
        for(int i=0;i<W;i++) {
            if(depth == 0) record[depth][i] = 1;
            else {
                if(record[depth-1][i] > 0) {
                    record[depth][i] = record[depth-1][i]+1;
                    if(record[depth][i] >= K) check[i] = true;
                }
                else {
                    record[depth][i] = 1;
                }
            }
        }
        dfs(cnt+1, depth+1);
        check = Arrays.copyOf(flag, W);

        flag = Arrays.copyOf(check, W);
        // B 약품 투입
        for(int i=0;i<W;i++) {
            if(depth == 0) record[depth][i] = -1;
            else {
                if(record[depth-1][i] < 0) {
                    record[depth][i] = record[depth-1][i]-1;
                    if(record[depth][i] <= -K) check[i] = true;
                }
                else {
                    record[depth][i] = -1;
                }
            }
        }
        dfs(cnt+1, depth+1);
        check = Arrays.copyOf(flag, W);

        flag = Arrays.copyOf(check, W);
        // 약품 투입 X
        for(int i=0;i<W;i++) {
            int curr = film[depth][i];
            if(depth == 0) record[depth][i] = curr == 0 ? 1 : -1;
            else {
                if(curr == 0) {
                    if(record[depth-1][i] > 0) {
                        record[depth][i] = record[depth-1][i]+1;
                        if(record[depth][i] >= K) check[i] = true;
                    }
                    else {
                        record[depth][i] = 1;
                    }
                } else {
                    if(record[depth-1][i] < 0) {
                        record[depth][i] = record[depth-1][i]-1;
                        if(record[depth][i] <= -K) check[i] = true;
                    }
                    else {
                        record[depth][i] = -1;
                    }
                }
            }
        }
        dfs(cnt, depth+1);
        check = Arrays.copyOf(flag, W);
    }
}
