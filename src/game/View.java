package game;


/**
 * View interface to represent a view for the dungeon following MVC principles.
 */
public interface View {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();


  /**
   * Sets the read only model for the frame and all of its panels.
   *
   * @param m the read only dungeon
   */
  void setModel(ReadonlyDungeonModel m);

  /**
   * Gives the player a message through a JLabel.
   *
   * @param message the message to send
   */
  void message(String message);

  /**
   * Gives the player information through a pop-up window.
   *
   * @param s the information to send to the player.
   */
  void invalid(String s);
}
