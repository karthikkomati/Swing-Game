package game;

/**
 * An interface to represent a creature that is living in a dungeon.
 */
public interface Creature {


  /**
   * Hits the creature, making it lose one point of health.
   */
  void hit();


  /**
   * Gets the amount of health this creature has.
   *
   * @return the health
   */
  int getHits();
}
