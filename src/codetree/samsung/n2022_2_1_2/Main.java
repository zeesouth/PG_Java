package codetree.samsung.n2022_2_1_2;
// 삼성 공채 2022 하반기 오전 2번
// 시간초과.. ㅜㅜ
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int N, M, size;
    static Map<Integer, Belt> map;
    static ArrayList<Belt> arr;
    // static Belt[] belt;
    public static void main(String[] args) throws Exception {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());
        while(Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            if(order == 100) init();
            else if(order == 200) sb.append(getOutPresents()).append("\n");
            else if(order == 300) sb.append(checkPresents(true)).append("\n");
            else if(order == 400) sb.append(checkPresents(false)).append("\n");
            else sb.append(isBrokenBelt()).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    // 1. 공장 설립
    static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        size = N/M;
        map = new HashMap<>();
        arr = new ArrayList<>();
        for(int i=0;i<M;i++) {
            Belt newBelt = new Belt(i);
            map.put(i, newBelt);
            arr.add(newBelt);
        }

        int ids[] = new int[N];
        for(int i=0;i<N;i++) ids[i] = Integer.parseInt(st.nextToken());

        for(int i=0;i<N;i++) {
            int pId = ids[i];
            int weight = Integer.parseInt(st.nextToken());
            int bId = i/size;
            arr.get(bId).addPresent(new Present(pId, weight));
        }

    }

    static class Belt {
        Present head, tail;
        int id, cnt;

        Belt(int id) {
            this.id = id;
            this.head = this.tail = null;
            this.cnt = 0;
        }

        void addPresent(Present newP) {
            if(this.head == null) {
                this.head = this.tail = newP;
                this.head.back = this.tail;
                this.tail.front = this.head;
            } else {
                newP.front = this.tail;
                this.tail.back = newP;
                this.tail = newP;
            }
            this.cnt++;
        }

        // 2. 물건 하차
        int getOutPresent(int weight) {
            if(this.cnt == 0) return 0;

            if(this.head.weight > weight) {
                this.tail.back = this.head;
                this.head.front = this.tail;
                this.tail = this.head;
                this.head = this.head.back;
                this.tail.back = null;
                return 0;
            }

            int res = head.weight;
            if(this.cnt == 1) this.head = this.tail = null;
            else {
                this.head = this.head.back;
                this.head.front = null;
            }
            this.cnt--;
            return res;
        }

        // 3. 물건 제거
        void removePresent(Present p) {
            if(this.cnt == 1) this.head = this.tail = null;
            else {
                if(p == this.head) {
                    this.head = this.head.back;
                    this.head.front = null;
                } else if(p == this.tail) {
                    this.tail = this.tail.front;
                    this.tail.back = null;
                } else {
                    Present f = p.front;
                    Present b = p.back;
                    f.back = b;
                    b.front = f;
                }
            }
            this.cnt--;
        }

        // 4. 물건 확인
        void moveFirstPresent(Present p) {
            this.head.front = this.tail;
            this.tail.back = this.head;
            this.tail = p.front;
            this.tail.back = null;
            this.head = p;
            this.head.front = null;
        }

        // 3, 4 물건 id 찾기
        Present checkPresent(int pId) {
            Present curr = this.head;
            while(curr != null) {
                if(curr.id == pId) break;
                curr = curr.back;
            }
            return curr;
        }

        // 5. 고장난 벨트의 물건 잇기
        void connect(Present newF, Present newB, int cnt) {
            this.tail.back = newF;
            newF.front = this.tail;
            this.tail = newB;
            this.cnt += cnt;
        }

        public boolean equals(Belt o) {
            return this.id == o.id;
        }
    }

    // 200. 물건 하차
    static int getOutPresents() {
        int weight = Integer.parseInt(st.nextToken());
        int res = 0;
        for(int i=0;i<arr.size();i++) {
            res += arr.get(i).getOutPresent(weight);
        }
        return res;
    }

    // 300. 물건 제거
    // 400. 물건 확인
    static int checkPresents(boolean isRemove) {
        int pId = Integer.parseInt(st.nextToken());
        for(int i=0;i<arr.size();i++) {
            Present p = arr.get(i).checkPresent(pId);
            if(p != null) {
                if(isRemove) {
                    arr.get(i).removePresent(p);
                    return pId;
                } else {
                    arr.get(i).moveFirstPresent(p);
                    return arr.get(i).id+1;
                }
            }
        }
        return -1;
    }


    // 5. 벨트 고장 여부
    static int isBrokenBelt() {
        int b_num = Integer.parseInt(st.nextToken())-1;
        if(!map.containsKey(b_num)) return -1;
        Belt target = map.get(b_num);
        int rmId = 0;
        for(int i=0;i<arr.size();i++) {
            if(arr.get(i).id == b_num) continue;
            arr.get(i).connect(target.head, target.tail, target.cnt);
            break;
        }
        map.remove(b_num);
        arr.remove(target);
        return b_num+1;
    }

    static class Present {
        int id, weight;
        Present front, back;
        Present(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }
    }
}
