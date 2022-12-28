public class Order {
	private String id;
	private String status;
	private int maxProducts;
	private int itemsProcessed;

	public Order(String id, int maxProducts) {
		this.id = id;
		this.maxProducts = maxProducts;
		this.itemsProcessed = 0;
		this.status = "processing";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMaxProducts() {
		return maxProducts;
	}

	public void setMaxProducts(int maxProducts) {
		this.maxProducts = maxProducts;
	}

	public int getItemsProcessed() {
		return itemsProcessed;
	}

	public void incrementItemsProcessed() {
		this.itemsProcessed += 1;
	}

	public void setItemsProcessed(int itemsProcessed) {
		this.itemsProcessed = itemsProcessed;
	}
}
