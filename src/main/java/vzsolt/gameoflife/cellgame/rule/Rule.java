package vzsolt.gameoflife.cellgame.rule;

import vzsolt.gameoflife.cellgame.board.Cell;

public interface Rule {

	boolean isAvaliable(Cell cell);
	
	void apply(Cell cell);
	
}
