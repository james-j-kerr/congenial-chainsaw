package mas.diseasespread.environment;

import mas.diseasespread.entities.Entity;

public interface Location {

  boolean equals(Object obj);

  void setObject(Entity e);

  Entity getObject();

  String toString();
}
