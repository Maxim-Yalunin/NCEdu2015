
import java.util.*;

class SimpleComparator implements Comparator<String> {
    private boolean IGNORE_CASE;// Игнорируем ли регистр

    SimpleComparator(boolean i_c) {
	IGNORE_CASE = i_c;
    }

    public int compare(String arg0, String arg1) {
	if (IGNORE_CASE)
	    return arg0.compareToIgnoreCase(arg1);
	else
	    return arg0.compareTo(arg1);
    }

    public void deleteDoubledFromList(List<String> list) {
	for (int i = 0; i < (list.size() - 1); i++) {
	    if ((IGNORE_CASE == true) && (list.get(i).compareToIgnoreCase(list.get(i + 1)) == 0)) {
		list.remove(i + 1);
		i -= 1;
	    }
	    if ((IGNORE_CASE == false) && (list.get(i).compareTo(list.get(i + 1)) == 0)) {
		list.remove(i + 1);
		i -= 1;
	    }
	}
    }
}
