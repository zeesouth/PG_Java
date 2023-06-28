package programmers.lv3.n43163;
import java.util.*;
class Solution {
    final int MAX = Integer.MAX_VALUE;
    int ans = MAX;
    String word[];
    boolean visited[];
    public int solution(String begin, String target, String[] words) {
        visited = new boolean[words.length];
        word = words;
        dfs(0, begin, target);
        if(ans == MAX) ans = 0;
        return ans;
    }

    void dfs(int cnt, String c, String t) {
        if(c.equals(t)) {
            ans = Math.min(ans, cnt);
            return;
        }

        for(int i=0;i<word.length;i++) {
            if(visited[i]) continue;
            int sub = calSub(c, word[i]);
            if(sub == 1) {
                visited[i] = true;
                dfs(cnt+1, word[i], t);
                visited[i] = false;
            }
        }
    }

    int calSub(String curr, String next) {
        int ans = 0;
        for(int i=0;i<curr.length();i++) {
            if(curr.charAt(i) != next.charAt(i)) ans++;
        }
        return ans;
    }
}