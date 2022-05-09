import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import game.MVCDungeonController;
import game.MockView;
import game.Randomiser;
import game.View;
import game.ViewController;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the view controller, using a mock view.
 */
public class ViewControllerTest {

  @Before
  public void setUp() {
    Randomiser.getInstance(2);
  }

  @After
  public void after() {
    Randomiser.removeInstance();
  }

  @Test
  public void testController() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    assertEquals(m.toString(), "");
  }

  @Test
  public void testControllerPlay() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.play(false, 5, 5, 0, 0, 1, 0, false);
    assertEquals(m.toString(), "setModel\n" +
            "refresh\n" +
            "message: \n");
  }

  @Test
  public void testControllerPlayCurrent() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.playWithCurrentSettings();

    assertEquals(m.toString(), "setModel\n" +
            "refresh\n" +
            "message: \n");
  }

  @Test
  public void testControllerPlayCurrentDungeon() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.playCurrentDungeon();

    assertEquals(m.toString(), "setModel\n" +
            "refresh\n" +
            "message: \n");
  }

  @Test
  public void testControllerMultiplePlay() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.playCurrentDungeon();
    c.playWithCurrentSettings();

    assertEquals(m.toString(), "setModel\n" +
            "refresh\n" +
            "message: \n" +
            "setModel\n" +
            "refresh\n" +
            "message: \n");
  }

  @Test
  public void testControllerKey1() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(10);
    assertEquals(m.toString(), "message: \n" +
            "message: \n");
  }

  @Test
  public void testControllerKey2() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(38);
    assertEquals(m.toString(), "Invalid: Invalid Direction\n" +
            "message: \n");
  }

  @Test
  public void testControllerKey3() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(40);
    assertEquals(m.toString(), "message: \n" +
            "refresh\n" +
            "message: \n");
  }

  @Test
  public void testControllerKey4() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    assertEquals(m.toString(), "message: Shooting...\n");
  }

  @Test
  public void testControllerKey5() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    c.handleKey(40);
    assertEquals(m.toString(), "message: Shooting...\n" +
            "message: Shooting South...\n");
  }

  @Test
  public void testControllerKey6() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    c.handleKey(40);
    c.handleKey(1);
    assertEquals(m.toString(), "message: Shooting...\n" +
            "message: Shooting South...\n" +
            "Invalid: Invalid shooting distance\n" +
            "message: \n" +
            "refresh\n" +
            "message: Miss\n");
  }

  @Test
  public void testControllerKey7() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(84);
    assertEquals(m.toString(), "message: \n" +
            "refresh\n" +
            "message: Picked up all the treasure\n");
  }

  @Test
  public void testControllerKey8() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(65);
    assertEquals(m.toString(), "message: \n" +
            "refresh\n" +
            "message: Picked up all the arrows\n");
  }

  @Test
  public void testControllerKey9() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    c.handleKey(12);
    assertEquals(m.toString(), "message: Shooting...\n" +
            "Invalid: Invalid shooting direction\n" +
            "message: \n");
  }

  @Test
  public void testControllerKey10() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    c.handleKey(38);
    c.handleKey(55);
    assertEquals(m.toString(), "message: Shooting...\n" +
            "message: Shooting North...\n" +
            "message: \n" +
            "refresh\n" +
            "message: Miss\n");
  }

  @Test
  public void testControllerKey11() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.handleKey(83);
    c.handleKey(38);
    c.handleKey(55);
    c.handleKey(83);
    c.handleKey(38);
    c.handleKey(55);
    c.handleKey(83);
    c.handleKey(38);
    c.handleKey(55);
    c.handleKey(83);
    c.handleKey(38);
    c.handleKey(55);
    assertEquals(m.toString(), "message: Shooting...\n" +
            "message: Shooting North...\n" +
            "message: \n" +
            "refresh\n" +
            "message: Miss\n" +
            "message: Shooting...\n" +
            "message: Shooting North...\n" +
            "message: \n" +
            "refresh\n" +
            "message: Miss\n" +
            "message: Shooting...\n" +
            "message: Shooting North...\n" +
            "message: \n" +
            "refresh\n" +
            "message: Miss\n" +
            "message: Shooting...\n" +
            "message: Shooting North...\n" +
            "message: \n" +
            "refresh\n" +
            "message: Out of arrows\n");
  }


  @Test
  public void testControllerMove() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.move(30, 30);
    assertEquals(m.toString(), "message: \n" +
            "refresh\n");
  }


  @Test
  public void testControllerMove2() {
    ViewController c = new MVCDungeonController();
    View m = new MockView(new StringBuilder());
    c.play(m);
    c.move(300, 300);
    assertEquals(m.toString(), "message: \n" +
            "Invalid: Invalid Move\n");
  }

}
