package programmers.lv2.n17686;

import java.util.*;

class Solution {
    public String[] solution(String[] files) {
        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String head1 = s1.split("[0-9]")[0];
                String head2 = s2.split("[0-9]")[0];
                int compareHead = head1.toUpperCase().compareTo(head2.toUpperCase());
                if (compareHead == 0) { // head가 같을 때 number 비교
                    s1 = s1.substring(head1.length());
                    s2 = s2.substring(head2.length());
                    StringBuilder number1 = new StringBuilder();
                    for (char c : s1.toCharArray()) { // number
                        if (Character.isDigit(c) && number1.length() <= 5)
                            number1.append(c);
                        else
                            break;
                    }
                    StringBuilder number2 = new StringBuilder();
                    for (char c : s2.toCharArray()) {
                        if (Character.isDigit(c) && number2.length() <= 5)
                            number2.append(c);
                        else
                            break;
                    }
                    return Integer.parseInt(number1.toString()) - Integer.parseInt(number2.toString());
                } else {
                    return compareHead;
                }
            }
        });

        return files;
    }
}
