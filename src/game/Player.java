package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DungeonGame.Player class to represent a player in a dungeon. Contains an inventory of treasures.
 */
public class Player implements Person, Serializable {

  private int arrowCount;

  private List<Treasure> inventory;

  private int[] location;

  private boolean isAlive;

  /**
   * Creates the player with a list of treasures as its inventory.
   */
  public Player() {
    inventory = new ArrayList<>();
    arrowCount = 3;
    isAlive = true;
  }

  @Override
  public void removeArrow() {
    if (arrowCount <= 0) {
      throw new IllegalStateException("No arrows to remove");
    }
    arrowCount--;
  }

  @Override
  public int getArrowCount() {
    return arrowCount;
  }

  @Override
  public void addArrows(int c) {
    arrowCount += c;
  }

  @Override
  public String getDescription() {

    StringBuilder sb = new StringBuilder();
    if (this.location != null) {
      sb.append("player location: ");
      sb.append(location[0]);
      sb.append(":");
      sb.append(location[1]);
    }
    sb.append(" |Treasures: ");
    for (Treasure t : inventory) {
      sb.append(t.name());
      sb.append(", ");
    }
    sb.append("|Arrows: ");
    sb.append(arrowCount);
    return sb.toString();
  }

  @Override
  public void addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure");
    }
    inventory.add(treasure);
  }

  @Override
  public void updateLocation(int[] position) {
    if (position == null) {
      throw new IllegalArgumentException("Invalid position");
    }
    if (position.length != 2) {
      throw new IllegalArgumentException("Illegal position");
    }
    if (position[0] < 0 || position[1] < 0) {
      throw new IllegalArgumentException("Invalid positions");
    }

    this.location = position;
  }

  @Override
  public int[] getLocation() {
    return this.location;
  }

  @Override
  public List<Treasure> getInventory() {
    return this.inventory;
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public void died() {
    this.isAlive = false;
  }


}
