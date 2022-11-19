package mas.diseasespread.data;

public class AgentAttributes {

  private boolean distancing;
  private boolean asymptomatic;
  private boolean quarantining;
  private boolean masking;
  private double compliance;
  private int sicknessDuration;

  public AgentAttributes() {
    distancing = false;
    asymptomatic = false;
    quarantining = false;
    masking = false;
    compliance = 1.0;
    sicknessDuration = 0;
  }

  public boolean getDistancing() {
    return distancing;
  }

  public void setDistancing(boolean b) {
    distancing = b;
  }

  public boolean getAsymptomatic() {
    return asymptomatic;
  }

  public void setAsymptomatic(boolean b) {
    asymptomatic = b;
  }

  public boolean getQuarantining() {
    return quarantining;
  }

  public void setQuarantining(boolean b)  {
    quarantining = b;
  }

  public boolean getMasking() {
    return masking;
  }

  public void setMasking(boolean b) {
    masking = b;
  }

  public double getCompliance() {
    return compliance;
  }

  public void setCompliance(double d) {
    compliance = d;
  }

  public int getSicknessDuration() {
    return sicknessDuration;
  }

  public void setSicknessDuration(int i) {
    sicknessDuration = i;
  }

}
