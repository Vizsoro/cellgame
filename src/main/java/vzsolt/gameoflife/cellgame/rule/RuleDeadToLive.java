package vzsolt.gameoflife.cellgame.rule;

import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellState;

@Component
public class RuleDeadToLive implements Rule {

	public boolean isAvaliable(Cell cell) {
		return cell.getLivingNeighbours() == 3 && CellState.DEAD.equals(cell.getState());
	}

	public void apply(Cell cell) {
		cell.setState(CellState.LIVE);
		cell.setColor(cell.getSurroundingColor());
	}

}
