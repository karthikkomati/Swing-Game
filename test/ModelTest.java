import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import game.Creature;
import game.Direction;
import game.Dungeon;
import game.DungeonGrid;
import game.DungeonRoom;
import game.Location;
import game.Otyugh;
import game.Person;
import game.Player;
import game.Randomiser;
import game.Treasure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * A JUnit test class to test the dungeon model.
 */
public class ModelTest {

  @Before
  public void setUp() {
    Randomiser.getInstance(2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsters() {
    new Dungeon(false, 8, 8, 0, 10, -1, 0, false);

  }

  @Test
  public void testMoveAlive() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    assertEquals(d.movePlayer(Direction.NORTH), 2);
  }

  @Test
  public void testMoveInjuredCreature() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);
    d.shoot(Direction.NORTH, 1);
    assertEquals(d.movePlayer(Direction.NORTH), 1);
  }

  @Test
  public void testMoveDead() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    assertEquals(d.movePlayer(Direction.NORTH), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveAfterDead() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);

  }

  @Test
  public void testPickUpArrows() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    assertEquals(d.getPlayerDescription(), "player location: 3:0 |Treasures: |Arrows: 3");
    d.pickUpArrows();
    assertEquals(d.getPlayerDescription(), "player location: 3:0 |Treasures: |Arrows: 5");
  }

  @Test
  public void testArrowsAndTreasureInSameLocation() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    assertEquals(d.getPlayerDescription(), "player location: 3:0 |Treasures: |Arrows: 3");

    d.pickUpArrows();
    d.pickUpItems();

    assertEquals(d.getPlayerDescription(), "player location: 3:0 " +
            "|Treasures: RUBY, RUBY, |Arrows: 5");
  }

  @Test(expected = IllegalStateException.class)
  public void testPickUpItemsAfterDeath() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    d.movePlayer(Direction.NORTH);
    d.pickUpItems();
  }

  @Test(expected = IllegalStateException.class)
  public void testPickUpArrowsAfterDeath() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    d.movePlayer(Direction.NORTH);
    d.pickUpArrows();
  }

  @Test
  public void testIsEndFalse() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    assertFalse(d.isEnd());
  }

  @Test
  public void testIsEndTrue() {

    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.EAST);
    d.movePlayer(Direction.SOUTH);
    d.movePlayer(Direction.SOUTH);
    d.shoot(Direction.SOUTH, 0);
    d.shoot(Direction.SOUTH, 0);
    d.movePlayer(Direction.SOUTH);
    assertTrue(d.isEnd());

  }

  @Test
  public void testShoot() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    d.shoot(Direction.NORTH, 1);

    assertEquals(d.movePlayer(Direction.NORTH), 1);

  }

  @Test
  public void testShoot1() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertEquals(d.movePlayer(Direction.NORTH), 2);
  }

  @Test
  public void testShoot3() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 10, 64, 0, false);

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertFalse(d.shoot(Direction.NORTH, 1));


  }

  @Test
  public void testShoot4() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 100, 64, 0, false);

    d.pickUpArrows();

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertTrue(d.shoot(Direction.NORTH, 1));

    assertFalse(d.shoot(Direction.NORTH, 1));

    d.movePlayer(Direction.NORTH);
    d.pickUpArrows();
    d.movePlayer(Direction.SOUTH);

    assertTrue(d.shoot(Direction.NORTH, 2));

    assertTrue(d.shoot(Direction.NORTH, 2));

    assertFalse(d.shoot(Direction.NORTH, 2));

  }

  @Test
  public void testShoot5() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 100, 64, 0, false);

    d.pickUpArrows();

    assertTrue(d.shoot(Direction.NORTH, 2));

    assertTrue(d.shoot(Direction.NORTH, 2));

    assertFalse(d.shoot(Direction.NORTH, 2));
    assertEquals(d.movePlayer(Direction.NORTH), 0);

  }

  @Test
  public void testShoot6() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 2, 0, false);
    assertEquals(d.toString(), "Location: 0:0 |Directions: South, East, " +
            "|Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 0:1 |Directions: South, East, West " +
            "|Treasures: RUBY,RUBY,|Arrows: 2|Monster: 2||\n" +
            "Location: 0:2 |Directions: South, West |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 1:0 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 1:1 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 1:2 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 2:0 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 2:1 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 2:2 |Directions: North, South, |Treasures: |Arrows: 2|Monster: none||\n" +
            "Location: 3:0 |Directions: North, " +
            "|Treasures: RUBY,RUBY,|Arrows: 2|START|Monster: none||\n" +
            "Location: 3:1 |Directions: North, " +
            "|Treasures: RUBY,RUBY,|Arrows: 2|END|Monster: 2||\n" +
            "Location: 3:2 |Directions: North, " +
            "|Treasures: RUBY,RUBY,|Arrows: 2|Monster: none||\n");

    d.pickUpArrows();

    assertFalse(d.shoot(Direction.NORTH, 0));
    assertTrue(d.shoot(Direction.NORTH, 1));

    d.movePlayer(Direction.NORTH);
    assertTrue(d.shoot(Direction.NORTH, 0));


  }

  @Test
  public void shootAtWall() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    assertFalse(d.shoot(Direction.SOUTH, 1));
  }

  @Test
  public void testGetPlayerInventory() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 2, 0, false);
    assertEquals(d.getPlayerInventory(), "Arrows:3 Treasures: RUBY:0; DIAMOND:0; SAPPHIRE:0; ");
    d.pickUpItems();
    assertEquals(d.getPlayerInventory(), "Arrows:3 Treasures: RUBY:2; DIAMOND:0; SAPPHIRE:0; ");
    d.pickUpArrows();
    assertEquals(d.getPlayerInventory(), "Arrows:5 Treasures: RUBY:2; DIAMOND:0; SAPPHIRE:0; ");
  }

  @Test
  public void testGetSmell() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 2, 0, false);
    assertEquals(d.getSmell(), 0);
    d.movePlayer(Direction.NORTH);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.getSmell(), 2);
    d.movePlayer(Direction.NORTH);
    assertEquals(d.getSmell(), 1);
    d.shoot(Direction.EAST, 0);
    d.shoot(Direction.EAST, 0);
    assertEquals(d.getSmell(), 0);

  }

  @Test
  public void testGetSmell2() {
    DungeonGrid d = new Dungeon(false, 4, 5, 2, 100, 4, 0, false);

    assertEquals(d.getSmell(), 2);
    d.movePlayer(Direction.EAST);
    assertEquals(d.getSmell(), 1);
    d.movePlayer(Direction.EAST);
    assertEquals(d.getSmell(), 1);
    d.shoot(Direction.NORTH, 1);
    d.shoot(Direction.NORTH, 1);
    assertEquals(d.getSmell(), 1);
    d.movePlayer(Direction.NORTH);
    d.pickUpArrows();
    d.shoot(Direction.EAST, 1);
    d.shoot(Direction.EAST, 1);
    d.movePlayer(Direction.SOUTH);
    assertEquals(d.getSmell(), 2);

  }

  @Test
  public void testGetArrowCount() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    assertEquals(d.getArrowCount(), 2);
    d.pickUpArrows();
    assertEquals(d.getArrowCount(), 0);
  }

  @Test
  public void testGetDirections() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    List<Direction> m = d.getDirections();
    assertEquals(m.toString(), "[NORTH]");
    d.movePlayer(Direction.NORTH);
    m = d.getDirections();
    assertEquals(m.toString(), "[NORTH, SOUTH]");
  }

  @Test
  public void getTreasures() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    Map<Treasure, Integer> m = d.getTreasure();
    assertEquals(m.toString(), "{RUBY=2, DIAMOND=0, SAPPHIRE=0}");
  }


  @Test
  public void testHasArrows1() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    assertTrue(d.hasArrows());
    d.shoot(Direction.SOUTH, 1);
    d.shoot(Direction.SOUTH, 1);
    d.shoot(Direction.SOUTH, 1);
    assertFalse(d.hasArrows());

  }


  @Test(expected = IllegalStateException.class)
  public void testShootWithoutArrows() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    d.shoot(Direction.SOUTH, 1);
    d.shoot(Direction.SOUTH, 1);
    d.shoot(Direction.SOUTH, 1);
    d.shoot(Direction.SOUTH, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveDirection() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    d.movePlayer(Direction.SOUTH);
  }

  @Test
  public void testLocationArrows() {
    DungeonRoom dr = new Location(0, 0);
    assertEquals(dr.getArrowCount(), 0);
    dr.addArrows(5);
    assertEquals(dr.getArrowCount(), 5);
  }

  @Test
  public void testLocationCreature() {
    DungeonRoom dr = new Location(0, 0);
    assertFalse(dr.hasAliveOtyugh());
    dr.setOtyugh();
    assertTrue(dr.hasAliveOtyugh());
  }

  @Test
  public void testLocationHasDirection() {
    DungeonRoom dr = new Location(0, 0);
    assertFalse(dr.hasDirection(Direction.WEST));
    dr.setLocation(Direction.WEST, new Location(0, 1));
    assertTrue(dr.hasDirection(Direction.WEST));
  }

  @Test
  public void testLocationGetDirection() {
    DungeonRoom dr = new Location(0, 0);
    assertEquals(dr.getLocation(Direction.WEST), null);
    DungeonRoom dr2 = new Location(0, 1);
    dr.setLocation(Direction.WEST, dr2);
    assertEquals(dr.getLocation(Direction.WEST), dr2);
  }

  @Test
  public void testLocationTreasures() {
    DungeonRoom dr = new Location(0, 0);
    dr.addTreasure(Treasure.RUBY);
    dr.addTreasure(Treasure.DIAMOND);
    assertEquals(dr.getTreasure().toString(), "[RUBY, DIAMOND]");
  }

  @Test(expected = IllegalStateException.class)
  public void testShootAfterDeath() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 0, 64, 0, false);
    d.movePlayer(Direction.NORTH);
    d.shoot(Direction.NORTH, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShootDistance() {
    DungeonGrid d = new Dungeon(true, 8, 8, 65, 0, 64, 0, false);

    d.shoot(Direction.NORTH, -3);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCreatureToRoom() {
    DungeonRoom dr = new Location(0, 0);
    dr.setOtyugh();
    dr.setOtyugh();
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalAddArrows() {
    DungeonRoom dr = new Location(0, 0);
    dr.addArrowsToPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalAddTreasure() {
    DungeonRoom dr = new Location(0, 0);
    dr.addTreasureToPlayer();
  }

  @Test
  public void testCreatureHits() {
    Creature c = new Otyugh();
    assertEquals(c.getHits(), 2);
    c.hit();
    assertEquals(c.getHits(), 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCreatureHit() {
    Creature c = new Otyugh();

    c.hit();
    c.hit();
    c.hit();

  }

  @Test
  public void testPlayerArrow() {
    Person p = new Player();
    assertEquals(p.getArrowCount(), 3);
    p.removeArrow();
    assertEquals(p.getArrowCount(), 2);
    p.addArrows(5);
    assertEquals(p.getArrowCount(), 7);
  }

  @Test
  public void testPlayerDeath() {
    Person p = new Player();
    assertTrue(p.isAlive());
    p.died();
    assertFalse(p.isAlive());
  }

  @Test(expected = IllegalStateException.class)
  public void testIllegalRemoveArrow() {
    Person p = new Player();
    p.removeArrow();
    p.removeArrow();
    p.removeArrow();
    p.removeArrow();
  }


  @Test
  public void testArrowPercentage() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 25, 0, 0, false);

    String s = d.toString();
    int index = s.indexOf("Arrows: 2");
    int count = 0;
    while (index != -1) {
      count++;
      s = s.substring(index + 1);
      index = s.indexOf("Arrows: 2");
    }
    assertEquals(count, 3);
  }

  @Test
  public void testMonsterCount() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 3, 0, false);

    String s = d.toString();
    int index = s.indexOf("Monster: 2");
    int count = 0;
    while (index != -1) {
      count++;
      s = s.substring(index + 1);
      index = s.indexOf("Monster: 2");
    }
    assertEquals(count, 3);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }


}
