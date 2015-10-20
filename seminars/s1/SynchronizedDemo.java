
abstract class ThreadBase implements Runnable {
	public static final int INC_COUNT = 50000;
	static Integer val;
	String name;

	ThreadBase() {
		this.val = 0;
	}

	public Integer getVal() {
		return val;
	}

	abstract void incVal();

	@Override
	public void run() {
		for (int i =0; i < INC_COUNT; i++) {
			incVal();
		}
	}
}

class NonSynchronizedThread extends ThreadBase {
	void incVal() {
		++val;
	}
}

class SynchronizedThread extends ThreadBase {
	void incVal() {
		synchronized(SynchronizedThread.class) {
			++val;
		}
	}
}


public class SynchronizedDemo {

	public static void main(String[] args) throws Exception {
		System.out.println("Несинхронизованный случай");
		Integer val = Integer.valueOf(0);

		Thread thread1 = new Thread(new NonSynchronizedThread());
		Thread thread2 = new Thread(new NonSynchronizedThread());
		System.out.println("Запуск 2-х потоков, каждый из которых увеличивает val на 1 " + ThreadBase.INC_COUNT +" раз");
		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
		System.out.println("Ожидание завершения потоков");
		System.out.println("В результате, val=" + NonSynchronizedThread.val);

		System.out.println();

		System.out.println("Синхронизованный случай");
		val = Integer.valueOf(0);

		Thread thread3 = new Thread(new SynchronizedThread());
		Thread thread4 = new Thread(new SynchronizedThread());
		System.out.println("Запуск 2-х потоков, каждый из которых увеличивает val на 1 " + ThreadBase.INC_COUNT +" раз");
		thread3.start();
		thread4.start();

		thread3.join();
		thread4.join();
		System.out.println("Ожидание завершения потоков");
		System.out.println("В результате, val=" + SynchronizedThread.val);
	}

} 