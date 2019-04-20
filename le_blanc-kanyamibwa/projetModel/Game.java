package projetModel;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import pathfinding.Path;
import projetGui.Map;
import projetGui.MyPanel;

public class Game {
	private Map m;
	public static final int w = 39;
	public static final int h = 3;
	public static final int NB_PLAYERS = 2;
	private Player[] players;
	private static int GOLD = 0; // nb de pièces d'or sur le terrain
	private static ArrayList<Gold> golds;
	private static ArrayList<Mud> muds;
	private static ArrayList<Tree> trees;
	private static ArrayList<Portal> portals;
	private Path pathFinding;

	public Game(Map m) {
		this.m = m;
		this.players = new Player[NB_PLAYERS];
		golds = new ArrayList<Gold>();
		muds = new ArrayList<Mud>();
		trees = new ArrayList<Tree>();
		portals = new ArrayList<Portal>();
		init_game();
		pathFinding = new Path(this);
	}

	public int getGOLD() {
		return GOLD;
	}

	public void setGOLD(int gOLD) {
		GOLD = gOLD;
	}

	public Player getP1() {
		return players[0];
	}

	public Player getP2() {
		return players[1];
	}

	public ArrayList<Gold> getGolds() {
		return golds;
	}

	public ArrayList<Mud> getMuds() {
		return muds;
	}

	public ArrayList<Tree> getTrees() {
		return trees;
	}

	public ArrayList<Portal> getPortals() {
		return portals;
	}

	public Map getM() {
		return m;
	}

	public void setM(Map m) {
		this.m = m;
	}

	public Path getPath() {
		return pathFinding;
	}

	private void init_game() {
		for (int k = 0; k < NB_PLAYERS; k++) {
			players[k] = new Player(this, k + 1);
		}
		int p1 = 0;
		int p2 = 0;
		for (int i = 0; i < Map.height; i++) {
			for (int j = 0; j < Map.width; j++) {
				if (getM().map[i][j] == 'B') {
					players[0].setQG(p1, i, j);
					System.out.println("Bleu: " + i + " " + j);
					p1++;
				} else if (getM().map[i][j] == 'R') {
					players[1].setQG(p2, i, j);
					System.out.println("Rouge: " + i + " " + j);
					p2++;
				}
			}
		}
		for (int k = 0; k < NB_PLAYERS; k++) {
			for (int i = 0; i < 3; i++) {
				players[k].createUnit();
			}

		}

		int g = random(20, 5); // déterminer aléatoirement le nombre de piles d'or
		cord_gold(g, golds);
		int mu = random(15, 5); // déterminer aléatoirement le nombre des zones boueuses
		cord_mud(mu, muds);
		int t = random(30, 15); // déterminer aléatoirement le nombre d'arbres
		cord_tree(t, trees);
		System.out.println("Pièces d'or sur le terrain : " + Game.GOLD + ", Nombre de piles d'or : " + g);

		for (int i = 0; i < Map.height; i++) {
			for (int j = 0; j < Map.width; j++) {
				if (getM().map[i][j] == 'P') {
					portals.add(new Portal(new Coordinate(i, j), true));
				}
			}
		}
		int i = portals.size()-1;
		System.out.println("NB de téléporteurs : "+ portals.size());
		for (Portal p : portals) {
			if (i == 0) {
				p.setDest(portals.get(0).getPos());
				break;
			}
			p.setDest(portals.get(i).getPos());
			i--;
		}
	}

	// GENERER UN NOMBRE ALEATOIRE ENTRE X ET Y
	private int random(int x, int y) {
		Random rand = new Random();
		int i = rand.nextInt(x - y) + y;
		return i;
	}

	// GENERER DES COORDONNEES ALEATOIRES POUR LES PILES D'OR
	public void cord_gold(int k, ArrayList<Gold> l) {
		for (int i = 0; i < k; i++) {
			int x = random(w, h);
			int y = random(w, h);
			while (getM().map[x][y] != ' ') {// vérifier si c'est possible de
												// placer une pile d'or
				x = random(w, h);
				y = random(w, h);
			}
			l.add(new Gold(new Coordinate(x, y), false));
			l.get(i).place(getM().map);
			Game.GOLD += l.get(i).getNB_PIECES();

		}
	}

	// GENERER DES COORDONNEES ALEATOIRES POUR LES ZONES BOUEUSES
	public void cord_mud(int k, ArrayList<Mud> l) {
		for (int i = 0; i < k; i++) {
			int x = random(w, h);
			int y = random(w, h);
			while (getM().map[x][y] != ' ') {
				x = random(w, h);
				y = random(w, h);
			}
			l.add(new Mud(new Coordinate(x, y), true));
			l.get(i).place(getM().map);

		}
	}

	// GENERER DES COORDONNEES ALEATOIRES POUR LES ARBRES
	public void cord_tree(int k, ArrayList<Tree> l) {
		for (int i = 0; i < k; i++) {
			int x = random(w, h);
			int y = random(w, h);
			while (getM().map[x][y] != ' ') {
				x = random(w, h);
				y = random(w, h);
			}
			l.add(new Tree(new Coordinate(x, y), false));
			l.get(i).place(getM().map);
		}
	}

	public boolean isEmpty(Coordinate c) {
		if (m.isEmpty(c))
			return true;
		return false;
	}

	boolean isOut(Coordinate c) {
		if (c.getX() <= 0 || c.getY() <= 0)
			return true;
		if (c.getX() >= Map.width || c.getY() >= Map.height)
			return true;
		return false;
	}

	public boolean isTraversable(Coordinate c) {
		if (!isOut(c)) {
			for (Gold g : golds) {
				if (c.equals(g.getPos())) {
					return g.isTraversable();
				}
			}
			for (Mud m : muds) {
				if (c.equals(m.getPos())) {
					return m.isTraversable();
				}
			}
			for (Tree t : trees) {
				if (c.equals(t.getPos())) {
					return t.isTraversable();
				}
			}
			for (Portal p : portals) {
				if (c.equals(p.getPos())) {
					return p.isTraversable();
				}
			}
			for (Unit i : getP1().getUnit()) {
				if (c.equals(i.getPos()))
					return true;
			}
			for (Unit i : getP2().getUnit()) {
				if (c.equals(i.getPos()))
					return true;
			}
			if (!isEmpty(c))
				return false;

			return true;
		}
		return false;
	}

	public Unit getU(Coordinate c) {
		for (int k = 0; k < NB_PLAYERS; k++) {
			for (Unit u : players[k].getUnit()) {
				if (u.getPos().equals(c))
					return u;
			}
		}
		return null;
	}

	public boolean game_over() {
		if (Game.GOLD <= 0) {
			return true;
		}
		return false;
	}

	// AFFICHE LE GAGNANT DE LA PARTIE
	public void winner(MyPanel p) {
		if (players[0].getTreasure() > players[1].getTreasure()) {
			String output = "Cette partie est terminé. Il n'y a plus d'or à récupérer.\n\nLe gagnant est le joueur 1 !";
			JOptionPane.showMessageDialog(p, output, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
		} else if (players[1].getTreasure() > players[0].getTreasure()) {
			String output = "Cette partie est terminé. Il n'y a plus d'or à récupérer.\n\nLe gagnant est le joueur 2 !";
			JOptionPane.showMessageDialog(p, output, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
		} else {
			String output = "Cette partie est terminé. Il n'y a plus d'or à récupérer.\n\nC'est un match nul !";
			JOptionPane.showMessageDialog(p, output, "GAME OVER", JOptionPane.INFORMATION_MESSAGE);

		}
		System.exit(0);
	}

}
