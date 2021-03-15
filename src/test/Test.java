package test;

import com.hsoft.codingtest.DataProvider;
import com.hsoft.codingtest.DataProviderFactory;
import com.hsoft.codingtest.MarketDataListener;
import com.hsoft.codingtest.PricingDataListener;

public class Test {
  public final static void main(String[] args) {
	  
    DataProvider provider = DataProviderFactory.getDataProvider();
    provider.addMarketDataListener(new MarketDataListener() {
      public void transactionOccured(String productId, long quantity, double price) {
        // Do Some Stuff
      }
    });
    provider.addPricingDataListener(new PricingDataListener() {
      public void fairValueChanged(String productId, double fairValue) {
        // Do Some Stuff
      }
    });

    provider.listen();
    // When this method returns, the test is finished and you can check your results
  }
}