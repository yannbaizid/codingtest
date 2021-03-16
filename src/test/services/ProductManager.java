package test.services;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import test.bo.Product;
import test.bo.Transaction;

public class ProductManager {
	private Map<String, Product> products;
	private static Logger logger = Logger.getLogger(ProductManager.class);

	public ProductManager() {
		this.products = new Hashtable<String, Product>();
	}

	/**
	 * Add transaction to selected product lastTransactions. Then compare updated
	 * vwap with fair value and log if vwap>fairvalue
	 * 
	 * @param productId string
	 * @param quantity  long
	 * @param price     double
	 */
	public void addTransaction(String productId, long quantity, double price) {
		if (!products.containsKey(productId)) {
			products.put(productId, new Product(productId));
		}

		Product product = products.get(productId);

		product.addTransaction(new Transaction(quantity, price));

		if (product.compareValues()) {
			this.logComparaison(product);
		}

	}

	/**
	 * Update fairValue of selected product. Then compare vwap with fair value and
	 * log if vwap>fairvalue
	 * 
	 * @param productId string
	 * @param fairValue double
	 */
	public void setFairValue(String productId, double fairValue) {
		if (!products.containsKey(productId)) {
			products.put(productId, new Product(productId));
		}
		Product product = products.get(productId);
		product.setFairValue(fairValue);

		if (product.compareValues()) {
			this.logComparaison(product);
		}
	}

	/**
	 * Log when vwap>fair value.
	 * Log on console only when product=TEST_PRODUCT.
	 * Log on file ./log/log.log on all cases.
	 * @param product
	 */
	public void logComparaison(Product product) {
		String message = "product:" + product.getProductId() + ". VWAP " + product.getVwap() + ") > FairValue ("
				+ product.getFairValue() + ")";

		if (product.getProductId().equals("TEST_PRODUCT")) {
			logger.info(message);

		} else {
			logger.debug(message);
		}
	}

}
