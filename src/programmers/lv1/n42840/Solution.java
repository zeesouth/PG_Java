package programmers.lv1.n42840;

import java.util.ArrayList;

class Solution {
    public int[] solution(int[] answers) {

        int[] answer = new int[3];

        int[] one = {1,2,3,4,5};
        int[] two = {2,1,2,3,2,4,2,5};
        int[] three = {3,3,1,1,2,2,4,4,5,5};

        int oneIndex = 0;
        int twoIndex = 0;
        int threeIndex = 0;



        for(int i=0; i<answers.length; i++) {

            int one_answer = one[oneIndex];
            int two_answer = two[twoIndex];
            int three_answer = three[threeIndex];

            if(one_answer == answers[i])
                answer[0]++;
            if(two_answer == answers[i])
                answer[1]++;
            if(three_answer == answers[i])
                answer[2]++;

            oneIndex = (oneIndex+1)%one.length;
            twoIndex = (twoIndex+1)%two.length;
            threeIndex = (threeIndex+1)%three.length;


        }

        int resultMax = Math.max(answer[0], Math.max(answer[1], answer[2]));
        ArrayList<Integer> result = new ArrayList<>();

        for(int i=0;i<3;i++)
            if(resultMax == answer[i]) result.add(i+1);

        int[] res = result.stream().mapToInt(i->i).toArray();

        return res;
    }
}

// 1번수포자
// 1,2,3,4,5 -> 반복
// 2번수포자
// 2,1,2,3,2,4,2,5  -> 반복
// 3번수포자
// 3,3,1,1,2,2,4,4,5,5 -> 반복
