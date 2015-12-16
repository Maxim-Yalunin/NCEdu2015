
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Reader extends Thread {// �����, �������� �� �����\������� � ������� � ����\�������
    private boolean isWorking;// �������� �� ����� � ������ ������
    private String PATH_TO_INPUT;// ���� � ����� �����
    private String PATH_TO_OUTPUT;// ���� � ����� ������
    public List<String> words;// ���������� ������
    private int amount = 0;// ��������� ���������� ����

    public boolean isWorking() {
	return isWorking;
    }

    public void setWorkingStatus(boolean b) {
	isWorking = b;
    }

    public int amount() {
	return amount;
    }

    public Reader(String input, String output) {
	PATH_TO_INPUT = input;
	PATH_TO_OUTPUT = output;
	words = Collections.synchronizedList(new ArrayList<String>());
    }
    /**
     * �������, ��������� �� ��������� ������ �������� ���� "!
     * ����� � �������� ��� ����, ����� ��������, ��� � ���� ������������ ����������� �����������.
     */
    static boolean isNotWord(String s) {
	Pattern p = Pattern.compile("^\"[.!?\"]{1,3}$");
	Matcher m = p.matcher(s);
	return m.matches();
    }

    public void readFile() {
	String buffer = new String();
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(PATH_TO_INPUT);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    isWorking = false;
	}

	Scanner scanner = new Scanner(fis, "UTF-8");
	isWorking = true;
	while (scanner.hasNext()) {
	    buffer = scanner.next();
	    if (!isNotWord(buffer)) {
		words.add(buffer);
		amount += 1;
	    }
	}
	scanner.close();
	isWorking = false;
    }

    void readConsole() {
	String buffer = new String();
	Scanner scanner = new Scanner(System.in);
	isWorking = true;
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
	    writer = new FileWriter(PATH_TO_OUTPUT);
	    for (String line : list) {
		writer.write(line);
		writer.write(System.getProperty("line.separator"));
	    }
	    writer.flush();
	} catch (Exception e) {
	    e.printStackTrace();
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
     * @return true - ���� ����� ��������� ������ � ������� ������� ��� ����� � ���� �� ������.
     */
    public boolean hasNoMoreWords() {
	if ((isWorking == false) && (words.size() == 0))
	    return true;
	else
	    return false;
    }

    /**
     * ������� ���������, ����������� �� � ������ ������ ������������.
     * @param current_num - ���������� �������������, ���������� �� ���� ����� � ������ ������
     * @return true - ���� ���������� ������������� ������������.
     */
    public boolean needsMoreSorters(int current_num) {
	return (words.size() > (amount / (current_num + 2)));
    }

    @Override
    public void run() {
	if (PATH_TO_INPUT == null) {
	    readConsole();
	} else {
	    readFile();
	}
    }

}
