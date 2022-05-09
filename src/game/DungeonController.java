package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A dungeon controller class that allows the user to run the text based dungeon game.
 */
public class DungeonController implements Controller {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Creates the dungeon controller with the input and output.
   *
   * @param in  the source to get the input form
   * @param out the place to output the messages to
   */
  public DungeonController(Readable in, Appendable out) {

    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);

  }


  @Override
  public void play(DungeonGrid m) {
    if (m == null) {
      throw new IllegalArgumentException("Invalid model");
    }


    boolean hasQuit = false;
    while (m.isPlayerAlive() && !hasQuit) {

      if (m.isEnd()) {
        try {
          out.append("You have reached the end alive. \n");
          out.append("Well done. You win. \n");

        } catch (IOException e) {
          e.printStackTrace();
        }

        break;
      }

      if (m.isTunnel()) {
        try {
          out.append("You currently are in a tunnel \n");
          out.append("Arrows in this tunnel: ");
          out.append(String.valueOf(m.getArrowCount()));
          out.append("\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        try {
          out.append("You are currently in a Cave \n");
          out.append("Arrows in this cave: ");
          out.append(String.valueOf(m.getArrowCount()));
          out.append("\n");

          out.append("Treasures in this cave: ");
          Map<Treasure, Integer> treasures = m.getTreasure();
          for (Treasure t : treasures.keySet()) {
            out.append(t.name());
            out.append(":");
            out.append(String.valueOf(treasures.get(t)));
            out.append("; ");
          }

          out.append("\n");

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (m.getSmell() == 1) {
        try {
          out.append("There is a strong terrible smell near you.");
          out.append("\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else if (m.getSmell() == 2) {
        try {
          out.append("There seems to be a faint terrible smell near you.");
          out.append("\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (m.getTraps()) {
        try {
          out.append("There is a trap nearby.");
          out.append("\n");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      List<Direction> l = m.getDirections();
      List<String> l1 = new ArrayList<>();

      if (l.contains(Direction.NORTH)) {
        l1.add("n");
      }
      if (l.contains(Direction.SOUTH)) {
        l1.add("s");
      }
      if (l.contains(Direction.EAST)) {
        l1.add("e");
      }
      if (l.contains(Direction.WEST)) {
        l1.add("w");
      }

      try {
        out.append("Doors lead to: ");

        for (Direction d : l) {
          out.append(d.name());
          out.append("; ");
        }
        out.append("\n");
      } catch (IOException e) {
        e.printStackTrace();
      }

      boolean c = true;
      while (c) {

        try {
          out.append("Move, Pickup treasure, Pickup arrows or Shoot (M-T-A-S)? \n");
        } catch (IOException e) {
          e.printStackTrace();
        }

        switch (scan.next()) {
          case "M":
          case "m":
            try {
              out.append("Which direction? \n");
            } catch (IOException e) {
              e.printStackTrace();
            }
            String s = scan.next();
            if (l1.contains(s.toLowerCase())) {

              int r = m.movePlayer(getDirection(s));

              if (r == 0) {

                try {
                  out.append("You are eaten by a monster. \n");
                  out.append("Game Over! \n");

                } catch (IOException e) {
                  e.printStackTrace();
                }

              } else if (r == 1) {
                try {
                  out.append("There was an injured monster in that room " +
                          "which you have successfully avoided \n \n");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else if (r == 3) {
                try {
                  out.append("There was a trap in that room which you fell in and died \n \n");
                  out.append("Game Over! \n");

                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else if (r == 4) {
                try {
                  out.append("There thief in that room who stole all your treasure " +
                          "and ran away \n \n");
                  out.append("Your items: ");
                  out.append(m.getPlayerInventory());
                  out.append("\n \n");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                try {
                  out.append("You moved to the next location \n \n");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }

              c = false;
            } else {
              try {
                out.append("Invalid direction \n");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }

            break;

          case "T":
          case "t":
            m.pickUpItems();
            try {
              out.append("You Picked up all the treasures in this room \n");

              out.append("Your items: ");
              out.append(m.getPlayerInventory());
              out.append("\n");
            } catch (IOException e) {
              e.printStackTrace();
            }

            break;


          case "A":
          case "a":
            m.pickUpArrows();
            try {
              out.append("You Picked up all the arrows in this room \n");

              out.append("Your items: ");
              out.append(m.getPlayerInventory());
              out.append("\n");
            } catch (IOException e) {
              e.printStackTrace();
            }

            break;


          case "S":
          case "s":

            if (m.hasArrows()) {

              try {
                out.append("Which direction? \n");
              } catch (IOException e) {
                e.printStackTrace();
              }
              String s1 = scan.next();
              if (l1.contains(s1.toLowerCase())) {

                try {
                  out.append("What distance? \n");
                } catch (IOException e) {
                  e.printStackTrace();
                }
                try {
                  int length = scan.nextInt();

                  if (length >= 0) {
                    boolean hit = m.shoot(getDirection(s1), length);
                    if (hit) {
                      try {
                        out.append("You hear something screaming in the distance\n");
                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                    } else {
                      try {
                        out.append("You hear nothing \n");
                      } catch (IOException e) {
                        e.printStackTrace();
                      }
                    }
                  } else {
                    try {
                      out.append("Invalid distance \n");
                      break;
                    } catch (IOException e) {
                      e.printStackTrace();
                    }
                  }

                } catch (InputMismatchException ex) {
                  try {
                    out.append("Invalid distance \n");
                    scan.nextLine();
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }


              } else {
                try {
                  out.append("Invalid direction \n");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            } else {
              try {
                out.append("You do not have any arrows to shoot \n");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
            break;


          case "q":
          case "Q":
            c = false;
            hasQuit = true;
            try {
              out.append("You quit. Game over. \n");
            } catch (IOException e) {
              e.printStackTrace();
            }
            break;

          default:
            try {
              out.append("Invalid command \n");
            } catch (IOException e) {
              e.printStackTrace();
            }
            break;
        }
      }


    }

  }

  private Direction getDirection(String s) {
    if (s.equalsIgnoreCase("n")) {
      return Direction.NORTH;
    } else if (s.equalsIgnoreCase("s")) {
      return Direction.SOUTH;
    } else if (s.equalsIgnoreCase("e")) {
      return Direction.EAST;
    } else if (s.equalsIgnoreCase("w")) {
      return Direction.WEST;
    } else {
      throw new IllegalArgumentException("Invalid direction");
    }
  }


}
