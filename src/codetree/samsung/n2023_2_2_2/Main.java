package codetree.samsung.n2023_2_2_2;

import java.io.*;
import java.util.*;

// https://www.codetree.ai/training-field/frequent-problems/problems/codetree-omakase/description?page=1&pageSize=20
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int L, Q;

    static class Query {
        int cmd, t, x, n;
        String name;

        Query(int cmd, int t, int x, String name, int n) {
            this.cmd = cmd;
            this.t = t;
            this.x = x;
            this.name = name;
            this.n = n;
        }
    }

    // 명령 관리
    static List<Query> queries = new ArrayList<>();

    // 방문자 관리
    static Set<String> names = new HashSet<>();

    // 방문자의 초밥 명령 관리
    static Map<String, List<Query>> make_susi_queries = new HashMap<>();

    // 방문자의 입장 시간 관리
    static Map<String, Integer> visit_time = new HashMap<>();

    // 방문자 위치 관리
    static Map<String, Integer> pos = new HashMap<>();

    // 방문자 퇴장 시간 관리
    static Map<String, Integer> exit_time = new HashMap<>();

    public static void main(String[] args) throws Exception {
        init();
        while (Q-- > 0) {
            inputQuery();
        }
        simulate();
        System.out.println(sb);
    }

    private static void simulate() {
        // 각 사람마다 자신의 이름이 적힌 조합을 언제 먹게 되는지를 계산하여 해당 정보를 기존 Query에 추가합니다. (111번 쿼리)
        for (String name : names) {
            // 해당 사람의 퇴장 시간 관리
            // 이는 마지막으로 먹는 초밥 시간 중 가장 늦은 시간
            exit_time.put(name, 0);

            for (Query q : make_susi_queries.get(name)) {
                int remove_time = 0;
                // 만약 초밥이 사람이 등장하기 전에 미리 주어졌다면?
                if (q.t < visit_time.get(name)) {
                    // 방문 시각일 때 초밥의 위치 구하기
                    int t_sushi_x = (q.x + visit_time.get(name) - q.t) % L;

                    // 몇 초가 더 지나야 만나는지 계산
                    int add_time = (pos.get(name) - t_sushi_x + L) % L;

                    remove_time = visit_time.get(name) + add_time;
                }
                // 초밥이 사람이 등장한 이후에 주어진다면
                else {
                    int add_time = (pos.get(name) - q.x + L) % L;
                    remove_time = q.t + add_time;
                }

                // 초밥이 사라지는 시간 중 가장 늦은 시간을 업데이트
                exit_time.put(name, Math.max(exit_time.get(name), remove_time));

                // 초밥이 사라지는 111번 쿼리 추가
                queries.add(new Query(111, remove_time, -1, name, -1));
            }
        }

        // 사람마다 초밥을 마지막으로 먹은 t를 계산하여 그 사람이 해당 t때 오마카세를 떠났다는 쿼리 추가
        for (String name : names)
            queries.add(new Query(222, exit_time.get(name), -1, name, -1));

        // 전체 Query를 시간순으로 정렬하되 t가 일치한다면 문제 조건상 사진 촬영에 해당하는 300이 가장 늦게 나오도록 cmd 순으로 오름차순 정렬을 합니다.
        // 이후 순서대로 보면서 사람, 초밥 수를 count하다가 300이 나오면 현재 사람, 초밥 수를 출력합니다.
        queries.sort((q1, q2) -> cmp(q1, q2) ? -1 : 1);

        int p_n = 0, s_n = 0;
        for (Query q : queries) {
            if (q.cmd == 100) s_n++;
            else if (q.cmd == 111) s_n--;
            else if (q.cmd == 200) p_n++;
            else if (q.cmd == 222) p_n--;
            else sb.append(p_n).append(" ").append(s_n).append("\n");
        }


    }

    private static boolean cmp(Query q1, Query q2) {
        if (q1.t != q2.t) return q1.t < q2.t;
        return q1.cmd < q2.cmd;
    }

    private static void inputQuery() throws Exception {
        st = new StringTokenizer(br.readLine());
        int cmd = Integer.parseInt(st.nextToken());
        int t = -1, x = -1, n = -1;
        String name = null;
        if (cmd == 100) {
            t = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            name = st.nextToken();
        } else if (cmd == 200) {
            t = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            name = st.nextToken();
            n = Integer.parseInt(st.nextToken());
        } else if (cmd == 300) {
            t = Integer.parseInt(st.nextToken());
        }

        Query q = new Query(cmd, t, x, name, n);

        queries.add(q);

        if (cmd == 100) {
            make_susi_queries.computeIfAbsent(name, k -> new ArrayList<>()).add(q);
        } else if (cmd == 200) {
            names.add(name);
            visit_time.put(name, t);
            pos.put(name, x);
        }
    }

    private static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
    }
}
