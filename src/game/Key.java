package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key class that acts as a listener for keyboard inputs.
 */
public class Key implements KeyListener {

  private final ViewController listener;


  /**
   * Creates a key with given controller.
   *
   * @param listener the controller to pass the instructions to
   */
  public Key(ViewController listener) {
    this.listener = listener;

  }

  @Override
  public void keyTyped(KeyEvent e) {
    //not needed
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //not needed
  }

  @Override
  public void keyReleased(KeyEvent e) {

    listener.handleKey(e.getKeyCode());


  }
}
