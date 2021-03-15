package test.bo;

import java.util.ArrayList;
import java.util.List;

public class Product {

	private String productId;
	private double fairValue;
	private List<Transaction> latestTransactions;
	private double vwap;
	private int occurence;

	public Product(String productId) {
		this(productId, 0, new ArrayList<Transaction>(), 1);
	}

	public Product(String productId, double fairValue, List<Transaction> latestTransactions, int occurence) {
		super();
		this.productId = productId;
		this.fairValue = fairValue;
		this.latestTransactions = latestTransactions;
		this.occurence = occurence;
	}

	public String getProductId() {
		return productId;
	}

	public double getFairValue() {
		return fairValue;
	}

	public void setFairValue(double fairValue) {
		this.fairValue = fairValue;

	}

	public double getVwap() {
		return this.vwap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", fairValue=" + fairValue + ", vwap=" + vwap + ",occurence="
				+ this.occurence + "]";
	}

	public synchronized int increment() {
		return this.occurence++;
	}

	public synchronized void addTransaction(Transaction transaction) {
		this.latestTransactions.add(transaction);
		if (this.latestTransactions.size() > 5) {
			this.latestTransactions.remove(0);

		}

		this.setVwap();
	}

	public synchronized double setVwap() {
		double totalPrice = 0;
		long totalVolume = 0;
		
		for (int i = 0; i < this.latestTransactions.size(); i++) {
			Transaction transaction = this.latestTransactions.get(i);
			totalPrice += transaction.getPrice() * transaction.getQuantity();
			totalVolume += transaction.getQuantity();
		}

		this.vwap = totalVolume != 0 ? totalPrice / totalVolume : 0;
		

		
		return this.vwap;
		
	}

	public synchronized boolean compareValues() {

		return this.vwap != 0 ? this.vwap > this.fairValue : false;
	}

}
