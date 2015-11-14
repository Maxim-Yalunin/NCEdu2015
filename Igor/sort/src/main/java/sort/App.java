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

    public static void main(String[] args) {
        ArgumentHandler arguments = new ArgumentHandler();
        try {
            CommandLine cml = arguments.getCommandLine(args);
            // parsing arguments
            InputStream input;
            OutputStream output;

            if (cml.hasOption(' ')) {
                input = new FileInputStream(cml.getOptionValue(' '));
            } else {
                input = System.in;
            }

            if (cml.hasOption('o')) {
                output = new FileOutputStream(cml.getOptionValue('o'));
            } else {
                output = System.out;
            }

            Comparator<String> cmp;
            if (cml.hasOption('i')) {
                cmp = new Comparators('i').getCmp();
            } else {
                cmp = new Comparators('n').getCmp();
            }

            int threadsCount = 4;
            if (cml.hasOption('t')) {
                threadsCount = Integer.parseInt(cml.getOptionValue('t'));
            }

            String[] array = IO.readLines(input);

            if (cml.hasOption('u')) {
                array = unique(array);
            }

            Sorter sorter = new Sorter(threadsCount, cmp, array);
            array = sorter.sort();
            IO.write(output, array);

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String[] unique(String[] array) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < array.length; ++i) {
            set.add(array[i]);
        }
        return set.toArray(new String[set.size()]);
    }
}
