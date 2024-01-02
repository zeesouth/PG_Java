package programmers.lv2.n250135;

public class Solution {
    class Time {
        int h, m, s;

        Time(int h, int m, int s) {
            this.h = h;
            this.m = m;
            this.s = s;
        }

        // 초를 h,m,s로 변환
        Time(int s) {
            this.h = s / 3600;
            this.m = (s % 3600) / 60;
            this.s = (s % 3600) % 60;
        }

        // h,m,s를 초로 변환
        int toSecond() {
            return h * 3600 + m * 60 + s;
        }

        // h,m,s를 각도로 변환
        double[] getDegree() {
            double hd = (h % 12) * 30d + m * 0.5d + s * (1 / 120d);
            double md = m * 6d + s * 0.1d;
            double sd = s * 6d;

            return new double[]{hd, md, sd};
        }
    }

    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int ans = 0;

        // 시작시간, 끝나는 시간을 초 단위로 변경
        int start = new Time(h1, m1, s1).toSecond();
        int end = new Time(h2, m2, s2).toSecond();

        for (int i = start; i < end; i++) {
            double[] cnt = new Time(i).getDegree();
            double[] next = new Time(i + 1).getDegree();

            // 시침-초침, 분침-초침이 겹치는지 판단
            boolean hMatch = hourMatch(cnt, next);
            boolean mMatch = minuteMatch(cnt, next);

            // 초침이 시침, 분침과 겹칠 때
            if (hMatch && mMatch) {
                // 시침, 분침이 같은 경우 한번만 처리
                if (Double.compare(next[0], next[1]) == 0) ans++;
                // 그렇지 않을 경우 두번 처리
                else ans += 2;
            }
            // 둘 중 하나라도 겹친다면 한번 처리
            else if (hMatch || mMatch) ans++;
        }

        // 시작 시간이 0시 or 12시에 시작한다면 한번 처리
        if (start == 0 || start == 43200) ans++;

        return ans;
    }


    // 시침-초침 겹침 판단
    boolean hourMatch(double[] cnt, double[] next) {
        if (cnt[0] > cnt[2] && next[0] <= next[2]) return true;

        // 초침이 354도에서 0도로 넘어갈 때 예외 케이스 처리
        if (cnt[2] == 354d && cnt[0] > 354d) return true;

        return false;
    }

    // 분침-초침 겹침 판단
    boolean minuteMatch(double[] cnt, double[] next) {
        if (cnt[1] > cnt[2] && next[1] <= next[2]) return true;

        // 초침이 354도에서 0도로 넘어갈 때 예외 케이스 처리
        if (cnt[2] == 354d && cnt[1] > 354d) return true;

        return false;
    }
}
