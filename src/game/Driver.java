package game;


import java.io.InputStreamReader;

/**
 * Driver class to run the program.
 */
public class Driver {

  /**
   * The main method to start the controller.
   */
  public static void main(String[] args) {
    Randomiser.removeInstance();
    if (args.length > 0) {

      DungeonGrid d = new Dungeon(Boolean.parseBoolean(args[0]), Integer.parseInt(args[1]),
              Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4])
              , Integer.parseInt(args[5]), Integer.parseInt(args[6]),
              Boolean.parseBoolean(args[7]));
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      new DungeonController(input, output).play(d);

    } else {

      ViewController c = new MVCDungeonController();
      c.play();

    }

  }
}
