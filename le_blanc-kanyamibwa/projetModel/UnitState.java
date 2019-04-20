package projetModel;

import projetGui.Map;
import projetGui.MyPanel;

public interface UnitState {
	public enum State {
		NORMAL, HARVESTER, ATTACKER, SENTINEL, NO_ROLE
	}

	State getstate();

	void move(Coordinate c, Map m, MyPanel p);

	void action(Map m, MyPanel p, Coordinate c);

}
