package mas.diseasespread.models;

public class Infected implements State {
  public State nextState() {
    return new Recovered();
  }

  public State prevState() {
    return new Susceptible();
  }
}
