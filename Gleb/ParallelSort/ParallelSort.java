package StringSort;

import java.util.*;

public class ParallelSort {//Мастер-класс, осуществляющий взаимодействие Reader'a и Sorter'ов
    private boolean PARAMETER_I = false;
    private boolean PARAMETER_U = false;
    private int PARAMETER_T = 1;// В моей реализации является наибольшим
				// допустимым числом тредов. Необходимое число
				// потоков, заведомо не большее этого параметра,
				// определяется в ходе выполнения программы.
				// Можно переделать так, чтобы это было
				// номинальным числом тредов.
    private String PARAMETER_O = null;
    private String PARAMETER_F = null;
    private int DELAY = 50;// Время ожидания между созданием тредов. Нужно,
			   // чтобы не плодить лишние, эффективно используя
			   // ядра.

    public void getParams(String[] arg) {
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
     * Функция анализирует список сортировщиков и делает вывод о том, работают
     * ли они.
     * 
     * @param list
     *            - список сортировщиков
     * @return true - если все сортировщики закончили работу
     */
    private boolean workEnded(List<Sorter> list) {
	for (int i = 0; i < list.size(); i++) {
	    if (list.get(i).hasEnded() == false)
		return false;
	}
	return true;
    }

    private static void mergeTwoLists(List<String> a, List<String> b, boolean i_c) {
	a.addAll(b);
	Collections.sort(a, new SimpleComparator(i_c));
	b.clear();// Необязательно. Смотря что важнее, скорость или память
    }

    private static void createSortedList2ndAlg(List<Sorter> list, boolean param_i) {// По-идее более быстрый алгориом, т.к. оперирует списками примерно равного размера
	int len = list.size();
	while (len > 1) {
	    for (int i = 0; i < (len - 1); i++) {
		mergeTwoLists(list.get(i).innerList, list.get(len - 1).innerList, param_i);
		len = len - 1;
	    }
	}
    }

    private static void createSortedList(List<Sorter> list, boolean param_i) {// Надёжный алгоритм
	for (int i = 1; i < list.size(); i++) {
	    mergeTwoLists(list.get(0).innerList, list.get(i).innerList, param_i);
	}
    }

    /**
     * Функция удаляет из списка неуникальные строки.
     * 
     * @param list
     *            - входной список
     */
    private void deleteDoubled(List<String> list) {
	for (int i = 0; i < (list.size() - 1); i++) {
	    if ((PARAMETER_U == true) && (PARAMETER_I == true) && (list.get(i).compareToIgnoreCase(list.get(i + 1)) == 0)) {
		list.remove(i + 1);
		i = i - 1;
	    }
	    if ((PARAMETER_U == true) && (PARAMETER_I == false) && (list.get(i).compareTo(list.get(i + 1)) == 0)) {
		list.remove(i + 1);
		i = i - 1;
	    }
	}
    }

    /**
     * Функция обрабатывает список.
     *
     * @param list
     *            - входной список
     * @param r
     *            - ридер, который будет записывать файл В ходе выполнения
     *            алгоритмов слияния list'om будет становиться внутренний список
     *            нулевого элемента списка сортировщиков.
     */
    private void processResult(List<String> list, Reader r) {
	System.out.println(list.size());
	if (PARAMETER_U == true) {
	    System.out.println("Started to delete doubled strings");
	    deleteDoubled(list);
	}
	if (PARAMETER_O == null) {
	    System.out.println(list);
	} else {
	    System.out.println("Started to save result to " + PARAMETER_O);
	    r.writeToFile(list);
	}
    }

    public ParallelSort(String[] argmnts) {
	getParams(argmnts);
	System.out.println("parameter_o = " + PARAMETER_O + " parameter_f = " + PARAMETER_F + " parameter_i = " + PARAMETER_I + " parameter_u = " + PARAMETER_U + " parameter_t = " + PARAMETER_T);
	List<Sorter> workers = new ArrayList<Sorter>();// Список сортировщиков
	Reader readr = new Reader(PARAMETER_F, PARAMETER_O);
	readr.start();
	while (true) {
	    if (((workers.size() == 0) && (readr.words.size() > 0)) || (readr.needsMoreSorters(workers.size()) && (workers.size() < PARAMETER_T))) {
		Sorter sortr = new Sorter(readr, "Worker" + workers.size(), PARAMETER_U, PARAMETER_I);
		sortr.start();
		workers.add(workers.size(), sortr);
		System.out.println("Workers' size has increased to " + workers.size());
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
	createSortedList2ndAlg(workers, PARAMETER_I);//Алгоритм создания сортированного листа - на выбор
	System.out.println(workers.get(0).innerList.size());
	processResult(workers.get(0).innerList, readr);
	System.out.println("The end.");
    }

    public static void main(String[] args) throws InterruptedException {
	new ParallelSort(args);
    }
}
