package test;

import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (o1, o2) -> o2 - o1
        );
        pq.add(1);
        pq.add(2);
        System.out.println(pq.poll());
    }
}
