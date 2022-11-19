package mas.diseasespread.app;

import mas.diseasespread.data.FieldStats;
import mas.diseasespread.data.SimulationParams;
import mas.diseasespread.environment.Field;
import mas.diseasespread.views.ChartView;
import mas.diseasespread.views.SimulatorView;

public class Simulator {

  private ChartView chartView;
  private SimulatorView simulationView;
  private SimulatorController controller;
  private SimulationParams params;
  private boolean setupFinished = false;
  private boolean simulationFinished = false;
  private int steps = 0;

  public Simulator(Field field, FieldStats fieldStats) {
    this.params = SimulationParams.getInstance();
    this.chartView = new ChartView(fieldStats);
    this.simulationView = new SimulatorView(params.DEPTH, params.WIDTH);
    controller = new SimulatorController(field, fieldStats);
    reset();
  }

  public boolean hasFinished() {
    return setupFinished && simulationFinished;
  }

  public void close() {
    this.simulationView.setVisible(false);
  }

  public void reset() {
    steps = 0;
    setupFinished = true;
    simulationFinished = false;
    controller.populate();
    this.simulationView.render(steps, controller.getBlueprint());
  }

  public void populate() {
    controller.populate();
  }

  public void tick() {
    if (setupFinished && !simulationFinished) {
      steps++;
      simulationFinished = controller.tick();
      this.simulationView.render(steps, controller.getBlueprint());
    }
  }
}
