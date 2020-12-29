package br.com.test.stockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.test.stockmanager.model.StockManager;

public interface StockManagerRepository extends JpaRepository<StockManager, Long> {

}
