package game;

import java.io.Serializable;

/**
 * An DungeonGame.Otyugh is a creature that lives in the dungeon. This is a class to represent
 * that creature.
 */
public class Otyugh implements Creature, Serializable {

  private int hits;

  /**
   * Creates an otyugh with 2 health.
   */
  public Otyugh() {
    hits = 2;
  }

  @Override
  public int getHits() {
    return hits;
  }

  @Override
  public void hit() {
    if (hits <= 0) {
      throw new IllegalStateException("DungeonGame.Otyugh is dead");
    }
    hits--;
  }

}
