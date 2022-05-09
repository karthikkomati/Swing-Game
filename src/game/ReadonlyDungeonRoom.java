package game;

/**
 * Read only dungeon room that provides access to the read only methods in the dungeon room.
 */
public interface ReadonlyDungeonRoom {
  /**
   * Checks if the current location has an alive Otyugh.
   *
   * @return true if the location has an alive Otyugh
   */
  boolean hasAliveOtyugh();

  /**
   * Checks if the current location is attached to another location in the specified direction.
   *
   * @param d the direction of the other location
   * @return true if the rooms has another location attached in the direction.
   */
  boolean hasDirection(Direction d);

  /**
   * Checks if the room has a player.
   *
   * @return true if the room has a player.
   */
  boolean hasPlayer();


  /**
   * Checks if the room has been visited by a player before.
   *
   * @return true if it was visited
   */
  boolean getVisited();

}
