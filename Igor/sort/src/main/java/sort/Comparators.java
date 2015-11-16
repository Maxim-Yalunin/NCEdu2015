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
    }

    Comparators() {
        cmp = getUsual();
    }

    private Comparator<String> getInsensitive() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                int n = (o1.length() > o2.length()) ? o2.length() : o1.length();
                int res = 0;
                for (int i = 0; i < n; i++) {
                    res = Character.toLowerCase(o1.charAt(i)) - Character.toLowerCase(o2.charAt(i));
                    if (res != 0) {
                        return res;
                    }
                }
                if (o1.length() < o2.length())
                    return -1;
                if (o2.length() < o1.length())
                    return 1;
                return 0;

            }
        };
    }

    private Comparator<String> getUsual() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                int n = (o1.length() > o2.length()) ? o2.length() : o1.length();
                int res = 0;
                for (int i = 0; i < n; i++) {
                    res = o1.charAt(i) - o2.charAt(i);
                    if (res != 0) {
                        return res;
                    }
                }
                if (o1.length() < o2.length())
                    return -1;
                if (o2.length() < o1.length())
                    return 1;
                return 0;
            }
        };
    }

}
