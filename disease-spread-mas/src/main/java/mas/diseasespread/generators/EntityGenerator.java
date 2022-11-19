package mas.diseasespread.generators;

import mas.diseasespread.entities.Entity;
import mas.diseasespread.environment.Location;

public abstract class EntityGenerator<E extends Entity, L extends Location> {

  public abstract E generate(L location);

}
