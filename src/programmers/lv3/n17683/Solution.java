package programmers.lv3.n17683;

class Solution {
    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";
        int play = 0;

        String nm = getPattern(m);

        for (int i = 0; i < musicinfos.length; i++) {
            String split[] = musicinfos[i].split(",");

            String time1 = split[0];
            String time2 = split[1];
            String name = split[2];
            String pattern = getPattern(split[3]);

            int time = getTime(time1, time2);
            StringBuilder np = new StringBuilder();
            if (pattern.length() > time) {
                np.append(pattern.substring(0, time));
            } else {
                for (int j = 0; j < time / pattern.length(); j++) {
                    np.append(pattern);
                }
                np.append(pattern.substring(0, time % pattern.length()));
            }

            if (!np.toString().contains(nm)) continue;

            if (play < time) {
                play = time;
                answer = name;
            }
        }

        return answer;
    }

    int getTime(String t1, String t2) {
        String tt1[] = t1.split(":");
        String tt2[] = t2.split(":");

        int h1 = Integer.parseInt(tt1[0]);
        int m1 = Integer.parseInt(tt1[1]);

        int h2 = Integer.parseInt(tt2[0]);
        int m2 = Integer.parseInt(tt2[1]);

        return (h2 * 60 + m2) - (h1 * 60 + m1);
    }

    String getPattern(String p) {
        int i = 0;
        StringBuilder mp = new StringBuilder();

        while (i < p.length()) {
            char c1 = p.charAt(i);

            i++;
            if (i < p.length()) {
                char c2 = p.charAt(i);
                if (c2 == '#') {
                    c1 += 6;
                    i++;
                }
            }

            mp.append(c1);
        }

        return mp.toString();
    }
}
