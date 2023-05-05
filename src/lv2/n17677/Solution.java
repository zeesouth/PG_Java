package lv2.n17677;

import java.util.*;
class Solution {
    public int solution(String str1, String str2) {

        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();

        HashMap<String, Integer> set = new HashMap<>();
        int d = 0;
        for(int i=0;i<str1.length()-1;i++) {
            String curr = str1.substring(i, i+2);
            if(curr.matches("[A-Z]+")) {
                if(!set.containsKey(curr)) set.put(curr, 0);
                set.replace(curr, set.get(curr)+1);
                d++;
            }
        }

        int n = 0;
        for(int i=0;i<str2.length()-1;i++) {
            String curr = str2.substring(i, i+2);
            if(curr.matches("[A-Z]+")) {
                if(set.containsKey(curr)) {
                    if(set.get(curr) == 1) set.remove(curr);
                    else set.replace(curr, set.get(curr)-1);
                    n++;
                }
                else d++;
            }
        }

        double ans = n == 0 && d == 0 ? 1 : (double)n/d;
        return (int)(ans*65536);
    }
}
