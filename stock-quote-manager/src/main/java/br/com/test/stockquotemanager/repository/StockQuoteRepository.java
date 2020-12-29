package br.com.test.stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.test.stockquotemanager.model.StockQuote;

public interface StockQuoteRepository extends JpaRepository<StockQuote, Long> {

	List<StockQuote> findById(String name);
}
