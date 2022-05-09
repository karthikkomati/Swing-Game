package game;

import java.util.List;

/**
 * DungeonGame.Person interface to represent a single person in a dungeon.
 */
public interface Person {

  /**
   * Gets the description of the player including their location and their treasures.
   *
   * @return the description
   */
  String getDescription();

  /**
   * Adds the given treasure to the player inventory.
   *
   * @param treasure the treasure to add
   */
  void addTreasure(Treasure treasure);

  /**
   * Sets the location of the player in the dungeon.
   *
   * @param position the current location of the player
   */
  void updateLocation(int[] position);

  /**
   * Gets the location of the player in the dungeon.
   *
   * @return the player location
   */
  int[] getLocation();


  /**
   * Removes one arrow from the person.
   */
  void removeArrow();

  /**
   * Gets the number of arrows the person has.
   *
   * @return the number of arrows
   */
  int getArrowCount();

  /**
   * Adds arrows to the person.
   *
   * @param c the number of arrows to add
   */
  void addArrows(int c);

  /**
   * Gets the treasure the person has in its inventory.
   *
   * @return the list of treasure
   */
  List<Treasure> getInventory();

  /**
   * Checks if the player is alive.
   *
   * @return true if the player is alive.
   */
  boolean isAlive();


  /**
   * Sets is alive as false to represent the player being dead.
   */
  void died();
}
