package codetree.samsung.n2023_2_1_2;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();
    static final int MAX_N = 100_000;
    static int N, Q;

    static int parent[][] = new int[MAX_N + 1][2];      //pNum, left/right
    static int power[] = new int[MAX_N + 1];
    static int child[][] = new int[MAX_N + 1][2];     // left, right
    static boolean mute[] = new boolean[MAX_N + 1];

    public static void main(String[] args) throws Exception {
        start();
        while (Q-- > 0) {
            simulate();
        }
        System.out.println(sb);
    }

    private static void simulate() throws Exception {
        st = new StringTokenizer(br.readLine());
        int order = Integer.parseInt(st.nextToken());

        if (order == 100) init();
        else if (order == 200) setAlarm();
        else if (order == 300) updatePower();
        else if (order == 400) changeParent();
        else sb.append(searchAvailCnt()).append("\n");

    }

    private static int searchAvailCnt() {
        int c = Integer.parseInt(st.nextToken());


        int cnt = 0;

        Stack<int[]> stack = new Stack<>();
        stack.add(new int[]{c, 0}); // id, depth

        while (!stack.isEmpty()) {
            int[] curr = stack.pop();

            int id = curr[0];
            int depth = curr[1];

            for (int i = 0; i <= 1; i++) {
                int next = child[id][i];
                if (next == 0) continue;
                if (mute[next]) continue;

                int nextP = power[next];
                if (nextP > depth) cnt++;

                stack.add(new int[]{next, depth + 1});
            }

        }

        return cnt;
    }

    private static void changeParent() {
        int c1 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        if(parent[c1][0] == parent[c2][0]) return;

        // 부모 바꾸기
        int tmp[] = {parent[c1][0], parent[c1][1]};

        parent[c1][0] = parent[c2][0];
        parent[c1][1] = parent[c2][1];
        child[parent[c2][0]][parent[c2][1]] = c1;

        parent[c2][0] = tmp[0];
        parent[c2][1] = tmp[1];
        child[tmp[0]][tmp[1]] = c2;
    }

    private static void updatePower() {
        int c = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        power[c] = p;
    }

    private static void setAlarm() {
        int c = Integer.parseInt(st.nextToken());
        mute[c] = !mute[c];
    }

    private static void init() {
        for (int i = 1; i <= N; i++) {
            int p = Integer.parseInt(st.nextToken());
            parent[i][0] = p;
            if (child[p][0] == 0) {
                child[p][0] = i;
                parent[i][1] = 0;
            } else {
                child[p][1] = i;
                parent[i][1] = 1;
            }
        }

        for (int i = 1; i <= N; i++) {
            power[i] = Integer.parseInt(st.nextToken());
        }
    }

    private static void start() throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
    }

}
