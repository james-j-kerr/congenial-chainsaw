package mas.diseasespread.views;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import mas.diseasespread.data.ClassCounter;
import mas.diseasespread.data.FieldStats;
import mas.diseasespread.data.GUIParams;
import mas.diseasespread.data.SimulationParams;
import mas.diseasespread.entities.Agent;

public class ChartView extends JFrame {

  private List<Integer> steps = new ArrayList<>(Arrays.asList(0));
  private List<Integer> susceptibles = new ArrayList<>(Arrays.asList(0));
  private JPanel panel;
  private final XYChart chart = new XYChartBuilder()
    .width(GUIParams.CHART_DIM_X)
    .height(GUIParams.CHART_DIM_Y)
    .title("Simulation Chart")
    .xAxisTitle("Step")
    .yAxisTitle("Number of entities")
    .build();

  public ChartView(FieldStats stats) {
    stats.addPropertyChangeListener(e -> {
      onUpdate(e);
    });
    chart.addSeries("Susceptible", steps, susceptibles);
    panel = new XChartPanel<XYChart>(chart);
    this.setTitle("Simulation Graph");
    this.setLocation(GUIParams.GRAPH_WINDOW_X, GUIParams.GRAPH_WINDOW_Y);
    this.add(panel);
    this.pack();
    this.setVisible(true);
  }

  public void saveImage(String path) {
    try {
      BitmapEncoder.saveBitmap(chart, path, BitmapFormat.PNG);
    } catch (Exception e) {
      System.out.println("Could not save image.");
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  private void onUpdate(PropertyChangeEvent event) {
    HashMap<Class<?>, ClassCounter> count = (HashMap<Class<?>, ClassCounter>) event.getNewValue();
    steps.add(steps.size());
    // int agentCount = count.get(Agent.class).getCount();
    int agentCount =  (int) Math.round(100 * SimulationParams.getInstance().getRandom().nextDouble());
    susceptibles.add(agentCount);

    chart.updateXYSeries("Susceptible", steps, susceptibles, null);
    panel.updateUI();
  }
}
