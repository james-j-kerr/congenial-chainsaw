package mas.diseasespread.environment;

import java.util.Iterator;
import java.util.List;

import mas.diseasespread.entities.Entity;

public interface Field<L extends Location> extends Iterable<L> {

  int dimensions();

  void clear();

  void place(Entity obj, L location);

  void clearLocation(L location);

  Entity getObjectAt(L location);

  List<L> getFreeAdjacentLocations(L location);

  L getSingleFreeAdjacentLocation(L location);

  Iterator<L> adjacentLocationsIterator(L location);

  Iterator<L> iterator();

  <T> boolean neighbourOf(L location, Class<T> clss);

  <T> Entity getNeighbour(L location, Class<T> clss);

  <T extends Entity> List<Entity> getAllNeighbours(L location, Class<T> clss);

  Field<L> clone();

}
