package game;


import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * An image panel which is a part of the dungeon panel. Contains one dungeon room picture.
 */
public class ImagePanel extends JPanel {

  private final BufferedImage b;

  /**
   * Creates an image panel with a picture.
   *
   * @param b the picture to be drawn on the panel
   */
  public ImagePanel(BufferedImage b) {

    this.b = b;
    this.setPreferredSize(new Dimension(64, 64));
  }

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.drawImage(b, 0, 0, null);

  }
}
