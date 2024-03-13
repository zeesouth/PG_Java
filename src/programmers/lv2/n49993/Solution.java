package programmers.lv2.n49993;

import java.util.*;

class Solution {
    public int solution(String skill, String[] skill_trees) {

        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < skill.length(); i++) {
            set.add(skill.charAt(i));
        }

        boolean passed;
        int answer = 0;
        int i;
        for (int j = 0; j < skill_trees.length; j++) {
            i = 0;
            passed = true;

            for (int k = 0; k < skill_trees[j].length(); k++) {
                char c = skill_trees[j].charAt(k);

                if (!set.contains(c)) continue;

                if (c != skill.charAt(i)) {
                    passed = false;
                    break;
                }
                i++;
            }

            if (passed) {
                answer++;
            }
        }

        return answer;
    }
}