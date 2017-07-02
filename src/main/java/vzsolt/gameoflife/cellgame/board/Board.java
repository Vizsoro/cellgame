package vzsolt.gameoflife.cellgame.board;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Board {

	
	private Map<Integer ,Map<Integer, Cell>> cells;
	private int boardSize;
	
	@JsonCreator
	public Board(@JsonProperty("cells") Map<Integer ,Map<Integer, Cell>> cells){	
		checkCells(cells);
		this.cells = cells;
		this.boardSize = cells.size();
	}
	
	private void checkCells(Map<Integer ,Map<Integer, Cell>> cells){
		int rows = cells.size();
		if(!cells.values().parallelStream().allMatch(map->map.size() == rows)){
			throw new IllegalArgumentException();
		}
	}
	
	
	public Map<Integer ,Map<Integer, Cell>> getCells() {
		return cells;
	}

	
	public int getBoardSize() {
		return boardSize;
	}
	
	public Board copy(){
		Map<Integer ,Map<Integer, Cell>> copyCells = new TreeMap<>();
		for (Entry<Integer, Map<Integer, Cell>> row : cells.entrySet()) {
			Map<Integer,Cell> copyRow = new TreeMap<>();
			for(Entry<Integer,Cell> entry : row.getValue().entrySet()){
				copyRow.put(entry.getKey(), new Cell(entry.getValue()));
			}
			copyCells.put(row.getKey(), copyRow);
		}
		return new Board(copyCells);		
	}
	
}
