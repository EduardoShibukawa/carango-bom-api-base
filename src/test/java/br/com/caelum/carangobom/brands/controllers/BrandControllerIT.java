package br.com.caelum.carangobom.brands.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql("/brands/happy_day.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", password = "admin", authorities = { "admin" })
class BrandControllerIT {

	@Autowired
	private MockMvc mockMVC;

	@Test
	void shouldCreateBrand() throws Exception {
		MockHttpServletResponse response = this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/brands")
						.content("{\"name\": \"BMW\"}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.id", is(4)))
			.andExpect(jsonPath("$.name", is("BMW")))
			.andReturn()
			.getResponse();
		
		String location = response.getHeader("location");
		
		assertThat(location, endsWith("/brands/4"));
	}


	@Test
	void shouldDeleteBrand() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.delete("/brands/1"))
			.andExpect(status().is2xxSuccessful());
	}
	
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

	@Test
	void whenFindAll_shouldReturnBrands() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/brands"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$[*].id").value(containsInAnyOrder(1, 2, 3)))
			.andExpect(jsonPath("$[*].name").value(containsInAnyOrder("Audi", "Fiat", "Ford")));
	}

	@Test
	void shouldFindById() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/brands/1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Audi")))
			.andReturn()
			.getResponse();
	}
	
	@Test
	void whenFindById_andBrandNotExists_shouldThrowNotFound() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/brands/4"))
			.andExpect(status().is4xxClientError())
			.andReturn()
			.getResponse();
	}
	
	@Test
	@WithAnonymousUser
	void whenFindAll_shouldNotAuthorize() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/brands"))
			.andExpect(status().is4xxClientError());
	}
}