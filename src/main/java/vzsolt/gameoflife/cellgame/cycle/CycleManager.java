package vzsolt.gameoflife.cellgame.cycle;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.rule.Rule;
import vzsolt.gameoflife.cellgame.rule.RuleFactory;

@Component
public class CycleManager implements CycleManagerInterface {

	@Autowired
	private BoardHandler boardHandler;
	@Autowired
	private RuleFactory ruleFactory;
	
	@Override
	public Board calculateNextCycle(final Board board){
		Board localBoard = board.copy();
		
		boardHandler.fillNeighbourInfo(localBoard);

		calculateCycle(localBoard);
		
		return localBoard;
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


	public CycleManager(BoardHandler boardHandler, RuleFactory ruleFactory) {
		this.boardHandler = boardHandler;
		this.ruleFactory = ruleFactory;
	}

}
