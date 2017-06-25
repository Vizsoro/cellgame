package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.BoardHandlerImplementation;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.NeighbourInfo;
import vzsolt.gameoflife.cellgame.board.CellColor;
import vzsolt.gameoflife.cellgame.board.CellState;

public class BoardHandlerTest {

	private BoardHandler boardHandler = new BoardHandlerImplementation();

	@Test
	public void neighbourTest() {
		boardHandler.saveBoard(boardHandler.generateBoard(4, 0.5));
		List<Cell> neighbours = boardHandler.getNeighbours(0,0);
		boolean neighbour1 = neighbours.stream().anyMatch(c -> c.getPosX() == 0 && c.getPosY() == 1);
		boolean neighbour2 = neighbours.stream().anyMatch(c -> c.getPosX() == 1 && c.getPosY() == 3);
		assertTrue(neighbour1);
		assertTrue(neighbour2);
	}

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
		boardHandler.saveBoard(new Board(cells));
		NeighbourInfo info = boardHandler.findNeighbourInfo(0,0);
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
		boardHandler.saveBoard(boardHandler.generateBoard(4, 0));
		NeighbourInfo info = boardHandler.findNeighbourInfo(0,0);
		assertTrue(info.getLivingNeighbour() == 8);
		
	}
	
}
