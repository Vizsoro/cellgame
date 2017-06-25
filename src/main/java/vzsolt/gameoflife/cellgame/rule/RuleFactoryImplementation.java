package vzsolt.gameoflife.cellgame.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vzsolt.gameoflife.cellgame.board.Cell;

@Component
public class RuleFactoryImplementation implements RuleFactory{

	@Autowired
	private final List<Rule> allRule = new ArrayList<Rule>();

	
	public Set<Rule> findRules(Cell cell) {
		Set<Rule> rules = new HashSet<Rule>();
		for(Rule rule : allRule){
			if(rule.isAvaliable(cell)){
				rules.add(rule);
			}
		}
		return rules;
	}
	
	public void addRule(Rule rule){
		allRule.add(rule);
	}

}
