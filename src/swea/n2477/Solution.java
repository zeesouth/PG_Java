package swea.n2477;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
public class Solution {
    static int T;
    static int N, M, K, A, B;
    static PriorityQueue<Desk> receptEmpty, repairEmpty;
    static PriorityQueue<Customer> receptIng, repairIng, receptWait, repairWait, customer;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/n2477.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int t=1;t<=T;t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());

            receptEmpty = new PriorityQueue<>((d1, d2) -> d1.id-d2.id);
            repairEmpty = new PriorityQueue<>((d1, d2) -> d1.id-d2.id);

            st = new StringTokenizer(br.readLine());
            for(int i=1;i<=N;i++) receptEmpty.add(new Desk(i, Integer.parseInt(st.nextToken())));

            st = new StringTokenizer(br.readLine());
            for(int i=1;i<=M;i++) repairEmpty.add(new Desk(i, Integer.parseInt(st.nextToken())));

            customer = new PriorityQueue<>((c1, c2) -> c1.time == c2.time ? c1.id-c2.id : c1.time-c2.time);
            st = new StringTokenizer(br.readLine());
            for(int i=1;i<=K;i++) customer.add(new Customer(i, Integer.parseInt(st.nextToken()), 0, 0, 0));

            sb.append("#").append(t).append(" ").append(simulate()).append("\n");
        }
        System.out.println(sb);
        br.close();
    }

    static int simulate() {
        int time = 0;
        int cnt = 0;
        int ans = 0;
        receptWait = new PriorityQueue<>((c1, c2)->c1.id - c2.id);
        receptIng = new PriorityQueue<>((c1, c2)->c1.time == c2.time ? c1.recept-c2.recept : c1.time-c2.time);
        repairWait = new PriorityQueue<>((c1, c2)->c1.time == c2.time ? c1.recept-c2.recept : c1.time-c2.time);
        repairIng = new PriorityQueue<>((c1, c2)->c1.time-c2.time);

        while(true) {

            while(!repairIng.isEmpty()) {
                if(time == repairIng.peek().time) {
                    Customer currC = repairIng.poll();
                    if(currC.recept == A && currC.repair == B) ans += currC.id;
                    repairEmpty.add(new Desk(currC.repair, currC.ing));
                    cnt++;
                } else break;
            }

            while(!repairWait.isEmpty()) {
                if(repairIng.size() < M) {
                    Customer currC = repairWait.poll();
                    Desk currD = repairEmpty.poll();
                    repairIng.add(new Customer(currC.id, time+currD.time, currC.recept, currD.id, currD.time));
                } else break;
            }

            while(!receptIng.isEmpty()) {
                if(time == receptIng.peek().time) {
                    Customer currC = receptIng.poll();
                    if(repairIng.size() < M) {
                        Desk currD = repairEmpty.poll();
                        repairIng.add(new Customer(currC.id, time+currD.time, currC.recept, currD.id, currD.time));
                    } else repairWait.add(currC);
                    receptEmpty.add(new Desk(currC.recept, currC.ing));
                } else break;
            }

            while(!receptWait.isEmpty()) {
                if(receptIng.size() < N) {
                    Customer currC = receptWait.poll();
                    Desk currD = receptEmpty.poll();
                    receptIng.add(new Customer(currC.id, time+currD.time, currD.id, 0, currD.time));
                } else break;
            }

            while(!customer.isEmpty()) {
                if(time == customer.peek().time) {
                    Customer currC = customer.poll();
                    if(receptIng.size() < N) {
                        Desk currD = receptEmpty.poll();
                        receptIng.add(new Customer(currC.id, time+currD.time, currD.id, 0, currD.time));
                    } else receptWait.add(currC);
                } else break;
            }

            time++;

            if(cnt == K) break;
        }

        return ans == 0 ? -1 : ans;
    }

    static class Desk {
        int id, time;
        Desk(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    static class Customer {
        int id, time, recept, repair, ing;
        Customer(int id, int time, int recept, int repair, int ing) {
            this.id = id;
            this.time = time;
            this.recept = recept;
            this.repair = repair;
            this.ing = ing;
        }
    }
}
