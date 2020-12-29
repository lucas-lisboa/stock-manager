package br.com.test.stockquotemanager;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.test.stockquotemanager.model.Quotes;
import br.com.test.stockquotemanager.model.StockQuote;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class StockQuoteManagerApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@BeforeAll
	void contextLoads() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void findAllTest() throws Exception {
		mockMvc.perform(get("/findAll")).andExpect(status().isOk());
	}

	@Test
	public void findByIdNoreturnTest() throws Exception {
		mockMvc.perform(get("/findById/99999")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void findByIdFailTest() throws Exception {
		mockMvc.perform(get("/findById/")).andExpect(status().isNotFound());
	}

	@Test
	public void createErrorTest() throws Exception {
		StockQuote stockQuote = new StockQuote();
		stockQuote.setId("IDTEST2");
		Quotes quotes = new Quotes();
		quotes.setDate(new Date(System.currentTimeMillis()));
		quotes.setPrice(BigDecimal.valueOf(22.36));
		stockQuote.setQuotes(quotes);

		mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockQuote))).andExpect(status().is4xxClientError());
	}

	@Test
	public void createSuccessTest() throws Exception {
		StockQuote stockQuote = new StockQuote();
		stockQuote.setId("PETR3");
		Quotes quotes = new Quotes();
		quotes.setDate(new Date(System.currentTimeMillis()));
		quotes.setPrice(BigDecimal.valueOf(22.36));
		stockQuote.setQuotes(quotes);

		mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockQuote))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is("PETR3")));
	}

	@Test
	public void refreshCacheTest() throws Exception {
		mockMvc.perform(delete("/stockcache")).andExpect(status().isNoContent());
	}

}
