package programmers.lv3.n72414;
class Solution {
    // 전체 동영상 재생시간, 광고 재생시간, 동영상 시청 기록
    public String solution(String play_time, String adv_time, String[] logs) {
        // init
        int play = timeToSecond(play_time); // 0~play
        int adv = timeToSecond(adv_time); // a ~ a+adv

        if(play == adv) return "00:00:00";

        int total[] = new int[play+1];

        for(int i=0;i<logs.length;i++) {
            String[] timeArea = logs[i].split("-");
            int start = timeToSecond(timeArea[0]);
            int end = timeToSecond(timeArea[1]);

            for(int j=start;j<end;j++) total[j]++;

        }

        long sum = 0;
        for (int i = 0; i < adv; i++) sum += total[i];

        long max = sum;
        int start = 0;

        // 1초씩 영상의 뒤로 이동하며 광고시간 길이만큼의 누적 재생시간의 최댓값 구하기
        for (int i = 1, j = adv; j < play; i++, j++) {
            sum += total[j] - total[i - 1];
            if (max < sum) {
                max = sum;
                start = i;
            }
        }

        return secondToTime(start);
    }

    int timeToSecond(String time) {
        String[] t = time.split(":");
        int h = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        int s = Integer.parseInt(t[2]);
        return h*3600+m*60+s;
    }

    String secondToTime(int second){
        int s = second % 60;
        second -= s;
        int m = (second % 3600) / 60;
        second -= m;
        int h = second / 3600;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", h));
        sb.append(":");
        sb.append(String.format("%02d", m));
        sb.append(":");
        sb.append(String.format("%02d", s));

        return sb.toString();
    }
}
/*
시작전 : timeToSecond 메서드(string -> int)와 secondToTime(int -> string) 메서드를 만든다.

1. 입력 값(play_time, adv_time)을 초 단위로 변경한다.
2. 전체 play_time만큼의 total배열을 선언한다
3. 주어진 logs배열을 순회하면서 로그의 시작 시점부터 종료 시점까지 total 배열에 +1를 한다.
4. 0~advTime(초)까지의 sum을 구해둔다.
5. 투 포인터 알고리즘을 활용해 sum에 시작 시간을 빼고 종료 시간을 더해가며 max값을 갱신한다.
   max값이 갱신되었다면, 시작 시간인 start를 기억해둔다.
6. 초단위로 기록되어있는 start를 string 형태로 변경한 후 return 한다.
*/
