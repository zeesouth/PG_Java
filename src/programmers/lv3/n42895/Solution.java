package programmers.lv3.n42895;
// Set 배열을 활용한 메모이제이션
import java.util.*;
class Solution {
    //순서쌍 a에 b 합치기
    public void unionSet(Set<Integer> union, Set<Integer> a, Set<Integer> b) {
        for (int n1 : a) {
            for (int n2 : b) {
                union.add(n1 + n2);
                union.add(n1 - n2);
                union.add(n1 * n2);
                if (n2 != 0) union.add(n1 / n2);
            }
        }
    }

    final int MAX = 8;
    public int solution(int N, int number) {
        if (number == N) return 1;

        Set<Integer> setList[] = new Set[MAX+1];

        for (int i = 0; i <= MAX; i++)
            setList[i] = new HashSet<>(); // 개수 별 해쉬셋

        setList[1].add(N); //1개로 만들 수 있는 건 나 자신뿐

        int n = N;
        for (int i = 2; i <= MAX; i++) {
            for (int j = 1; j <= i / 2; j++) {
                unionSet(setList[i], setList[i - j], setList[j]);
                unionSet(setList[i], setList[j], setList[i - j]);
            }

            n = (n * 10) + N;
            setList[i].add(n); //연속된 숫자 넣기
            for (int num : setList[i])
                if (num == number) return i;
        }
        return -1;
    }
}