package sort;

import org.jblas.util.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by igoryan on 15.11.15.
 */
public class TestSorter {
    private String[] sortedArray = {"aaaaaaa", "bbbbbbbb", "cccccccccc", "dddddddd", "eeee", "fffff", "ggggggg"};
    private String[] array = {"bbbbbbbb", "eeee", "aaaaaaa", "fffff", "cccccccccc", "ggggggg", "dddddddd"};
    private Comparator<String> cmp = new Comparators().getCmp();
    private int threadsCount;
    private Sorter sorter;
    private List<String[]> list;
    private static Logger LOG = Logger.getLogger();

    @Before
    public void getList() throws InterruptedException {
        threadsCount = 3;
        sorter = new Sorter(threadsCount, cmp, array);
        list = sorter.divideAndSort();
    }

    @Test(timeout = 7000)
    public void testSort() {
        Sorter sorter = new Sorter(1, new Comparators().getCmp(), array);
        try {
            array = sorter.sort();
            assertTrue(array.length == sortedArray.length);
            assertEquals(sortedArray, array);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testdivideAndSort() throws InterruptedException {
        int length = 0;
        //check arrays
        for (String[] array : list) {
            length += array.length;
            String[] sorted = Arrays.copyOf(array, array.length);
            Arrays.sort(sorted, cmp);
            for (int i = 0; i < array.length; ++i) {
                assertEquals(sorted[i], array[i]);
            }
        }
        //check length
        assertTrue("different length", length == array.length);
    }

    @Test
    public void testMergeTwoArrays() {
        String[] array1 = {"a", "k", "l", "m"};
        String[] array2 = {"aa", "e", "f", "g"};
        String[] result = new String[array1.length + array2.length];
        String[] must = {"a", "aa", "e", "f", "g", "k", "l", "m"};
        sorter.mergeTwoArrays(array1, array2, result);
        assertEquals(must, result);
    }

    @Test(timeout = 5000)
    public void testMerge() throws InterruptedException {
        String[] result = sorter.merge(list);
        assertTrue("result array and first length is different", result.length == array.length);
        assertEquals(result, sortedArray);
    }
}
