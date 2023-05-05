package lv3.n77486;

import java.util.*;

class Node {
    int parent;
    String name;
    int count;
    Node (int parent, String name) {
        this.parent = parent;
        this.name = name;
        this.count = 0;
    }
}

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Node root = new Node(-1, "-");
        Node[] nodeKey = new Node[enroll.length+1];
        nodeKey[0] = root;
        Map<String, Integer> indexMap = new HashMap<>();
        indexMap.put("-", 0);
        int[] countArr = new int[enroll.length];

        for(int i=0;i<enroll.length;i++) {
            String name = enroll[i];
            int parent = indexMap.get(referral[i]);

            Node newNode = new Node(parent, name);
            nodeKey[i+1] = newNode;
            indexMap.put(name, i+1);
        }

        for(int i=0;i<seller.length;i++) {
            int currIndex = indexMap.get(seller[i]);
            int currAmount = amount[i]*100;
            while(currIndex != 0) {
                int parentCount = currAmount/10;
                int childCount = currAmount-parentCount;

                nodeKey[currIndex].count += childCount;
                countArr[currIndex-1] = nodeKey[currIndex].count;

                currIndex = nodeKey[currIndex].parent;
                currAmount = parentCount;
            }
        }

        return countArr;
    }
}
