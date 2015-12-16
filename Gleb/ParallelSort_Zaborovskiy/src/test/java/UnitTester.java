import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import org.junit.*;

public class UnitTester {

    private Logger logger = null;
    private ConsoleHandler ch = null;

    private Reader r;
    Sorter s1;
    Sorter s2;
    Sorter s3;
    Sorter s4;
    ParallelSort p;
    String[] a = { "Sentipede", "-u", "-t", "17" };
    String[] b = { "-Taran", "-i", "-t", "3" };
    String[] c = { "Movie", "-u", "-t", "17" };
    String[] d = { "-u-i-iu", "-u", "-t", "17" };

    @Before
    public void setUp() {
	logger = Logger.getLogger(UnitTester.class.getName());
	// ch = new ConsoleHandler();
	// ch.setFormatter(new SimpleFormatter());
	// logger.addHandler(ch);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParametersGetter() {
	ParallelSort sort = new ParallelSort(a);
	Assert.assertEquals(Integer.toString(sort.getPARAMETER_T()), a[3]);
    }

    @Test
    public void testOfComparator() {
	String str1 = new String("Qwer");
	String str2 = new String("qwer");
	SimpleComparator sc1 = new SimpleComparator(true);
	SimpleComparator sc2 = new SimpleComparator(false);
	Assert.assertTrue("Ignore case error", sc1.compare(str1, str2) == 0);
	Assert.assertTrue("Comparison error", sc2.compare(str1.toLowerCase(), str2.toLowerCase()) == 0);
	Assert.assertFalse("Comparison error", sc2.compare(str1, str2) == 0);
    }

    @Ignore
    @Test
    public void testOfReader() {
	Assert.fail("Still no test were made");
    }

    @Test
    public void testMergingParameterTrue() {
	List<String> one = new ArrayList<String>();
	List<String> two = new ArrayList<String>();
	for (int i = 0; i < 20; i++) {
	    one.add("a" + Integer.toString(i));
	}
	logger.info(one.toString());
	for (int j = 30; j > 10; j--) {
	    two.add("A" + Integer.toString(j));
	}
	int SIZE = one.size() + two.size();
	logger.info(two.toString());
	ParallelSort.mergeTwoLists(one, two, true);
	logger.info(one.toString());
	Assert.assertTrue("some elements were lost", one.size() == SIZE);
	Assert.assertTrue("second list was not cleared", two.size() == 0);
    }

    @Test
    public void TestMergingParameterFalse() {
	List<String> one = new ArrayList<String>();
	List<String> two = new ArrayList<String>();
	for (int i = 0; i < 20; i++) {
	    one.add("a" + Integer.toString(i));
	}
	logger.info(one.toString());
	for (int j = 30; j > 10; j--) {
	    two.add("A" + Integer.toString(j));
	}

	logger.info(two.toString());
	int SIZE = one.size() + two.size();
	ParallelSort.mergeTwoLists(one, two, false);
	logger.info(one.toString());
	Assert.assertTrue("some elements were lost", one.size() == SIZE);
	Assert.assertTrue("second list was not cleared", two.size() == 0);
    }

    @Ignore("Тест на конкуренцию сортировщиков. Пока не удалось написать хороший")
    @Test
    public void testOfSorter() {
	r = new Reader(null, null);
	s1 = new Sorter(r, "Jake", true, true);
	s2 = new Sorter(r, "Finn", false, true);
	s3 = new Sorter(r, "Simon", true, false);
	s4 = new Sorter(r, "Pin", false, false);
	r.setWorkingStatus(true);
	for (int i = 0; i < 10000; i++) {
	    r.words.add("A" + Integer.toString(i));
	}
	logger.info(r.words.size() + " => " + s1.innerList.size() + " " + s2.innerList.size() + " " + s3.innerList.size() + " " + s4.innerList.size());
	s1.start();
	s2.start();
	s3.start();
	s4.start();
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    logger.info(e.toString());
	}
	logger.info(r.words.size() + " => " + s1.hasEnded() + " " + s2.hasEnded() + " " + s3.hasEnded() + " " + s4.hasEnded() + " ");
	// for(int i=10000;i<20000;i++){
	for (int i = 0; i < 10000; i++) {
	    r.words.add("a" + Integer.toString(i));
	}
	int j = s1.innerList.size();
	int f = s2.innerList.size();
	int s = s3.innerList.size();
	int p = s4.innerList.size();
	logger.info(Integer.toString(j + f + s + p));
	r.setWorkingStatus(false);
	logger.info(r.words.size() + " => " + s1.innerList.size() + " " + s2.innerList.size() + " " + s3.innerList.size() + " " + s4.innerList.size());
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    logger.info(e.toString());
	}
	logger.info(r.words.size() + " => " + s1.innerList.size() + " " + s2.innerList.size() + " " + s3.innerList.size() + " " + s4.innerList.size());
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    logger.info(e.toString());
	}
	logger.info(r.words.size() + " => " + s1.hasEnded() + " " + s2.hasEnded() + " " + s3.hasEnded() + " " + s4.hasEnded() + " ");
	Assert.assertEquals("Words missed is " + (20000 - (s1.innerList.size() + s2.innerList.size() + s3.innerList.size() + s4.innerList.size())), 20000, s1.innerList.size() + s2.innerList.size() + s3.innerList.size() + s4.innerList.size());

	r.setWorkingStatus(false);
	try {
	    Thread.sleep(10000);
	} catch (InterruptedException e) {
	    logger.info(e.toString());
	}
	logger.info(r.words.size() + " => " + s1.innerList.size() + " " + s2.innerList.size() + " " + s3.innerList.size() + " " + s4.innerList.size());
	Assert.assertFalse(s1.innerList.size() == j);
	Assert.assertFalse(s2.innerList.size() == f);
	Assert.assertFalse(s3.innerList.size() == s);
	Assert.assertTrue(s4.innerList.size() == p);
    }
}
