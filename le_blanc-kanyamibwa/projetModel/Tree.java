package projetModel;

public class Tree extends Ground {
	
	public final static char rep = 'T';

	public Tree(Coordinate pos, boolean t) {
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

	@Override
	public void place(char[][] map) {
		if (map[pos.getX()][pos.getY()] == ' ') {
			map[pos.getX()][pos.getY()] = rep;
		}
	}

}
