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





	/**
	 * Add transaction to lastTransactions list.
	 * If more than 5 transactions, drops the older one.
	 * Then update vwap and compare values.
	 * 
	 * @param transaction Transaction
	 * @return boolean true if updated vwap >fairvalue
	 */
	public synchronized boolean addTransaction(Transaction transaction) {
		this.latestTransactions.add(transaction);
		if (this.latestTransactions.size() > 5) {
			this.latestTransactions.remove(0);

		}

		this.setVwap();
		return this.compareValues();
	}

	/**
	 *  Calculates Vwap based on lastTransactions list.
	 *  
	 * @return updated vwap : double
	 */
	public synchronized double setVwap() {
	
		double totalPrice = this.latestTransactions.stream()
				.mapToDouble(transaction -> transaction.getQuantity() * transaction.getPrice()).sum();
		long totalVolume = this.latestTransactions.stream().mapToLong(transaction -> transaction.getQuantity()).sum();

		this.vwap = totalVolume != 0 ? totalPrice / totalVolume : 0;

		return this.vwap;

	}

	/**
	 * Compare vwap and fair value
	 * @return true if vwap > fairvalue
	 * 
	 */
	public synchronized boolean compareValues() {

		return this.vwap != 0 ? this.vwap > this.fairValue : false;
	}

}
