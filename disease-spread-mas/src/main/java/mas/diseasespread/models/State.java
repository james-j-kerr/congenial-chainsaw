package mas.diseasespread.models;

public interface State {
  public abstract State nextState();
  public abstract State prevState();
}
