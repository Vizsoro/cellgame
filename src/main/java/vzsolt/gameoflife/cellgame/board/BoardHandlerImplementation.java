package vzsolt.gameoflife.cellgame.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class BoardHandlerImplementation implements BoardHandler {
	
	private NeighbourInfo findNeighbourInfo(Cell cell, Board board) {
		NeighbourInfo info = new NeighbourInfo();
		int livingNeighbour = 0;
		int blue = 0;
		int green = 0;
		List<Cell> neighbours = getNeighbours(cell.getPosX() , cell.getPosY(), board);
		for (Cell neighbour : neighbours) {
			if (CellState.LIVE.equals(neighbour.getState())) {
				livingNeighbour++;
				if (CellColor.BLUE.equals(neighbour.getColor())) {
					blue++;
				} else {
					green++;
				}
			}
		}
		info.setColor(blue > green ? CellColor.BLUE : CellColor.GREEN);
		info.setLivingNeighbour(livingNeighbour);
		return info;
	}

	
	public List<Cell> getNeighbours(int x, int y, final Board board) {
		List<Cell> neighbours = new ArrayList<Cell>();
		Map<Integer, Map<Integer, Cell>> cells = board.getCells();
		int max = board.getBoardSize() - 1;
		int nextColumn = y == max ? 0 : y + 1;
		int previousColumn = y == 0 ? max : y - 1;
		int nextRow = x == max ? 0 : x + 1;
		int previousRow = x == 0 ? max : x - 1;
		neighbours.add(cells.get(x).get(nextColumn));
		neighbours.add(cells.get(x).get(previousColumn));
		neighbours.add(cells.get(previousRow).get(nextColumn));
		neighbours.add(cells.get(previousRow).get(y));
		neighbours.add(cells.get(previousRow).get(previousColumn));
		neighbours.add(cells.get(nextRow).get(nextColumn));
		neighbours.add(cells.get(nextRow).get(y));
		neighbours.add(cells.get(nextRow).get(previousColumn));
		return neighbours;
	}

	@Override
	public Board generateBoard(int size, double probability) {
		Map<Integer ,Map<Integer, Cell>> board = new TreeMap<>();
		for (int i = 0; i < size; i++) {
			Map<Integer, Cell> row = new TreeMap<>();
			for (int j = 0; j < size; j++) {
				Cell cell = new Cell();
				cell.setPosX(i).setPosY(j);
				cell.setState(Math.random() >= probability ? CellState.LIVE : CellState.DEAD);
				cell.setColor(Math.random() > 0.5 ? CellColor.BLUE : CellColor.GREEN);
				row.put(j, cell);
			}	
			board.put(i, row);
		}
		return new Board(board);
	}

	@Override
	public void fillNeighbourInfo(final Board board) {
		board.getCells().values().parallelStream().flatMap(map->map.values().stream())
		.forEach(cell->cell.setNeighbourInfo(findNeighbourInfo(cell,board)));
	}

}
