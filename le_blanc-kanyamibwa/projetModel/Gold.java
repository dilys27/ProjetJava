package projetModel;

import java.util.Random;

public class Gold extends Ground {

	private int NB_PIECES = random(10, 1) * 10; // nombre de pièces dans une pile
	public final static char rep = 'G';
	

	public Gold(Coordinate pos, boolean t) {
		super(pos, t);
	}

	@Override
	public boolean isTraversable() {
		return traversable;
	}
	
	@Override
	public Coordinate getPos() {
		return pos;
	}

	public void setTraversable(boolean t) {
		traversable = t;
	}
	

	public int getNB_PIECES() {
		return NB_PIECES;
	}

	public void setNB_PIECES(int nB_PIECES) {
		NB_PIECES = nB_PIECES;
	}

	// GENERER UN NOMBRE ALEATOIRE ENTRE X ET Y
	private static int random(int x, int y) { 
		Random rand = new Random();
		int i = rand.nextInt(x) + y;
		return i;
	}

	@Override
	public void place(char[][] map) {
		if (map[pos.getX()][pos.getY()] == ' ') {
			map[pos.getX()][pos.getY()] = rep;
		}
	}

	public void vanish(char[][] map) {
		if (getNB_PIECES() <= 0) {
			map[pos.getX()][pos.getY()] = ' ';
		}
	}

}
