package programmers.lv2.n1835;

class Solution {
    char[] member = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    boolean visited[] = new boolean[8];
    String[] condi;
    int answer = 0;

    public int solution(int n, String[] data) {
        condi = data;
        dfs(new char[8], 0);
        return answer;
    }

    void dfs(char[] line, int idx) {
        if (idx == 8) {
            if (check(line)) answer++;
            return;
        }

        for (int i = 0; i < 8; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            line[idx] = member[i];
            dfs(line, idx + 1);
            visited[i] = false;
        }
    }

    boolean check(char[] line) {
        for (String c : condi) {
            char a = c.charAt(0);
            char b = c.charAt(2);

            char e = c.charAt(3);
            int diff1 = c.charAt(4) - '0';
            int diff2 = getDiff(line, a, b);

            if (e == '=') {
                if (diff1 != diff2) return false;
            } else if (e == '>') {
                if (diff1 >= diff2) return false;
            } else {
                if (diff1 <= diff2) return false;
            }
        }

        return true;
    }

    int getDiff(char[] line, char a, char b) {
        int c = 0, d = 0;

        for (int i = 0; i < 8; i++) {
            if (line[i] == a) c = i;
            else if (line[i] == b) d = i;
        }

        return Math.abs(c - d) - 1;
    }
}