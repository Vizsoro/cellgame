package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.BoardHandlerImplementation;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellColor;
import vzsolt.gameoflife.cellgame.board.CellState;
import vzsolt.gameoflife.cellgame.board.NeighbourInfo;

public class BoardHandlerTest {

	private BoardHandler boardHandler = new BoardHandlerImplementation();


	@Test
	public void neighbourInfoTestOneCell() {
		Cell cell = new Cell();
		cell.setPosX(0).setPosY(0);
		cell.setColor(CellColor.BLUE);
		cell.setState(CellState.DEAD);
		Map<Integer, Map<Integer,Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		row.put(0, cell);
		cells.put(0, row);
		Board board = new Board(cells);
		boardHandler.fillNeighbourInfo(board);
		NeighbourInfo info = board.getCells().get(0).get(0).getNeighbourInfo();
		assertTrue(info.getColor().equals(CellColor.GREEN));
		assertTrue(info.getLivingNeighbour() == 0);
	}

	@Test
	public void zeroProbabilityTest() {
		assertTrue(boardHandler.generateBoard(10, 0).getCells().values().parallelStream().flatMap(map->map.values().stream())
				.allMatch(c -> CellState.LIVE.equals(c.getState())));
	}
	
	@Test
	public void oneProbabilityTest() {
		assertTrue(boardHandler.generateBoard(10, 1).getCells().values().parallelStream().flatMap(map->map.values().stream())
				.allMatch(c -> CellState.DEAD.equals(c.getState())));
	}
	
	@Test
	public void livingNeighbourTest(){
		Board board = boardHandler.generateBoard(10, 1);
		boardHandler.fillNeighbourInfo(board);
		assertTrue(board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.allMatch(c -> c.getLivingNeighbours()==0));
		board = boardHandler.generateBoard(10, 0);
		boardHandler.fillNeighbourInfo(board);
		assertTrue(board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.allMatch(c -> c.getLivingNeighbours()==8));
	}
	
}
