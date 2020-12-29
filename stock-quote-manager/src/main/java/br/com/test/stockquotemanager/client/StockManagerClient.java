package br.com.test.stockquotemanager.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.test.stockquotemanager.model.StockManager;
import net.minidev.json.JSONObject;

@Component
@EnableCaching
public class StockManagerClient {

	private final RestTemplate restTemplate;
	@Value("${server.port}")
	private int port;
	@Value("${quote-manager.endpoint}")
	private String endpoint;

	StockManagerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Cacheable("stockManager")
	public List<StockManager> getStockManager() {

		ResponseEntity<StockManager[]> serviceStock = restTemplate.getForEntity("http://" + endpoint +":8080/stock/",
				StockManager[].class);

		return Arrays.asList(serviceStock.getBody());

	}

	@CacheEvict(value = "stockManager", allEntries = true)
	public void refreshCash() {
	}

	public void notification() {
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JSONObject personJsonObject = new JSONObject();
			personJsonObject.put("host", InetAddress.getLocalHost().getHostName());
			personJsonObject.put("port", port);
			HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
			restTemplate.postForObject("http://" + endpoint +":8080/notification", request, Object.class);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
