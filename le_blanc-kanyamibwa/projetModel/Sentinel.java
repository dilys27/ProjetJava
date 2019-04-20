package projetModel;

import projetGui.Map;
import projetGui.MyPanel;

public class Sentinel extends Attacking implements UnitState {

	private Unit u;
	private Game g;
	private Map m;
	private Coordinate c;
	private MyPanel p;
	private Thread t;

	public Sentinel(Unit u, Game g) {
		this.u = u;
		this.g = g;
	}
	
	@Override
	public UnitState.State getstate() {
		return UnitState.State.SENTINEL;
	}
	
	@Override
	public void move(Coordinate c, Map m, MyPanel p) {
		u.move(c, m, p);
	}

	@Override
	public void action(Map m, MyPanel p, Coordinate c) {
		this.m = m;
		this.c = c;
		this.p = p;
		u.getWalk().add(c);
		t = new Thread(this);
		t.start();
	}
	
	public void run() {
		Unit e;
		move(c, m, p);
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
