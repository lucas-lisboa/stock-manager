package br.com.test.stockquotemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.test.stockquotemanager.model.Quotes;

public interface StockRepository extends JpaRepository<Quotes, Long> {

}
