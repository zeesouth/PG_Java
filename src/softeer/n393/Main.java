package softeer.n393;

import java.io.*;
import java.util.*;

public class Main {
    static int N, arr[], LIS[], LIS_L[], LIS_R[], LIS_R_L[];

    public static void main(String[] args) throws Exception {
        init();
        getArr();
        getAnswer();
    }

    private static void getAnswer() {
        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans = Math.max(LIS_L[i] + LIS_R_L[i] - 1, ans);
        }
        System.out.println(ans);
    }

    private static void getArr() {
        LIS = new int[N];
        LIS_L = new int[N];

        LIS[0] = arr[0];
        LIS_L[0] = 1;
        int cnt = 1;
        for (int i = 1; i < N; i++) {
            if (LIS[cnt - 1] < arr[i]) {
                LIS[cnt++] = arr[i];
            } else {
                int idx = getBound(LIS, arr[i], 0, cnt);
                LIS[idx] = arr[i];
            }
            LIS_L[i] = cnt;
        }

        LIS_R = new int[N];
        LIS_R_L = new int[N];

        LIS_R[0] = arr[N - 1];
        LIS_R_L[N - 1] = 1;
        cnt = 1;

        for (int i = N - 2; i >= 0; i--) {
            if (LIS_R[cnt - 1] < arr[i]) {
                LIS_R[cnt++] = arr[i];
            } else {
                int idx = getBound(LIS_R, arr[i], 0, cnt);
                LIS_R[idx] = arr[i];
            }
            LIS_R_L[i] = cnt;
        }
    }

    private static int getBound(int[] arr, int val, int s, int e) {
        while (s < e) {
            int mid = s + (e - s) / 2;
            if (val <= arr[mid]) e = mid;
            else s = mid + 1;
        }
        return s;
    }

    private static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(st.nextToken());
    }
}

