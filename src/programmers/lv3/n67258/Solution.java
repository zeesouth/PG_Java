package programmers.lv3.n67258;

import java.util.HashMap;
import java.util.HashSet;
class Solution {
    public int[] solution(String[] gems) {
        HashSet<String> set = new HashSet<>();
        for(String s : gems) set.add(s);

        int n = gems.length;            // 배열 길이
        int dist = Integer.MAX_VALUE;   // 범위 구간 크기 비교
        int left = 0, right = 0;        // 범위
        int start = 0, end = 0;         // 정답

        HashMap<String, Integer> map = new HashMap<>();

        while(true) {
            // 크기가 같은 경우
            if(set.size() == map.size()) {
                map.put(gems[left], map.get(gems[left])-1);
                if(map.get(gems[left]) == 0) map.remove(gems[left]);
                left++;
            } else if(right == n) break;
            else {
                map.put(gems[right], map.getOrDefault(gems[right], 0) + 1);
                right++;
            }

            if(map.size() == set.size()) {
                if(right-left < dist) {
                    dist = right-left;
                    start = left+1;
                    end = right;
                }
            }
        }

        return new int[]{start, end};
    }
}
