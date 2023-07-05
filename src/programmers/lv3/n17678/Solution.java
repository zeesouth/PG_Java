package programmers.lv3.n17678;

import java.util.*;
class Solution {

    /*
    셔틀은 09:00부터 총 n회 t분 간격으로 역에 도착함

    n : 셔틀 운행 횟수
    t : 셔틀 운행 간격
    m : 한 셔틀에 탈 수 있는 최대 크루 수
    timetable : 크루가 대기열에 도착하는 시각
    */
    public String solution(int n, int t, int m, String[] timetable) {
        int time[] = new int[timetable.length];

        for(int i=0;i<timetable.length;i++)
            time[i] = timeToMinute(timetable[i]);

        Arrays.sort(time);

        int currT = timeToMinute("09:00");
        int last = 0;   // 마지막으로 탄 사람 시간
        int idx = 0;
        int cnt = 0;    // 현재 버스에 탄 사람 수
        int i = 0;
        for(; i<n; i++){
            cnt = 0;
            if(idx >= time.length) break;
            while(cnt < m){
                if(currT >= time[idx]){
                    last = time[idx];   // 마지막으로 탄 사람 갱신
                    idx++;
                    cnt++;
                    if(idx >= time.length) break;
                } else break;
            }
            currT += t;
        }

        if(cnt < m){    // 마지막 버스에 자리가 있음
            if (i < n) return minuteToTime(currT);
            else return minuteToTime(currT-t);
        }else { // 마지막 버스에 자리가 없음 -> 마지막으로 탄사람보다 1분 먼저 와야 함
            return minuteToTime(last-1);
        }
    }

    int timeToMinute(String time) {
        String[] t = time.split(":");
        int h = Integer.parseInt(t[0]);
        int m = Integer.parseInt(t[1]);
        return h * 60 + m;
    }

    String minuteToTime(int minute) {
        int h = minute / 60;
        int m = minute % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", h));
        sb.append(":");
        sb.append(String.format("%02d", m));
        return sb.toString();
    }
}