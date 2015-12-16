import java.util.*;
import java.util.logging.Logger;

public class ParallelSort {// Мастер-класс, осуществляющий взаимодействие Reader'a и Sorter'ов
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
    // В моей реализации параметр T задаёт наибольшее
    // допустимое числом тредов. Необходимое число
    // потоков, заведомо не большее этого параметра,
    // определяется в ходе выполнения программы.
    // Можно переделать так, чтобы это было
    // номинальным числом тредов.
    private int PARAMETER_T = 1;
    private String PARAMETER_O = null;
    private String PARAMETER_F = null;
    // Время ожидания между созданием тредов. Нужно,
    // чтобы давая программе время на релаксацию не
    // плодить лишние потоки, эффективно используя ядра.
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
     * Функция анализирует список сортировщиков и делает вывод о том, закончили ли они выполнение.
     * @param list-список сортировщиков
     * @return true-если все сортировщики закончили работу
     */
    private boolean workEnded(List<Sorter> list) {
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i).hasEnded() == false)
		return false;
	}
	return true;
    }
    /**
     * Функция объединяет списки a и b.
     * @param a - список, куда будет записан результат
     * @param b - список
     * @param i_c - игнорировать ли регистр при сравнении.
     */
    static void mergeTwoLists(List<String> a, List<String> b, boolean i_c) {
	a.addAll(b);
	Collections.sort(a, new SimpleComparator(i_c));
	b.clear();//Необязательно. Смотря что важнее, скорость или память
    }

    // более быстрый алгоритм,
    // т.к. оперирует списками
    // примерно равного размера
    /**
     * Функция спешивает списки различных сортировщиков. Результат будет записан во внутренний список нулевого элемента из list.
     * @param list - список сортировщиков.
     * @param i - игнорировать ли регистр при смешивании.
     */
    private static void createSortedList2ndAlg(List<Sorter> list, boolean param_i) {// По-идее

	int len = list.size();
	while (len > 1) {
	    for (int i = 0; i < (len - 1); i++) {
		mergeTwoLists(list.get(i).innerList, list.get(len - 1).innerList, param_i);
		len = len - 1;
	    }
	}
    }

    /**
     * Функция обрабатывает список, приводя его к желаемому виду.
     * @param list-входной список
     * @param r-ридер, который будет записывать файл. В ходе выполнения
     * алгоритмов слияния list'om станет внутренний список нулевого
     * элемента из списка сортировщиков.
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
     * Функция запускает логику работы класса.
     */
    public void doAllStuff() {
	List<Sorter> workers = new ArrayList<Sorter>();// Список сортировщиков
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
	    if ((workers.size() > 0) && (readr.hasNoMoreWords()) && (workEnded(workers))) {// Работы
		// больше
		// нет
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
