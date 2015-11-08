package StringSort;

import java.io.*;
import java.util.*;

class Reader extends Thread {//Класс, читающий из файла\консоли и пишущий в файл\консоль
    private boolean isWorking;// работает ли ридер в данный момент
    private String STRING_TO_INPUT;// путь к файлу ввода
    private String STRING_TO_OUTPUT;// путь к файлу выхода
    public List<String> words;// внутренний список
    private int amount = 0;// суммарное количество слов

    public boolean isWorking() {
	return isWorking;
    }

    public int amount() {
	return amount;
    }

    public Reader(String input, String output) {
	STRING_TO_INPUT = input;
	STRING_TO_OUTPUT = output;
	words = Collections.synchronizedList(new ArrayList<String>());
    }

    public void readFile() {
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(STRING_TO_INPUT);
	} catch (FileNotFoundException e) {
	    System.out.println("Can't locate input file");
	    isWorking = false;
	}

	Scanner scanner = new Scanner(fis, "UTF-8");
	isWorking = true;
	while (scanner.hasNext()) {
	    words.add(scanner.next());
	    amount += 1;
	}
	System.out.println("That was my last word");// Слова закончились
	scanner.close();
	isWorking = false;
    }

    void readConsole() {
	String buffer = new String();
	Scanner scanner = new Scanner(System.in);
	isWorking = true;
	System.out.println("Remember, the stopword is THEEND");
	while (scanner.hasNext()) {
	    buffer = scanner.next();
	    if (buffer.equals("THEEND")) {
		break;
	    }
	    words.add(buffer);
	    amount += 1;
	}
	scanner.close();
	isWorking = false;
    }

    public void writeToFile(List<String> list) {
	Writer writer = null;
	try {
	    writer = new FileWriter(STRING_TO_OUTPUT);
	    for (String line : list) {
		writer.write(line);
		writer.write(System.getProperty("line.separator"));
	    }
	    writer.flush();
	} catch (Exception e) {
	    System.out.println("Can't write to " + STRING_TO_OUTPUT);
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException ex) {
		}
	    }
	}
    }

    /**
     * @return true - если ридер прекратил работу и сортеры забрали все слова у
     *         него из списка.
     */
    public boolean hasNoMoreWords() {
	if ((isWorking == false) && (words.size() == 0))
	    return true;
	return false;
    }

    /**
     * @param current_num
     *            - количество сортировщиков, работающих на этот ридер в данный
     *            момент
     * @return true - если количество сортировщиков недостаточно.
     */
    public boolean needsMoreSorters(int current_num) {
	return (words.size() > (amount / (current_num + 2)));
    }

    @Override
    public void run() {
	if (STRING_TO_INPUT == null) {
	    readConsole();
	} else {
	    readFile();
	}
    }

}
