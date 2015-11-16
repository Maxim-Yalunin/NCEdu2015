package sort;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    private static CommandLine CML;
    private static InputStream INPUT;
    private static OutputStream OUTPUT;
    private static Comparator<String> CMP;
    private static int THREADS_COUNT = 4;
    private static String[] ARRAY;
    private static String[] ARGS;

    public static void main(String[] args) {
        ARGS = args;
        ArgumentHandler arguments = new ArgumentHandler();
        try {
            CML = arguments.getCommandLine(args);
            // parsing arguments
            setInput();
            setOutput();
            setComparator();
            setThreadsCount();
            ARRAY = IO.readLines(INPUT);
            Sorter sorter = new Sorter(THREADS_COUNT, CMP, ARRAY);
            ARRAY = sorter.sort();
            IO.write(OUTPUT, ARRAY);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setInput() throws FileNotFoundException {
        if (CML.hasOption('o')) {
            INPUT = new FileInputStream(CML.getOptionValues('o')[1]);
        } else {
            INPUT = System.in;
        }
    }

    public static void setOutput() throws FileNotFoundException {
        if (CML.hasOption('o')) {
            OUTPUT = new FileOutputStream(CML.getOptionValues('o')[0]);
        } else {
            OUTPUT = System.out;
        }
    }

    public static void setComparator() {
        if (CML.hasOption('i')) {
            CMP = new Comparators('i').getCmp();
        } else {
            CMP = new Comparators().getCmp();
        }
    }

    public static void setThreadsCount() {
        if (CML.hasOption('t')) {
            THREADS_COUNT = Integer.parseInt(CML.getOptionValue('t'));
        }
    }

    public static void checkUniqueFlag() {
        if (CML.hasOption('u')) {
            ARRAY = unique(ARRAY);
        }
    }

    public static String[] unique(String[] array) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < array.length; ++i) {
            set.add(array[i]);
        }
        return set.toArray(new String[set.size()]);
    }

    public static CommandLine getCML() {
        return CML;
    }

    public static OutputStream getOUTPUT() {
        return OUTPUT;
    }

    public static InputStream getINPUT() {
        return INPUT;
    }

    public static Comparator<String> getCMP() {
        return CMP;
    }

    public static int getThreadsCount() {
        return THREADS_COUNT;
    }

    public static String[] getARRAY() {
        return ARRAY;
    }

    public static void setCML(CommandLine CML) {
        App.CML = CML;
    }
}
