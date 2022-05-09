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
 * Test cases for the updated dungeon controller, using mocks for readable and
 * appendable.
 */
public class UpdatedControllerTest {

  @Before
  public void setUp() {
    Randomiser.getInstance(2);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }

  @Test
  public void testTrap() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 1, false);
    StringReader input = new StringReader("m n m n q");
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
            "There is a trap nearby.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testTrap2() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 1, false);
    StringReader input = new StringReader("m n m n m n");
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
            "There is a trap nearby.\n" +
            "Doors lead to: NORTH; SOUTH; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "There was a trap in that room which you fell in and died \n" +
            " \n" +
            "Game Over! \n");
  }

  @Test
  public void testThief() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, true);
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
            "There thief in that room who stole all your treasure and ran away \n" +
            " \n" +
            "Your items: Arrows:3 Treasures: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "You quit. Game over. \n");
  }

  @Test
  public void testThiefFled() {
    DungeonGrid d = new Dungeon(false, 4, 3, 0, 0, 0, 0, true);
    StringReader input = new StringReader("m n m n m n m e m w q");
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
            "There thief in that room who stole all your treasure and ran away \n" +
            " \n" +
            "Your items: Arrows:3 Treasures: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            " \n" +
            "You currently are in a tunnel \n" +
            "Arrows in this tunnel: 0\n" +
            "Doors lead to: SOUTH; EAST; \n" +
            "Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n" +
            "Which direction? \n" +
            "You moved to the next location \n" +
            " \n" +
            "You are currently in a Cave \n" +
            "Arrows in this cave: 0\n" +
            "Treasures in this cave: RUBY:0; DIAMOND:0; SAPPHIRE:0; \n" +
            "Doors lead to: SOUTH; EAST; WEST; \n" +
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
}
