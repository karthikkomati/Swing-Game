import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import game.Controller;
import game.Dungeon;
import game.DungeonController;
import game.DungeonGrid;
import game.Randomiser;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the dungeon controller, using mocks for readable and
 * appendable.
 */
public class ControllerTest {

  @Before
  public void setUp() {
    Randomiser.getInstance(2);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    StringReader input = new StringReader("t q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(null);
  }

  @Test
  public void testQuit() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0 , 0, false);
    StringReader input = new StringReader("q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testMove() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    StringReader input = new StringReader("m n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testMultipleMoves() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    StringReader input = new StringReader("m n m n m n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }


  @Test
  public void testInvalidMove() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    StringReader input = new StringReader("m l q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "Invalid direction \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testInvalidMove2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, false);
    StringReader input = new StringReader("m s q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "Invalid direction \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testPickUpItems() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("t q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You Picked up all the treasures in this room \n" +
            "Your items: Arrows:3 Treasures: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testPickUpArrows() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("a q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You Picked up all the arrows in this room \n" +
            "Your items: Arrows:5 Treasures: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testInvalidCommand() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("x q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Invalid command \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootHit() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 4, 0, false);
    StringReader input = new StringReader("s n 1 q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear something screaming in the distance\n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootMiss() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("s n 5 q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear nothing \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootInvalidDirection() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("s x q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "Invalid direction \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootInvalidDirection2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("s s q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "Invalid direction \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootInvalidDistance() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("s n x \n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "Invalid distance \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testShootInvalidDistance2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("s n -3 \n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "Invalid distance \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testSmell() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 4, 0, false);
    StringReader input = new StringReader("m n m n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testSmell2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 4, 0, false);
    StringReader input = new StringReader("m n m n m n q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }


  @Test
  public void testMoveDied() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 4, 0, false);
    StringReader input = new StringReader("m n m n m n m e q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You are eaten by a monster. \n" +
            "Game Over! \n");
  }

  @Test
  public void testMoveSurvive() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 4, 0, false);
    StringReader input = new StringReader("m n m n m n s e 0 m e q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear something screaming in the distance\n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "There was an injured monster in that room which you have successfully avoided \n" +
            " \n" +
            "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: SOUTH; EAST; WEST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }


  @Test
  public void testEnd() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("m n m n m n m e m s m s s s 0 m s q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: SOUTH; EAST; WEST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear something screaming in the distance\n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "There was an injured monster in that room which you have successfully avoided \n" +
            " \n" +
            "You have reached the end alive. \n" +
            "Well done. You win. \n");
  }

  @Test
  public void testEndDied() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("m n m n m n m e m s m s m s q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: SOUTH; EAST; WEST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You are eaten by a monster. \n" +
            "Game Over! \n");
  }

  @Test
  public void testEndAlive() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 100, 0, 0, false);
    StringReader input = new StringReader("m n m n m n m e m s m s s s 0 s s 0 m s q");
    Appendable gameLog = new StringBuilder();
    Controller c = new DungeonController(input, gameLog);
    c.play(d);
    assertEquals(gameLog.toString(), "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: NORTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You are currently in a Cave \n" +
            "Arrows in this cave: 2\n" +
            "Treasures in this cave: RUBY:2; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: SOUTH; EAST; WEST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There seems to be a faint terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 2\n" +
            "There is a strong terrible smell near you.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear something screaming in the distance\n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "What distance? \n" +
            "You hear something screaming in the distance\n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You have reached the end alive. \n" +
            "Well done. You win. \n");
  }
}
