package swea.n16006;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static int T, N, ans;
    static int[] arr;
    public static void main (String[] args) throws Exception {
        System.setIn(new FileInputStream("res/n16006.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        T = Integer.parseInt(br.readLine());
        for(int t=1;t<=T;t++) {
            N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr = new int[N];
            for(int i=0;i<N;i++) arr[i] = Integer.parseInt(st.nextToken());
            ans = N/2;
            ans += N%2;
            sb.append("#").append(t).append(" ").append(ans).append("\n");
        }
        System.out.println(sb);
    }
}