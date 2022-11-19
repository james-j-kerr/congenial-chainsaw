package mas.diseasespread.generators;

import java.util.HashMap;
import java.util.Random;

import mas.diseasespread.data.AgentAttributes;
import mas.diseasespread.data.SimulationParams;
import mas.diseasespread.entities.Agent;
import mas.diseasespread.entities.Agent.AgentBuilder;
import mas.diseasespread.environment.Location;
import mas.diseasespread.models.State;
import mas.diseasespread.models.Susceptible;
import mas.diseasespread.models.Infected;

public class AgentGenerator extends EntityGenerator<Agent, Location> {

  private static final AgentBuilder agentBuilder = new Agent.AgentBuilder();
  private final SimulationParams params;
  private final int[] agentsQuota;
  private final HashMap<Integer, Class<?>> agentClassMap;
  private static AgentGenerator generatorInstance;

  private AgentGenerator() {
    params = SimulationParams.getInstance();
    agentsQuota = new int[] {
      (int) ((params.DEPTH * params.WIDTH) * params.AGENT_ZERO_DENSITY),
      (int) ((params.DEPTH * params.WIDTH) * params.AGENT_DENSITY),
    };
    agentClassMap = new HashMap<>(2);
    agentClassMap.put(0, Infected.class);
    agentClassMap.put(1, Susceptible.class);
  }

  public static AgentGenerator getInstance() {
    if (generatorInstance == null) {
      generatorInstance = new AgentGenerator();
    }
    return generatorInstance;
  }

  @Override
  public Agent generate(Location location) {

    if (agentsQuota[0] <= 0 && agentsQuota[1] <= 0) return null;

    try {
      State agentState;
      int index = params.getRandom().nextInt(2);

      if (agentsQuota[index] <= 0) {
        index = index == 0 ? index++ : index--;
      }
      agentState = (State) agentClassMap.get(index).newInstance();
      agentsQuota[index]--;

      Agent ag = agentBuilder
        .setLocation(location)
        .setAttributes(new AttributeGenerator().generate())
        .setDiseaseState(agentState)
        .build();
      return ag;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  class AttributeGenerator {

    public AgentAttributes generate() {

      AgentAttributes attr = new AgentAttributes();
      Random random = params.getRandom();

      attr.setCompliance(params.AGENT_COMPLIANCE);

      attr.setSicknessDuration(
          (int) (
          (params.AVERAGE_DISEASE_DURATION - params.DISEASE_DURATION_VARIANCE) +
          (params.AVERAGE_DISEASE_DURATION * random.nextDouble() * 2)
      ));

      if (random.nextDouble() < params.ASYMPTOMATIC_RATE) {
        attr.setAsymptomatic(true);
      }

      if (random.nextDouble() < params.AGENT_COMPLIANCE) {
        attr.setDistancing(true);
      }

      if (random.nextDouble() < params.AGENT_COMPLIANCE) {
        attr.setQuarantining(true);
      }

      if (random.nextDouble() < params.AGENT_COMPLIANCE) {
        attr.setMasking(true);
      }

      return attr;
    }

  }

}
