package game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * An MVC dungeon controller that acts as a link between the view and model.
 */
public class MVCDungeonController implements ViewController {

  private View view;
  private DungeonGrid model;
  private int prevInput;
  private int prevInput2;

  private boolean wrapping;
  private int rows;
  private int cols;
  private int connectivity;
  private int treasurePercent;
  private int monsters;
  private int traps;
  private boolean thief;
  private byte[] byteData;

  /**
   * Creates the controller with the default settings.
   */
  public MVCDungeonController() {
    createInitial();
  }

  private void createInitial() {

    wrapping = false;
    rows = 8;
    cols = 8;
    connectivity = 0;
    treasurePercent = 40;
    monsters = 5;
    traps = 0;
    thief = false;

    model = new Dungeon(wrapping, rows, cols, connectivity,
            treasurePercent, monsters, traps, thief);

    prevInput = -1;
    prevInput2 = -1;


    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos;
    try {
      oos = new ObjectOutputStream(bos);
      oos.writeObject(model);
      oos.flush();
      oos.close();
      bos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    byteData = bos.toByteArray();
  }

  @Override
  public void play() {
    this.view = new DungeonView(model, this);
  }

  @Override
  public void play(View v) {
    this.view = v;
  }

  @Override
  public void play(boolean wrapping, int rows, int cols, int connectivity,
                   int treasurePercent, int monsters, int traps, boolean thief) {
    try {
      DungeonGrid m = new Dungeon(wrapping, rows, cols,
              connectivity, treasurePercent, monsters, traps, thief);

      this.model = m;
      view.setModel(m);
      view.refresh();
      this.resetShot();

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(m);
      oos.flush();
      oos.close();
      bos.close();

      this.byteData = bos.toByteArray();


      this.wrapping = wrapping;
      this.rows = rows;
      this.cols = cols;
      this.connectivity = connectivity;
      this.treasurePercent = treasurePercent;
      this.monsters = monsters;
      this.traps = traps;
      this.thief = thief;

    } catch (IllegalArgumentException e) {
      view.invalid(e.getMessage().replace("DungeonGame.", ""));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalStateException ex) {
      view.invalid("Try again");
    }


  }

  @Override
  public void playWithCurrentSettings() {
    try {
      DungeonGrid m = new Dungeon(wrapping, rows, cols,
              connectivity, treasurePercent, monsters, traps, thief);
      this.model = m;
      view.setModel(m);
      view.refresh();
      this.resetShot();

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos;
      try {
        oos = new ObjectOutputStream(bos);
        oos.writeObject(model);
        oos.flush();
        oos.close();
        bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      byteData = bos.toByteArray();
    } catch (IllegalStateException ex) {
      view.invalid("Try again");
    }


  }

  @Override
  public void playCurrentDungeon() {
    ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
    DungeonGrid m = null;
    try {
      m = (DungeonGrid) new ObjectInputStream(bais).readObject();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    this.model = m;
    view.setModel(m);
    view.refresh();
    this.resetShot();
  }


  @Override
  public void move(int x, int y) {
    if (!playerCheck()) {
      return;
    }

    resetShot();
    int a = x / 64;
    int b = y / 64;


    int d = Math.abs(b - model.getPlayerLocation()[0]) + Math.abs(a - model.getPlayerLocation()[1]);
    if (d == 1) {
      Direction dir = null;

      if (b < model.getPlayerLocation()[0]) {
        dir = Direction.NORTH;
      } else if (b > model.getPlayerLocation()[0]) {
        dir = Direction.SOUTH;
      } else if (a < model.getPlayerLocation()[1]) {
        dir = Direction.WEST;
      } else if (a > model.getPlayerLocation()[1]) {
        dir = Direction.EAST;
      }

      try {
        int m = model.movePlayer(dir);
        view.refresh();
        moveMessage(m);
        playerCheck();

      } catch (IllegalArgumentException e) {
        view.invalid("Invalid Direction");
      }


    } else {
      view.invalid("Invalid Move");
    }
  }


  private void move(Direction d) {

    try {
      int m = model.movePlayer(d);

      resetShot();
      view.refresh();
      moveMessage(m);
      playerCheck();


    } catch (IllegalArgumentException e) {
      view.invalid("Invalid Direction");
    }


  }

  private void moveMessage(int m) {
    if (m == 0) {
      view.message("You are eaten by a monster! Game Over!");
    } else if (m == 1) {
      view.message("You avoided an injured monster.");
    } else if (m == 3) {
      view.message("You fell in a trap and died. Game Over!");
    } else if (m == 4) {
      view.invalid("A thief stole all your treasure and fled.");
    }
  }


  private boolean shoot(int dir, int dis) {
    Direction d = null;

    if (dir == 37) {
      d = Direction.WEST;
    } else if (dir == 38) {
      d = Direction.NORTH;
    } else if (dir == 39) {
      d = Direction.EAST;
    } else if (dir == 40) {
      d = Direction.SOUTH;
    }

    if (dis < 48 || dis > 57) {
      view.invalid("Invalid shooting distance");

    } else {
      try {

        return model.shoot(d, dis - 48);
      } catch (IllegalStateException ex) {
        view.invalid("Out of arrows");
      }

    }

    return false;
  }

  @Override
  public void handleKey(int key) {

    if (key == 10) {
      resetShot();
    } else if (!playerCheck()) {
      return;
    }
    if (prevInput == 83) {

      if (prevInput2 != -1) {

        if (model.hasArrows()) {
          boolean h = this.shoot(prevInput2, key);
          resetShot();
          view.refresh();
          if (h) {
            view.message("Hit");
          } else {
            view.message("Miss");
          }
        } else {
          resetShot();
          view.refresh();
          view.message("Out of arrows");
        }
      } else {
        if (key == 38) {
          prevInput2 = 38;
          view.message("Shooting North...");
        } else if (key == 39) {
          prevInput2 = 39;
          view.message("Shooting East...");
        } else if (key == 40) {
          prevInput2 = 40;
          view.message("Shooting South...");
        } else if (key == 37) {
          prevInput2 = 37;
          view.message("Shooting West...");
        } else {
          view.invalid("Invalid shooting direction");
          resetShot();
        }
      }
    } else {
      if (key == 65) {

        model.pickUpArrows();
        resetShot();
        view.refresh();
        view.message("Picked up all the arrows");
      } else if (key == 84) {
        model.pickUpItems();
        resetShot();
        view.refresh();
        view.message("Picked up all the treasure");
      } else if (key == 83) {
        view.message("Shooting...");
        prevInput = 83;
      } else if (key == 38) {
        this.move(Direction.NORTH);
        resetShot();
      } else if (key == 39) {
        this.move(Direction.EAST);
        resetShot();
      } else if (key == 40) {
        this.move(Direction.SOUTH);
        resetShot();
      } else if (key == 37) {
        this.move(Direction.WEST);
        resetShot();
      } else {
        resetShot();
      }
    }
  }

  private void resetShot() {
    this.prevInput = -1;
    this.prevInput2 = -1;
    view.message("");
  }

  private boolean playerCheck() {
    if (!model.isPlayerAlive()) {
      view.invalid("You are Dead. Game over!");
      return false;
    } else if (model.isEnd()) {
      view.invalid("You have reached the end. You win!");
      return false;
    }

    return true;
  }

}
