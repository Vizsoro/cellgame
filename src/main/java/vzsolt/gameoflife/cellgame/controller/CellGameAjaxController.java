package vzsolt.gameoflife.cellgame.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.cycle.CycleManagerInterface;

@RestController
@RequestMapping("/cellgame")
public class CellGameAjaxController {

	@Autowired
	private CycleManagerInterface cycleManager;
	
	@Autowired
	private BoardHandler boardHandler;
	
	
	
	@RequestMapping(value="/create/{boardsize}/{cycle}")
	public Map<Integer, Board> create(@PathVariable("boardsize") int boardSize, @PathVariable("cycle") int cycle) throws JsonProcessingException{
		int index = 0;
		Map<Integer, Board> boardMap = new HashMap<>();
		Board board = boardHandler.generateBoard(boardSize, 0.5);
		while(cycle-index++>0){
			boardMap.put(index, board);
			board = cycleManager.calculateNextCycle(board);
		}
		return boardMap;
	}
	
	
	@RequestMapping(value="/calculate/{cycle}", method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Map<Integer, Board> calcucalteCycles(@RequestBody Board userBoard, @PathVariable("cycle") int cycle){
		Map<Integer, Board> boardMap = new HashMap<>();
		int index = 0;
		while(cycle-index++>0){
			boardMap.put(index, userBoard);
			userBoard = cycleManager.calculateNextCycle(userBoard);
		}		
		return boardMap;
	}
}
