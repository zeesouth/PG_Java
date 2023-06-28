package programmers.lv3.n43164;
import java.util.*;
class Solution {
    boolean[] visited;  // 티켓 사용 유무
    ArrayList<String> ans = new ArrayList<>();
    public String[] solution(String[][] tickets) {
        visited = new boolean[tickets.length];
        dfs(tickets, "ICN", 0, "ICN");
        // 티켓 정렬
        Collections.sort(ans);

        return ans.get(0).split(" ");
    }

    void dfs(String[][] tickets, String curr, int cnt, String path) {
        // 사용한 티켓 수(cnt)와 전체 티켓 수가 같아진 경우
        if(cnt == tickets.length) {
            ans.add(path);
            return;
        }

        for(int i=0;i<tickets.length;i++) {
            // 티켓을 사용하지 않았고 해당 티켓의 출발지가 현재 위치와 같은 경우
            if(!visited[i] && curr.equals(tickets[i][0])) {
                // 티켓을 사용하고 이동
                visited[i] = true;
                dfs(tickets, tickets[i][1], cnt+1, path+" "+tickets[i][1]);
                // 티켓 사용 해제 (다른 경로로 모든 티켓을 사용할 가능성이 있기 때문)
                visited[i] = false;
            }

        }
    }
}
/*
<핵심>
1. 가능한 경로가 2개 이상 나오는 경우,
'알파벳 오름차순으로 정렬'하기 쉽게 하기 위해 이동 경로를 String 타입으로 저장
2. 티켓은 빠짐없이 모두 사용해야 하므로 visited[] 배열과 cnt변수를 사용해서 티켓 사용 유무를 체크
3. dfs 탐색 이후, 다른 경로로도 빠질 수 있어야 하기 때문에 dfs 호출 이후 방문 여부(visited)를 다시 false로 바꿔주기
 */
