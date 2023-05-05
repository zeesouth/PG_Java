package lv1.n42576;

import java.util.*;
class Solution {
    public String solution(String[] participant, String[] completion) {

        String answer = "";
        Map<String, Integer> comp = new HashMap<String, Integer>();

        for(String c : completion) {
            if(comp.containsKey(c)) comp.replace(c, comp.get(c)+1);
            else comp.put(c, 1);
        }

        for(String p : participant) {
            if(!comp.containsKey(p)) return p;
            else {
                if(comp.get(p) == 1) comp.remove(p);
                else comp.replace(p, comp.get(p)-1);
            }
        }

        return answer;
    }
}
