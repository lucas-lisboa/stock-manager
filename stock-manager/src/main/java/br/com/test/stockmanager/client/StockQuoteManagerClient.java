package br.com.test.stockmanager.client;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableCaching
public class StockQuoteManagerClient {

	private final RestTemplate restTemplate;

	StockQuoteManagerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public void notifyApplications(List<String> registeredApps) {

		for (String host : registeredApps) {
			restTemplate.delete("http://" + host + "/stockcache");
		}
	}

}
