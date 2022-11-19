package mas.diseasespread.data;

import java.util.Random;

import java.awt.Color;

public class SimulationParams {

  private static SimulationParams instance;

  private Random random;
  public int DEPTH = 100;
  public int WIDTH = 100;
  public int SEED = 1;
  public int RUNTIME = 100;
  public Color SUS_CLR = Color.BLUE;
  public Color INF_CLR = Color.RED;
  public Color REC_CLR = Color.GREEN;
  public Color DOA_CLR = Color.GRAY;

  public boolean SOCIAL_DISTANCING = false;
  public boolean QUARANTINING = false;
  public boolean MASKING = false;
  public double AGENT_DENSITY = 0.05;
  public double AGENT_ZERO_DENSITY = 0.01;
  public double AGENT_COMPLIANCE = 1;
  public double MASK_RISK_REDUCTION = 1;
  public double INFECTIVITY = 1;
  public double ASYMPTOMATIC_RATE = 1;
  public double AVERAGE_DISEASE_DURATION = 1;
  public double DISEASE_DURATION_VARIANCE = 1;

  private SimulationParams() {
    random = new Random(SEED);
  }

  public static SimulationParams getInstance() {
    if (instance == null) {
      instance = new SimulationParams();
    }
    return instance;
  }

  public Random getRandom() {
    return random;
  }

}
