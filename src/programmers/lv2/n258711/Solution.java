package programmers.lv2.n258711;
// 참고: https://velog.io/@carrotcookie/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%EB%8F%84%EB%84%9B%EA%B3%BC-%EB%A7%89%EB%8C%80-%EA%B7%B8%EB%9E%98%ED%94%84

class Solution {
    public int[] solution(int[][] edges) {
        int[] answer = new int[4];

        int N = edges.length + 1;
        int cnt[][] = new int[N + 1][2];

        for (int[] e : edges) {
            int a = e[0];
            int b = e[1];

            // 준 것, 받은 것 카운팅
            // a, b는 a가 b에 준 것 = b가 a에게 받은 것
            cnt[a][0]++;
            cnt[b][1]++;
        }

        for (int i = 1; i <= N; i++) {
            // 그래프는 최소 2개 이상으로 나가는 정점이 2개 이상인 것이 생성점
            if (cnt[i][0] >= 2 && cnt[i][1] == 0) answer[0] = i;
            // 받은 것만 있는 정점의 개수는 막대 그래프 개수
            else if (cnt[i][0] == 0 && cnt[i][1] > 0) answer[2] += 1;
            // 준 것, 받은 것 각각 2개 이상인 점의 개수는 8자 그래프
            else if (cnt[i][0] >= 2 && cnt[i][1] >= 2) answer[3] += 1;
        }

        // 전체 그래프 개수인 생성점의 준 것에서 2종류의 그래프 개수를 빼면 도넛 그래프 개수
        answer[1] = cnt[answer[0]][0] - answer[2] - answer[3];

        return answer;
    }
}

/*
1. 생성점 -> 들어오는 간선 수 = 0 / 나가는 간선 수 >= 2
2. 막대그래프 개수 -> 나가는 간선 없이 들어오는 간선 수가 1개인 정점의 수
3. 8자그래프 개수 -> 들어오는 간선, 나가는 간선 수가 각각 2개 이상인 정점의 수
4. 도넛 그래프 -> 나머지 그래프
*/