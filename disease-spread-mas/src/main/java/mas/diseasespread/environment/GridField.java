package mas.diseasespread.environment;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.LinkedList;
import lombok.NonNull;

import mas.diseasespread.entities.Entity;

public class GridField implements Field<GridLocation> {

  private int depth;
  private int width;
  private GridLocation[][] field;

  /**
   * Create depth * width matrix that represents the
   * field
   */
  public GridField(int depth, int width) {
    this.depth = depth;
    this.width = width;
    field = new GridLocation[depth][width];
  }

  public int getDepth() {
    return depth;
  }

  public int getWidth() {
    return width;
  }

  public int dimensions() {
    return depth * width;
  }

  public void clear() {
    field = new GridLocation[depth][width];
    for (int row = 0; row < depth; row++) {
      for (int col = 0; col < width; col++) {
        field[row][col] = new GridLocation(row, col);
      }
    }
  }

  public void place(Entity e, int row, int col) {
    if (!validCoordinates(row, col))
      return;
    field[row][col].setObject(e);
  }

  public void place(Entity e, @NonNull GridLocation l) {
    place(e, l.getRow(), l.getCol());
  }

  public void clearLocation(int row, int col) {
    if (!validCoordinates(row, col))
      return;
    field[row][col].setObject(null);
  }

  public void clearLocation(@NonNull GridLocation l) {
    clearLocation(l.getRow(), l.getCol());
  }

  public Entity getObjectAt(int row, int col) {
    if (!validCoordinates(row, col))
      return null;
    return field[row][col].getObject();
  }

  public Entity getObjectAt(GridLocation l) {
    return getObjectAt(l.getRow(), l.getCol());
  }

  public List<GridLocation> getFreeAdjacentLocations(int row, int col) {
    List<GridLocation> freeAdjacentLocations;

    if (!validCoordinates(row, col))
      freeAdjacentLocations = Collections.emptyList();
    else {
      List<GridLocation> adjLocations = adjacentLocations(new GridLocation(row, col), 1);
      freeAdjacentLocations = adjLocations
          .stream()
          .filter(location -> {
            // return getObjectAt(location) == null;
            return location.getObject() == null;
          })
          .collect(Collectors.toList());
    }

    return freeAdjacentLocations;
  }

  public List<GridLocation> getFreeAdjacentLocations(@NonNull GridLocation l) {
    return getFreeAdjacentLocations(l.getRow(), l.getCol());
  }

  public GridLocation getSingleFreeAdjacentLocation(@NonNull GridLocation l) {
    return getFreeAdjacentLocations(l).get(0);
  }

  @Override
  public <T> boolean neighbourOf(GridLocation l, Class<T> c) {
    List<GridLocation> adjs = adjacentLocations(l, 1);
    return adjs.stream().anyMatch(loc -> {
      Entity e = getObjectAt(loc);
      return e != null && e.getClass() == c;
    });
  }

  public <T> Entity getNeighbour(@NonNull GridLocation l, Class<T> c) {
    Iterator<GridLocation> itr = adjacentLocationsIterator(l, 1);
    while (itr.hasNext()) {
      GridLocation current = itr.next();
      if (getObjectAt(current).getClass() == c) {
        return getObjectAt(current);
      }
    }
    return null;
  }

  public <T extends Entity> List<Entity> getAllNeighbours(GridLocation l, Class<T> c) {
    List<Entity> neighbours = new ArrayList<>();
    Iterator<GridLocation> itr = adjacentLocationsIterator(l, 1);
    while (itr.hasNext()) {
      GridLocation current = itr.next();
      Entity e = getObjectAt(current);
      if (e != null && e.getClass() == c) {
        neighbours.add(e);
      }
    }
    return neighbours;
  }

  public Iterator<GridLocation> adjacentLocationsIterator(GridLocation l) {
    return adjacentLocationsIterator(l, 1);
  }

  private Iterator<GridLocation> adjacentLocationsIterator(GridLocation l, int d) {
    return adjacentLocations(l, d).iterator();
  }

  @Override
  public GridField clone() {
    GridField clone = new GridField(depth, width);
    for (int row = 0; row < depth; row++) {
      for (int col = 0; col < width; col++) {
        clone.field[row][col] = field[row][col];
      }
    }
    return clone;
  }

  @Override
  public Iterator<GridLocation> iterator() {
    return new FieldIterator(this);
  }

  private List<GridLocation> adjacentLocations(GridLocation l, int d) {
    int row = l.getRow();
    int col = l.getCol();

    LinkedList<GridLocation> locations = new LinkedList<>();

    for (int rowOffset = -d; rowOffset <= d; rowOffset++) {
      int nextRow = row + rowOffset;
      for (int colOffset = -d; colOffset <= d; colOffset++) {
        int nextCol = col + colOffset;
        nextRow = normalise(nextRow, depth);
        nextCol = normalise(nextCol, width);
        locations.add(new GridLocation(nextRow, nextCol));
      }
    }
    return locations;
  }

  private int normalise(int coordinate, int dim) {
    return (coordinate + dim) % dim;
  }

  private boolean validCoordinates(int row, int col) {
    return row < depth && col < width;
  }

  class FieldIterator implements Iterator<GridLocation> {

    private int rowdex;
    private int coldex;
    private int finaldex;
    private GridField f;

    public FieldIterator(GridField f) {
      rowdex = 0;
      coldex = 0;
      finaldex = f.getDepth() * f.getWidth();
      this.f = f;
    }

    public boolean hasNext() {
      return finaldex > 0;
    }

    public GridLocation next() {
      GridLocation location = f.field[rowdex][coldex];
      incrementPointer();
      finaldex--;
      return location;
    }

    private void incrementPointer() {
      coldex++;
      if (coldex % f.getWidth() == 0) {
        coldex = 0;
        rowdex++;
      }
    }
  }
}
