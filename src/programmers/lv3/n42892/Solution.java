package programmers.lv3.n42892;
import java.util.*;
class Node {
    int data, x, y;
    Node left, right;
    Node(int data, int x, int y) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.left = null;
        this.right = null;
    }
}
class Solution {
    int idx;
    int[] pre, post;
    public int[][] solution(int[][] nodeinfo) {
        // 트리 배열 생성 후 정렬 y큰->x작
        ArrayList<Node> tree = new ArrayList<>();
        for(int i=0;i<nodeinfo.length;i++)
            tree.add(new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]));
        Collections.sort(tree, (o1, o2)->o1.y == o2.y ? o1.x-o2.x : o2.y-o1.y);
        Node root = tree.get(0);
        for(int i=1;i<tree.size();i++) makeTree(root, tree.get(i));


        idx = 0;
        pre = new int[nodeinfo.length];
        preOrder(root);

        idx = 0;
        post = new int[nodeinfo.length];
        postOrder(root);


        int[][] answer = {pre, post};
        return answer;
    }

    // 트리 만들기
    void makeTree(Node parent, Node child) {
        if(parent.x > child.x) {
            if(parent.left == null) parent.left = child;
            else makeTree(parent.left, child);
        } else {
            if(parent.right == null) parent.right = child;
            else makeTree(parent.right, child);
        }
    }

    // 전위순회
    void preOrder(Node curr) {
        pre[idx++] = curr.data;
        if(curr.left != null) preOrder(curr.left);
        if(curr.right != null) preOrder(curr.right);
    }

    // 후위순회
    void postOrder(Node curr) {
        if(curr.left != null) postOrder(curr.left);
        if(curr.right != null) postOrder(curr.right);
        post[idx++] = curr.data;
    }
}

