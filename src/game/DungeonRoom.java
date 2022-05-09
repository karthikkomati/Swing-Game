package game;

import java.util.List;

/**
 * A dungeon room interface to represent a single room in a
 * dungeon that can be a cave or a location.
 */
public interface DungeonRoom extends ReadonlyDungeonRoom {

  /**
   * Checks if the current room is a tunnel.
   *
   * @return true if it is a tunnel
   */
  boolean isTunnel();

  /**
   * Adds a player to the current room.
   *
   * @param player the player to add.
   */
  void addPlayer(Person player);

  /**
   * Removes the player from the room.
   */
  void removePlayer();

  /**
   * Adds another DungeonGame.DungeonRoom as the next room in the given direction.
   *
   * @param direction the direction of the next room
   * @param location  the next room
   */
  void setLocation(Direction direction, DungeonRoom location);

  /**
   * Sets this room as the starting point.
   */
  void setStart();

  /**
   * Moves all the treasure in this room to the player in this room.
   */
  void addTreasureToPlayer();

  /**
   * Adds treasure into this room.
   *
   * @param treasure the treasure to add.
   */
  void addTreasure(Treasure treasure);

  /**
   * Gets the description of the room, including the available directions and treasure.
   *
   * @return the room description
   */
  String getRoomDescription();

  /**
   * Gets the current location of the room in the dungeon.
   *
   * @return the current location.
   */
  int[] getLocation();

  /**
   * Gets the location that is attached in the specified direction.
   *
   * @param direction the direction of the other room
   * @return DungeonGame.Location in the direction
   */
  DungeonRoom getLocation(Direction direction);

  /**
   * Sets the room as the end point.
   */
  void setEnd();

  /**
   * Gets the room north of this room.
   *
   * @return the northern room
   */
  DungeonRoom getNorth();

  /**
   * Gets the room south of this room.
   *
   * @return the southern room
   */
  DungeonRoom getSouth();

  /**
   * Gets the room east of this room.
   *
   * @return the eastern room
   */
  DungeonRoom getEast();

  /**
   * Gets the room west of this room.
   *
   * @return the western room
   */
  DungeonRoom getWest();

  /**
   * Moves the player to the room in the specified direction.
   *
   * @param   direction the direction to move the player
   * @return  0 if the player dies while moving, 1 if the player escapes a monster
   *          and 2 if the player moves without any problems.
   */
  int move(Direction direction);


  /**
   * Adds arrows to this location.
   *
   * @param arrows the number of arrows to add
   */
  void addArrows(int arrows);


  /**
   * Gets the number of arrows in this location.
   *
   * @return the number of arrows
   */
  int getArrowCount();

  /**
   * Adds an DungeonGame.Otyugh to this room.
   */
  void setOtyugh();

  /**
   * Adds all the arrows in this room to the player and removes them from this room.
   */
  void addArrowsToPlayer();

  /**
   * Shoots an arrow in the specified direction to a specified distance and
   * attempts to hit a monster.
   *
   * @param d The direction to shoot.
   * @param c The distance to shoot.
   * @return true if the monster was hit.
   */
  boolean shoot(Direction d, int c);

  /**
   * Checks if the current location is the end location.
   *
   * @return true if it is the end.
   */
  boolean isEnd();

  /**
   * Checks if the current location is the start location.
   *
   * @return true if it is the start.
   */
  boolean isStart();


  /**
   * Gets all the treasure in this room.
   *
   * @return the list of treasures
   */
  List<Treasure> getTreasure();

  /**
   * Gets the health of the creature in this location.
   *
   * @return the health of the creature
   */
  int getCreatureHits();


  /**
   * Returns if the room has a trap or not.
   *
   * @return true if the room has a trap.
   */
  boolean getTrap();

  /**
   * Adds a trap to the current location.
   */
  void setTrap();

  /**
   * Checks if the room has a thief.
   *
   * @return true if the room has a thief.
   */
  boolean getThief();

  /**
   * Adds a thief to the room.
   */
  void setThief();

  /**
   * Removes a thief from the room.
   */
  void removeThief();


}
