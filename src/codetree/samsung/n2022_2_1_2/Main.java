package codetree.samsung.n2022_2_1_2;
// 삼성 공채 2022 하반기 오전 2번
// 시간초과.. ㅜㅜ
import java.io.*;
import java.util.*;

public class Main {
    static final int MAX_M = 10;

    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int N, M;

    // id별로 상자 무게 관리
    static Map<Integer, Integer> weight = new HashMap<>();

    // id별로 상자의 next, prev 관리, 0일경우 없다는 뜻
    static Map<Integer, Integer> prev = new HashMap<>();
    static Map<Integer, Integer> next = new HashMap<>();

    // 벨트별로 head, tail, id 관리
    // 0이면 없다는 뜻
    static int head[] = new int[MAX_M];
    static int tail[] = new int[MAX_M];

    // 벨트가 망가졌는지 확인
    static boolean broken[] = new boolean[MAX_M];

    // 물건 별로 벨트 번호 기입
    // 벨트 번호가 0이면 사라진 물건
    static Map<Integer, Integer> beltNum = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine());
        while(Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            if(order == 100) init();
            else if(order == 200) sb.append(getOutPresent()).append("\n");
            else if(order == 300) sb.append(removePresent()).append("\n");
            else if(order == 400) sb.append(checkPresent()).append("\n");
            else sb.append(brokeBelt()).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    // 1. 공장 설립
    static void init() {
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[] ids = new int[N];
        int[] ws = new int[N];

        for(int i=0;i<N;i++) ids[i] = Integer.parseInt(st.nextToken());
        for(int i=0;i<N;i++) ws[i] = Integer.parseInt(st.nextToken());

        for(int i=0;i<N;i++) weight.put(ids[i], ws[i]);

        int size = N/M;
        for(int i=0;i<M;i++) {
            // 각 벨트별로 head, tail 설정
            head[i] = ids[i*size];
            tail[i] = ids[(i+1)*size-1];

            for(int j = i*size; j<(i+1)*size;j++) {
                // 상자 ID마다 벨트 번호 기입하기
                beltNum.put(ids[j], i+1);

                // next, prev 설정하기
                if(j < (i+1) * size - 1) {
                    next.put(ids[j], ids[j+1]);
                    prev.put(ids[j+1], ids[j]);
                }
            }
        }
    }

    // id에 해당하는 상자 삭제하기
    static void removedId(int id, boolean removeBelt) {
        int bNum = beltNum.get(id)-1;

        // 벨트 제거 옵션이 true라면 벨트 제거
        if(removeBelt) beltNum.put(id, 0);

        // 벨트의 원소가 하나라면
        if(head[bNum] == tail[bNum]) head[bNum] = tail[bNum] = 0;

            // 삭제 되는게 head라면 head만 변경
        else if(id == head[bNum]) {
            int nextId = next.get(id);
            head[bNum] = nextId;
            prev.put(nextId, 0);
        }

        // 삭제 되는게 tail이라면 tail만 변경
        else if(id == tail[bNum]) {
            int prevId = prev.get(id);
            tail[bNum] = prevId;
            next.put(prevId, 0);
        }

        // 삭제 되는게 중간에 있는 것이라면 next, prev 모두 변경
        else {
            int prevId = prev.get(id), nextId = next.get(id);
            next.put(prevId, nextId);
            prev.put(nextId, prevId);
        }

        // next, prev 지우기
        next.put(id, 0);
        prev.put(id, 0);
    }

    // targetId 바로 뒤에 id 추가
    static void pushId(int targetId, int id) {
        next.put(targetId, id);
        prev.put(id, targetId);

        // targetId가 tail이었다면 id로 tail변경
        int bNum = beltNum.get(targetId)-1;
        if(tail[bNum] == targetId) tail[bNum] = id;
    }

    // 200. 물건 하차
    static int getOutPresent() {
        int mWeight = Integer.parseInt(st.nextToken());

        int res = 0;
        for(int i=0;i<M;i++) {
            // 망가진 벨트라면 넘어가기
            if(broken[i]) continue;

            // 벨트의 head 확인
            if(head[i] != 0) {
                int id = head[i];
                int w = weight.get(id);

                // 가장 앞에 있는 상자의 무게가 mWeight 이하라면 하차
                if(w <= mWeight) {
                    res += w;

                    removedId(id, true);
                } else if(next.get(id) != 0) {
                    // 그렇지 않다면 상자를 맨 뒤로 올리기

                    // 제거한 후
                    removedId(id, false);

                    // 맨 뒤에 push
                    pushId(tail[i], id);
                }
            }
        }

        return res;
    }

    // 300. 물건 제거
    static int removePresent() {
        int rId = Integer.parseInt(st.nextToken());

        // 제거되지 않은 상자라면
        if(beltNum.getOrDefault(rId, 0) != 0) {
            removedId(rId, true);
            return rId;
        }

        // 이미 제거된 상자라면 -1
        return -1;
    }


    // 400. 물건 확인
    static int checkPresent() {
        int fId = Integer.parseInt(st.nextToken());
        if(beltNum.getOrDefault(fId, 0) == 0) return -1;

        // 상자를 찾아서 맨 앞으로 당겨준다.
        // 찾은 상자가 head가 아닐 경우에만 유효
        int bNum = beltNum.get(fId)-1;
        if(head[bNum] != fId) {
            int originTail = tail[bNum];
            int originHead = head[bNum];

            // 새로운 tail 갱신
            int newTail = prev.get(fId);
            tail[bNum] = newTail;
            next.put(newTail, 0);

            // 기존 tail의 next를 head로
            // head의 prev를 기존 tail로 만들기
            next.put(originTail, originHead);
            prev.put(originHead, originTail);

            // 새로운 head 지정하기
            head[bNum] = fId;
        }

        return bNum+1;
    }


    // 5. 벨트 고장시키기
    static int brokeBelt() {
        int bNum = Integer.parseInt(st.nextToken())-1;

        if(broken[bNum]) return -1;

        broken[bNum] = true;

        if(head[bNum] == 0) return bNum+1;

        // 해당 벨트의 오른쪽으로 순회하며 망가지지 않은 벨트 위로 상자를 옮기기
        int nextNum = bNum;
        while(true) {
            nextNum = (nextNum+1)%M;

            if(!broken[nextNum]) {
                // 벨트가 비어있다면 그대로 옮겨주기
                if(tail[nextNum] == 0) {
                    head[nextNum] = head[bNum];
                    tail[nextNum] = tail[bNum];
                } else {
                    // 선택한 벨트 뒤로 모두 옮겨주기
                    pushId(tail[nextNum], head[bNum]);
                    tail[nextNum] = tail[bNum];
                }

                // head~tail까지 순회하며 beltNum 갱신
                int id = head[bNum];
                while(id != 0) {
                    beltNum.put(id, nextNum + 1);
                    id = next.getOrDefault(id, 0);
                }

                head[bNum] = tail[bNum] = 0;
                break;
            }
        }
        return bNum+1;
    }

}