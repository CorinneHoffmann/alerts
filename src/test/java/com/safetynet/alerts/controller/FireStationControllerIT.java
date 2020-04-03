package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.FireStationDao;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.services.FireStationService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FireStationControllerIT {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private FireStationDao fireStationDao;

	@BeforeEach
	void SetUpEnv() {
		fireStationDao.deleteAllFireStation();
	}

	@Test
	void shouldGetFireStationWhenEmpty() throws Exception {

		MvcResult result = mockmvc.perform(get("/firestation")).andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldGetFireStation() throws Exception {
		fireStationService.createFireStation(new FireStation("address1", 1));
		MvcResult result = mockmvc.perform(get("/firestation")).andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldCreateFireStation() throws Exception {

		String json = "{ \"address\":\"address1\", \"station\":\"1\" }";

		MvcResult result = mockmvc.perform(
				post("/firestation").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldCreateFireStationOtherTest() throws Exception {

		FireStation fireStation = new FireStation();
		fireStation.setAllAttributes("address1", 1);
		ObjectMapper objectMapper = new ObjectMapper();
		String fireStationJson = objectMapper.writeValueAsString(fireStation);

		this.mockmvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(fireStationJson))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldUpdateFireStation() throws Exception {

		fireStationService.createFireStation(new FireStation("address3", 1));
		String json = "{ \"address\":\"address3\", \"station\":\"2\" }";

		MvcResult result = mockmvc.perform(
				put("/firestation").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotUpdateFireStationBecauseNotFound() throws Exception {

		fireStationService.createFireStation(new FireStation("address3", 1));
		String json = "{ \"address\":\"address4\", \"station\":\"2\" }";

		MvcResult result = mockmvc.perform(
				put("/firestation").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
	}

	@Test
	void shouldDeleteFireStationByAdress() throws Exception {
		fireStationService.createFireStation(new FireStation("address3", 1));
		MvcResult result = mockmvc
				.perform(delete("/firestation/address").param("address", "address3").characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotDeleteFireStationByAdressBecauseNotFound() throws Exception {
		fireStationService.createFireStation(new FireStation("address3", 1));
		MvcResult result = mockmvc
				.perform(delete("/firestation/address").param("address", "address4").characterEncoding("utf-8"))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotDeleteFireStationByAdressBecauseNotParam() throws Exception {
		fireStationService.createFireStation(new FireStation("address3", 1));
		MvcResult result = mockmvc.perform(delete("/firestation/address").characterEncoding("utf-8"))
				.andExpect(status().isInternalServerError()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotFindCompletePath() throws Exception {

		fireStationService.createFireStation(new FireStation("address3", 1));
		MvcResult result = mockmvc
				.perform(delete("/firestation/add").param("address", "address4").characterEncoding("utf-8"))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotFindPath() throws Exception {

		fireStationService.createFireStation(new FireStation("address3", 1));
		MvcResult result = mockmvc
				.perform(delete("/firestation/").param("address", "address4").characterEncoding("utf-8"))
				.andExpect(status().isMethodNotAllowed()).andDo(print()).andReturn();
	}

	@Test
	void shouldDeleteFireStationByStation() throws Exception {
		fireStationService.createFireStation(new FireStation("address1", 1));
		fireStationService.createFireStation(new FireStation("address2", 2));
		fireStationService.createFireStation(new FireStation("address3", 1));

		MvcResult result = mockmvc
				.perform(delete("/firestation/station").param("station", "1").characterEncoding("utf-8"))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotDeleteFireStationByStationBecauseNotFound() throws Exception {
		fireStationService.createFireStation(new FireStation("address1", 1));
		fireStationService.createFireStation(new FireStation("address2", 2));
		fireStationService.createFireStation(new FireStation("address3", 1));

		MvcResult result = mockmvc
				.perform(delete("/firestation/station").param("station", "3").characterEncoding("utf-8"))
				.andExpect(status().isNotFound()).andDo(print()).andReturn();
	}

	@Test
	void shouldNotDeleteFireStationByStationBecauseNotParam() throws Exception {
		fireStationService.createFireStation(new FireStation("address1", 1));
		fireStationService.createFireStation(new FireStation("address2", 2));
		fireStationService.createFireStation(new FireStation("address3", 1));

		MvcResult result = mockmvc.perform(delete("/firestation/station").characterEncoding("utf-8"))
				.andExpect(status().isBadRequest()).andDo(print()).andReturn();
	}
}
