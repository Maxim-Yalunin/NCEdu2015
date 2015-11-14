package sort;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by igoryan on 14.11.15.
 */
public class Sorter {
    private String[] data;
    private Comparator<String> cmp;
    private final int threadsCount;

    public Sorter(int threadsCount, Comparator<String> cmp, String[] data) {
        this.threadsCount = threadsCount;
        this.cmp = cmp;
        this.data = data;
    }

    public String[] sort() throws InterruptedException {
        int size = data.length;
        if (size > threadsCount) {
            int step = size / threadsCount;
            MyThread[] threads = new MyThread[threadsCount];
            List<String[]> arrays = new LinkedList<String[]>();
            for (int i = 0; i < threadsCount; i++) {
                int begin = i * step;
                int end = (i == threadsCount - 1) ? size - 1 : (i + 1) * step - 1;
                String[] array = Arrays.copyOfRange(data, begin, end);
                arrays.add(array);
                MyThread thread = new MyThread(array);
                thread.run();
                threads[i] = thread;
            }
            for (MyThread thread : threads) {
                thread.join();
            }
            return merge(arrays);
        }
        Arrays.sort(data, cmp);
        return data;
    }

    class MyThread extends Thread {
        private String[] array;

        MyThread(String[] array) {
            super();
            this.array = array;
        }

        @Override
        public void run() {
            Arrays.sort(array, cmp);
        }
    }

    private String[] merge(List<String[]> arrays) throws InterruptedException {
        if (arrays.size() == 1) {
            return arrays.get(0);
        }
        do {
            arrays = mergeInList(arrays);
        }
        while (arrays.size() != 1);
        return arrays.get(0);
    }

    private List<String[]> mergeInList(List<String[]> arrays) throws InterruptedException {
        List<String[]> res = new LinkedList<String[]>();
        List<ArraysMerger> threads = new LinkedList<ArraysMerger>();
        int index = 0;
        while (index < arrays.size()) {
            if ((index + 1) < arrays.size()) {
                String[] array1 = arrays.get(index);
                String[] array2 = arrays.get(index + 1);
                String[] added = new String[array1.length + array2.length];
                res.add(added);
                threads.add(new ArraysMerger(array1, array2, added));
            } else {
                res.add(arrays.get(index));
                break;
            }
            ++index;
        }
        for (ArraysMerger merger : threads) {
            merger.join();
        }
        return res;
    }

    private class ArraysMerger extends Thread {
        private String[] result;
        private String[] array1;
        private String[] array2;

        ArraysMerger(String[] array1, String[] array2, String[] result) {
            super();
            this.array1 = array1;
            this.array2 = array2;
            this.result = result;
        }

        @Override
        public void run() {
            mergeTwoArrays(array1, array2, result);
        }
    }

    private void mergeTwoArrays(String[] array1, String[] array2, String[] result) {
        int pos1 = 0;
        int pos2 = 0;
        int length1 = array1.length;
        int length2 = array2.length;
        for (int i = 0; i < result.length; ++i) {
            if ((pos1 < length1) && (pos2 < length2)) {
                if (cmp.compare(array1[pos1], array2[pos2]) < 0) {
                    result[i] = array1[pos1];
                    ++pos1;
                } else {
                    result[i] = array2[pos2];
                    ++pos2;
                }
            } else {
                if (pos1 < length1) {
                    result[i] = array1[pos1];
                    ++pos1;
                }
                if (pos2 < length2) {
                    result[i] = array2[pos2];
                    ++pos2;
                }
            }
        }
    }
}