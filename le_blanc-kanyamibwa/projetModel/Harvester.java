package projetModel;

import javax.swing.JOptionPane;

import projetGui.Map;
import projetGui.MyPanel;
import projetModel.Game;

public class Harvester implements UnitState, Runnable {

	private Unit u;
	private int carry_g = 0;
	private Coordinate gold;
	private Map m;
	private MyPanel p;
	private Coordinate click;
	private Game g;
	private Thread t;

	public Harvester(Unit u, Game g) {
		this.u = u;
		this.g = g;
	}

	@Override
	public State getstate() {
		return State.HARVESTER;
	}

	@Override
	public void move(Coordinate c, Map m, MyPanel p) {
		u.move(c, m, p);
	}

	@Override
	public void action(Map m, MyPanel p, Coordinate c) {
		this.m = m;
		this.p = p;
		this.click = c;
		t = new Thread(this);
		t.start();

	}
	
	private Coordinate pos_gold(Coordinate p) {
		Coordinate pos1 = new Coordinate(click.getX()-1, click.getY());
		Coordinate pos2 = new Coordinate(click.getX()+1, click.getY());
		Coordinate pos3 = new Coordinate(click.getX(), click.getY()-1);
		Coordinate pos4 = new Coordinate(click.getX(), click.getY()+1);
		if(g.isTraversable(pos1)) {
			return pos1;
		}else if(g.isTraversable(pos2)) {
			return pos2;
		}else if(g.isTraversable(pos3)) {
			return pos3;
		}else if(g.isTraversable(pos4)) {
			return pos4;
		}
		return null;
	}

	public Gold search_gold(Coordinate c) {
		for (Gold o : g.getGolds()) {
			if (o.getPos().equals(c)) {
				gold = pos_gold(o.getPos());
				return o;
			}
		}
		return null;
	}

	public Gold dig(Gold o, char[][] map, MyPanel p) {
		o.setNB_PIECES(o.getNB_PIECES() - 10);
		g.setGOLD(g.getGOLD() - 10);
		carry_g += 10;
		if (o.getNB_PIECES() <= 0) {
			o.vanish(map);
			g.getGolds().remove(o);
			o = null;
		}
		return o;
	}

	public void retour(MyPanel p) {
		Player p1 = g.getP1();
		Player p2 = g.getP2();
		if (carry_g > 0) {
			for (Unit i : p1.getUnit()) {
				if (i == u) {
					Coordinate c = new Coordinate(p1.getQG()[3].getX(), p1.getQG()[3].getY()+1);
					move(c, g.getM(), p);
					carry_g -= 10;
					p1.setTreasure(p1.getTreasure() + 10);
				}
			}
			for (Unit j : p2.getUnit()) {
				if (j == u) {
					Coordinate c = new Coordinate(p2.getQG()[0].getX(), p2.getQG()[0].getY()-1);
					move(c, g.getM(), p);
					carry_g -= 10;
					p2.setTreasure(p2.getTreasure() + 10);
				}
			}
		}
	}

	@Override
	public void run() {
		Gold o = search_gold(click);
		if (o == null) {
			move(click, m, p);
		} else if (o != null) {
			move(gold, m, p); // va se déplacer vers la pile d'or
			String output = "Voulez-vous creuser de l'or ?";
			int q = JOptionPane.showConfirmDialog(p, output, "Gold", JOptionPane.YES_NO_OPTION);
			if (q == JOptionPane.YES_OPTION) {
				while (o != null) {
					if (carry_g == 0) {
						o = dig(o, m.map, p); // creuse de l'or dans la pile
						System.out.println("Gold left : " + g.getGOLD());
						retour(p); // retourne à la base
					}
					move(gold, m, p); // va se placer vers la pile d'or
				}
				
			}
		}
	}

}
