package br.com.caelum.carangobom.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerIT {

	@Autowired
	private MockMvc mockMVC;
	

	@Test
	void whenCreate_ifNameIsEmpty_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/brands")
						.content("{}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}


}