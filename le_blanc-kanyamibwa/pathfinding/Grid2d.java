package pathfinding;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import projetModel.Coordinate;

/**
 * Creates nodes and neighbours from a 2d grid. Each point in the map has an
 * integer value that specifies the cost of crossing that point. If this value
 * is negative, the point is unreachable.
 * 
 * If diagonal movement is allowed, the Chebyshev distance is used, else
 * Manhattan distance is used.
 * 
 */
public class Grid2d {
	private final double[][] map;
	private final boolean allowDiagonal;

	/**
	 * A node in a 2d map. This is simply the coordinates of the point.
	 * 
	 */
	public class MapNode implements Node<MapNode> {
		private final Coordinate c; 

		public MapNode(Coordinate c) {
			this.c = c;
		}

		public double getHeuristic(MapNode goal) { //DISTANCE VERS GOAL
			if (allowDiagonal) {
				return Math.max(Math.abs(c.getX() - goal.c.getX()), Math.abs(c.getY() - goal.c.getY()));
			} else {
				return Math.abs(c.getX() - goal.c.getX()) + Math.abs(c.getY() - goal.c.getY());
			}
		}

		public double getTraversalCost(MapNode neighbour) { //COÛT POUR ALLER VERS NEIGHBOUR
			return 1 + map[neighbour.c.getX()][neighbour.c.getY()]; 
		}

		public Set<MapNode> getNeighbours() {
			Set<MapNode> neighbours = new HashSet<MapNode>();

			for (int i = c.getX() - 1; i <= c.getX() + 1; i++) {
				for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
					if ((i == c.getX() && j == c.getY()) || i < 0 || j < 0 || j >= map.length
							|| i >= map[j].length) {
						continue;
					}

					if (!allowDiagonal &&
					         ((i < c.getX() && j < c.getY()) ||
					          (i < c.getX() && j > c.getY()) ||
					          (i > c.getX() && j > c.getY()) ||
					          (i > c.getX() && j < c.getY()))) {
					     continue;
					}

					if (map[i][j] < 0) {
						continue;
					}

					neighbours.add(new MapNode(new Coordinate (i, j)));
				}
			}

			return neighbours;
		}

		@Override
		public String toString() {
			return "(" + c.getX() + ", " + c.getY() + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + c.getX();
			result = prime * result + c.getY();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MapNode other = (MapNode) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (c.getX() != other.c.getX())
				return false;
			if (c.getY() != other.c.getY())
				return false;
			return true;
		}

		private Grid2d getOuterType() {
			return Grid2d.this;
		}
		
		public Coordinate getC() {
			return c;
		}

	}

	public Grid2d(double[][] map, boolean allowDiagonal) {
		this.map = map;
		this.allowDiagonal = allowDiagonal;
	}

	public List<MapNode> findPath(Coordinate start, Coordinate goal) {
		return PathFinding.doAStar(new MapNode(start), new MapNode(goal));
	}

}
