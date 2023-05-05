package lv2.n43165;

class Solution {

    static int answer;
    public int solution(int[] numbers, int target) {
        answer = 0;
        dfs(numbers, 0, target, 0);
        return answer;
    }

    public void dfs(int[] numbers, int depth, int target, int sum) {
        // numbers : 알고리즘을 실행할 대상 배열
        // depth : 노드의 깊이
        // target : 타겟 넘버
        // sum : 이전 노드 까지의 결과 값

        if(numbers.length == depth) {
            if(target == sum) answer++;
        } else {
            dfs(numbers, depth+1, target, sum+numbers[depth]);
            dfs(numbers, depth+1, target, sum-numbers[depth]);
        }

    }
}
