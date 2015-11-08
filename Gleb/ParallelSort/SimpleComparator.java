package StringSort;

import java.util.*;

class SimpleComparator implements Comparator<String> {
    private boolean IGNORE_CASE;// Игнорируем ли регистр

    SimpleComparator(boolean i_c) {
	IGNORE_CASE = i_c;
    }

    @Override
    public int compare(String arg0, String arg1) {
	if (IGNORE_CASE)
	    return arg0.compareToIgnoreCase(arg1);
	else
	    return arg0.compareTo(arg1);
    }
}
