package programmers.lv3.n81303;

import java.util.*;

class Solution {
    int N, sid;
    int up[], down[], stack[][];

    public String solution(int n, int k, String[] cmd) {
        init(n);

        for (int i = 0; i < cmd.length; i++) {
            k = play(k, cmd[i]);
        }

        return print();
    }

    void init(int n) {
        N = n;
        sid = 0;
        up = new int[n];
        down = new int[n];
        stack = new int[n][3];  // data, up, down
        for (int i = 0; i < N; i++) {
            if (i - 1 >= 0) up[i] = i - 1;
            else up[i] = -1;

            if (i + 1 < N) down[i] = i + 1;
            else down[i] = -1;
        }
    }

    int play(int k, String cmd) {
        int answer = k;
        StringTokenizer st = new StringTokenizer(cmd);
        char order = st.nextToken().charAt(0);
        // 삭제
        if (order == 'C') {
            int[] delete = {k, up[k], down[k]};

            if (up[k] != -1) down[up[k]] = down[k];
            if (down[k] != -1) up[down[k]] = up[k];

            if (down[k] == -1) answer = up[k];
            else answer = down[k];

            stack[sid++] = delete;
        }
        // 취소
        else if (order == 'Z') {
            int[] repair = stack[--sid];
            stack[sid] = new int[]{0, 0, 0};

            if (repair[1] != -1) down[repair[1]] = repair[0];
            if (repair[2] != -1) up[repair[2]] = repair[0];
        }
        // UP or DOWN
        else {
            int amount = Integer.parseInt(st.nextToken());
            while (amount-- > 0) {
                if (order == 'D') answer = down[answer];
                else answer = up[answer];
            }
        }
        return answer;
    }

    String print() {
        char[] ans = new char[N];
        Arrays.fill(ans, 'O');

        for (int i = 0; i < sid; i++) ans[stack[i][0]] = 'X';

        return new String(ans);
    }
}

/*
U X: 현재 선택된 행에서 X칸 위에 있는 행 선택
D X: 현재 선택된 행에서 X칸 아래에 있는 행 선택
C: 현재 선택된 행을 삭제한 후, 바로 아래 행 선택
   -> 단, 삭제된 행이 가장 마지막 행인 경우 바로 윗 행 선택
Z: 가장 최근에 삭제된 행을 원래대로 복구 (단, 현재 선택된 행은 바뀌지 않음)
*/