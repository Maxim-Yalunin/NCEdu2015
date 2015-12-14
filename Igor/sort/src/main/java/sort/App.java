package sort;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    private static CommandLine cml;
    private static InputStream input;
    private static OutputStream output;
    private static Comparator<String> cmp;
    private static int threads_count = 4;
    private static String[] array;
    private static String[] args;

    public static void main(String[] args) {
        App.args = args;
        ArgumentHandler arguments = new ArgumentHandler();
        try {
            cml = arguments.getCommandLine(args);
            // parsing arguments
            setInput();
            setOutput();
            setComparator();
            setThreadsCount();
            array = IO.readLines(input);
            input.close();
            checkUniqueFlag();
            Sorter sorter = new Sorter(threads_count, cmp, array);
            array = sorter.sort();
            IO.write(output, array);
            output.close();

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
        if (cml.hasOption('o')) {
            input = new FileInputStream(cml.getOptionValues('o')[1]);
        } else {
            input = System.in;
        }
    }

    public static void setOutput() throws FileNotFoundException {
        if (cml.hasOption('o')) {
            output = new FileOutputStream(cml.getOptionValues('o')[0]);
        } else {
            output = System.out;
        }
    }

    public static void setComparator() {
        if (cml.hasOption('i')) {
            cmp = new Comparators('i').getCmp();
        } else {
            cmp = new Comparators().getCmp();
        }
    }

    public static void setThreadsCount() {
        if (cml.hasOption('t')) {
            threads_count = Integer.parseInt(cml.getOptionValue('t'));
        }
    }

    public static void checkUniqueFlag() {
        if (cml.hasOption('u')) {
            if (cml.hasOption('i')) {
                array = unique(array, Comparators.getInsensitive());
            }
            else {
                array = unique(array, Comparators.getUsual());
            }
        }
    }

    public static String[] unique(String[] array, Comparator<String> cmp) {
        Set<String> set = new TreeSet<String>(cmp);
        for (int i = 0; i < array.length; ++i) {
            set.add(array[i]);
        }
        return set.toArray(new String[set.size()]);
    }

    public static CommandLine getCML() {
        return cml;
    }

    public static OutputStream getOutput() {
        return output;
    }

    public static InputStream getInput() {
        return input;
    }

    public static Comparator<String> getCmp() {
        return cmp;
    }

    public static int getThreads_count() {
        return threads_count;
    }

    public static String[] getArray() {
        return array;
    }

    public static void setCML(CommandLine CML) {
        App.cml = CML;
    }
}
