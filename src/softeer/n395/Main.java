package softeer.n395;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[][] arr = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());
            arr[i][0] = M;
            arr[i][1] = P;
        }

        Arrays.sort(arr, (o1, o2) -> o2[1] - o1[1]);

        int ans = 0;
        int idx = 0;
        while (W != 0) {
            if (W > arr[idx][0]) {
                ans += arr[idx][0] * arr[idx][1];
                W -= arr[idx][0];
                idx++;
            } else {
                ans += W * arr[idx][1];
                W = 0;
            }
        }
        System.out.println(ans);
        br.close();
    }
}
