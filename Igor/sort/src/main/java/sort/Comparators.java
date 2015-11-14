package sort;

import java.util.Comparator;

/**
 * Created by igoryan on 14.11.15.
 */
public class Comparators {
    private Comparator<String> cmp;

    public Comparator<String> getCmp() {
        return cmp;
    }

    Comparators(char flag) {
        if ('i' == flag) {
            cmp = getInsensitive();
        }
        else {
            cmp = getUsual();

        }
    }

    private Comparator<String> getInsensitive() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                String s1 = o1.toLowerCase();
                String s2 = o2.toLowerCase();
                return compare(s1, s2);
            }
        };
    }

    private Comparator<String> getUsual() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                return compare(o1, o2);
            }
        };
    }

    private int compare(String s1, String s2) {
        int n = (s1.length() > s2.length()) ? s2.length() : s1.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = s2.charAt(i) - s1.charAt(i);
            if (res != 0) {
                return res;
            }
        }
        if (s2.length() < s1.length())
            return 1;
        else
            return -1;
    }
}
