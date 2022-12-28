import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	public static Lock mutex;
	public static ExecutorService pool;
	public static String directory;
	public static int threadCnt;
	public static FileWriter ordersWriter;
	public static FileWriter itemsWriter;

	public static void main(String[] args) {
		directory = args[0];
		threadCnt = Integer.parseInt(args[1]);
		mutex = new ReentrantLock(true);
		pool = Executors.newFixedThreadPool(threadCnt);

		/* INITIALIZE READERS */
		BufferedReader orderReader = null;
		try {
			orderReader = new BufferedReader(new FileReader(directory + "/orders.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Order file not found!");
		}

		/* INITIALIZE WRITERS */
		try {
			ordersWriter = new FileWriter(new File("orders_out.txt"));
			itemsWriter = new FileWriter(new File("order_products_out.txt"));
		} catch (IOException e) {
			System.out.println("An error occurred while creating the writers.");
			e.printStackTrace();
		}

		/* OPEN "threadCnt" THREADS FOR PROCESSING ORDERS */
		Thread[] t = new Thread[threadCnt];
		for (int i = 0; i < threadCnt; ++i) {
			t[i] = new SplitOrders(i, orderReader, directory);
			t[i].start();

		}

		for (int i = 0; i < threadCnt; ++i) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		pool.shutdown();

	}

}
