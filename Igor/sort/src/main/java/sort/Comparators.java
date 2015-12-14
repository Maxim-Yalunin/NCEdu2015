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

    public static Comparator<String> getInsensitive() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        };
    }

    public static Comparator<String> getUsual() {
        return new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

}
