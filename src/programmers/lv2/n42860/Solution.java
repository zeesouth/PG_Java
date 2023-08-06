package programmers.lv2.n42860;

class Solution {
    int ans = Integer.MAX_VALUE;
    // 각 알파벳 별 변화 횟수
    int change[] = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1
    };
    boolean visited[];

    public int solution(String name) {
        visited = new boolean[name.length()];
        int startCnt = 1;
        for (int i = 1; i < name.length(); i++) {
            // 이미 선택 위치의 문자가 `A`라면 별도로 방문해서 문자를 바꿀 이유가 없음. 방문 처리
            if (name.charAt(i) == 'A') {
                visited[i] = true;
                startCnt++;
            }
        }
        dfs(0, startCnt, 0, name);
        return ans;
    }

    void dfs(int idx, int cnt, int sum, String name) {
        visited[idx] = true;
        sum += change[name.charAt(idx) - 'A'];

        if (cnt == name.length()) {
            ans = Math.min(sum, ans);
            return;
        }

        for (int i = 0; i < name.length(); i++) {
            if (i == idx || visited[i]) continue;
            dfs(i,
                    cnt + 1,
                    sum + (Math.abs(i - idx) <= name.length() / 2 ? Math.abs(i - idx) : name.length() - Math.abs(i - idx)),
                    name);
            visited[i] = false;
        }
    }
}