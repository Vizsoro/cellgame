package vzsolt.gameoflife.cellgame.board.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import vzsolt.gameoflife.cellgame.board.Cell;
import vzsolt.gameoflife.cellgame.board.CellColor;
import vzsolt.gameoflife.cellgame.board.CellState;
import vzsolt.gameoflife.cellgame.board.NeighbourInfo;
import vzsolt.gameoflife.cellgame.rule.Rule;
import vzsolt.gameoflife.cellgame.rule.RuleDeadToLive;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToDead;
import vzsolt.gameoflife.cellgame.rule.RuleLiveToLive;

public class RuleTest {
	
	
	private Rule ruleDeadToLive = new RuleDeadToLive();
	private Rule ruleLiveToLive = new RuleLiveToLive();
	private Rule ruleLiveToDead = new RuleLiveToDead();
	
	
	@Test
	public void deadToLiveAvaliableTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(3);
		Cell cell = new Cell();
		cell.setState(CellState.DEAD);
		cell.setNeighbourInfo(info);
		assertTrue(ruleDeadToLive.isAvaliable(cell));
	}
	
	@Test
	public void deadToLiveApplyTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(3);
		Cell cell = new Cell();
		cell.setState(CellState.DEAD);
		cell.setNeighbourInfo(info);
		ruleDeadToLive.apply(cell);
		assertTrue(CellState.LIVE.equals(cell.getState()));
		assertTrue(CellColor.BLUE.equals(cell.getColor()));
	}
	
	@Test
	public void liveToLiveAvaliableTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(3);
		Cell cell = new Cell();
		cell.setState(CellState.LIVE);
		cell.setNeighbourInfo(info);
		assertTrue(ruleLiveToLive.isAvaliable(cell));
		info.setLivingNeighbour(2);
		assertTrue(ruleLiveToLive.isAvaliable(cell));
	}
	
	@Test
	public void liveToLiveApplyTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(3);
		Cell cell = new Cell();
		cell.setColor(CellColor.GREEN);
		cell.setState(CellState.LIVE);
		cell.setNeighbourInfo(info);
		ruleLiveToLive.apply(cell);
		assertTrue(CellState.LIVE.equals(cell.getState()));
		assertTrue(CellColor.GREEN.equals(cell.getColor()));
	}
	
	@Test
	public void liveToDeadAvaliableTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(1);
		Cell cell = new Cell();
		cell.setState(CellState.LIVE);
		cell.setNeighbourInfo(info);
		assertTrue(ruleLiveToDead.isAvaliable(cell));
		info.setLivingNeighbour(5);
		assertTrue(ruleLiveToDead.isAvaliable(cell));
	}
	
	@Test
	public void liveToDeadApplyTest(){
		NeighbourInfo info = new NeighbourInfo();
		info.setColor(CellColor.BLUE);
		info.setLivingNeighbour(4);
		Cell cell = new Cell();
		cell.setColor(CellColor.GREEN);
		cell.setState(CellState.LIVE);
		cell.setNeighbourInfo(info);
		ruleLiveToDead.apply(cell);
		assertTrue(CellState.DEAD.equals(cell.getState()));
		assertTrue(CellColor.GREEN.equals(cell.getColor()));
	}
	
	

}
