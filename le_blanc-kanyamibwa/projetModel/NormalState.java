package projetModel;

import projetGui.Map;
import projetGui.MyPanel;

public class NormalState implements UnitState {

	private Unit u;

	protected NormalState(Unit u) {
		this.u = u;
	}

	@Override
	public void move(Coordinate c, Map m, MyPanel p) {
		u.move(c, m, p);
	}

	@Override
	public void action(Map m, MyPanel p, Coordinate c) {
		move(c, m, p);
	}

	@Override
	public State getstate() {
		return State.NORMAL;
	}

}
