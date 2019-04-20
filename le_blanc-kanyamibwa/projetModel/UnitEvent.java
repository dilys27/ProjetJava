package projetModel;

public class UnitEvent {
	public static enum ChangeType {
		ENTER, LEAVE
	}

	private Coordinate coordinate;
	private ChangeType changeType;

	public UnitEvent(Coordinate coordinate, ChangeType changeType) {
		this.coordinate = coordinate;
		this.changeType = changeType;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public String toString() {
		ChangeType type = changeType;
		String s;
		if (type == ChangeType.ENTER)
			s = "Enters";
		else
			s = "Leaves";
		return s + " (" + coordinate.getX() + ", " + coordinate.getY() + ")";
	}

}
