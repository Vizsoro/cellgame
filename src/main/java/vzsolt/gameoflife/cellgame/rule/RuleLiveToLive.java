package vzsolt.gameoflife.cellgame.rule;

import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellState;

@Component
public class RuleLiveToLive implements Rule {

	public boolean isAvaliable(Cell cell) {
		return cell.getLivingNeighbours() < 4 && cell.getLivingNeighbours() > 1
				&& CellState.LIVE.equals(cell.getState());
	}

	public void apply(Cell cell) {
		//no action needed
	}

}
