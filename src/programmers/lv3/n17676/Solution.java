package programmers.lv3.n17676;
class Solution {
    public int solution(String[] lines) {

        if(lines.length == 1) return 1;

        int start_time[] = new int[lines.length];
        int end_time[] = new int[lines.length];     // end time은 이미 정렬됨

        // 로그가 끝나는 시간, 시작하는 시간을 각 배열에 저장
        for(int i=0;i<lines.length;i++) {
            String[] l = lines[i].split(" ");
            int endTime = getEndTime(l[1]);
            int startTime = getStartTime(endTime, l[2].substring(0, l[2].length()-1));
            start_time[i] = startTime;
            end_time[i] = endTime;
        }

        int ans = 0;
        for(int i=0;i<lines.length;i++) {
            int cnt = 0;
            int currEndTime = end_time[i];
            System.out.println("currEndTime : "+currEndTime);
            for(int j=i;j<lines.length;j++) {
                // 각 로그의 시작 시간을 1초 앞으로 당겼을 때, endTime보다 작다면 1초 이내에 처리된 것으로 생각할 수 있음 -> cnt+1
                if(currEndTime > start_time[j] - 1000) {
                    cnt += 1;
                }
            }
            ans = Math.max(cnt, ans);
        }
        return ans;
    }

    // 로그가 끝나는 시간
    int getEndTime(String time) {
        String[] t = time.split(":");

        int h = Integer.parseInt(t[0]) * 3600;
        int m = Integer.parseInt(t[1]) * 60;
        double s = Double.parseDouble(t[2]);

        return 1000 * (h+m) + (int)(1000*s);
    }

    // 로그가 시작하는 시간
    int getStartTime(int endTime, String duration) {
        int dur = (int) (1000 * Double.parseDouble(duration));
        return endTime-dur+1;
    }
}