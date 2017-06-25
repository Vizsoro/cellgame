package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellColor;
import vzsolt.gameoflife.cellgame.board.CellState;

public class BoardTest {

	@Test(expected = IllegalArgumentException.class)
	public void initErrorTest() {
		Map<Integer, Map<Integer, Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		Cell cell = new Cell();
		row.put(1, cell);
		cells.put(1, row);
		cells.put(2, row);
		new Board(cells);
	}

	@Test
	public void sizeTest() {
		Map<Integer, Map<Integer, Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		Cell cell = new Cell();
		row.put(1, cell);
		row.put(2, cell);
		cells.put(1, row);
		cells.put(2, row);
		Board board = new Board(cells);
		assertTrue(board.getBoardSize() == 2);
	}

	@Test
	public void equalsTestForCopy() {
		Map<Integer, Map<Integer, Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		Cell cell = new Cell().setColor(CellColor.BLUE).setState(CellState.LIVE);
		row.put(0, cell);
		cells.put(0 , row);
		Board board = new Board(cells);
		Board board2 = board.copy();
		assertFalse(board.equals(board2));
		assertTrue(board2.getCells().get(0).get(0).equals(cell));
		assertFalse(board2.getCells().get(0).get(0) == cell);
	}

	@Test
	public void cellTestForCopy() {
		Map<Integer, Map<Integer, Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		Cell cell = new Cell().setColor(CellColor.BLUE).setState(CellState.LIVE);
		row.put(0, cell);
		cells.put(0 , row);
		Board board = new Board(cells);
		Board board2 = board.copy();
		assertTrue(board2.getCells().get(0).get(0).getColor().equals(CellColor.BLUE));
		assertTrue(board2.getCells().get(0).get(0).getState().equals(CellState.LIVE));
	}

	@Test
	public void cellModificationTestForCopy() {
		Map<Integer, Map<Integer, Cell>> cells = new HashMap<>();
		Map<Integer, Cell> row = new HashMap<>();
		Cell cell1 = new Cell().setColor(CellColor.BLUE).setState(CellState.LIVE);
		row.put(0, cell1);
		cells.put(0, row);
		Board board = new Board(cells);
		Board board2 = board.copy();
		cell1.setColor(CellColor.GREEN).setState(CellState.DEAD);
		assertTrue(board2.getCells().get(0).get(0).getColor().equals(CellColor.BLUE));
		assertTrue(board2.getCells().get(0).get(0).getState().equals(CellState.LIVE));
	}

}
