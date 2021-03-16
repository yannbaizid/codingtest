package test.bo;

public class Transaction {
	private long quantity;
	private double price;
	
	public Transaction(long quantity, double price) {
		this.quantity=quantity;
		this.price=price;
	}

	public long getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	
	
}
