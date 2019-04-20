package projetModel;

public class Portal extends Ground{
	private Coordinate dest;
	public final static char rep = 'P';

	
	public Portal(Coordinate pos, boolean t) {
		super(pos, t);
	}
	
	public void setDest(Coordinate d) {
		dest = d;
	}
	
	public Coordinate getDest() {
		return dest;
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
		
	} 
	
}
