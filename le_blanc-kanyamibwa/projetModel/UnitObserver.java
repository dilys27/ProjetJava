package projetModel;

import java.util.List;

public interface UnitObserver {
	void notify(List<UnitEvent> events);
}
