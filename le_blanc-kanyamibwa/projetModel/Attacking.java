package projetModel;

import projetGui.MyPanel;
import projetModel.UnitState;

public abstract class Attacking extends Thread {
	
	// RECHERCHE DE L'ENNEMI POUR LE JOUEUR 1
	public Unit search_enemyP1(Game g, Unit u, MyPanel p) {
		Player p1 = g.getP1();
		Player p2 = g.getP2();
		if (p1.getUnit().contains(u)) {
			while (true) {
				for (int i = -3; i <= 3; i++) {
					for (int j = -3; j <= 3; j++) {
						for (Unit e : p2.getUnit()) {
							int a = u.getPos().getX() + i;
							int z = u.getPos().getY() + j;
							Coordinate ep = new Coordinate(a, z);
							if (e.getPos().equals(ep)) {
								System.out.println("Vie de l'unité p2:" + e.getPv());
								if (u.getState() == UnitState.State.SENTINEL) {
									Coordinate d = new Coordinate(a - i, z - j); 
									u.move(d, g.getM(), p);
								}
								if (u.getState() == UnitState.State.ATTACKER) {
									Coordinate c = new Coordinate(e.getPos().getX() - 1, e.getPos().getY());
									u.move(c, g.getM(), p);
								}
								return e;
							}
						}
					}
				}
				if (u.getState() == UnitState.State.SENTINEL) {
					walking(u, g, p);
				} else {
					u.getWalk().clear();
				}
			}
		}
		return null;

	}

	// RECHERCHE DE L'ENNEMI POUR LE JOUEUR 2
	public Unit search_enemyP2(Game g, Unit u, MyPanel p) {
		Player p1 = g.getP1();
		Player p2 = g.getP2();
		if (p2.getUnit().contains(u)) {
			while (true) {
				if (u.getState() == UnitState.State.ATTACKER || u.getState() == UnitState.State.SENTINEL) {
					for (int i = -3; i <= 3; i++) {
						for (int j = -3; j <= 3; j++) {
							for (Unit e : p1.getUnit()) {
								int a = u.getPos().getX() + i;
								int z = u.getPos().getY() + j;
								if (e.getPos().equals(new Coordinate(a, z))) {
									System.out.println("Vie de l'unité p1:" + e.getPv());
									if (u.getState() == UnitState.State.SENTINEL) {
										Coordinate d = new Coordinate(a - i, z - j); 
										u.move(d, g.getM(), p);
									}
									if (u.getState() == UnitState.State.ATTACKER) {
										Coordinate c = new Coordinate(e.getPos().getX() - 1, e.getPos().getY());
										u.move(c, g.getM(), p);
									}
									return e;
								}
							}
						}
					}
				}
				if (u.getState() == UnitState.State.SENTINEL) {
					walking(u, g, p);
				} else {
					u.getWalk().clear();
				}
			}
		}
		return null;
	}
	
	// ATTAQUE SUR L'ENNEMI
	public void attack(Unit i, Unit e, Game g, MyPanel p) {
		while (e != null) {
			e.setPv(e.getPv() - 1);
			e.dies(g.getM().map, p);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (i.getState() == UnitState.State.ATTACKER || i.getState() == UnitState.State.SENTINEL) {
				if (i.getRep() == 1) {
					e = search_enemyP1(g, i, p);
				} else {
					e = search_enemyP2(g, i, p);
				}
			} else
				e = null;
		}
	}
	
	// ALLERS-RETOURS DE LA SENTINELLE
	public void walking(Unit u, Game g, MyPanel p) {
		int w = u.getWalk().size();
		if (w > 1) {
			u.move(u.getWalk().get(w - 2), g.getM(), p);
			u.move(u.getWalk().get(w - 1), g.getM(), p);
		}
	}
}
