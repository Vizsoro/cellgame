package vzsolt.gameoflife.cellgame.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.cycle.CycleManagerInterface;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CellGameAjaxControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private BoardHandler boardHandler;

	@Test
	public void createTest() throws Exception {
		int size = 4;
		int cycle = 4;
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/cellgame/create/" + size + "/" + cycle))
				.andExpect(status().isOk()).andReturn();
		Map<Integer, Board> boardMap = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<Map<Integer, Board>>() {});
		assertEquals(size, boardMap.get(cycle).getBoardSize());
	}

	@Test
	public void calculateTest() throws Exception {
		int cycle = 5;
		int size = 4;
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/cellgame/calculate/" + cycle).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(boardHandler.generateBoard(size, 0.5))).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		Map<Integer, Board> boardMap = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<Map<Integer, Board>>() {});
		assertEquals(size, boardMap.get(cycle).getBoardSize());
	}
}
