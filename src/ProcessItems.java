import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class ProcessItems extends Thread {
	private int id;
	private Order order;
	private BufferedReader itemReader;
	private Lock mutex;

	public ProcessItems(int id, Order order, BufferedReader itemReader, Lock mutex) {
		this.id = id;
		this.order = order;
		this.itemReader = itemReader;
		this.mutex = mutex;
	}

	public void run() {
		try {
			while (itemReader.ready()) {
				mutex.lock();
				if (itemReader.ready()) {
					String line = itemReader.readLine();
					String[] dataParts = line.split(",");
					String orderID = dataParts[0];
					String itemID = dataParts[1];
					if (orderID.equals(order.getId())) {
						Main.itemsWriter.write(orderID + "," + itemID + ",shipped\n");
						Main.itemsWriter.flush();
						order.incrementItemsProcessed();
						if (order.getItemsProcessed() == order.getMaxProducts()) {
							Main.ordersWriter.write(order.getId() + "," + order.getItemsProcessed() + ",shipped\n");
							Main.ordersWriter.flush();
						}
					}
				}
				mutex.unlock();
			}
		} catch (IOException e) {
			System.out.println("Error while reading file!");
			e.printStackTrace();
		}
	}
}
