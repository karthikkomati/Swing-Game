package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DungeonGame.Location class to represent a single location in a dungeon.
 * This could be a tunnel or a cave.
 */
public class Location implements DungeonRoom, Serializable {

  private DungeonRoom north;
  private DungeonRoom south;
  private DungeonRoom east;
  private DungeonRoom west;
  private final int xLocation;
  private final int yLocation;
  private Person player;
  private List<Treasure> treasures;
  private boolean isEnd;
  private boolean isStart;
  private Creature otyugh;
  private int arrowCount;
  private boolean hasTrap;
  private boolean hasThief;
  private boolean visited;

  @Override
  public boolean getTrap() {
    return hasTrap;
  }

  @Override
  public boolean getVisited() {
    return visited;
  }

  @Override
  public void setTrap() {
    if (this.hasAliveOtyugh()) {
      throw new IllegalStateException("Room cant have a trap and a monster");
    }
    this.hasTrap = true;
  }

  @Override
  public boolean getThief() {
    return hasThief;
  }

  @Override
  public void setThief() {
    if (this.hasAliveOtyugh()) {
      throw new IllegalStateException("Room cant have a thief and a monster");
    }
    if (this.hasTrap) {
      throw new IllegalStateException("Room cant have a trap and a thief");
    }
    this.hasThief = true;
  }

  @Override
  public void removeThief() {
    this.hasThief = false;
  }


  @Override
  public DungeonRoom getNorth() {
    return north;
  }

  @Override
  public DungeonRoom getSouth() {
    return south;
  }

  @Override
  public DungeonRoom getEast() {
    return east;
  }

  @Override
  public DungeonRoom getWest() {
    return west;
  }


  /**
   * Creates the location with its position in the dungeon.
   *
   * @param xLocation The locations x position
   * @param yLocation The locations y position
   */
  public Location(int xLocation, int yLocation) {
    if (xLocation < 0 || yLocation < 0) {
      throw new IllegalArgumentException("Invalid location");
    }
    this.xLocation = xLocation;
    this.yLocation = yLocation;
    this.player = null;
    this.north = null;
    this.south = null;
    this.east = null;
    this.west = null;
    treasures = new ArrayList<>();
    isEnd = false;
    isStart = false;
    otyugh = null;
    hasTrap = false;
    hasThief = false;
    arrowCount = 0;
    visited = false;
  }

  @Override
  public void setOtyugh() {
    if (otyugh != null) {
      throw new IllegalStateException("DungeonGame.Otyugh already exists");
    }
    otyugh = new Otyugh();
  }

  @Override
  public DungeonRoom getLocation(Direction direction) {

    if (direction == Direction.NORTH) {
      return north;
    } else if (direction == Direction.SOUTH) {
      return south;
    } else if (direction == Direction.EAST) {
      return east;
    } else {
      return west;
    }
  }

  @Override
  public int[] getLocation() {
    return new int[]{xLocation, yLocation};
  }

  @Override
  public boolean hasAliveOtyugh() {
    if (otyugh == null) {
      return false;
    }

    return otyugh.getHits() != 0;
  }

  @Override
  public void addArrows(int arrows) {
    arrowCount += arrows;
  }


  @Override
  public int getArrowCount() {
    return arrowCount;
  }

  @Override
  public void addArrowsToPlayer() {

    if (this.player == null) {
      throw new IllegalStateException("No player to add arrows to");
    }

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive");
    }


