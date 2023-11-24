package programmers.lv2.n92335;

class Solution {

    boolean isPrime(long n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public int solution(int n, int k) {
        StringBuilder num = new StringBuilder();

        while (n >= k) {
            int a = n / k;
            int b = n % k;
            num.insert(0, b);
            n /= k;
        }
        num.insert(0, n);

        String[] s = (num.toString()).split("[0]+");

        int answer = 0;

        for (String data : s) {
            long number = Long.parseLong(data);
            boolean flag = isPrime(number);
            if (flag) answer++;
        }

        return answer;
    }
}

/*
 2/5 ... 1
 2/2 ... 0
   1
*/
