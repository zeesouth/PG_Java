package programmers.lv3.n64064;
import java.util.*;
class Solution {
    Set<Integer> set = new HashSet<>();
    public int solution(String[] user_id, String[] banned_id) {
        dfs(0, 0, user_id, banned_id);
        return set.size();
    }

    void dfs(int curr, int bid, String[] u, String[] b) {
        if(bid == b.length) {
            set.add(curr);
            return;
        }

        for(int i=0;i<u.length;i++) {
            if((curr & (1<<i)) == (1<<i)) continue;
            if(u[i].length() != b[bid].length()) continue;
            boolean flag = true;
            for(int j=0;j<b[bid].length() && flag;j++) {
                char c = b[bid].charAt(j);
                if(c == '*') continue;
                if(c != u[i].charAt(j)) flag = false;
            }

            if(flag) dfs(curr | (1 << (i)), bid+1, u, b);
        }
    }
}
