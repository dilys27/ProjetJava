package pathfinding;

import java.util.ArrayList;
import java.util.List;

import pathfinding.Grid2d;
import pathfinding.Grid2d.MapNode;
import projetGui.Map;
import projetModel.Coordinate;
import projetModel.Game;

public class Path {
	@SuppressWarnings("unused")
	private Game g;
	private double[][] map = new double[Map.height][Map.width];
	private static Grid2d map2d;
	
	public Path(Game g) {
		this.g = g;
		for(int i = 0; i<map.length; i++) {
			for(int j = 0; j<map[i].length; j++) {
				if(!g.isTraversable(new Coordinate(i, j)))
					map[i][j] = -1;
				else 
					map[i][j] = 1;
			}
		}
		map2d = new Grid2d(map, false);
	}
	
	public static ArrayList<Coordinate> path(Coordinate start, Coordinate goal) {
		List<MapNode> p = map2d.findPath(start, goal);
		ArrayList<Coordinate> path = new ArrayList<>(); 
		for(MapNode n : p) {
			path.add(n.getC());
		}
		System.out.println("Path : "+map2d.findPath(start, goal));
		return path;
	}

}
