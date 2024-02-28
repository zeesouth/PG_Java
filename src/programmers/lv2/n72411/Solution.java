package programmers.lv2.n72411;

import java.util.*;

class Solution {
    ArrayList<String>[] res;
    HashMap<String, Integer> map = new HashMap<>();
    int[] max;
    String[] order;

    public String[] solution(String[] orders, int[] course) {
        res = new ArrayList[course.length];
        max = new int[course.length];
        order = orders;

        for (int i = 0; i < course.length; i++) {
            res[i] = new ArrayList<>();
            max[i] = 0;
            for (int j = 0; j < orders.length; j++) {
                dfs(i, j, course[i], 0, 0, new char[course[i]]);
            }
        }


        ArrayList<String> ans = new ArrayList<>();

        for (int i = 0; i < res.length; i++) {
            if (max[i] <= 1) continue;
            ans.addAll(res[i]);
        }

        Collections.sort(ans);

        String[] array = ans.toArray(new String[ans.size()]);

        return array;
    }

    void dfs(int i, int j, int m, int idx, int cnt, char[] arr) {
        if (cnt == m) {
            char[] tmp = Arrays.copyOf(arr, arr.length);
            Arrays.sort(tmp);
            String key = new String(tmp);
            if (!map.containsKey(key)) map.put(key, 0);
            map.replace(key, map.get(key) + 1);

            int c = map.get(key);
            if (c > max[i]) {
                max[i] = c;
                res[i].clear();
                res[i].add(key);
            } else if (c == max[i]) {
                res[i].add(key);
            }
            return;
        }

        if (idx == order[j].length()) return;

        for (int k = idx; k < order[j].length(); k++) {
            arr[cnt] = order[j].charAt(k);
            dfs(i, j, m, k + 1, cnt + 1, arr);
        }
    }
}