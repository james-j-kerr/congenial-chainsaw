package mas.diseasespread.environment;

import mas.diseasespread.entities.Entity;

public class GridLocation implements Location {

  private int row;
  private int col;
  private Entity obj;

  public GridLocation(int row, int col) {
    this.row = row;
    this.col = col;
    this.obj = null;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof GridLocation))
      return false;
    GridLocation loc = (GridLocation) obj;
    return loc.getRow() == row && loc.getCol() == col;
  }

  public int getRow() {
    return this.row;
  }

  public int getCol() {
    return this.col;
  }

  @Override
  public Entity getObject() {
    return obj;
  }

  @Override
  public void setObject(Entity e) {
    this.obj = e;
  }

  @Override
  public String toString() {
    return String.valueOf(row) + "," + String.valueOf(col);
  }

}
