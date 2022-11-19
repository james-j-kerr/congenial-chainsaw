package mas.diseasespread.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;

import mas.diseasespread.entities.Entity;
import mas.diseasespread.environment.Field;
import mas.diseasespread.environment.Location;

public class FieldStats {

  private HashMap<Class<?>, ClassCounter> counters;
  private boolean validated;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  public FieldStats() {
    counters = new HashMap<Class<?>, ClassCounter>();
    validated = true;
  }

  public boolean valid() {
    return validated;
  }

  public int getClassCount(Class<?> clss) {
    if (!counters.containsKey(clss))
      return 0;
    return counters.get(clss).getCount();
  }

  public HashMap<String, Integer> getAllClassCounts() {
    HashMap<String, Integer> classCounts = new HashMap<>();
    Iterator<ClassCounter> itr = counters.values().iterator();
    while (itr.hasNext()) {
      ClassCounter counter = itr.next();
      classCounts.put(counter.getClassId(), counter.getCount());
    }
    return classCounts;
  }

  public void generateCounts(Field<Location> field) {
    reset();

    Iterator<Location> itr = field.iterator();
    while (itr.hasNext()) {
      Object e = itr.next().getObject();
      if (e != null && e instanceof Entity) {
        incrementCount((Entity) e);
      }
    }

    validated = true;
    support.firePropertyChange("classCounts", null, counters);
  }

  public void reset() {
    validated = false;

    Iterator<Class<?>> keyIterator = counters.keySet().iterator();
    while (keyIterator.hasNext()) {
      Class<?> key = keyIterator.next();
      counters.get(key).reset();
    }
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }

  private void incrementCount(Entity entity) {
    Class<?> clss = entity.getClass();
    if (counters.containsKey(clss)) {
      counters.get(clss).increment();
    } else {
      ClassCounter cc = new ClassCounter(clss.getSimpleName());
      cc.increment();
      counters.put(clss, cc);
    }
  }

}
