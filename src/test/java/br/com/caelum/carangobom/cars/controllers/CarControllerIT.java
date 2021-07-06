package br.com.caelum.carangobom.cars.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.caelum.carangobom.cars.dtos.CarRequest;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql("/cars/happy_day.sql")
@ActiveProfiles("test")
@WithMockUser(username = "admin", password = "admin", authorities = { "admin" })
class CarControllerIT {

	@Autowired
	private MockMvc mockMVC;
	
	private ObjectMapper mapper;

	@BeforeEach
	void setAll() {
		this.mapper = new ObjectMapper();
	}
	
	@Test
	void shouldCreateCar() throws Exception {
		
		String carRequest = mapper.writeValueAsString(new CarRequest(1L, "A6", 2020, BigDecimal.valueOf(100000L)));
		
		MockHttpServletResponse response = this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/cars")
						.content(carRequest)
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.id", is(9)))
			.andExpect(jsonPath("$.model", is("A6")))
			.andExpect(jsonPath("$.brand.id", is(1)))
			.andExpect(jsonPath("$.year", is(2020)))
			.andExpect(jsonPath("$.value", is(100000)))
			.andReturn()
			.getResponse();
		
		String location = response.getHeader("location");
		
		assertThat(location, endsWith("/cars/9"));
	}


	@Test
	void shouldDelete() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.delete("/cars/1"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	void whenCreate_ifNameIsEmpty_shouldValidate() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.post("/cars")
						.content("{}")
						.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}

	@Test
	void whenFindAll_shouldReturnCars() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/cars"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$", hasSize(8)))
			.andExpect(jsonPath("$[7].id", is(8)))
			.andExpect(jsonPath("$[7].model", is("New Fiesta Hatch")))
			.andExpect(jsonPath("$[7].brand.id", is(3)))
			.andExpect(jsonPath("$[7].year", is(2018)))
			.andExpect(jsonPath("$[7].value", is(55000.0)));
	}

	@Test
	void shouldFindById() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/cars/1"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.model", is("A5")))
			.andExpect(jsonPath("$.brand.id", is(1)))
			.andExpect(jsonPath("$.year", is(2021)))
			.andExpect(jsonPath("$.value", is(300000.0)))
			.andReturn()
			.getResponse();
	}
	
	@Test
	void whenFindById_andBrandNotExists_shouldThrowNotFound() throws Exception {
		this.mockMVC
			.perform(
					MockMvcRequestBuilders
						.get("/cars/9"))
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
						.post("/cars/1"))
			.andExpect(status().is4xxClientError());
	}
	


}