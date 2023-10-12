package codetree.samsung_re.n2022_2_2_2;

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_N = 100_000, MAX_M = 100_000;
    static int[] front = new int[MAX_M + 1];
    static int[] back = new int[MAX_M + 1];
    static int[][] belt = new int[MAX_N + 1][3];
    static int N, M;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int Q = Integer.parseInt(br.readLine());
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            simulate(order);
        }
        System.out.println(sb);
    }


    private static void simulate(int order) {
        if (order == 100) init();
        else if (order == 200) sb.append(move()).append("\n");
        else if (order == 300) sb.append(change()).append("\n");
        else if (order == 400) sb.append(divide()).append("\n");
        else if (order == 500) sb.append(getPresentInfo()).append("\n");
        else sb.append(getBeltInfo()).append("\n");
    }

    private static int getBeltInfo() {
        int b_num = Integer.parseInt(st.nextToken());

        int a = belt[b_num][0] == 0 ? -1 : belt[b_num][0];
        int b = belt[b_num][1] == 0 ? -1 : belt[b_num][1];
        int c = belt[b_num][2];

        return a + 2 * b + 3 * c;
    }

    private static int getPresentInfo() {
        int p_num = Integer.parseInt(st.nextToken());

        int a = front[p_num] == 0 ? -1 : front[p_num];
        int b = back[p_num] == 0 ? -1 : back[p_num];


        return a + 2 * b;
    }

    private static int divide() {
        int m_src = Integer.parseInt(st.nextToken());
        int m_dst = Integer.parseInt(st.nextToken());

        if (belt[m_src][2] <= 1) return belt[m_dst][2];

        int end = belt[m_src][2] / 2;
        int cnt = 1;

        int curr = belt[m_src][0];

        while (cnt < end) {
            curr = back[curr];
            cnt++;
        }


        if (belt[m_dst][0] == 0) {
            belt[m_dst][0] = belt[m_src][0];
            belt[m_dst][1] = curr;
            belt[m_src][0] = back[curr];
            front[back[curr]] = 0;
            back[curr] = 0;
        } else {
            int back_curr = back[curr];

            front[back_curr] = 0;
            back[curr] = belt[m_dst][0];
            front[belt[m_dst][0]] = curr;
            belt[m_dst][0] = belt[m_src][0];
            belt[m_src][0] = back_curr;

        }

        belt[m_dst][2] += end;
        belt[m_src][2] -= end;

        return belt[m_dst][2];
    }

    private static int change() {
        int m_src = Integer.parseInt(st.nextToken());
        int m_dst = Integer.parseInt(st.nextToken());

        int s_f = belt[m_src][0];
        int d_f = belt[m_dst][0];

        if (s_f != 0 && d_f != 0) {
            int s_f_b = back[s_f];
            int d_f_b = back[d_f];

            if(s_f_b != 0) {
                back[d_f] = s_f_b;
                front[s_f_b] = d_f;
            }
            else {
                back[d_f] = 0;
                belt[m_src][1] = d_f;
            }

            if(d_f_b != 0) {
                back[s_f] = d_f_b;
                front[d_f_b] = s_f;
            }
            else {
                back[s_f] = 0;
                belt[m_dst][1] = s_f;
            }

            belt[m_src][0] = d_f;
            belt[m_dst][0] = s_f;

        } else if (s_f == 0 && d_f != 0) {
            belt[m_src][0] = d_f;
            belt[m_src][1] = d_f;
            belt[m_src][2]++;

            belt[m_dst][0] = back[d_f];
            if(back[d_f] == 0) belt[m_dst][1] = 0;
            belt[m_dst][2]--;

            front[back[d_f]] = 0;
            back[d_f] = 0;


        } else if (s_f != 0 && d_f == 0) {
            belt[m_dst][0] = s_f;
            belt[m_dst][1] = s_f;
            belt[m_dst][2]++;

            belt[m_src][0] = back[s_f];
            if(back[s_f] == 0) belt[m_src][1] = 0;
            belt[m_src][2]--;

            front[back[s_f]] = 0;
            back[s_f] = 0;
        }

        return belt[m_dst][2];
    }

    // O
    private static int move() {
        int m_src = Integer.parseInt(st.nextToken());
        int m_dst = Integer.parseInt(st.nextToken());

        if (belt[m_src][0] == 0) return belt[m_dst][2];


        if (belt[m_dst][0] != 0) {
            front[belt[m_dst][0]] = belt[m_src][1];
            back[belt[m_src][1]] = belt[m_dst][0];
        } else {
            belt[m_dst][1] = belt[m_src][1];
        }
        belt[m_dst][0] = belt[m_src][0];

        belt[m_dst][2] += belt[m_src][2];
        belt[m_src][0] = belt[m_src][1] = belt[m_src][2] = 0;

        return belt[m_dst][2];
    }

    private static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= M; i++) {
            int b_id = Integer.parseInt(st.nextToken());
            if (belt[b_id][0] == 0) {
                belt[b_id][0] = belt[b_id][1] = i;
            } else {
                back[belt[b_id][1]] = i;
                front[i] = belt[b_id][1];
                belt[b_id][1] = i;
            }
            belt[b_id][2]++;
        }
    }
}
