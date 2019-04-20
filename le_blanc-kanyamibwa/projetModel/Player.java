package projetModel;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import projetGui.MyPanel;

public class Player {
	private Game game;
	private ArrayList<Unit> units; // liste des unités
	private Coordinate[] qg = new Coordinate[4]; // maison-mère
	private int treasure; // coffre-fort
	private int nb; // numéro du joueur
	private int num_unit; // nombre d'unités

	public Player(Game game, int nb) {
		this.nb = nb;
		this.game = game;
		this.units = new ArrayList<Unit>();
		this.treasure = 100;
		this.num_unit = 0;
	}

	public void setQG(int p, int i, int j) {
		qg[p] = new Coordinate(i, j);
	}

	public Coordinate[] getQG() {
		return qg;
	}
	
	public ArrayList<Unit> getUnit() {
		return units;
	}
	
	public int getNum_unit() {
		return num_unit;
	}

	public void setNum_unit(int num_unit) {
		this.num_unit = num_unit;
	}

	public void setTreasure(int t) {
		treasure = t;
	}

	public int getTreasure() {
		return treasure;
	}

	//CREATION D'UNITE
	public void createUnit() {
		Random r = new Random();
		Coordinate c = new Coordinate(0, 0);

		if (qg[3].getX() < 27 && qg[3].getY() < 27) {
			int x = r.nextInt(qg[3].getX() + 10);
			int y = r.nextInt(qg[3].getY() + 10);

			while (!game.isTraversable(c)) {
				r = new Random();
				x = r.nextInt(qg[3].getX() + 10);
				y = r.nextInt(qg[3].getY() + 10);
				c = new Coordinate(x, y);
			}
			System.out.println("Bls: " + x + " " + y);
		} else {
			int x = r.nextInt(qg[0].getX()) + qg[0].getX() - 10;
			int y = r.nextInt(qg[0].getY()) + qg[0].getY() - 10;

			while (!game.isTraversable(c)) {
				x = r.nextInt(qg[0].getX()) + qg[0].getX() - 10;
				y = r.nextInt(qg[0].getY()) + qg[0].getY() - 10;
				c = new Coordinate(x, y);
			}
			System.out.println("Rgs: " + x + " " + y);
		}
		System.out.println(nb);
		units.add(new Unit(game, nb, c));
		units.get(num_unit).place(game.getM().map);
		units.get(num_unit).setId(num_unit);
		setNum_unit(getNum_unit() + 1);
	}

	//VERIFIE SI LE JOUEUR POSSEDE ASSEZ DE PIECES
	public boolean enough_gold(int x, String s, MyPanel p) {
		int t = treasure - x;
		if (t < 0) {
			String output = "Vous n'avez pas assez d'or pour " + s +".";
			JOptionPane.showMessageDialog(p, output, "Manque d'or", JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else {
			treasure = treasure - x;
			System.out.println("Vous avez assez d'or ! Gold du joueur : " + treasure );
			return true;
		}
	}

	// AFFICHE LES INFOS SUR LE JOUEUR
	public void infos_joueur(MyPanel p) {
		String output = "Informations Générales\n\n"
				+ "COFFRE-FORT : " + treasure + " pièces d'or\n"
				+ "NB D'UNITES DU JOUEUR : "+ units.size() + "\n";
		JOptionPane.showMessageDialog(p, output, "INFORMATIONS JOUEUR " + nb, JOptionPane.INFORMATION_MESSAGE);
	}
}
