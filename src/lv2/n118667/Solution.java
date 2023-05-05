package lv2.n118667;

class Solution {


    public int solution(int[] queue1, int[] queue2) {

        int answer = 0;

        int length = queue1.length + queue2.length;

        int[] q1 = new int[length], q2 = new int[length];
        System.arraycopy(queue1, 0, q1, 0, queue1.length);
        System.arraycopy(queue2, 0, q2, 0, queue2.length);

        int head1 = 0, tail1 = queue1.length-1;
        int head2 = 0, tail2 = queue2.length-1;

        long sum1 = 0, sum2 = 0;
        for(int i=0;i<Math.max(queue1.length, queue2.length);i++) {
            if(i<queue1.length) sum1 += queue1[i];
            if(i<queue2.length) sum2 += queue2[i];
        }

        while(sum1 != sum2 && answer < 2 * (queue1.length + queue2.length)) {

            int get = 0;

            if(sum1 > sum2){

                get = q1[head1];
                head1 = (head1+1)%q1.length;

                tail2 = (tail2+1)%q2.length;
                q2[tail2] = get;

                sum1-=get; sum2+=get;
            } else {
                get = q2[head2];
                head2 = (head2+1)%q2.length;

                tail1 = (tail1+1)%q1.length;
                q1[tail1] = get;

                sum1+=get; sum2-=get;
            }
            answer++;
        }

        if(sum1 != sum2) answer = -1;

        return answer;

    }

}
