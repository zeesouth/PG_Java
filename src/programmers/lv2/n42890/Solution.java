package programmers.lv2.n42890;

import java.util.*;

class Solution {
    public int solution(String[][] relation) {
        ArrayList<Integer> candidateKey = new ArrayList<>();

        int rowLen = relation.length;
        int colLen = relation[0].length;

        for (int set = 1; set < (1 << colLen); set++) {
            // 최소성 검사
            if (!isMinimal(set, candidateKey)) continue;

            // 유일성 검사
            if (isUnique(set, rowLen, colLen, candidateKey, relation)) {
                // System.out.println(Integer.toBinaryString(set));
                candidateKey.add(set);
            }
        }

        return candidateKey.size();
    }

    // 유일성 만족 여부 판단
    private boolean isUnique(int set, int rowLen, int colLen, ArrayList<Integer> candidateKey, String[][] relation) {
        HashSet<String> dataSet = new HashSet<>();

        for (int row = 0; row < rowLen; ++row) {
            StringBuilder dataByKeySet = new StringBuilder();

            for (int th = 0; th < colLen; ++th) {
                if ((set & (1 << th)) == (1 << th)) {
                    dataByKeySet.append(relation[row][th]);
                }
            }

            // 중복된 데이터가 있다면 -> 유일성 만족을 안함
            if (dataSet.contains(dataByKeySet.toString())) return false;
            else dataSet.add(dataByKeySet.toString());
        }

        return true;
    }

    // 최소성 만족 여부
    private boolean isMinimal(int set, ArrayList<Integer> candidateKey) {
        for (int key : candidateKey) {
            if ((key & set) == key) return false;
        }
        return true;
    }
}