package vzsolt.gameoflife.cellgame.cycle;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.rule.Rule;
import vzsolt.gameoflife.cellgame.rule.RuleFactory;

//@Component
public class CycleManager implements CycleManagerInterface {

	@Autowired
	private BoardHandler boardHandler;
	@Autowired
	private RuleFactory ruleFactory;
	private boolean previous;

	@Override
	public void moveToNextCycle() {

		previous = true;

		Board board = boardHandler.getBoard();

		fillNeighbourInfo(board);

		boardHandler.savePreviousBoard(board.copy());

		calculateCycle(board);

	}

	private void fillNeighbourInfo(final Board board) {
		board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.forEach(boardHandler::setNeighbourInfo);
	}

	private void calculateCycle(final Board board) {
		board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.forEach(CycleManager.this::applyRules);
	}

	private void applyRules(Cell cell) {
		Set<Rule> rules = ruleFactory.findRules(cell);
		for (Rule rule : rules) {
			rule.apply(cell);
		}
	}

	@Override
	public void moveToPreviousCycle() {
		previous = false;
		boardHandler.saveBoard(boardHandler.getPreviousBoard());

	}

	@Override
	public Map<Integer, Map<Integer, Cell>> getCurrentState() {
		return boardHandler.getBoard().getCells();
	}

	@Override
	public void startGame(int size, double probability) {
		boardHandler.saveBoard(boardHandler.generateBoard(size, probability));
		fillNeighbourInfo(boardHandler.getBoard());
	}

	public CycleManager(BoardHandler boardHandler, RuleFactory ruleFactory) {
		this.boardHandler = boardHandler;
		this.ruleFactory = ruleFactory;
	}

	@Override
	public boolean previousEnable() {
		return previous;
	}
	
	@Override
	public Board getBoardCopy(){
		return boardHandler.getBoard().copy();
	}

}
