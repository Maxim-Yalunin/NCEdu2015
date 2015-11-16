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
            return merge(divideAndSort());
        }
        Arrays.sort(data, cmp);
        return data;
    }

    public List<String[]> divideAndSort() throws InterruptedException {
        int size = data.length;
        int step = size / threadsCount;
        MyThread[] threads = new MyThread[threadsCount];
        List<String[]> arrays = new LinkedList<String[]>();
        for (int i = 0; i < threadsCount; i++) {
            int begin = i * step;
            int end = (i == threadsCount - 1) ? size - 1 : (i + 1) * step - 1;
            String[] array = Arrays.copyOfRange(data, begin, end + 1);
            arrays.add(array);
            MyThread thread = new MyThread(array);
            thread.run();
            threads[i] = thread;
        }
        for (MyThread thread : threads) {
            thread.join();
        }
        return arrays;
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

    public String[] merge(List<String[]> arrays) throws InterruptedException {
        if (arrays.size() == 1) {
            return arrays.get(0);
        }
        do {
            arrays = mergeInList(arrays);
        }
        while (arrays.size() != 1);
        return arrays.get(0);
    }

    public List<String[]> mergeInList(List<String[]> arrays) throws InterruptedException {
        List<String[]> res = new LinkedList<String[]>();
        int countMergers = arrays.size() / 2;
        ArraysMerger[] mergers = new ArraysMerger[countMergers];
        if ((arrays.size() % 2) > 0) {
            res.add(arrays.get(arrays.size() - 1));
        }
        for (int i = 0; i < countMergers; ++i) {
            String[] array1 = arrays.get(i);
            String[] array2 = arrays.get(countMergers * 2 - 1 - i);
            String[] added = new String[array1.length + array2.length];
            ArraysMerger merger = new ArraysMerger(array1, array2, added);
            merger.run();
            mergers[i] = merger;
            res.add(added);
        }
        for (ArraysMerger merger : mergers) {
            merger.join();
        }
        return res;
    }

    public class ArraysMerger extends Thread {
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

    public void mergeTwoArrays(String[] array1, String[] array2, String[] result) {
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