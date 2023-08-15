package programmers.lv2.n60057;

class Solution {
    int answer;

    public int solution(String s) {
        // 문자열을 전혀 압축하지 않을 경우 가장 큼
        answer = s.length();

        if (answer == 1) return 1;

        // 압축할 수 있는 최대 단위
        int max = s.length() / 2;

        for (int i = 1; i <= max; i++) {
            dfs(0, 0, i, s);
        }

        return answer;
    }

    void dfs(int res, int idx, int size, String s) {
        if (idx == s.length()) {
            answer = Math.min(res, answer);
            return;
        }

        String target = null;
        int cnt = 0;
        int nextIdx = idx;
        while (nextIdx + size <= s.length()) {
            if (target == null) target = s.substring(nextIdx, nextIdx + size);
            else {
                if (!target.equals(s.substring(nextIdx, nextIdx + size))) break;
            }
            nextIdx += size;
            cnt++;
        }

        if (cnt == 1000) dfs(res + 4 + size, nextIdx, size, s);
        else if (cnt >= 100) dfs(res + 3 + size, nextIdx, size, s);
        else if (cnt >= 10) dfs(res + 2 + size, nextIdx, size, s);
        else if (cnt > 1) dfs(res + 1 + size, nextIdx, size, s);
        else {
            if (idx + size <= s.length())
                dfs(res + size, idx + size, size, s);
            else
                dfs(res + (s.length() - idx), s.length(), size, s);
        }
    }
}
