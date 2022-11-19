package mas.diseasespread.models;

public class Recovered implements State {
  public State nextState() {
    return this;
  }

  public State prevState() {
    return new Infected();
  }
}
