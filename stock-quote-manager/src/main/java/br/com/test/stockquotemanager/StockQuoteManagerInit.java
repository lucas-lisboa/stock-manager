package br.com.test.stockquotemanager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import br.com.test.stockquotemanager.client.StockManagerClient;

@Component
public class StockQuoteManagerInit implements InitializingBean {

	private StockManagerClient stockManager;

	StockQuoteManagerInit(StockManagerClient validateStock) {
		this.stockManager = validateStock;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		stockManager.notification();
	}

}
