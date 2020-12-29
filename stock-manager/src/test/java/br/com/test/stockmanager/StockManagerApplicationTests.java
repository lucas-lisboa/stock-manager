package br.com.test.stockmanager;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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

import br.com.test.stockmanager.model.RegisterApps;
import br.com.test.stockmanager.model.StockManager;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class StockManagerApplicationTests {

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
		mockMvc.perform(get("/stock")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("PETR3")))
				.andExpect(jsonPath("$[1].id", is("VALE5")));
	}

	@Test
	public void createTest() throws Exception {
		StockManager stockManager = new StockManager();
		stockManager.setDescription("teste description");
		stockManager.setId("IDTEST");

		mockMvc.perform(post("/stock").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(stockManager))).andExpect(status().isOk());
	}

	@Test
	public void notificationTest() throws Exception {
		RegisterApps regApps = new RegisterApps();
		regApps.setHost("hosttest");
		regApps.setPort(9999);
		mockMvc.perform(post("/notification").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(regApps))).andExpect(status().isOk());
	}

}
