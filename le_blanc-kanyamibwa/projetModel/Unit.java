package projetModel;

import projetModel.UnitEvent.ChangeType;
import projetModel.UnitState.State;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import pathfinding.Path;
import projetGui.Map;
import projetGui.MyPanel;

public class Unit {
	private Game game;
	private Coordinate pos; // position de l'unit�
	private int SPEED = 500; // vitesse de d�placement de l'unit�
	private int pv; // points de vie de l'unit�
	private int id; // diff�rencie les unit�s
	private int rep; // repr�sentation de l'unit�
	private boolean selected; // s�lection de l'unit�
	private UnitState state; // r�le de l'unit�
	private List<UnitObserver> observers;
	private ArrayList<Coordinate> walk = new ArrayList<Coordinate>();

	public Unit(Game game, int rep, Coordinate p) {
		this.game = game;
		this.pos = p;
		this.rep = rep;
		this.pv = 100;
		this.observers = new ArrayList<>();
		this.selected = false;
		this.state = new NormalState(this);
		this.id = 0;
	}

	public boolean setXY(Coordinate next) {
		if (game.isTraversable(next) || (game.getP1().getUnit().contains(this) && iftherep1(next) != null)
				|| (game.getP2().getUnit().contains(this) && iftherep2(next) != null)) {
			this.pos = next;
			return true;
		}
		return false;
	}

	// VERIFIE SI UNE UNITE DE LA MEME EQUIPE EXISTE
	public Unit iftherep2(Coordinate c) {
		for (Unit e : game.getP2().getUnit()) {
			if (e.getPos().equals(c)) {
				return e;
			}
		}
		return null;
	}

	public Unit iftherep1(Coordinate c) {
		for (Unit e : game.getP1().getUnit()) {
			if (e.getPos().equals(c)) {
				return e;
			}
		}
		return null;

	}

	public Coordinate getPos() {
		return pos;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getRep() {
		return rep;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Coordinate> getWalk() {
		return walk;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public State getState() {
		return state.getstate();
	}

	// PLACE L'UNITE DANS LA CARTE SI C'EST TRAVERSABLE
	public void place(char[][] map) {
		if (game.isTraversable(pos))
			map[pos.getX()][pos.getY()] = (char) rep;
	}

	// DEPLACEMENT DE L'UNITE AVEC LE PLUS COURT CHEMIN
	public void move(Coordinate goal, Map m, MyPanel p) {
		ArrayList<Coordinate> path = Path.path(pos, goal);
		path.remove(0); // supprime la premi�re coordonn�e car c'est la position de d�part
		int speed = SPEED;
		for (Coordinate next : path) {
			if (onMud())
				speed = speed * 2;
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			m.updateU(this, next, p);
			speed = SPEED;
		}
	}

	// SUPPRIME LA POSITION PRECEDENTE DE L'UNITE DE LA CARTE
	public void reset(Coordinate c, char[][] map) {
		Unit i = iftherep1(c);
		Unit j = iftherep2(c);
		if (i != null) {
			if (map[c.getX()][c.getY()] == i.getRep())
				map[c.getX()][c.getY()] = (char) i.getRep();
		} else if (j != null) {
			if (map[c.getX()][c.getY()] == j.getRep())
				map[c.getX()][c.getY()] = (char) j.getRep();
		} else {
			map[c.getX()][c.getY()] = ' ';
		}
	}

	// RALENTIE L'UNITE SE TROUVANT DANS LA ZONE BOUEUSE
	public boolean onMud() {
		for (Mud m : game.getMuds()) {
			if (pos.equals(m.getPos())) {
				return true;
			}
		}
		return false;
	}

	// ENVOIE L'UNITE A LA DESTINATION DU PORTAIL
	public boolean onPortal(MyPanel panel) { 
		for (Portal p : game.getPortals()) {
			if (pos.equals(p.getPos())) {
				pos = p.getDest();
				String output = "Joueur " + rep + " :\n"
						+ "Votre unit� a �t� t�l�port�e � la position suivante (" + p.getDest().getX() + ", " + p.getDest().getY() + ")";
				JOptionPane.showMessageDialog(panel, output, "T�l�portation", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	// CHANGEMENT DU R�LE DE L'UNITE
	public State changeState(MyPanel p, Player a) {
		Object[] options = { "R�colteur (Gratuit)", "Sentinelle (20 pi�ces)", "Attaquant (30 pi�ces)",
				"Normal (Gratuit)" };
		String n = (String) JOptionPane.showInputDialog(p, "Choississez un r�le pour l'unit�:",
				"Affectation du r�le de l'unit�", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == "R�colteur (Gratuit)") {
			setState(new Harvester(this, game));
			return UnitState.State.HARVESTER;
		} else if (n == "Sentinelle (20 pi�ces)") {
			if (a.enough_gold(20, "changer le r�le de l'unit� en sentinelle", p)) {
				setState(new Sentinel(this, game));
				return UnitState.State.SENTINEL;
			}
			return UnitState.State.NO_ROLE;
		} else if (n == "Attaquant (30 pi�ces)") {
			if (a.enough_gold(30, "changer le r�le de l'unit� en attaquant", p)) {
				setState(new Attacker(this, game));
				return UnitState.State.ATTACKER;
			}
			return UnitState.State.NO_ROLE;
		} else if (n == "Normal (Gratuit)") {
			setState(new NormalState(this));
			return UnitState.State.NORMAL;
		} else {
			setState(state);
			return UnitState.State.NO_ROLE;
		}
	}

	// ACTION DE L'UNITE PAR RAPPORT A SON R�LE
	public void action(Map m, MyPanel p, Coordinate c) {
		state.action(m, p, c);
	}

	// MORT DE L'UNITE
	public void dies(char[][] map, MyPanel p) {
		if (pv <= 0) {
			int i = 0;
			map[pos.getX()][pos.getY()] = ' ';
			if (game.getP1().getUnit().contains(this)) {
				game.getP1().getUnit().remove(this);
				i = 1;
			} else if (game.getP2().getUnit().contains(this)) {
				game.getP2().getUnit().remove(this);
				i = 2;
			}
			map[pos.getX()][pos.getY()] = ' ';
			String output = "Joueur " + i + " : Une de vos unit�s est morte";
			JOptionPane.showMessageDialog(p, output, "", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void register(UnitObserver o) {
		observers.add(o);
	}

	public void unregister(UnitObserver o) {
		observers.remove(o);
	}

	public void notifyObserver(List<UnitEvent> events) {
		for (UnitObserver unitObserver : observers) {
			unitObserver.notify(events);
		}
	}

	public void notifyMove(Coordinate prec) {
		List<UnitEvent> events = new ArrayList<>();
		events.add(new UnitEvent(prec, ChangeType.LEAVE));
		events.add(new UnitEvent(pos, ChangeType.ENTER));
		notifyObserver(events);
	}

}