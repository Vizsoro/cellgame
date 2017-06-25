package vzsolt.gameoflife.cellgame.board;

import java.util.List;


public interface BoardHandler {
	
	Board getPreviousBoard();
	
	void saveBoard(Board board);

	void setNeighbourInfo(Cell cell);
	
	List<Cell> getNeighbours(int x, int y);
	
	Board generateBoard(int size, double probability);

	void savePreviousBoard(Board board);
	
	Board getBoard();
	
	NeighbourInfo findNeighbourInfo(int x, int y);
}