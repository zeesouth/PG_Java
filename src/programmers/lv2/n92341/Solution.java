package programmers.lv2.n92341;

import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {

        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> ans = new HashMap<>();

        for (String r : records) {
            String[] s = r.split(" ");
            int t = strToInt(s[0]);
            int num = Integer.parseInt(s[1]);

            if (!map.containsKey(num)) map.put(num, t);
            else {
                int res = t - map.remove(num);
                if (!ans.containsKey(num)) ans.put(num, 0);
                ans.replace(num, res + ans.get(num));
            }
        }

        for (int key : map.keySet()) {
            int t = strToInt("23:59");
            int res = t - map.get(key);
            if (!ans.containsKey(key)) ans.put(key, 0);
            ans.replace(key, res + ans.get(key));
        }

        ArrayList<int[]> arr = new ArrayList<>();

        for (int key : ans.keySet()) {
            int[] tmp = new int[2];
            tmp[0] = key;
            int data = ans.get(key);

            tmp[1] = fees[1];

            if (data > fees[0]) {
                int g = (data - fees[0]) / fees[2];
                if ((data - fees[0]) % fees[2] != 0) g += 1;
                tmp[1] += (g) * fees[3];
            }
            arr.add(tmp);
        }

        Collections.sort(arr, (o1, o2) -> o1[0] - o2[0]);

        int[] answer = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            answer[i] = arr.get(i)[1];
        }

        return answer;
    }

    int strToInt(String time) {
        String[] s = time.split(":");
        int h = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        return h * 60 + m;
    }
}
