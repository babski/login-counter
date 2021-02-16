package com.mbabski.logincounter.controller;

import java.time.LocalDate;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest()
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {

	MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void shouldReturnUserWithCalculations() throws Exception {
		//given
		String uri = "/users/octocat";

		//when
		mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON))
				//then
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(583231)))
				.andExpect(jsonPath("$.login", is("octocat")))
				.andExpect(jsonPath("$.name", is("The Octocat")))
				.andExpect(jsonPath("$.type", is("User")))
				.andExpect(jsonPath("$.calculations", is("0,02"))) //valid for 2021-01-25
				.andExpect(jsonPath("$.avatar_url", is("https://avatars.githubusercontent.com/u/583231?v=4"))) //valid for 2021-01-25
				.andExpect(jsonPath("$.created_at", is("2011-01-25T18:44:36")));
	}

	@Test
	public void shouldReturnUserWithNoCalculations() throws Exception {
		//given
		String uri = "/users/mb54408";

		//when
		mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON))
				//then
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(65966225)))
				.andExpect(jsonPath("$.login", is("mb54408")))
				.andExpect(jsonPath("$.name", is(nullValue())))
				.andExpect(jsonPath("$.type", is("User")))
				.andExpect(jsonPath("$.calculations", is("Cannot calculate for user with no followers - dividing by zero is undefined")))
				.andExpect(jsonPath("$.avatar_url", is("https://avatars.githubusercontent.com/u/65966225?v=4")))
				.andExpect(jsonPath("$.created_at", is("2020-05-26T15:18:03")));
	}

	@Test
	public void shouldReturnErrorWhenUserDoesNotExist() throws Exception {
		//given
		String uri = "/users/das2h123o";

		//when
		mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON))
				//then
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.timestamp", CoreMatchers.startsWith(LocalDate.now().toString())))
				.andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.name", is("NOT_FOUND")))
				.andExpect(jsonPath("$.message", is("User with login 'das2h123o' not found")))
				.andExpect(jsonPath("$.path", is("/users/das2h123o")));
	}
}