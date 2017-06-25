package vzsolt.gameoflife.cellgame.rule;

import java.util.Set;

import vzsolt.gameoflife.cellgame.board.Cell;

public interface RuleFactory {

	Set<Rule> findRules(Cell cell);
}
