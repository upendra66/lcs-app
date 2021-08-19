package com.assessment.lcsservice.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class LcsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findLcs_PositiveCase() throws Exception {

		String request = "{\"setOfStrings\":[{\"value\":\"commcast\"},{\"value\":\"commcastic\"},{\"value\":\"commbroadcaster\"}]}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lcs").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());
		assertEquals(true,response.getContentAsString().contains("comm"));

	}
	
	
	
	@Test
	public void findLcs_IncorrectMethod() throws Exception {

		String request = "{\"setOfStrings\":[{\"value\":\"commcast\"},{\"value\":\"commcastic\"},{\"value\":\"commbroadcaster\"}]}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/lcs").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(405, response.getStatus());	

	}
	
	
	@Test
	public void findLcs_IncorrectFormat() throws Exception {

		String request = "{\"setOfStrings\":[{\"value1\":\"commcast\"},{\"value\":\"commcastic\"},{\"value\":\"commbroadcaster\"}]}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lcs").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());	

	}
	
	
	@Test
	public void findLcs_EmptySetOfString() throws Exception {

		String request = "{\"setOfStrings\":[]}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lcs").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());	

	}
	
	
	@Test
	public void findLcs_NotUniqueSetOfString() throws Exception {

		String request = "{\"setOfStrings\":[{\"value\":\"commcast\"},{\"value\":\"commcast\"},{\"value\":\"commbroadcaster\"}]}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lcs").accept(MediaType.APPLICATION_JSON)
				.content(request).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());	

	}

}
