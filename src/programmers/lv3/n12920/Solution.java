package programmers.lv3.n12920;

class Solution {
    public int solution(int n, int[] cores) {

        if (n <= cores.length) return n;

        int answer = 0;

        int min = 0;
        int max = 10000 * n;

        int time = 0;   // 작업량을 처리한 최소시간
        int m = 0;  // 처리한 작업량

        while (true) {
            int mid = (min + max) / 2;

            int count = calculate(mid, cores);

            if (min > max) break;

            // 해당 시간까지 처리한 작업량이 n보다 크면 time과 m 갱신
            if (count >= n) {
                max = mid - 1;
                time = mid;
                m = count;
            } else {
                min = mid + 1;
            }
        }

        m -= n; // 처리한 작업량과 처리해야할 작업량(n)의 차이 갱신
        for (int i = cores.length - 1; i >= 0; i--) {
            if (time % cores[i] == 0) {
                if (m-- == 0) {
                    answer = i + 1;
                    break;
                }
            }
        }

        return answer;
    }

    int calculate(int time, int[] cores) {
        if (time == 0) {
            // 시간이 0일때, 처리할 수 있는 작업량은 코어의 개수
            return cores.length;
        }

        // 시간이 0일때, 처리한 작업 량
        int count = cores.length;

        // time까지 코어가 처리할 수 있는 작업량 합산
        for (int i = 0; i < cores.length; i++) {
            count += (time / cores[i]);
        }

        return count;
    }
}