    player.addArrows(this.arrowCount);
    this.arrowCount = 0;
  }

  @Override
  public boolean shoot(Direction d, int c) {


    if (c < 0) {
      throw new IllegalArgumentException("Invalid distance");
    }

    if (this.isTunnel()) {
      if (this.hasDirection(d)) {
        return this.getLocation(d).shoot(d, c);
      } else {
        return this.getLocation(getOtherDirection(swapDirection(d)))
                .shoot(getOtherDirection(swapDirection(d)), c);
      }
    } else {
      if (c == 0) {
        if (this.hasAliveOtyugh()) {
          this.otyugh.hit();

          return true;
        } else {
          return false;
        }
      } else {
        if (this.hasDirection(d)) {
          return this.getLocation(d).shoot(d, c - 1);
        } else {
          return false;
        }
      }
    }

  }

  private Direction getOtherDirection(Direction d) {

    if (!this.isTunnel()) {
      throw new IllegalStateException("Needs to be a tunnel");
    }
    if (d == Direction.NORTH) {

      if (this.south != null) {
        return Direction.SOUTH;
      } else if (this.west != null) {
        return Direction.WEST;
      } else {
        return Direction.EAST;
      }

    } else if (d == Direction.SOUTH) {

      if (this.north != null) {
        return Direction.NORTH;
      } else if (this.west != null) {
        return Direction.WEST;
      } else {
        return Direction.EAST;
      }

    } else if (d == Direction.EAST) {

      if (this.south != null) {
        return Direction.SOUTH;
      } else if (this.west != null) {
        return Direction.WEST;
      } else {
        return Direction.NORTH;
      }

    } else {

      if (this.south != null) {
        return Direction.SOUTH;
      } else if (this.east != null) {
        return Direction.EAST;
      } else {
        return Direction.NORTH;
      }

    }
  }

  @Override
  public boolean hasDirection(Direction d) {
    return this.getLocation(d) != null;
  }


  @Override
  public boolean isTunnel() {
    int count = 0;
    if (this.north == null) {
      count++;
    }
    if (this.south == null) {
      count++;
    }
    if (this.east == null) {
      count++;
    }
    if (this.west == null) {
      count++;
    }

    return count == 2;
  }

  @Override
  public void setLocation(Direction direction, DungeonRoom location) {
    if (direction == Direction.EAST && this.east == null) {
      this.east = location;
    }
    if (direction == Direction.WEST && this.west == null) {
      this.west = location;
    }
    if (direction == Direction.NORTH && this.north == null) {
      this.north = location;
    }
    if (direction == Direction.SOUTH && this.south == null) {
      this.south = location;
    }
  }

  @Override
  public void addPlayer(Person player) {
    if (player == null) {
      throw new IllegalArgumentException("Invalid player");
    }


    this.player = player;
    this.visited = true;
  }

  @Override
  public void removePlayer() {
    this.player = null;
  }

  @Override
  public boolean hasPlayer() {
    return this.player != null;
  }

  @Override
  public void addTreasureToPlayer() {


    if (player != null) {
      if (!player.isAlive()) {
        throw new IllegalStateException("Player is not alive");
      }
      for (Treasure t : treasures) {
        player.addTreasure(t);
      }
      treasures.clear();
    } else {
      throw new IllegalStateException("no player to add treasure to");
    }
  }

  @Override
  public void addTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure");
    }
    treasures.add(treasure);
  }

  @Override
  public String getRoomDescription() {

    StringBuilder sb = new StringBuilder();

    sb.append("Location: ");
    sb.append(xLocation);
    sb.append(":");
    sb.append(yLocation);
    sb.append(" |Directions: ");
    if (this.north != null) {
      sb.append("North, ");
    }
    if (this.south != null) {
      sb.append("South, ");
    }
    if (this.east != null) {
      sb.append("East, ");
    }
    if (this.west != null) {
      sb.append("West ");
    }

    sb.append("|Treasures: ");
    for (Treasure t : treasures) {
      sb.append(t.name());
      sb.append(",");
    }

    sb.append("|Arrows: ");
    sb.append(arrowCount);

    if (isEnd) {
      sb.append("|END");
    }

    if (isStart) {
      sb.append("|START");
    }

    sb.append("|Monster: ");

    if (this.otyugh == null) {
      sb.append("none");
    } else {
      sb.append(this.otyugh.getHits());
    }

    sb.append("||");

    if (this.hasThief) {
      sb.append("Thief|");
    }

    if (this.hasTrap) {
      sb.append("Trap|");
    }

    return sb.toString();

  }

  @Override
  public void setEnd() {
    isEnd = true;
  }

  @Override
  public void setStart() {
    isStart = true;
  }

  @Override
  public int move(Direction direction) {

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive to move");
    }

    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction");
    }

    if (this.hasDirection(direction)) {

      DungeonRoom dr = this.getLocation(direction);

      if (dr.getThief()) {
        dr.removeThief();
        dr.addPlayer(player);
        player.getInventory().clear();
        player.updateLocation(dr.getLocation());
        this.removePlayer();
        return 4;
      } else if (dr.getTrap()) {
        this.player.died();
        dr.addPlayer(player);
        player.updateLocation(dr.getLocation());
        this.removePlayer();
        return 3;

      } else if (dr.hasAliveOtyugh()) {

        if (dr.getCreatureHits() == 1) {
          int r = Randomiser.getInstance().getRandomNumber(1, 2);
          if (r == 1) {
            this.player.died();
            dr.addPlayer(player);
            player.updateLocation(dr.getLocation());
            this.removePlayer();
            return 0;
          } else {
            dr.addPlayer(player);
            player.updateLocation(dr.getLocation());
            this.removePlayer();
            return 1;
          }

        } else {
          this.player.died();
          dr.addPlayer(player);
          player.updateLocation(dr.getLocation());
          this.removePlayer();
          return 0;
        }

      } else {
        dr.addPlayer(player);
        player.updateLocation(dr.getLocation());
        this.removePlayer();
        return 2;
      }


    } else {
      throw new IllegalArgumentException("Invalid direction");
    }

  }

  @Override
  public boolean isEnd() {
    return isEnd;
  }

  @Override
  public boolean isStart() {
    return isStart;
  }

  @Override
  public int getCreatureHits() {
    return this.otyugh.getHits();
  }

  private Direction swapDirection(Direction direction) {
    if (direction == Direction.NORTH) {
      return Direction.SOUTH;
    } else if (direction == Direction.SOUTH) {
      return Direction.NORTH;
    } else if (direction == Direction.EAST) {
      return Direction.WEST;
    } else {
      return Direction.EAST;
    }
  }

  @Override
  public List<Treasure> getTreasure() {
    return this.treasures;
  }


}
