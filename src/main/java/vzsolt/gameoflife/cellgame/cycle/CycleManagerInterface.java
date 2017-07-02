package vzsolt.gameoflife.cellgame.cycle;

import java.util.Map;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.Cell;

public interface CycleManagerInterface {

	void startGame (int size, double probability);
	
	void moveToNextCycle();
	
	void moveToPreviousCycle();
	
	Map<Integer, Map<Integer, Cell>> getCurrentState();
	
	boolean previousEnable();

	Board getBoardCopy();
	
	void startGame(Board board);
	
	public void fillNeighbourInfo(final Board board);

}