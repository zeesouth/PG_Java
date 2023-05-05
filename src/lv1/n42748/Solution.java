package lv1.n42748;

import java.util.*;

class Solution {

    public int[] solution(int[] array, int[][] commands) {

        int[] answer = new int[commands.length];


        for(int i=0;i<commands.length;i++) {

            // 시작인덱스, 끝인덱스, 출력할 번호
            int[] currCommand = commands[i];
            int[] cutArr = Arrays.copyOfRange(array, currCommand[0]-1, currCommand[1]);

            // 1. 선택 정렬 Selection
            /*
            int minIndex = 0;
            for(int j=0;j<cutArr.length;j++) {
                for(int k=j+1; k<cutArr.length; k++) {
                    if(cutArr[minIndex] > cutArr[k])
                        minIndex = k;
                }

                // SWAP
                int tmp = cutArr[j];
                cutArr[j] = cutArr[minIndex];
                cutArr[minIndex] = tmp;

            }
            */

            // 삽입 정렬 Insertion
            /*
            for(int j=0;j<cutArr.length;j++) {
                for(int k=j; k >= 1; k--) {
                    // 한 칸 씩 왼쪽으로 이동
                    if(cutArr[k] < cutArr[k-1]) {
                        int tmp = cutArr[k];
                        cutArr[k] = cutArr[k-1];
                        cutArr[k-1] = tmp;
                    } else break;   // 본인보다 작은 데이터를 만나면 정렬 종료

                }
            }
            */

            // 퀵 정렬  Quick
            quickSort(cutArr, 0, cutArr.length-1);

            // 계수 정렬 Count



            // Answer
            answer[i] = cutArr[currCommand[2]-1];
        }

        return answer;
    }

    // 퀵 정렬
    public static void quickSort(int arr[], int start, int end) {

        // 원소가 1개인 경우 종료
        if(start>=end) return;

        int pivot = start;  // 피벗의 시작은 첫 번째 원소
        int left = start+1;
        int right = end;

        while(left <= right) {

            // 피벗보다 큰 데이터를 찾을 때까지 반복
            while(left <= end && arr[left] <= arr[pivot])
                left += 1;

            // 피벗보다 작은 데이터를 찾을 때까지 반복
            while(right > start && arr[right] >= arr[pivot])
                right -= 1;

            // 엇갈렸다면 둘 중 작은 데이터와 피벗을 교체
            if(left > right) {
                int tmp = arr[right];
                arr[right] = arr[pivot];
                arr[pivot] = tmp;
            } else { // 엇갈리지 않았다면 작은 데이터와 큰 데이터를 교체
                int tmp = arr[right];
                arr[right] = arr[left];
                arr[left] = tmp;
            }

            // 분할 이후 왼쪽 부분과 오른쪽 부분에서 각각 정렬 수행
            // right-1까지는 pivot보다 작은
            // right +1부터는 pivot보다 큰
            quickSort(arr, start, right-1);
            quickSort(arr, right+1, end);


        }


    }
}