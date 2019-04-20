package projetModel;

public abstract class Ground {
	protected Coordinate pos;
	protected boolean traversable;

	public Ground(Coordinate pos, boolean t) {
		this.pos = pos;
		this.traversable = t;
	}

	public abstract void place(char[][] map);

	public abstract boolean isTraversable();

	public abstract Coordinate getPos();
}
