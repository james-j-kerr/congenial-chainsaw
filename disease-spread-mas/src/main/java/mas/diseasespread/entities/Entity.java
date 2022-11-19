package mas.diseasespread.entities;

import mas.diseasespread.environment.Location;

public abstract class Entity {

  protected Location location;

  public Entity(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }
}
