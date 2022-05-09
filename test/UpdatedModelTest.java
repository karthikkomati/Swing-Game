import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.Direction;
import game.Dungeon;
import game.DungeonGrid;
import game.DungeonRoom;
import game.Location;

import game.Randomiser;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test cases for the updated dungeon model.
 */
public class UpdatedModelTest {

  public Randomiser r;

  @Before
  public void setUp() {
    r = Randomiser.getInstance(2);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTraps() {
    new Dungeon(false, 10, 10, 0, 10, 10, -5, false);

  }

  @Test
  public void testTraps() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 1, true);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.movePlayer(Direction.NORTH), 3);
    assertFalse(d.isPlayerAlive());
  }

  @Test
  public void testTraps2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 1, true);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.getTraps(), true);

  }

  @Test
  public void testTrapNumber() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 3, true);

    int lastIndex = 0;
    int count = 0;

    while (lastIndex != -1) {

      lastIndex = d.toString().indexOf("Trap", lastIndex);

      if (lastIndex != -1) {
        count++;
        lastIndex += 4;
      }
    }

    assertEquals(count, 3);
  }

  @Test
  public void testThief() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 3, true);
    assertTrue(d.toString().contains("Thief"));
  }

  @Test
  public void testThief2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 0, true);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);

    assertEquals(d.movePlayer(Direction.NORTH), 4);
  }

  @Test
  public void testPlayerArrowCount() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 1, 0, true);
    assertEquals(d.getPlayerArrowCount(), 3);
    d.pickUpArrows();
    assertEquals(d.getPlayerArrowCount(), 5);
  }

  @Test
  public void testIsPlayerAlive() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 1, true);
    assertTrue(d.isPlayerAlive());
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    assertFalse(d.isPlayerAlive());
  }

  @Test
  public void testPlayerLocation() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 1, 1, true);
    assertEquals(d.getPlayerLocation()[0], 3);
    assertEquals(d.getPlayerLocation()[1], 0);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.getPlayerLocation()[0], 2);
    assertEquals(d.getPlayerLocation()[1], 0);
  }

  @Test
  public void testDungeonRoomTrap() {
    DungeonRoom d = new Location(0, 0);
    assertFalse(d.getTrap());

    d.setTrap();

    assertTrue(d.getTrap());

  }

  @Test
  public void testDungeonRoomThief() {
    DungeonRoom d = new Location(0, 0);
    assertFalse(d.getThief());

    d.setThief();

    assertTrue(d.getThief());

  }
}
