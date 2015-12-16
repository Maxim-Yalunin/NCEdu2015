import java.util.*;
import java.util.logging.Logger;

public class ParallelSort {// ������-�����, �������������� �������������� Reader'a � Sorter'��
    Logger logger = null;
    private boolean PARAMETER_I = false;

    public boolean isPARAMETER_I() {
	return PARAMETER_I;
    }

    public boolean isPARAMETER_U() {
	return PARAMETER_U;
    }

    public int getPARAMETER_T() {
	return PARAMETER_T;
    }

    public String getPARAMETER_O() {
	return PARAMETER_O;
    }

    public String getPARAMETER_F() {
	return PARAMETER_F;
    }

    private boolean PARAMETER_U = false;
    // � ���� ���������� �������� T ����� ����������
    // ���������� ������ ������. ����������� �����
    // �������, �������� �� ������� ����� ���������,
    // ������������ � ���� ���������� ���������.
    // ����� ���������� ���, ����� ��� ����
    // ����������� ������ ������.
    private int PARAMETER_T = 1;
    private String PARAMETER_O = null;
    private String PARAMETER_F = null;
    // ����� �������� ����� ��������� ������. �����,
    // ����� ����� ��������� ����� �� ���������� ��
    // ������� ������ ������, ���������� ��������� ����.
    private int DELAY = 50;

    public void setParams(String[] arg) {
	for (int i = 0; i < arg.length; i++) {

	    if (arg[i].equals("-u")) {
		PARAMETER_U = true;
	    }
	    if (arg[i].equals("-i")) {
		PARAMETER_I = true;
	    }
	    if ((arg[i].equals("-iu")) || (arg[i].equals("ui"))) {
		PARAMETER_U = true;
		PARAMETER_I = true;
	    }

	    if (arg[i].equals("-t")) {
		PARAMETER_T = Integer.parseInt(arg[i + 1]);
	    }
	    if (arg[i].equals("-o")) {
		PARAMETER_O = new String(arg[i + 1]);
	    }
	    if (arg[i].equals("-f")) {
		PARAMETER_F = new String(arg[i + 1]);
	    }
	}
    }

    /**
     * ������� ����������� ������ ������������� � ������ ����� � ���, ��������� �� ��� ����������.
     * @param list-������ �������������
     * @return true-���� ��� ������������ ��������� ������
     */
    private boolean workEnded(List<Sorter> list) {
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i).hasEnded() == false)
		return false;
	}
	return true;
    }
    /**
     * ������� ���������� ������ a � b.
     * @param a - ������, ���� ����� ������� ���������
     * @param b - ������
     * @param i_c - ������������ �� ������� ��� ���������.
     */
    static void mergeTwoLists(List<String> a, List<String> b, boolean i_c) {
	a.addAll(b);
	Collections.sort(a, new SimpleComparator(i_c));
	b.clear();//�������������. ������ ��� ������, �������� ��� ������
    }

    // ����� ������� ��������,
    // �.�. ��������� ��������
    // �������� ������� �������
    /**
     * ������� ��������� ������ ��������� �������������. ��������� ����� ������� �� ���������� ������ �������� �������� �� list.
     * @param list - ������ �������������.
     * @param i - ������������ �� ������� ��� ����������.
     */
    private static void createSortedList2ndAlg(List<Sorter> list, boolean param_i) {// ��-����

	int len = list.size();
	while (len > 1) {
	    for (int i = 0; i < (len - 1); i++) {
		mergeTwoLists(list.get(i).innerList, list.get(len - 1).innerList, param_i);
		len = len - 1;
	    }
	}
    }

    /**
     * ������� ������������ ������, ������� ��� � ��������� ����.
     * @param list-������� ������
     * @param r-�����, ������� ����� ���������� ����. � ���� ����������
     * ���������� ������� list'om ������ ���������� ������ ��������
     * �������� �� ������ �������������.
     */
    private void processResult(List<String> list, Reader r) {
	if (PARAMETER_U == true) {
	    // deleteDoubled(list);
	    SimpleComparator sc = new SimpleComparator(PARAMETER_U);
	    sc.deleteDoubledFromList(list);
	}
	if (PARAMETER_O == null) {
	    System.out.println(list);
	} else {
	    r.writeToFile(list);
	}
    }

    /**
     * ������� ��������� ������ ������ ������.
     */
    public void doAllStuff() {
	List<Sorter> workers = new ArrayList<Sorter>();// ������ �������������
	Reader readr = new Reader(PARAMETER_F, PARAMETER_O);
	readr.start();
	while (true) {
	    if (((workers.size() == 0) && (readr.words.size() > 0)) || (readr.needsMoreSorters(workers.size()) && (workers.size() < PARAMETER_T))) {
		Sorter sortr = new Sorter(readr, "Worker" + workers.size(), PARAMETER_U, PARAMETER_I);
		sortr.start();
		workers.add(workers.size(), sortr);
		try {
		    Thread.sleep(DELAY);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    if ((workers.size() > 0) && (readr.hasNoMoreWords()) && (workEnded(workers))) {// ������
		// ������
		// ���
		break;
	    }
	}
	// createSortedList(workers, PARAMETER_I);
	createSortedList2ndAlg(workers, PARAMETER_I);
	processResult(workers.get(0).innerList, readr);
    }

    public ParallelSort(String[] argmnts) {
	setParams(argmnts);
    }

    public static void main(String[] args) throws InterruptedException {
	//String[] args2={"-t","15", "-i", "-f","D:\\wp.txt", "-o", "D:\\result_iu_15.txt"};
	ParallelSort proxy = new ParallelSort(args);
	proxy.doAllStuff();
    }
}
