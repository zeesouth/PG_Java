package codetree.samsung.n2023_2_1_2;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static final int MAX_N = 100_001, MAX_D = 22;
    // 뎁스별로 부모를 나타냄
    static int author[] = new int[MAX_N];    // 권한 세기
    static int parent[] = new int[MAX_N];       // 부모
    static int value[] = new int[MAX_N];        // ?
    static boolean mute[] = new boolean[MAX_N];   // 알림 ON/OFF 여부
    static int[][] na = new int[MAX_N][MAX_D];  // i번노드가 j만큼 위에 전달할 수 있는 채팅창 개수
    static int N, Q;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int node, power, node1, node2;

            switch (order) {
                case 100:
                    init();
                    break;
                case 200:
                    node = Integer.parseInt(st.nextToken());
                    toggleNotice(node);
                    break;
                case 300:
                    node = Integer.parseInt(st.nextToken());
                    power = Integer.parseInt(st.nextToken());
                    changePower(node, power);
                    break;
                case 400:
                    node1 = Integer.parseInt(st.nextToken());
                    node2 = Integer.parseInt(st.nextToken());
                    changeParent(node1, node2);
                    break;
                case 500:
                    node = Integer.parseInt(st.nextToken());
                    printCount(node);
            }
        }

        System.out.println(sb);
    }

    // 100: 사내 메신저 준비
    private static void init() throws Exception {

        // 각 노드의 부모 채팅방 번호
        for (int i = 1; i <= N; i++) {
            parent[i] = Integer.parseInt(st.nextToken());
        }

        // 각 노드의 권한
        for (int i = 1; i <= N; i++) {
            author[i] = Math.min(Integer.parseInt(st.nextToken()), 20);

        }

        for (int i = 1; i <= N; i++) {
            int curr = i;
            int power = author[i];
            na[curr][power]++;

            while (parent[curr] != 0 && power != 0) {
                curr = parent[curr];
                power--;
                if (power != 0) na[curr][power]++;
                value[curr]++;
            }
        }

    }

    // 200: 알림망 설정 ON/OFF
    private static void toggleNotice(int node) {
        int curr = parent[node];
        int power = 1;

        // 알림 OFF -> ON
        if (mute[node]) {
            // 상위 채팅으로 이동하여 알림여부(notice 값)에 따라 na, value 값 갱신하기
            while (curr != 0) {
                for (int i = power; i < MAX_D; i++) {
                    value[curr] += na[node][i];
                    if (i > power) na[curr][i - power] += na[node][i];
                }

                if (mute[curr]) break;
                curr = parent[curr];
                power++;
            }
        }
        // 알림 ON -> OFF
        else {
            // 상위 채팅으로 이동하여 알림여부(notice 값)에 따라 na, value 값 갱신하기
            while (curr != 0) {
                for (int i = power; i < MAX_D; i++) {
                    value[curr] -= na[node][i];
                    if (i > power) na[curr][i - power] -= na[node][i];
                }

                if (mute[curr]) break;
                curr = parent[curr];
                power++;
            }
        }

        mute[node] = !mute[node];
    }

    // 300: 권한 세기 변경
    private static void changePower(int node, int power) {
        int before_power = author[node];
        power = Math.min(power, 20);
        author[node] = power;

        na[node][before_power]--;
        if (!mute[node]) {
            int currN = parent[node];
            int currP = 1;

            while (currN != 0) {
                if (before_power >= currP) value[currN]--;
                if (before_power > currP) na[currN][before_power - currP]--;
                if (mute[currN]) break;
                currN = parent[currN];
                currP++;
            }
        }

        na[node][power]++;
        if (!mute[node]) {
            int currN = parent[node];
            int currP = 1;

            while (currN != 0) {
                if (power >= currP) value[currN]++;
                if (power > currP) na[currN][power - currP]++;
                if (mute[currN]) break;
                currN = parent[currN];
                currP++;
            }
        }
    }

    // 400: 부모 채팅방 교환
    private static void changeParent(int node1, int node2) {
        boolean before_mute1 = mute[node1];
        boolean before_mute2 = mute[node2];

        if (!mute[node1]) toggleNotice(node1);
        if (!mute[node2]) toggleNotice(node2);

        int temp = parent[node1];
        parent[node1] = parent[node2];
        parent[node2] = temp;

        if (!before_mute1) toggleNotice(node1);
        if (!before_mute2) toggleNotice(node2);
    }


    // 500: 알림을 받을 수 있는 채팅방 수 조회
    private static void printCount(int node) {
        sb.append(value[node]).append("\n");
    }
}
