package br.com.test.stockquotemanager.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StockQuote {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String primaryKey;

	@Column(name = "name")
	private String id;

	@ManyToOne
	private Quotes quotes;

	public Optional<String> getId() {
		return Optional.ofNullable(this.id);
	}

	public void setId(String id) {
		this.id = id;
	}

	public Quotes getQuotes() {
		return quotes;
	}

	public void setQuotes(Quotes quotes) {
		this.quotes = quotes;
	}

}
