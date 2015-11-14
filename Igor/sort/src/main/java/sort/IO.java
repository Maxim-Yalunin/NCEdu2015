package sort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igoryan on 14.11.15.
 */
public class IO {
    public static String[] readLines(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        return fromReader(bufferedReader);
    }

    private static String[] fromReader(BufferedReader bufferedReader) {
        List<String> lines = new ArrayList<String>();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }
    public static void write(OutputStream out, String[] array) {
        PrintWriter writer = new PrintWriter(out);
        for (int i = 0; i < array.length; ++i)
            writer.println(array[i]);
    }
}