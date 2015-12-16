
import java.util.*;

class Sorter extends Thread {// �����, ����������� ��, ��� ������ Reader

    private Reader reader;// �����, � �������� �������� ������ �����������
    public List<String> innerList;// ���������� ����, ���� ���������� ����� ��
    // ����� ������.
    private boolean PARAMETER_U;
    private boolean PARAMETER_I;
    private boolean isWorking;// �������� ��� ������� ����� ������
    private static int DELAY = 100;// ����� �������� � ������ �������� �������
    // ����������� ������ ����������� ������.
    // [ms]
    private boolean hasEnded = false;// �������� �� ������ ���������. �����

    // �������� ������ ����� ������������,
    // ����� ����, ��� this.hasEnded=true -
    // ���.

    public boolean isWorking() {
	return isWorking;
    }

    public boolean hasEnded() {// 1 �� 2-�!
	return hasEnded;
    }

    public Sorter(Reader read, String name, boolean param_u, boolean param_i) {
	reader = read;
	innerList = new ArrayList<String>();// ����� ���� �� ���, � ������ u
	setName(name);
	PARAMETER_U = param_u;
	PARAMETER_I = param_i;
    }

    /**
     * ������� ������� �� ����������� ������ ������������ ������������ ������.
     */
  //  public void deleteDoubledFromSublist() {
//	for (int i = 0; i < (innerList.size() - 1); i++) {
//	    if ((PARAMETER_I == true) && (innerList.get(i).compareToIgnoreCase(innerList.get(i + 1)) == 0)) {
//		innerList.remove(i + 1);
//		i -= 1;
//	    }
//	    if ((PARAMETER_I == false) && (innerList.get(i).compareTo(innerList.get(i + 1)) == 0)) {
//		innerList.remove(i + 1);
//		i -= 1;
//	    }
//	}
//    }

    @Override
    public void run() {
	String buff;
	// ��������, �� ������ �� ��� ������ ����
	if (reader.hasNoMoreWords()) {
	    isWorking = false;
	    hasEnded = true;
	}
	// ����, � ������� ����������� �������� ����� �� ����������� ������ ������.
	while (true) {
	    if (reader.words.size() > 0) {
		try {
		    buff = reader.words.remove(0);
		    innerList.add(buff);
		    isWorking = true;
		} catch (IndexOutOfBoundsException e) {
		    isWorking = false;
		    if (reader.hasNoMoreWords()) {
			hasEnded = true;
			break;
		    }
		}
	    } else {
		if (!reader.hasNoMoreWords()) {
		    isWorking = false;
		    try {
			Thread.sleep(DELAY);
			// ���� ����� ��� ��������, �� ���� �
			// ��� ������ ���. �����
			// ���������, ���� ����� �������
			// ������� ������� ���������� �������������.
			// ��-�������� � ����� ������ ������
			// ������������ ����� �������, �� ���
			// ���� ��� ���� ���� ����������� ������.
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		} else {
		    SimpleComparator sc = new SimpleComparator(PARAMETER_I);
		    Collections.sort(innerList, sc);
		    if (PARAMETER_U == true) {
			sc.deleteDoubledFromList(innerList);
		    }
		    isWorking = false;
		    hasEnded = true;
		    break;
		}

	    }

	}
    }

}
