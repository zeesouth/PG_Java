package codetree.samsung.n2023_1_2_2;
// 2023 상반기 오후 2번

import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_N = 50000;
    static final int MAX_D = 300;
    static StringTokenizer st = null;
    static int N;

    // 도메인에서 해당 문제 ID가 wait큐에 있는지 관라하기
    static Set<Integer> wait[] = new HashSet[MAX_D + 1];

    // 쉬고 있는 채점기 관리
    static PriorityQueue<Integer> rJudger = new PriorityQueue<>();

    // 각 채점기들이 채점할 때, 도메인의 인덱스 저장
    static int judgeDomain[] = new int[MAX_N + 1];

    // 각 도메인 별 start, gap, end(채점 가능한 최소 시간)을 관리
    static int start[] = new int[MAX_D + 1];
    static int gap[] = new int[MAX_D + 1];
    static int end[] = new int[MAX_D + 1];

    // 도메인을 관리하기 위해 cnt 이용
    // 도메인 문자열을 int로 변환해주는 map 관리
    static Map<String, Integer> domainToIdx = new HashMap<>();
    static int cnt = 0;

    // 현재 채점 대기 큐에 있는 task의 개수를 관리합니다.
    static int ans = 0;

    static class URL implements Comparable<URL> {
        // 시간, 우선순위, 문제 번호
        int time, priority, id;

        URL(int time, int priority, int id) {
            this.time = time;
            this.priority = priority;
            this.id = id;
        }

        @Override
        public int compareTo(URL u) {
            if (this.priority != u.priority) return this.priority - u.priority;
            return this.time - u.time;
        }
    }

    // 각 도메인별로 pq를 만들어 우선순위가 가장 높은 URL 뽑기
    static PriorityQueue<URL> url_pq[] = new PriorityQueue[MAX_D + 1];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            switch (order) {
                case 100:
                    init();
                    break;
                case 200:
                    request();
                    break;
                case 300:
                    attempt();
                    break;
                case 400:
                    quit();
                    break;
                case 500:
                    sb.append(select()).append("\n");
            }
        }
        System.out.println(sb);
        br.close();
    }

    // 1. 코드트리 채점기 준비
    static void init() {
        N = Integer.parseInt(st.nextToken());
        for (int i = 1; i <= N; i++) rJudger.add(i);

        String[] url = st.nextToken().split("/"); // domain/id
        String domain = url[0];
        int id = Integer.parseInt(url[1]);

        // 도메인이 처음 나온 도메인이라면 domainToIdx에 저장
        if (!domainToIdx.containsKey(domain)) {
            cnt++;
            domainToIdx.put(domain, cnt);
        }

        int domainID = domainToIdx.get(domain);
        // 만약 domain이 이미 wait큐에 있으면 중복되므로 return;
        if (wait[domainID] == null) {
            wait[domainID] = new HashSet<>();
            url_pq[domainID] = new PriorityQueue<>();
        }

        wait[domainID].add(id);

        URL newURL = new URL(0, 1, id);
        url_pq[domainID].add(newURL);

        // 채점 대기 큐에 값이 추가됐으므로 개수 1 추가
        ans++;
    }

    // 2. 채점 요청
    static void request() {
        // t초에 채점우선순위가 p인 url이 u인 문제에 대한 채점 요청이 들어옴
        int t = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        String[] url = st.nextToken().split("/");
        String domain = url[0];
        int id = Integer.parseInt(url[1]);

        // 도메인이 처음 나온 도메인이라면 domainToIdx에 저장
        if (!domainToIdx.containsKey(domain)) {
            cnt++;
            domainToIdx.put(domain, cnt);
        }

        int domainID = domainToIdx.get(domain);

        if (wait[domainID] == null) {
            wait[domainID] = new HashSet<>();
            url_pq[domainID] = new PriorityQueue<>();
        }

        // 만약 domain이 이미 wait큐에 있으면 중복되므로 return;
        if (wait[domainID].contains(id)) return;

        wait[domainID].add(id);

        URL newURL = new URL(t, p, id);
        url_pq[domainID].add(newURL);

        // 채점 대기 큐에 값이 추가됐으므로 개수 1 추가
        ans++;
    }

    // 3. 채점 시도
    static void attempt() {
        // t초에 채점 대기 큐에서 즉시 채점이 가능한 경우 우선순위가 가장 높은 채점 task를 골라 채점 진행
        int t = Integer.parseInt(st.nextToken());

        // 모든 채점기가 사용중이면 함수 종료
        if (rJudger.size() == 0) return;

        // 가장 우선순위가 높은 URL 찾기
        int min_domain = -1;
        URL minURL = new URL(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (int i = 1; i <= cnt; i++) {
            // 만약 현재 채점중이거나, 현재 시간에 이용할 수 없으면 넘어가기
            if (end[i] > t) continue;

            // 만약 i번 도메인에 해당하는 URL이 존재한다면
            // 해당 도메인에서 가장 우선순위가 높은 URL을 뽑고 갱신
            if (!url_pq[i].isEmpty()) {
                URL currURL = url_pq[i].peek();

                if (minURL.compareTo(currURL) > 0) {
                    minURL = currURL;
                    min_domain = i;
                }
            }
        }

        // 만약 가장 우선순위가 높은 URL이 존재하면
        // 해당 도메인과 쉬고 있는 가장 낮은 번호의 채점기를 연결해주기
        if (min_domain != -1) {
            int judger_idx = rJudger.poll();

            // 해당 도메인의 가장 우선순위가 높은 URL 지우기
            url_pq[min_domain].poll();

            // 도메인의, start, end 갱신하기
            start[min_domain] = t;
            end[min_domain] = Integer.MAX_VALUE;

            // judger_idx번 채점기가 채점하고 있는 번호 갱신하기
            judgeDomain[judger_idx] = min_domain;

            // wait큐에서 해당 URL의 id를 지워주기
            wait[min_domain].remove(minURL.id);

            // 채점 대기 큐에서 값이 지워졌으므로 -1
            ans--;
        }
    }

    // 4. 채점 종료
    static void quit() {
        // t초에 j_id번 채점기가 진행하던 채점이 종료됨
        int t = Integer.parseInt(st.nextToken());
        int j_id = Integer.parseInt(st.nextToken());

        // 만약 id번 채점기가 채점 중이 아닐 경우 skip
        if (judgeDomain[j_id] == 0) return;

        // id번 채점기를 마무리하기
        rJudger.add(j_id);
        int domain_id = judgeDomain[j_id];
        judgeDomain[j_id] = 0;

        // 해당 도메인의 gap, end 갱신하기
        gap[domain_id] = t - start[domain_id];
        end[domain_id] = start[domain_id] + 3 * gap[domain_id];
    }

    // 5. 채점 대기 큐 조회
    static int select() {
        // t초에 채점 대기 큐에 있는 채점 task의 수 출력
        int t = Integer.parseInt(st.nextToken());
        return ans;
    }
}
