package game;

import java.io.IOException;

/**
 * Mock view class that records all the methods that are called by the controller.
 */
public class MockView implements View {

  private Appendable a;
  private ReadonlyDungeonModel m;

  /**
   * Creates a mock view with the appendable.
   *
   * @param a the appendable
   */
  public MockView(Appendable a) {
    this.a = a;
  }

  @Override
  public void refresh() {
    try {
      a.append("refresh");
      a.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void makeVisible() {
    try {
      a.append("makeVisible");
      a.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setModel(ReadonlyDungeonModel m) {
    try {
      a.append("setModel");
      a.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void message(String message) {
    try {
      a.append("message: ");
      a.append(message);
      a.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void invalid(String s) {
    try {
      a.append("Invalid: ");
      a.append(s);
      a.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return a.toString();
  }
}
