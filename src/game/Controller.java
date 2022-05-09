package game;

/**
 * A controller interface to represent a  controller for the ungeon.
 */
public interface Controller {


  /**
   * Executes the text based dungeon game. Finishes when the dungeon run is over.
   *
   * @param m a model of the dungeon
   */
  void play(DungeonGrid m);

}
