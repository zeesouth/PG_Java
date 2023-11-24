package test;

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=1;i<=99;i++) {
            sb.append("[");
            sb.append(i);
            sb.append(",");
            sb.append(i+1);
            sb.append("], ");
        }
        sb.append("]");
        System.out.println(sb);
    }
}
