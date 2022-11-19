package mas.diseasespread.models;

public class Susceptible implements State {
  public State nextState() {
    return new Infected();
  }

  public State prevState() {
    return this;
  }
}
