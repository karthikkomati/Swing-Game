package game;

/**
 * View controller interface to represent a controller for the dungeon following MVC principles.
 */
public interface ViewController {

  /**
   * Moves the player to the next location in the dungeon
   * based on the input directions and distance.
   *
   * @param x the x coordinate of the next location
   * @param y the x coordinate of the next location
   */
  void move(int x, int y);

  /**
   * Starts the game with the default settings.
   */
  void play();

  /**
   * Creates a dungeon model with the given inputs and starts a game with the model.
   *
   * @param wrapping        If the dungeon is wrapping or not
   * @param rows            The number of rows in the dungeon
   * @param cols            The number of columns in the dungeon
   * @param connectivity    The connectivity of the dungeon
   * @param treasurePercent The percentage of rooms that should be filled with treasure
   * @param monsters        The number of monsters in the dungeon
   * @param traps           The number of traps in the dungeon
   * @param thief           If the dungeon has a thief or not
   */
  void play(boolean wrapping, int rows, int cols, int connectivity,
            int treasurePercent, int monsters, int traps, boolean thief);

  /**
   * Starts the game with the given view and the default dungeon.
   *
   * @param v the view
   */
  void play(View v);

  /**
   * Handles the key input from the view by telling the model what to do based on the key pressed.
   *
   * @param key the input key code
   */
  void handleKey(int key);

  /**
   * Restarts the game with a new copy of the same dungeon.
   */
  void playCurrentDungeon();

  /**
   * Restarts the game with a new dungeon with the same settings.
   */
  void playWithCurrentSettings();


}
