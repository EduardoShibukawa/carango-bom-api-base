package br.com.caelum.carangobom.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

	@Autowired
	private MockMvc mockMVC;

	@Test
	void whenCreate_ifBodyIsEmpty_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/users")
						.content("{}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}

	@Test
	void whenCreate_ifUserNameIsLowerThenFiveCharacters_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/users")
						.content("{\"userName\":\"luc\", \"password\":\"123456\"}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}

	@Test
	void whenCreate_ifPasswordIsLowerThenFiveCharacters_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/users")
						.content("{\"userName\":\"lucas\", \"password\":\"123\"}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}

	@Test
	void whenCreate_ifUserNameAndPasswordIsLowerThenFiveCharacters_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/users")
						.content("{\"userName\":\"luc\", \"password\":\"123\"}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}
}