package programmers.lv2.n42885;
import java.util.*;
class Solution {
    public int solution(int[] people, int limit) {
        if (people.length == 1) return 1;
        Arrays.sort(people);

        int ans = 0;
        int left = 0;
        int right = people.length - 1;
        while (left <= right) {
            int weight = people[left] + people[right];

            if (left == right) {
                ans++;
                break;
            }

            if (weight <= limit) {
                left++;
                right--;
                ans++;
            } else {
                ans++;
                right--;
            }
        }
        return ans;
    }
}