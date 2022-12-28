import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SplitOrders extends Thread {
	private int id;
	private BufferedReader orderReader;
	private String directory;
	private Lock mutex;

	public SplitOrders(int id, BufferedReader orderReader, String directory) {
		this.id = id;
		this.orderReader = orderReader;
		this.directory = directory;
		this.mutex = new ReentrantLock(true);
	}

	public void run() {
		try {
			while (orderReader.ready()) {
				Main.mutex.lock();
				/* PARSE A LINE FROM THE FILE AND CREATE THE ORDER */
				if (orderReader.ready()) {
					String line = orderReader.readLine();

					String[] dataParts = line.split(",");
					Order order = new Order(dataParts[0], Integer.parseInt(dataParts[1]));

					/* INITIALIZE ITEM READER FOR THE ORDER ITEMS */
					BufferedReader itemReader = null;
					try {
						itemReader = new BufferedReader(new FileReader(directory + "/order_products.txt"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						System.out.println("Item file not found!");
					}

					/* INITIALIZE THREADS THAT HANDLE THE ORDER */
					if (order.getMaxProducts() > 0) {
						for (int i = 10 * this.id; i < 10 * this.id + Main.threadCnt; i++) {
							ProcessItems processItems = new ProcessItems(i, order, itemReader, this.mutex);
							Main.pool.submit(processItems);
						}
					}
				}
				Main.mutex.unlock();
			}

		} catch (IOException e) {
			System.out.println("Error while reading file!");
		}
	}

}




