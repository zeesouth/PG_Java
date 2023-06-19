package programmers.lv3.n150366;

import java.util.*;
class Solution {
    final int N = 50;
    final int C = 1000;

    // 정답 출력 수 및 정답 테이블
    int cnt = 0;
    String answer[] = new String[C];

    // 각 칸들이 가리키고 있는 방향
    Pair[][] posTable = new Pair[N+1][N+1];;

    // 각 칸이 나타내는 값
    String[][] valTable = new String[N+1][N+1];;

    // 값별로 위치를 나타내는 칸 (가장 최상위 칸만 포함)
    Map<String, HashSet<Pair>> valMap = new HashMap<>();

    // 각 칸이 병합되고 있는 칸
    HashSet<Pair>[][] mergeTable = new HashSet[N+1][N+1];;

    public String[] solution(String[] commands) {
        init();

        for(String c : commands) {
            StringTokenizer st = new StringTokenizer(c);
            String order = st.nextToken();
            if(order.equals("UPDATE")) {
                String v1 = st.nextToken();
                String v2 = st.nextToken();
                if(st.hasMoreTokens()) update(Integer.parseInt(v1), Integer.parseInt(v2), st.nextToken());
                else update(v1, v2);
            } else if(order.equals("MERGE")) {
                int r1 = Integer.parseInt(st.nextToken());
                int c1 = Integer.parseInt(st.nextToken());
                int r2 = Integer.parseInt(st.nextToken());
                int c2 = Integer.parseInt(st.nextToken());
                merge(r1, c1, r2, c2);
            } else if(order.equals("UNMERGE")) {    // UNMERGE
                int r1 = Integer.parseInt(st.nextToken());
                int c1 = Integer.parseInt(st.nextToken());
                unmerge(r1, c1);
            } else {
                int r1 = Integer.parseInt(st.nextToken());
                int c1 = Integer.parseInt(st.nextToken());
                print(r1, c1);
            }
        }

        return Arrays.copyOfRange(answer, 0, cnt);
    }

    void init() {
        for(int i=1;i<=N;i++) {
            for(int j=1;j<=N;j++) {
                posTable[i][j] = new Pair(i, j);
                mergeTable[i][j] = new HashSet<>();
                mergeTable[i][j].add(posTable[i][j]);
            }
        }
    }

    void update(int r, int c, String value) {
        Pair p = findParent(r, c);
        if(valTable[p.r][p.c] != null) {
            if(valTable[p.r][p.c].equals(value)) return;
            valMap.get(valTable[p.r][p.c]).remove(p);
        }

        valTable[p.r][p.c] = value;
        if(!valMap.containsKey(value)) valMap.put(value, new HashSet<>());
        valMap.get(value).add(p);
    }

    void update(String value1, String value2) {
        if(value1.equals(value2)) return;
        if(!valMap.containsKey(value1)) return;
        HashSet<Pair> pairSet = valMap.remove(value1);

        for(Pair p : pairSet) valTable[p.r][p.c] = value2;

        if(!valMap.containsKey(value2)) valMap.put(value2, new HashSet<>());
        valMap.get(value2).addAll(pairSet);
    }

    void merge(int r1, int c1, int r2, int c2) {
        if(r1 == r2 && c1 == c2) return;

        Pair p1 = findParent(r1, c1);
        Pair p2 = findParent(r2, c2);

        if(p1.r == p2.r && p1.c == p2.c) return;

        if(valTable[p1.r][p1.c] != null) {
            for(Pair p : mergeTable[p2.r][p2.c]) {
                posTable[p.r][p.c] = p1;
                mergeTable[p1.r][p1.c].add(p);
                mergeTable[p.r][p.c] = new HashSet<>();
            }

            if(valTable[p2.r][p2.c] != null) {
                valMap.get(valTable[p2.r][p2.c]).remove(p2);
                valTable[p2.r][p2.c] = null;
            }

        } else {
            for(Pair p : mergeTable[p1.r][p1.c]) {
                posTable[p.r][p.c] = p2;
                mergeTable[p2.r][p2.c].add(p);
                mergeTable[p.r][p.c] = new HashSet<>();
            }
        }
    }

    void unmerge(int r, int c) {
        Pair p = findParent(r, c);
        String val = valTable[p.r][p.c];
        HashSet<Pair> mgt = mergeTable[p.r][p.c];

        if(mgt.size() == 1) return;

        for(Pair pp : mgt) {
            mergeTable[pp.r][pp.c] = new HashSet<>();
            mergeTable[pp.r][pp.c].add(pp);
            valTable[pp.r][pp.c] = null;
            posTable[pp.r][pp.c] = pp;
        }

        if(val != null) {
            valMap.get(val).remove(p);
            valMap.get(val).add(new Pair(r, c));
            valTable[r][c] = val;
        }
    }

    void print(int r, int c) {
        Pair p = findParent(r, c);
        if(valTable[p.r][p.c] == null) answer[cnt++] = "EMPTY";
        else answer[cnt++] = valTable[p.r][p.c];
    }


    Pair findParent(int r, int c) {
        return posTable[r][c];
    }

}

class Pair extends Object {
    int r, c;

    Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int hashCode() {
        return this.r * 100 + this.c;
    }

    public boolean equals(Object o) {
        return ((Pair)o).r == this.r && ((Pair)o).c == this.c;
    }
}