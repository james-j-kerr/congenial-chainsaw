package mas.diseasespread.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.Color;

import mas.diseasespread.data.FieldStats;
import mas.diseasespread.data.GUIParams;
import mas.diseasespread.data.SimulationParams;
import mas.diseasespread.entities.Agent;
import mas.diseasespread.entities.Entity;
import mas.diseasespread.entities.Obstacle;
import mas.diseasespread.environment.Field;
import mas.diseasespread.environment.Location;
import mas.diseasespread.generators.AgentGenerator;
import mas.diseasespread.models.Infected;
import mas.diseasespread.models.Recovered;
import mas.diseasespread.models.Susceptible;

public class SimulatorController {

  private List<Entity> entities;
  private Field<Location> field;
  private FieldStats fieldStats;
  private SimulationParams params;

  public SimulatorController(Field<Location> field, FieldStats fieldStats) {
    this.field = field;
    this.fieldStats = fieldStats;
    this.entities = new ArrayList<>();
    this.params = SimulationParams.getInstance();
  }

  public void reset() {
    this.populate();
  }

  public void populate() {
    this.field.clear();
    this.entities.clear();
    AgentGenerator generator = AgentGenerator.getInstance();
    Iterator<Location> fieldIterator = this.field.iterator();

    while (fieldIterator.hasNext()) {
      Location location = fieldIterator.next();
      Entity agent = generator.generate(location);
      if (agent == null)
        break;
      this.field.place(agent, location);
      this.entities.add(agent);
    }

    this.fieldStats.generateCounts(field);
  }

  public boolean tick() {

    Field<Location> tempField = this.field.clone();
    transmitDisease(tempField);
    actAllAgents(tempField);
    this.field = tempField;
    this.fieldStats.generateCounts(this.field);

    return this.viable();
  }

  public Map<String, Color> getBlueprint() {
    Map<String, Color> blueprint = new HashMap<>();
    Iterator<Location> fieldIterator = this.field.iterator();
    while (fieldIterator.hasNext()) {
      Location location = fieldIterator.next();
      if (location != null) {
        Object e = location.getObject();
        if (!(e instanceof Entity)) {
          blueprint.put(location.toString(), GUIParams.EMP_COLOR);
          continue;
        }
        Color color = null;
        if (e instanceof Obstacle) {
          color = GUIParams.OBS_COLOR;
        } else if (e instanceof Agent) {
          Agent ag = (Agent) e;
          if (ag.getState().getClass() == Susceptible.class) {
            color = GUIParams.SUS_COLOR;
          }
          if (ag.getState().getClass() == Infected.class) {
            color = GUIParams.INF_COLOR;
          }
          if (ag.getState().getClass() == Recovered.class) {
            color = GUIParams.REC_COLOR;
          }
        } else {
          color = GUIParams.EMP_COLOR;
        }
        blueprint.put(location.toString(), color);
      }
    }
    return blueprint;
  }

  private void transmitDisease(Field<Location> tempField) {

    for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
      Entity entity = iterator.next();
      if (!(entity instanceof Agent))
        continue;

      Agent agent = (Agent) entity;
      if (agent.getState().getClass() != Susceptible.class)
        continue;

      List<Agent> infectedNeighbours =
        tempField.getAllNeighbours(agent.getLocation(), Agent.class)
          .stream()
          .map(neighbour -> {
            return (Agent) neighbour;
          })
          .filter(neighbour -> isInfected(neighbour))
          .collect(Collectors.toList());

      if (infectedNeighbours.size() < 1)
          continue;

      if (hasContracted(infectedNeighbours)) {
        agent.getState().nextState();
      }
    }
  }

  private void actAllAgents(Field<Location> tempField) {
    for (Entity entity : entities) {
      if (entity instanceof Agent) {
        Agent ag = (Agent) entity;
        ag.act(tempField);
      }
    }
  }

  private boolean isInfected(Agent ag) {
    return ag.getState().getClass() == Infected.class;
  }

  private boolean hasContracted(List<Agent> infected) {
    double infectionRisk = 1.0;
    for (Agent ag : infected) {
      double infectivity = params.INFECTIVITY;
      if (ag.getAttributes().getMasking()) {
        infectivity *= params.MASK_RISK_REDUCTION;
      }
      infectionRisk *= (1.0 - infectivity);
    }
    double infection = 1.0 - infectionRisk;
    return params.getRandom().nextDouble() < infection;
  }

  private boolean viable() {
    for (Iterator<Entity> itr = entities.iterator(); itr.hasNext();) {
      Entity e = itr.next();
      if (e instanceof Agent) {
        Agent ag = (Agent) e;
        if (ag.getState().getClass() == Infected.class) return true;
      }
    }
    return false;
  }

}
