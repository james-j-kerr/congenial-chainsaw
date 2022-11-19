package mas.diseasespread.data;

import java.util.List;
import java.util.ArrayList;

public class SimulationRecorder {
  private List<String[]> data;

  public SimulationRecorder() {
    data = new ArrayList<>();
  }

  public void addRecord(String[] record) {
    data.add(record);
  }

  public List<String[]> getData() {
    return data;
  }
}
