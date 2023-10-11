package codetree.samsung_re.n2022_2_1_2;
// https://www.codetree.ai/training-field/frequent-problems/problems/santa-gift-factory/description?page=1&pageSize=20

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_M = 10;
    static final int MAX_N = 100_000;
    static HashMap<Integer, Integer> pidMap = new HashMap<>();
    static int[] originId = new int[MAX_N + 1]; // 원래 ID
    static int[] beltId = new int[MAX_N + 1];   // 벨트 ID
    static int[] front = new int[MAX_N + 1];    // 앞
    static int[] back = new int[MAX_N + 1];     // 뒤
    static int[] weight = new int[MAX_N + 1];   // 무게
    static int[][] belt = new int[MAX_M + 1][2]; // front, back

    static int N, M, Q;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st = null;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        Q = Integer.parseInt(br.readLine());
        while (Q-- > 0) {
            simulate();
        }
        br.close();

        System.out.println(sb);
    }

    private static void simulate() throws Exception {
        st = new StringTokenizer(br.readLine());
        int order = Integer.parseInt(st.nextToken());

        if (order == 100) init();
        else if (order == 200) sb.append(removeFrontPresent()).append("\n");
        else if (order == 300) sb.append(removePresent()).append("\n");
        else if (order == 400) sb.append(checkPresent()).append("\n");
        else sb.append(brokeBelt()).append("\n");
    }

    // 500. 벨트 고장
    private static int brokeBelt() {
        int b_id = Integer.parseInt(st.nextToken());

        if (belt[b_id] == null) return -1;

        if (belt[b_id][0] == 0) {
            belt[b_id] = null;
            return b_id;
        }


        for (int id = 1; id < M; id++) {
            int i = (b_id - 1 + id) % M + 1;
            if (belt[i] == null) continue;

            int curr = belt[b_id][0];

            while (curr > 0) {
                beltId[curr] = i;
                curr = back[curr];
            }


            if (belt[i][0] == 0) {
                belt[i][0] = belt[b_id][0];
            } else {
                back[belt[i][1]] = belt[b_id][0];
                front[belt[b_id][0]] = belt[i][1];
            }
            belt[i][1] = belt[b_id][1];

            belt[b_id] = null;

            break;
        }

        return b_id;
    }

    // 400. 물건 확인
    private static int checkPresent() {
        int f_id = Integer.parseInt(st.nextToken());
        if (!pidMap.containsKey(f_id)) return -1;

        int p_id = pidMap.get(f_id);
        int b_id = beltId[p_id];

        int b_front = belt[b_id][0];
        int b_back = belt[b_id][1];

        if(b_front != p_id) {

            front[b_front] = b_back;
            back[b_back] = b_front;

            back[front[p_id]] = 0;
            belt[b_id][1] = front[p_id];
            front[p_id] = 0;
            belt[b_id][0] = p_id;
        }

        return b_id;
    }

    // 300. 물건 제거 (원하는 고유번호의 물건 제거)
    private static int removePresent() {
        int r_id = Integer.parseInt(st.nextToken());
        if (!pidMap.containsKey(r_id)) return -1;

        int p_id = pidMap.remove(r_id);
        int b_id = beltId[p_id];

        int front_id = front[p_id];
        int back_id = back[p_id];

        if (belt[b_id][0] == p_id && belt[b_id][1] == p_id) {
            belt[b_id][0] = belt[b_id][1] = 0;
        } else if (belt[b_id][0] != p_id && belt[b_id][1] == p_id) {
            belt[b_id][1] = front_id;
            back[front_id] = 0;
        } else if (belt[b_id][0] == p_id && belt[b_id][1] != p_id) {
            belt[b_id][0] = back_id;
            front[back_id] = 0;
        } else {
            back[front_id] = back_id;
            front[back_id] = front_id;
        }

        return r_id;
    }


    // 200. 물건 하차 (맨 앞 물건 제거)
    private static int removeFrontPresent() {
        int w_max = Integer.parseInt(st.nextToken());

        int w_sum = 0;

        for (int i = 1; i <= M; i++) {
            // 고장난 벨트라면 건너뛰기
            if (belt[i] == null) continue;

            int front_pid = belt[i][0];

            // 벨트가 비어있다면 건너뛰기
            if (front_pid == 0) continue;

            // 맨 앞 상자 무게가 w_max보다 같거나 작다면, 빼기
            if (weight[front_pid] <= w_max) {
                w_sum += weight[front_pid];

                pidMap.remove(originId[front_pid]);
                // 상자가 한개였다면
                if (back[front_pid] == 0) {
                    belt[i][0] = 0;
                    belt[i][1] = 0;
                } else {
                    int new_front_id = back[front_pid];
                    front[new_front_id] = 0;
                    belt[i][0] = new_front_id;
                }
            } else {
                // 상자가 한개가 아니라면 진행
                if(back[front_pid] != 0) {
                    int new_front_id = back[front_pid];
                    front[new_front_id] = 0;
                    belt[i][0] = new_front_id;

                    front[front_pid] = belt[i][1];
                    back[front_pid] = 0;
                    back[belt[i][1]] = front_pid;
                    belt[i][1] = front_pid;
                }
            }
        }

        return w_sum;
    }

    private static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int size = N / M;

        for (int i = 1; i <= N; i++) {
            int id = Integer.parseInt(st.nextToken());
            originId[i] = id;
            pidMap.put(id, i);

            int b_id = (i - 1) / size + 1;

            if (belt[b_id][0] == 0) {
                belt[b_id][0] = belt[b_id][1] = i;
            } else {
                back[belt[b_id][1]] = i;
                front[i] = belt[b_id][1];
                belt[b_id][1] = i;
            }
            beltId[i] = b_id;
        }

        for (int i = 1; i <= N; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

    }
}
