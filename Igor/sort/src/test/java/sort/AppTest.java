package sort;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.jblas.util.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */

public class AppTest {
    private final String I = "-i";
    private final String U = "-u";
    private final String IU = "-iu";
    private final String T = "-t";
    private final String THREADS_COUNT = "4";
    private final String O = "-o";
    private final String INPUT_FILE = "wp.txt";
    private final String OUTPUT_FILE = "output.txt";
    private final String WP_ = "wp_.txt";
    private final String WP_I = "wp_i.txt";
    private final String WP_IU = "wp_iu.txt";
    private final String WP_U = "wp_u.txt";
    private ArgumentHandler handler = new ArgumentHandler();
    private App app = new App();
    private static Logger LOG = Logger.getLogger();


    @Before
    public void deleteOutputFile() {
        File out = new File(OUTPUT_FILE);
        if (out.exists()) {
            out.delete();
        }
    }

    @Test(timeout = 15000)
    public void testMain() throws IOException {
        LOG.info("test main with arguments -o wp.txt output.txt");
        String[] args = {O, OUTPUT_FILE, INPUT_FILE};
        testOutputFiles(args, WP_);
        deleteOutputFile();

        LOG.info("test main with arguments -i -o wp.txt output.txt");
        args = new String[]{I, O, OUTPUT_FILE, INPUT_FILE};
        testOutputFiles(args, WP_I);
        deleteOutputFile();

        LOG.info("test main with arguments -iu -o wp.txt output.txt");
        args = new String[]{IU, O, OUTPUT_FILE, INPUT_FILE};
        testOutputFiles(args, WP_IU);
        deleteOutputFile();

        LOG.info("test main with arguments -u -o wp.txt output.txt");
        args = new String[]{U, O, OUTPUT_FILE, INPUT_FILE};
        testOutputFiles(args, WP_U);
    }

    public void testOutputFiles(String[] args, String expectedFile) throws IOException {
        app.main(args);
        InputStream actual = new FileInputStream(OUTPUT_FILE);
        InputStream expected = new FileInputStream(expectedFile);
        String[] myArray = IO.readLines(actual);
        String[] sortedArray = IO.readLines(expected);
        assertEquals(sortedArray, myArray);
    }

    @Test
    public void testSetInput() {
        String[] args = {O, OUTPUT_FILE, INPUT_FILE};
        try {
            //check when input stream is file
            app.setCML(handler.getCommandLine(args));
            app.setInput();
            InputStream in = app.getINPUT();
            assertTrue("check input file", IOUtils.contentEquals(in, new FileInputStream(INPUT_FILE)));

            //check when input stream system.in
            args = new String[]{I};
            app.setCML(handler.getCommandLine(args));
            app.setInput();
            assertEquals(System.in, app.getINPUT());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetOutput() {
        String[] args = {O, OUTPUT_FILE, INPUT_FILE};
        try {
            //check when output stream is file
            app.setCML(handler.getCommandLine(args));
            app.setOutput();
            OutputStream out = new FileOutputStream(OUTPUT_FILE);
            assertEquals(out.getClass(), app.getOUTPUT().getClass());

            //check when output stream is System.out
            args = new String[]{IU};
            app.setCML(handler.getCommandLine(args));
            app.setOutput();
            assertEquals(System.out, app.getOUTPUT());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testsetComparator() {
        String[] args = {I};
        try {
            app.setCML(handler.getCommandLine(args));
            app.setComparator();
            assertTrue(app.getCMP().compare("TEST", "test") == 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}