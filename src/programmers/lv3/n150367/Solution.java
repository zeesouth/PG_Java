package programmers.lv3.n150367;
import java.util.*;
class Solution {
    TreeSet<Integer> set;
    public void init() {
        long max_n = 1_000_000_000_000_000L;
        String max = Long.toBinaryString(max_n);
        set = new TreeSet<>();
        int num = 1;
        int last = 1;
        while(last <= max.length()) {
            set.add(last);
            num *= 2;
            last += num;
        }
        if(last > max.length()) set.add(last);
    }

    public int[] solution(long[] numbers) {
        init();
        int[] answer = new int[numbers.length];
        for(int i=0;i<numbers.length;i++) {
            long number_10 = numbers[i];
            if(number_10 == 1) {
                answer[i] = 1;
                continue;
            }
            String number_2 = Long.toBinaryString(number_10);
            int length = set.ceiling(number_2.length());
            int rest = length-number_2.length();
            answer[i] = check((length-1)/2, rest, number_2, ((length-1)/2+1)/2) ? 1 : 0;
        }
        return answer;
    }

    boolean check(int parent, int rest, String num, int level) {

        int left = parent-level;
        int right = parent+level;

        int l = left >= 0 && left < rest ? 0 : num.charAt(left-rest)-'0';
        int p = parent >= 0 && parent < rest ? 0 : num.charAt(parent-rest)-'0';
        int r = right >= 0 && right < rest ? 0 : num.charAt(right-rest)-'0';
        if(p == 0 && (l == 1 || r == 1)) return false;

        boolean ans = true;
        if(level > 1) {
            ans &= check(left, rest, num, level/2);
            ans &= check(right, rest, num, level/2);
        }
        return ans;
    }
}

