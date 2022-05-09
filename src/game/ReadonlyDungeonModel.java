package game;

import java.util.List;
import java.util.Map;

/**
 * A read only dungeon model that provides access to the read only methods for the dungeon.
 */
public interface ReadonlyDungeonModel {

  /**
   * Gets the description of the location that the player is currently in.
   *
   * @return the location description
   */
  String getLocationDescription();

  /**
   * Gets the description of the player.
   *
   * @return the player description
   */
  String getPlayerDescription();

  /**
   * Checks if the current player location is a tunnel.
   *
   * @return true if the current location is a tunnel
   */
  boolean isTunnel();

  /**
   * Checks if the current player location is the end.
   *
   * @return true if the current location is the end
   */
  boolean isEnd();

  /**
   * Gets the number of arrows in the current player location.
   *
   * @return the number of arrows
   */
  int getArrowCount();


  /**
   * Returns the treasure present int the current player location.
   *
   * @return A map of the treasures and the amount of that treasure in the location.
   */
  Map<Treasure, Integer> getTreasure();

  /**
   * Gets the smell that the player should sense based on the current location.
   *
   * @return 0 if there is no smell, 1 if there is a strong smell or 2 if there is a weak smell
   */
  int getSmell();

  /**
   * Gets the possible directions that the player can move from the current location.
   *
   * @return the list of directions.
   */
  List<Direction> getDirections();

  /**
   * Checks if the player has arrows.
   *
   * @return true if the player has arrows
   */
  boolean hasArrows();

  /**
   * Gets all the items the player has including treasures and arrows.
   *
   * @return the string of all the items the player has
   */
  String getPlayerInventory();


  /**
   * Gets the number of arrows the player has.
   *
   * @return the number of arrows
   */
  int getPlayerArrowCount();


  /**
   * Gets all the treasures the player has.
   *
   * @return map of the player treasures and the amount
   */
  Map<Treasure, Integer> getPlayerTreasures();

  /**
   * Checks if the player is alive.
   *
   * @return true if the player is alive.
   */
  boolean isPlayerAlive();


  /**
   * Gets the whole dungeon room grid.
   *
   * @return the dungeon room grid.
   */
  ReadonlyDungeonRoom[][] getGrid();

  /**
   * Checks if a location one distance away from the player has traps.
   *
   * @return true if there are traps
   */
  boolean getTraps();
}
