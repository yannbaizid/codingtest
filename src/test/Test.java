package test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hsoft.codingtest.DataProvider;
import com.hsoft.codingtest.DataProviderFactory;
import com.hsoft.codingtest.MarketDataListener;
import com.hsoft.codingtest.PricingDataListener;

import test.bo.Product;
import test.bo.Transaction;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private static Set<String> productIdList = new HashSet<String>();

	public final static void main(String[] args) {
		Map<String, Product> products=new Hashtable<String, Product>();

		DataProvider provider = DataProviderFactory.getDataProvider();
		provider.addMarketDataListener(new MarketDataListener() {
			public void transactionOccured(String productId, long quantity, double price) {
			if (!products.containsKey(productId)) {
				products.put(productId, new Product(productId));
			}
			
				Product product=products.get(productId);
				
				product.addTransaction(new Transaction(quantity, price));
			
				if(product.compareValues() && productId.equals("TEST_PRODUCT")) {
					logger.info("VWAP "+product.getVwap()+") > FairValue ("+product.getFairValue()+")");
				}
				
			}
		});
		provider.addPricingDataListener(new PricingDataListener() {
			public void fairValueChanged(String productId, double fairValue) {
				if (!products.containsKey(productId)) {
					products.put(productId, new Product(productId));
				}
				Product product=products.get(productId);
				products.get(productId).setFairValue(fairValue);
				if(product.compareValues() && productId.equals("TEST_PRODUCT")) {
					logger.info("VWAP "+product.getVwap()+") > FairValue ("+product.getFairValue()+")");
				};
			}
		});

		provider.listen();
		System.out.println(products);
		System.out.println(products.get("TEST_PRODUCT"));
		// When this method returns, the test is finished and you can check your results

	}
}