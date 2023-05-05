package lv2.n42746;

import java.util.*;
// 내림차순 정렬 후 문자열로 출력
class Solution {
    public String solution(int[] numbers) {

        String answer = "";

        // 문자열 리턴을 해 줄 스트링배열 생성
        String[] str = new String[numbers.length];

        // int배열 numbers를 String배열로 전환
        for(int i=0;i<numbers.length;i++) {
            str[i] = String.valueOf(numbers[i]);
        }

        Arrays.sort(str, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                System.out.println((b+a)+", "+(a+b));
                // 내림차순 정렬
                return (b+a).compareTo(a+b);
                // 오름차순 정렬
                // return (a+b).comapreTo(a+b);
            }
        });

        // 0값이 중복일 경우 ex) {0, 0, 0}
        // 답이 00이 나오면 안되므로 첫번쨰 값이 0이면 0을 리턴
        if (str[0].equals("0")) return "0";

        // 0이 아니라면 문자열 출력해주기
        return Arrays.toString(str).replaceAll("[^\\w+]", "");

    }


}
