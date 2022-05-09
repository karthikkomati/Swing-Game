package game;

/**
 * DungeonGame.DungeonGrid interface represents a single dungeon.
 */
public interface DungeonGrid extends ReadonlyDungeonModel {

  /**
   * Moves the player in the specified direction.
   *
   * @param direction the direction to move the player
   * @return true if the move is successful
   */
  int movePlayer(Direction direction);

  /**
   * Makes the player pick up the items in their present location.
   */
  void pickUpItems();


  /**
   * Adds the arrows in the players current location to the player.
   */
  void pickUpArrows();

  /**
   * Shoots an arrow from the current player location in the specified direction
   * to a specified distance and attempts to hit a monster.
   *
   * @param direction The direction to shoot.
   * @param length    The distance to shoot.
   * @return true if the monster was hit.
   */
  boolean shoot(Direction direction, int length);


  /**
   * Gets the players current location in the dungeon.
   *
   * @return the current location
   */
  int[] getPlayerLocation();

}
