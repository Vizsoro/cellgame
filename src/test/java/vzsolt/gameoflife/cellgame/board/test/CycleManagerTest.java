package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandler;
import vzsolt.gameoflife.cellgame.board.BoardHandlerImplementation;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellState;
import vzsolt.gameoflife.cellgame.cycle.CycleManager;
import vzsolt.gameoflife.cellgame.cycle.CycleManagerInterface;
import vzsolt.gameoflife.cellgame.rule.RuleDeadToLive;
import vzsolt.gameoflife.cellgame.rule.RuleFactoryImplementation;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToDead;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToLive;

@RunWith(Parameterized.class)
public class CycleManagerTest {

	private CycleManagerInterface cycleManager;

	private BoardHandler boardHandler;

	// public CycleManagerTest(){
	//
	// }

	public CycleManagerTest(CycleManagerInterface cycleManager) {
		this.cycleManager = cycleManager;
		this.boardHandler = new BoardHandlerImplementation();
	}

	@Test
	public void overPopulationTest() {
		Board fullBoard = boardHandler.generateBoard(10, 0);
		assertTrue(cycleManager.calculateNextCycle(fullBoard).getCells().values().stream()
				.flatMap(c -> c.values().stream()).allMatch(c -> CellState.DEAD.equals(c.getState())));
	}

	@Test
	public void underPopulationTest(){
		Board emptyBoard = boardHandler.generateBoard(10, 1);
		assertTrue(cycleManager.calculateNextCycle(emptyBoard).getCells().values().stream().flatMap(c -> c.values().stream())
				.allMatch(c -> CellState.DEAD.equals(c.getState())));
	}

	@Test
	public void liveToLiveTest() {
		Board board = boardHandler.generateBoard(10, 0.5);
		boardHandler.fillNeighbourInfo(board);
		Optional<Cell> liveCell = board.getCells().values().stream().flatMap(c -> c.values().stream())
				.filter(c -> CellState.LIVE.equals(c.getState())
						&& (c.getLivingNeighbours() == 2 || c.getLivingNeighbours() == 3))
				.findAny();

		if (liveCell.isPresent()) {
			board = cycleManager.calculateNextCycle(board);
			int x = liveCell.get().getPosX();
			int y = liveCell.get().getPosY();
			assertTrue(board.getCells().get(x).get(y).getState().equals(CellState.LIVE));
		}

	}

	@Test
	public void deadToLiveTest() {
		Board board = boardHandler.generateBoard(10, 0.5);
		boardHandler.fillNeighbourInfo(board);
		Optional<Cell> deadCell = board.getCells().values().stream().flatMap(map -> map.values().stream())
				.filter(c -> CellState.DEAD.equals(c.getState()) && c.getLivingNeighbours() == 3).findAny();

		if (deadCell.isPresent()) {
			board = cycleManager.calculateNextCycle(board);
			int x = deadCell.get().getPosX();
			int y = deadCell.get().getPosY();
			assertTrue(board.getCells().get(x).get(y).getState().equals(CellState.LIVE));
		}

	}

	@Parameterized.Parameters
	public static Collection<Object> instancesToTest() {
		RuleFactoryImplementation ruleFactory = new RuleFactoryImplementation();
		ruleFactory.addRule(new RuleDeadToLive());
		ruleFactory.addRule(new RuleLiveToDead());
		ruleFactory.addRule(new RuleLiveToLive());
		return Arrays.asList(new Object[] { new CycleManager(new BoardHandlerImplementation(), ruleFactory) });
	}

}
