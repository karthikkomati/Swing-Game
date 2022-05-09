package game;

import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * A dungeon panel class that shows the main part of the dungeon in the frame.
 */
public class DungeonPanel extends JPanel {

  private ReadonlyDungeonModel model;

  /**
   * Creates a dungeon panel based on the given model.
   *
   * @param d the read only model
   */
  public DungeonPanel(ReadonlyDungeonModel d) {
    this.model = d;

    int rows = Math.max(6, model.getGrid().length);
    int cols = Math.max(10, model.getGrid()[0].length);
    this.setLayout(new GridLayout(rows, cols, 0, 0));

  }

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);

    this.removeAll();


    int rows = Math.max(6, model.getGrid().length);
    int cols = Math.max(10, model.getGrid()[0].length);
    this.setLayout(new GridLayout(rows, cols, 0, 0));


    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {


        if (i < model.getGrid().length && j < model.getGrid()[0].length) {

          ImagePanel p = new ImagePanel(getImage(model.getGrid()[i][j]));

          this.add(p);
        } else {
          this.add(new JPanel());
        }

      }
    }

    revalidate();

  }

  private BufferedImage getImage(ReadonlyDungeonRoom r) {


    BufferedImage im = null;

    if (r.getVisited()) {


      StringBuilder sb = new StringBuilder("/bw-cells/");
      if (r.hasDirection(Direction.NORTH)) {
        sb.append("N");
      }

      if (r.hasDirection(Direction.EAST)) {
        sb.append("E");
      }

      if (r.hasDirection(Direction.SOUTH)) {
        sb.append("S");
      }
      if (r.hasDirection(Direction.WEST)) {
        sb.append("W");
      }

      sb.append(".png");

      try {

        im = ImageIO.read(getClass().getResource(sb.toString()));
      } catch (IOException e) {
        e.printStackTrace();
      }

      if (r.hasAliveOtyugh()) {
        try {
          im = overlay(im, "/otyugh.png", 1);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      if (r.hasPlayer()) {

        try {
          im = overlay(im, "/GamePlayer.png", 17);
        } catch (IOException e) {
          e.printStackTrace();
        }

        if (model.getSmell() == 2) {
          try {
            im = overlay(im, "/stench01.png", 0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else if (model.getSmell() == 1) {
          try {
            im = overlay(im, "/stench02.png", 0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        if (model.getTraps()) {
          try {
            im = overlay(im, "/xicon.png", 0);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }


      }

    } else {
      try {
        im = ImageIO.read(getClass().getResource("/blank.png"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    return im;
  }

  private BufferedImage overlay(BufferedImage starting, String
          fpath, int offset) throws IOException {
    BufferedImage overlay = ImageIO.read(getClass().getResource(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offset, offset, null);
    return combined;
  }


  //sets the model for this panel.
  void setModel(ReadonlyDungeonModel m) {
    this.model = m;
  }
}
