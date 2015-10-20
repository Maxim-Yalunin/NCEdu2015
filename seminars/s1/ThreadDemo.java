class ThreadExample extends Thread {
	int val;
	String name;

	ThreadExample(String name) {
		val = 0;
		this.name = name;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			++val;
			System.out.println("Количество вызовов потока " + name +": " + val);			
		}
	}
}

class ThreadExample2 implements Runnable {
	int val;
	String name;

	ThreadExample2(String name) {
		val = 0;
		this.name = name;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			++val;
			System.out.println("Количество вызовов потока " + name +": " + val);			
		}
	}

}
public class ThreadDemo {
	public static void main(String[] args) throws Exception {

		ThreadExample testThread = new ThreadExample("Test Thread");
		testThread.run();
		Thread.sleep(1);
		testThread.interrupt();


		ThreadExample2 testThread2 = new ThreadExample2("Test Thread 2");
		Thread testThread3 = new Thread(testThread2);
		testThread3.start();
		Thread.sleep(1);
		testThread3.interrupt();
	}

} 