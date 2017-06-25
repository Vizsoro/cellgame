package vzsolt.gameoflife.cellgame.cycle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.rule.Rule;
import vzsolt.gameoflife.cellgame.rule.RuleFactory;

@Component
public class InMemoryCycleManager implements CycleManagerInterface {
	@Autowired
	private BoardHandler boardHandler;
	@Autowired
	private RuleFactory ruleFactory;
	private Map<Integer, Board> boards;
	private int actualCycle;
	private int maxCycle;

	@Override
	public void moveToNextCycle() {

		if(actualCycle == maxCycle){
			++actualCycle;
			++maxCycle;
			
			Board board = boardHandler.getBoard();
			
			fillNeighbourInfo(board);
			
			calculateCycle(board);
			
			boards.put(actualCycle, board.copy());
		} else if(actualCycle < maxCycle && actualCycle > 0) {
			++actualCycle;
			boardHandler.saveBoard(boards.get(actualCycle));
		}

	}

	private void fillNeighbourInfo(final Board board) {
		board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.forEach(boardHandler::setNeighbourInfo);
	}

	private void calculateCycle(final Board board) {
		board.getCells().values().parallelStream().flatMap(map->map.values().stream())
				.forEach(InMemoryCycleManager.this::applyRules);
	}

	private void applyRules(Cell cell) {
		Set<Rule> rules = ruleFactory.findRules(cell);
		for (Rule rule : rules) {
			rule.apply(cell);
		}
	}

	@Override
	public void moveToPreviousCycle() {
		--actualCycle;
		boardHandler.saveBoard(boards.get(actualCycle));

	}

	@Override
	public Map<Integer, Map<Integer, Cell>> getCurrentState() {
		return boardHandler.getBoard().getCells();
	}

	@Override
	public void startGame(int size, double probability) {
		boards = new HashMap<>();
		actualCycle = 1;
		maxCycle = 1;
		boardHandler.saveBoard(boardHandler.generateBoard(size, probability));
		boards.put(1, boardHandler.getBoard().copy());
		fillNeighbourInfo(boardHandler.getBoard());
	}

	public InMemoryCycleManager(BoardHandler boardHandler, RuleFactory ruleFactory) {
		this.boardHandler = boardHandler;
		this.ruleFactory = ruleFactory;
	}

	@Override
	public boolean previousEnable() {
		return actualCycle -1 > 0;
	}
	
	@Override
	public Board getBoardCopy(){
		return boardHandler.getBoard().copy();
	}

}
