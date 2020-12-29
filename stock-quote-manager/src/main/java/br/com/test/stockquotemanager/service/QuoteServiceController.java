package br.com.test.stockquotemanager.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.stockquotemanager.client.StockManagerClient;
import br.com.test.stockquotemanager.model.StockManager;
import br.com.test.stockquotemanager.model.StockQuote;
import br.com.test.stockquotemanager.repository.StockQuoteRepository;
import br.com.test.stockquotemanager.repository.StockRepository;

@RestController
public class QuoteServiceController {

	private final StockQuoteRepository stockQuoteRepo;
	private final StockRepository stockRepo;
	private StockManagerClient validateStock;

	QuoteServiceController(StockQuoteRepository stockQuoteRepo, StockRepository stockRepo,
			StockManagerClient validateStock) {
		this.stockQuoteRepo = stockQuoteRepo;
		this.stockRepo = stockRepo;
		this.validateStock = validateStock;
	}

	@GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockQuote> findAllStockQuoets() {
		return stockQuoteRepo.findAll();
	}

	@GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockQuote> findStockQuotesById(@PathVariable String id) {
		return stockQuoteRepo.findById(id);
	}

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<StockQuote>> create(@RequestBody StockQuote stockQuote) {
		if (!validate(stockQuote)) {
			return ResponseEntity.status(422).build();
		}
		stockRepo.save(stockQuote.getQuotes());
		StockQuote savedStockQuote = stockQuoteRepo.save(stockQuote);

		return ResponseEntity
				.created(savedStockQuote.getId()
						.map(id -> linkTo(methodOn(QuoteServiceController.class).findStockQuotesById(id)).toUri())
						.orElseThrow(() -> new RuntimeException("Failed to create stock quote")))
				.body(EntityModel.of(savedStockQuote));

	}

	public boolean validate(StockQuote stockQuote) {
		List<StockManager> stockManager = validateStock.getStockManager();
		return stockManager.stream().filter(s -> s.getId().equals(stockQuote.getId().get())).count() > 0;
	}

	@GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockManager> validateTestCache(StockQuote stockQuote) {
		return validateStock.getStockManager();
	}

	@DeleteMapping(value = "/stockcache")
	public ResponseEntity<?> stockcache() {
		validateStock.refreshCash();
		return ResponseEntity.noContent().build();
	}


}
