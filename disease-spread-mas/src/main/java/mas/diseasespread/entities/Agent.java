package mas.diseasespread.entities;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import mas.diseasespread.environment.Field;
import mas.diseasespread.environment.Location;
import mas.diseasespread.models.Infected;
import mas.diseasespread.models.State;
import mas.diseasespread.data.AgentAttributes;

public class Agent extends Entity {

  private State diseaseState;
  private AgentAttributes attributes;
  private Set<Percept> percepts;
  private List<Action> actions;

  private Agent() {
    super(null);
    percepts = new HashSet<>();
    actions = new ArrayList<>();
    // order of priority
    actions.add(new Quarantine());
    actions.add(new MoveDistanced());
    actions.add(new MoveRandom());
  }

  interface Builder {
    void reset();
    Builder setLocation(Location location);
    Builder setDiseaseState(State state);
    Builder setAttributes(AgentAttributes agentAttributes);
    Agent build();
  }

  public static class AgentBuilder implements Builder {

    private Agent agent = new Agent();

    @Override
    public void reset() {
      this.agent = new Agent();
    }

    @Override
    public AgentBuilder setLocation(Location location) {
      this.agent.location = location;
      return this;
    }

    @Override
    public AgentBuilder setDiseaseState(State state) {
      this.agent.diseaseState = state;
      return this;
    }

    @Override
    public AgentBuilder setAttributes(AgentAttributes agentAttributes) {
      this.agent.attributes = agentAttributes;
      return this;
    }

    @Override
    public Agent build() {
      return this.agent;
    }
  }

  public State getState() {
    return diseaseState;
  }

  public AgentAttributes getAttributes() {
    return attributes;
  }

  public void act(Field<Location> field) {
    sense(field);
    actuate(field);
  }

  private void sense(Field<Location> field) {
    if (!attributes.getAsymptomatic()) {
      percepts.add(Percept.SYMPTOMATIC);
    } else {
      percepts.remove(Percept.SYMPTOMATIC);
    }
  }

  private void actuate(Field<Location> field) {
    for (Action action : actions) {
      if (action.act(field))
        return;
    }
  }

  private void move(Field<Location> field, Location moveLocation) {
    field.clearLocation(location);
    field.place(this, moveLocation);
    this.location = moveLocation;
  }

  interface Action {
    boolean act(Field<Location> field);
  }

  private class MoveRandom implements Action {
    @Override
    public boolean act(Field<Location> field) {
      Location adjLocation = field.getSingleFreeAdjacentLocation(location);
      if (adjLocation == null)
        return false;
      move(field, adjLocation);
      return true;
    }
  }

  private class MoveDistanced implements Action {
    @Override
    public boolean act(Field<Location> field) {
      if (!attributes.getDistancing())
        return false;

      Iterator<Location> freeLocations = field.adjacentLocationsIterator(location);
      if (!freeLocations.hasNext())
        return true;

      Location leastNeighbours = null;
      int fewestNeigboursCount = -1;
      do {
        Location current = freeLocations.next();
        int count = field.getAllNeighbours(current, Agent.class).size();
        if (fewestNeigboursCount < 0 || count < fewestNeigboursCount) {
          leastNeighbours = current;
          fewestNeigboursCount = count;
        }
      } while (freeLocations.hasNext());

      move(field, leastNeighbours);
      return true;
    }
  }

  private class Quarantine implements Action {
    @Override
    public boolean act(Field<Location> field) {
      return diseaseState instanceof Infected && percepts.contains(Percept.SYMPTOMATIC);
    }
  }

}
