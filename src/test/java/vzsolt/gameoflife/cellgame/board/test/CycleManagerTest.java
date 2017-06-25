package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import vzsolt.gameoflife.cellgame.board.Board;
import vzsolt.gameoflife.cellgame.board.BoardHandlerImplementation;
import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellState;
import vzsolt.gameoflife.cellgame.cycle.CycleManager;
import vzsolt.gameoflife.cellgame.cycle.CycleManagerInterface;
import vzsolt.gameoflife.cellgame.cycle.InMemoryCycleManager;
import vzsolt.gameoflife.cellgame.rule.RuleDeadToLive;
import vzsolt.gameoflife.cellgame.rule.RuleFactoryImplementation;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToDead;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToLive;

@RunWith(Parameterized.class)
public class CycleManagerTest {

	private CycleManagerInterface cycleManager;
	
//	public CycleManagerTest(){
//		
//	}

	public CycleManagerTest(CycleManagerInterface cycleManager){
		this.cycleManager = cycleManager;
	}
	
	@Test
	public void overPopulationTest() {
		cycleManager.startGame(10, 0);
		cycleManager.moveToNextCycle();
		assertTrue(cycleManager.getCurrentState().values().stream().flatMap(c -> c.values().stream())
				.allMatch(c -> CellState.DEAD.equals(c.getState())));
	}

	@Test
	public void previousAfteroverPopulationTest() {
		cycleManager.startGame(10, 0);
		cycleManager.moveToNextCycle();
		cycleManager.moveToPreviousCycle();
		assertTrue(cycleManager.getCurrentState().values().stream().flatMap(c -> c.values().stream())
				.allMatch(c -> CellState.LIVE.equals(c.getState())));
	}

	@Test
	public void underPopulationTest() {
		cycleManager.startGame(10, 1);
		assertTrue(cycleManager.getCurrentState().values().stream().flatMap(c -> c.values().stream())
				.allMatch(c -> CellState.DEAD.equals(c.getState())));
		cycleManager.moveToNextCycle();
		assertTrue(cycleManager.getCurrentState().values().stream().flatMap(c -> c.values().stream())
				.allMatch(c -> CellState.DEAD.equals(c.getState())));
	}

	@Test
	public void liveToLiveTest() {
		cycleManager.startGame(10, 0.5);
		Optional<Cell> liveCell = cycleManager.getCurrentState().values().stream().flatMap(c -> c.values().stream())
				.filter(c -> CellState.LIVE.equals(c.getState())
						&& (c.getLivingNeighbours() == 2 || c.getLivingNeighbours() == 3))
				.findAny();

		if (liveCell.isPresent()) {
			cycleManager.moveToNextCycle();
			int x = liveCell.get().getPosX();
			int y = liveCell.get().getPosY();
			assertTrue(cycleManager.getCurrentState().get(x).get(y).getState()
					.equals(CellState.LIVE));
		}

	}
	
	@Test
	public void deadToLiveTest() {
		cycleManager.startGame(10, 0.5);
		Optional<Cell> deadCell = cycleManager.getCurrentState().values().stream().flatMap(map -> map.values().stream())
				.filter(c -> CellState.DEAD.equals(c.getState())
						&& c.getLivingNeighbours() == 3)
				.findAny();

		if (deadCell.isPresent()) {
			cycleManager.moveToNextCycle();
			int x = deadCell.get().getPosX();
			int y = deadCell.get().getPosY();
			assertTrue(cycleManager.getCurrentState().get(x).get(y).getState()
					.equals(CellState.LIVE));
		}

	}
	
	@Test
	public void cycleManagementTest(){
		int size = 2;
		cycleManager.startGame(size, 0.5);
		Board currentBoard = cycleManager.getBoardCopy();
		Map<Integer,Map<Integer,Cell>> currentState = currentBoard.getCells();
		cycleManager.moveToNextCycle();
		cycleManager.moveToPreviousCycle();
		assertTrue(cycleManager.getCurrentState().values().stream()
				.flatMap(map->map.values().stream())
				.allMatch(c->currentState.get(c.getPosX()).get(c.getPosY()).equals(c)));
	}
	
	@Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
    	RuleFactoryImplementation ruleFactory = new RuleFactoryImplementation();
		ruleFactory.addRule(new RuleDeadToLive());
		ruleFactory.addRule(new RuleLiveToDead());
		ruleFactory.addRule(new RuleLiveToLive());
        return Arrays.asList(
                    new Object[]{new CycleManager(new BoardHandlerImplementation(), ruleFactory)},
                    new Object[]{new InMemoryCycleManager(new BoardHandlerImplementation(), ruleFactory)}
        );
    }

}
