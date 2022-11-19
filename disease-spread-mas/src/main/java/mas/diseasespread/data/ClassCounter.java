package mas.diseasespread.data;

public class ClassCounter {

  private String classId;
  private int count;

  public ClassCounter(String classId) {
    this.classId = classId;
    count = 0;
  }

  public String getClassId() {
    return classId;
  }

  public int getCount() {
    return count;
  }

  public void increment() {
    count++;
  }

  public void decrement() {
    count--;
  }

  public void reset() {
    count = 0;
  }
}
