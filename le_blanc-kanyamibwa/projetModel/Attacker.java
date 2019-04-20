package projetModel;

import projetGui.Map;
import projetGui.MyPanel;

public class Attacker extends Attacking implements UnitState {

	private Unit u;
	private MyPanel p;
	private Coordinate click;
	private Thread t;
	private Game g;
	private Map m;

	public Attacker(Unit u, Game g) {
		this.u = u;
		this.g = g;
	}

	@Override
	public UnitState.State getstate() {
		return UnitState.State.ATTACKER;
	}

	@Override
	public void move(Coordinate c, Map m, MyPanel p) {
		u.move(c, m, p);
	}

	@Override
	public void action(Map m, MyPanel p, Coordinate c) {
		this.p = p;
		this.click = c;
		this.m = m;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		Unit e;
		move(click, m, p);
		if (u.getRep() == 1) {
			e = search_enemyP1(g, u, p);
		} else {
			e = search_enemyP2(g, u, p);
		}
		if (e != null) {
			attack(u, e, g, p);
		}
	}

}
