import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.Direction;
import game.Dungeon;
import game.DungeonGrid;
import game.DungeonRoom;
import game.Location;
import game.Person;
import game.Player;
import game.Randomiser;
import game.Treasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * A JUnit test class to test the dungeon.
 */
public class OldModelTests {

  public Randomiser r;

  @Before
  public void setUp() {
    r = Randomiser.getInstance(2);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }

  @Test
  public void testDungeonDescription() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    assertEquals(d.toString(),
            "Location: 0:0 |Directions: South, East, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 0:1 |Directions: South, East, West " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 0:2 |Directions: South, West " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 1:0 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 1:1 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 1:2 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 2:0 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 2:1 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 2:2 |Directions: North, South, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n" +
                    "Location: 3:0 |Directions: North, " +
                    "|Treasures: |Arrows: 0|START|Monster: none||\n" +
                    "Location: 3:1 |Directions: North, " +
                    "|Treasures: |Arrows: 0|END|Monster: 2||\n" +
                    "Location: 3:2 |Directions: North, " +
                    "|Treasures: |Arrows: 0|Monster: none||\n");

  }

  @Test
  public void testRoomDescription() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    assertEquals(d.getLocationDescription(),
            "Location: 3:0 |Directions: North, |Treasures: |Arrows: 0|START|Monster: none||");
  }

  @Test
  public void testInvalidDungeonSize() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 0, 5, 0, 10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }


  @Test
  public void testInvalidDungeonSize2() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 5, 0, 0, 10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testInvalidDungeonSize3() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 5, 5, -1, 10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testInvalidDungeonSize4() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 5, 5, 0, -10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testInvalidDungeonSize5() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 5, 5, 0, 1000,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testInvalidDungeonSize6() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(false, 5, 5, 1000, 10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testInvalidDungeonSize7() {
    Throwable e = null;
    try {
      DungeonGrid d = new Dungeon(true, 5, 5, 1000, 10,0, 0, false);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }


  @Test
  public void testValidDungeon() {
    DungeonGrid d = new Dungeon(false, 5, 5, 0, 10,0, 0, false);
    assertTrue(d.toString() != null);
  }

  @Test
  public void testValidDungeon2() {
    DungeonGrid d = new Dungeon(true, 6, 5, 0, 10,0, 0, false);
    assertTrue(d.toString() != null);
  }

  @Test
  public void testMovement() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    String s = d.getLocationDescription();
    d.movePlayer(Direction.NORTH);
    String s1 = d.getLocationDescription();


    assertTrue(s != s1);
  }

  @Test
  public void testMovement2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    String s = d.getLocationDescription();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.SOUTH);
    String s1 = d.getLocationDescription();

    assertTrue(s.equals(s1));
  }

  @Test
  public void testMovement3() {

    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    String s1 = d.getLocationDescription();
    assertEquals(s1, "Location: 0:1 |Directions: South, East, West " +
            "|Treasures: |Arrows: 0|Monster: none||");
  }

  @Test
  public void testMovement4() {

    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    String s = d.getLocationDescription();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    d.movePlayer(Direction.WEST);
    String s1 = d.getLocationDescription();
    assertEquals(s1, "Location: 0:0 |Directions: South, East, " +
            "|Treasures: |Arrows: 0|Monster: none||");
  }

  @Test
  public void testIllegalMovement() {
    Throwable e = null;
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    try {
      d.movePlayer(Direction.SOUTH);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testNullMovement() {
    Throwable e = null;
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    try {
      d.movePlayer(null);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testPlayerDescriptions() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    assertEquals(d.getPlayerDescription(), "player location: 3:0 |Treasures: |Arrows: 3");
  }

  @Test
  public void testPickupItems() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    d.pickUpItems();
    assertEquals(d.getPlayerDescription(), "player location: 3:0 " +
            "|Treasures: RUBY, RUBY, |Arrows: 3");
  }


  @Test
  public void testWrapping() {
    Randomiser.removeInstance();
    DungeonGrid d = new Dungeon(true, 10, 10, 101, 0,0, 0, false);
    String s = d.getLocationDescription();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    String s1 = d.getLocationDescription();
    assertEquals(s, s1);

  }

  @Test
  public void testConnected() {
    DungeonGrid d = new Dungeon(false, 4, 3, 1, 0,0, 0, false);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.getLocationDescription(),
            "Location: 1:0 |Directions: North, South, East, " +
                    "|Treasures: |Arrows: 0|Monster: none||");

  }


  @Test
  public void testNotConnected() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    assertEquals(d.toString(), "Location: 0:0 |Directions: South, East, " +
            "|Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 0:1 |Directions: South, East, West " +
            "|Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 0:2 |Directions: South, West |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:0 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:1 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:2 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:0 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:1 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:2 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 3:0 |Directions: North, |Treasures: |Arrows: 0|START|Monster: none||\n" +
            "Location: 3:1 |Directions: North, |Treasures: |Arrows: 0|END|Monster: 2||\n" +
            "Location: 3:2 |Directions: North, |Treasures: |Arrows: 0|Monster: none||\n");
  }


  @Test
  public void testTreasure() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    String s = d.getLocationDescription();
    d.movePlayer(Direction.NORTH);
    String s1 = d.getLocationDescription();
    assertEquals(s, "Location: 3:0 |Directions: North, " +
            "|Treasures: RUBY,RUBY,|Arrows: 2|START|Monster: none||");
  }

  @Test
  public void tunnelTreasure() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    d.movePlayer(Direction.NORTH);
    String s1 = d.getLocationDescription();
    assertEquals(s1, "Location: 2:0 |Directions: North, South, " +
            "|Treasures: |Arrows: 2|Monster: none||");
  }

  @Test
  public void testTreasure1() {
    DungeonRoom dr = new Location(0, 0);
    dr.addTreasure(Treasure.DIAMOND);
    String s = dr.getRoomDescription();
    assertEquals(s, "Location: 0:0 |Directions: |Treasures: DIAMOND,|Arrows: 0|Monster: none||");
  }

  @Test
  public void testMultipleTreasure() {
    DungeonRoom dr = new Location(0, 0);
    dr.addTreasure(Treasure.DIAMOND);
    dr.addTreasure(Treasure.SAPPHIRE);
    String s = dr.getRoomDescription();
    assertEquals(s, "Location: 0:0 |Directions: " +
            "|Treasures: DIAMOND,SAPPHIRE,|Arrows: 0|Monster: none||");
  }


  @Test
  public void start() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    String s = d.getLocationDescription();
    assertTrue(s.contains("START"));
  }

  @Test
  public void start1() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    String s = d.getLocationDescription();
    assertEquals(s, "Location: 3:0 |Directions: North, " +
            "|Treasures: |Arrows: 0|START|Monster: none||");
  }


  @Test
  public void endTunnel() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);

    d.movePlayer(Direction.SOUTH);
    d.movePlayer(Direction.SOUTH);
    d.movePlayer(Direction.SOUTH);

    String s = d.getLocationDescription();
    assertEquals(s, "Location: 3:1 |Directions: North, |Treasures: |Arrows: 0|END|Monster: 2||");
  }

  @Test
  public void testPickupMultipleItems() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    d.pickUpItems();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    d.pickUpItems();
    assertEquals(d.getPlayerDescription(),
            "player location: 0:1 |Treasures: RUBY, RUBY, RUBY, RUBY, |Arrows: 3");
  }

  @Test
  public void testRoomAfterTreasure() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    d.pickUpItems();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    String s = d.getLocationDescription();
    d.pickUpItems();
    String s1 = d.getLocationDescription();
    assertTrue(!s.equals(s1));
  }

  @Test
  public void testEnd() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);

    d.movePlayer(Direction.SOUTH);
    d.movePlayer(Direction.SOUTH);


    d.movePlayer(Direction.SOUTH);


    assertTrue(d.getLocationDescription().contains("END"));
  }

  @Test
  public void testRoomAfterTreasure2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100,0, 0, false);
    d.pickUpItems();
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    d.pickUpItems();
    assertEquals(d.getLocationDescription(),
            "Location: 0:1 |Directions: South, East, West |Treasures: |Arrows: 2|Monster: none||");
  }






  @Test
  public void testInvalidDungeonRoom() {
    Throwable e = null;
    try {
      DungeonRoom dr = new Location(-1, -1);
    } catch (Exception exception) {
      e = exception;
    }
    assertTrue(e instanceof IllegalArgumentException);
  }

  @Test
  public void testRoomLocation() {
    DungeonRoom dr = new Location(2, 2);
    assertEquals(dr.getLocation()[0], 2);
    assertEquals(dr.getLocation()[1], 2);
  }

  @Test
  public void testTunnel() {
    DungeonRoom dr = new Location(2, 2);
    assertFalse(dr.isTunnel());
  }

  @Test
  public void testTunnel2() {
    DungeonRoom dr = new Location(2, 2);
    dr.setLocation(Direction.SOUTH, new Location(3, 2));
    dr.setLocation(Direction.NORTH, new Location(1, 2));
    assertTrue(dr.isTunnel());
  }

  @Test
  public void testGetLocation() {
    DungeonRoom dr = new Location(2, 2);
    assertEquals(dr.getEast(), null);
  }

  @Test
  public void testGetAndSetLocation() {
    DungeonRoom dr = new Location(2, 2);
    DungeonRoom dr2 = new Location(2, 3);
    dr.setLocation(Direction.EAST, dr2);
    assertEquals(dr.getEast(), dr2);
  }

  @Test
  public void noPlayerTest() {
    Throwable e = null;
    DungeonRoom dr = new Location(2, 2);
    try {
      dr.addTreasureToPlayer();
    } catch (Exception exception) {
      e = exception;
    }



    assertTrue(e instanceof IllegalStateException);
  }

  @Test
  public void testAddPlayer() {
    DungeonRoom dr = new Location(2, 2);
    dr.addPlayer(new Player());
    dr.addTreasureToPlayer();
    assertTrue(dr.getRoomDescription() != null);

  }

  @Test
  public void testAddInvalidPlayer() {
    Throwable e = null;
    DungeonRoom dr = new Location(2, 2);
    try {
      dr.addPlayer(null);
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalArgumentException);
  }


  @Test
  public void testRemovePlayer() {
    Throwable e = null;
    DungeonRoom dr = new Location(2, 2);
    dr.addPlayer(new Player());
    dr.removePlayer();
    try {
      dr.addTreasureToPlayer();
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalStateException);
  }

  @Test
  public void testDungeonRoomDescription() {
    DungeonRoom dr = new Location(2, 2);
    assertEquals(dr.getRoomDescription(), "Location: 2:2 |Directions: " +
            "|Treasures: |Arrows: 0|Monster: none||");
  }


  @Test
  public void testAddTreasureToRoom() {
    DungeonRoom dr = new Location(2, 2);
    dr.addTreasure(Treasure.DIAMOND);
    assertEquals(dr.getRoomDescription(), "Location: 2:2 |Directions: " +
            "|Treasures: DIAMOND,|Arrows: 0|Monster: none||");
  }

  @Test
  public void testAddMultipleTreasureToRoom() {
    DungeonRoom dr = new Location(2, 2);
    dr.addTreasure(Treasure.DIAMOND);
    dr.addTreasure(Treasure.RUBY);
    assertEquals(dr.getRoomDescription(), "Location: 2:2 |Directions: " +
            "|Treasures: DIAMOND,RUBY,|Arrows: 0|Monster: none||");
  }

  @Test
  public void moveTest() {
    Throwable e = null;
    DungeonRoom dr = new Location(2, 2);
    DungeonRoom dr2 = new Location(2, 3);
    dr.addPlayer(new Player());
    dr.setLocation(Direction.EAST, dr2);
    dr.move(Direction.EAST);
    try {
      dr.addTreasureToPlayer();
    } catch (Exception exception) {
      e = exception;
    }

    assertTrue(e instanceof IllegalStateException);
  }


  @Test
  public void moveTest2() {
    Throwable e = null;
    DungeonRoom dr = new Location(2, 2);
    DungeonRoom dr2 = new Location(2, 3);
    dr.addPlayer(new Player());
    dr.setLocation(Direction.EAST, dr2);
    dr.move(Direction.EAST);
    dr2.addTreasureToPlayer();
    assertTrue(dr2.getRoomDescription() != null);

  }

  @Test
  public void testPlayer() {
    Person p = new Player();
    assertEquals(p.getDescription(), " |Treasures: |Arrows: 3");
  }

  @Test
  public void testPlayerAddTreasure() {
    Person p = new Player();
    p.addTreasure(Treasure.SAPPHIRE);
    assertEquals(p.getDescription(), " |Treasures: SAPPHIRE, |Arrows: 3");
  }

  @Test
  public void testPlayerAddTreasure2() {
    Person p = new Player();
    p.addTreasure(Treasure.SAPPHIRE);
    p.addTreasure(Treasure.RUBY);
    assertEquals(p.getDescription(), " |Treasures: SAPPHIRE, RUBY, |Arrows: 3");
  }

  @Test
  public void testPlayerSetLocation() {
    Person p = new Player();
    p.updateLocation(new int[]{2, 2});
    assertEquals(p.getDescription(), "player location: 2:2 |Treasures: |Arrows: 3");

  }

  @Test
  public void testPlayerGetLocation() {
    Person p = new Player();
    p.updateLocation(new int[]{2, 2});
    assertEquals(p.getLocation()[0], 2);
    assertEquals(p.getLocation()[1], 2);

  }

  @Test
  public void testPlayerSetIllegalLocation() {
    Person p = new Player();
    Throwable e = null;
    try {
      p.updateLocation(null);
    } catch (Exception exception) {
      e = exception;
    }
    assertTrue(e instanceof IllegalArgumentException);

  }

  @Test
  public void testPlayerSetIllegalLocation2() {
    Person p = new Player();
    Throwable e = null;
    try {
      p.updateLocation(new int[]{2});
    } catch (Exception exception) {
      e = exception;
    }
    assertTrue(e instanceof IllegalArgumentException);

  }

  @Test
  public void testPlayerSetIllegalLocation3() {
    Person p = new Player();
    Throwable e = null;
    try {
      p.updateLocation(new int[]{-2, 2});
    } catch (Exception exception) {
      e = exception;
    }
    assertTrue(e instanceof IllegalArgumentException);

  }


  @Test
  public void testEndDistance() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0,0, 0, false);

    int c = 0;
    d.movePlayer(Direction.NORTH);
    c++;
    d.movePlayer(Direction.NORTH);
    c++;
    d.movePlayer(Direction.NORTH);
    c++;
    d.movePlayer(Direction.EAST);
    c++;
    d.movePlayer(Direction.SOUTH);
    c++;
    d.movePlayer(Direction.SOUTH);
    c++;
    d.movePlayer(Direction.SOUTH);
    c++;
    assertEquals(c, 7);
    assertEquals(d.toString(),"Location: 0:0 |Directions: South, East, " +
            "|Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 0:1 |Directions: South, East, West " +
            "|Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 0:2 |Directions: South, West |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:0 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:1 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 1:2 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:0 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:1 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 2:2 |Directions: North, South, |Treasures: |Arrows: 0|Monster: none||\n" +
            "Location: 3:0 |Directions: North, |Treasures: |Arrows: 0|START|Monster: none||\n" +
            "Location: 3:1 |Directions: North, |Treasures: |Arrows: 0|END|Monster: 2||\n" +
            "Location: 3:2 |Directions: North, |Treasures: |Arrows: 0|Monster: none||\n");

  }


}
