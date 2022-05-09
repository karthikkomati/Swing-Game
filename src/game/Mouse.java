package game;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse class to capture a click on the view and send that information to the controller.
 */
public class Mouse extends MouseAdapter {

  private final ViewController listener;

  /**
   * Creates the mouse class with the listener (controller) to pass information to.
   *
   * @param listener The listener
   */
  public Mouse(ViewController listener) {
    this.listener = listener;
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    listener.move(e.getX(), e.getY());

  }
}
