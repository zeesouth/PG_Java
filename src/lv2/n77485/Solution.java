package lv2.n77485;

import java.util.*;

// 회전에 의해 위치가 바뀐 숫자들 중 가장 작은 숫자들
// 테두리 숫자만 보면 됨

class Solution {
    public int[] solution(int rows, int columns, int[][] queries) {

        int[][] map = new int[rows+1][columns+1];

        for(int i=1;i<=rows;i++) {
            int currRow = columns*(i-1);
            Arrays.setAll(map[i], j->(currRow+j));
        }

        /*
        for(int i=1;i<=rows;i++) {
            for(int j=1;j<=columns;j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
        */

        int[] answer = new int[queries.length];

        for(int i=0;i<queries.length;i++) {

            int[] query = queries[i];

            int rowLength = query[2]-query[0]+1;
            int colLength = query[3]-query[1]+1;
            int caseLength = Math.max(rowLength, colLength);

            int currAnswer = Integer.MAX_VALUE;

            int[][] original = new int[rowLength][colLength];
            for(int k=0;k<rowLength;k++) {
                System.arraycopy(
                        map[query[0]+k], query[1], original[k], 0, original[k].length);
            }

            for(int j=0;j<caseLength-1;j++) {


                if(j >= 0 && j < rowLength-1) {
                    // 행+1
                    map[query[0]+j+1][query[3]] = original[j][colLength-1];
                    currAnswer = Math.min(currAnswer, map[query[0]+j+1][query[3]]);

                    // 행-1
                    map[query[0]+j][query[1]] = original[j+1][0];
                    currAnswer = Math.min(currAnswer, map[query[0]+j][query[1]]);
                }

                if(j >= 0 && j < colLength-1) {
                    // 열+1
                    map[query[0]][query[1]+j+1] = original[0][j];
                    currAnswer = Math.min(currAnswer, map[query[0]][query[1]+j+1]);

                    // 열-1
                    map[query[2]][query[1]+j] = original[rowLength-1][j+1];
                    currAnswer = Math.min(currAnswer, map[query[2]][query[1]+j]);
                }
            }

            answer[i] = currAnswer;

        }


        return answer;
    }
}
