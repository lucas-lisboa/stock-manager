package br.com.test.stockmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.stockmanager.client.StockQuoteManagerClient;
import br.com.test.stockmanager.model.RegisterApps;
import br.com.test.stockmanager.model.StockManager;
import br.com.test.stockmanager.repository.StockManagerRepository;

@RestController
public class StockManagerServiceController {

	private final StockManagerRepository stockQuoteRepo;
	private final StockQuoteManagerClient stockQuoteClient;

	private List<String> registeredApps = new ArrayList<>();

	StockManagerServiceController(StockManagerRepository stockManagerRepo, StockQuoteManagerClient stockQuoteClient) {
		this.stockQuoteRepo = stockManagerRepo;
		this.stockQuoteClient = stockQuoteClient;
	}

	@GetMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockManager> findAll() {
		return stockQuoteRepo.findAll();
	}

	@PostMapping(value = "/stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public StockManager create(@RequestBody StockManager stockManager) {
		StockManager savedStockManager = stockQuoteRepo.save(stockManager);
		stockQuoteClient.notifyApplications(registeredApps);
		return savedStockManager;
	}

	@PostMapping(value = "/notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> notification(@RequestBody RegisterApps registerApps) {
		if (!registeredApps.contains(registerApps.toString())) {
			registeredApps.add(registerApps.toString());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/findAllHosts", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> findAllHosts() {
		return registeredApps;
	}

}
