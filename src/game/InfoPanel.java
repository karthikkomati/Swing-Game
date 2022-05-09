package game;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Map;
import java.awt.Color;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;



/**
 * An info panel which makes up the bottom part of the JFrame. Shows player information.
 */
public class InfoPanel extends JPanel {

  private ReadonlyDungeonModel model;
  private JLabel label;

  /**
   * Creates the info panel with the given model.
   *
   * @param d the read only model
   */
  public InfoPanel(ReadonlyDungeonModel d) {
    this.model = d;
    setLayout(null);
    label = new JLabel();
    label.setBounds(205, 10, 280, 60);

    add(label);

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.drawLine(200, 0, 200, 100);
    g2d.drawLine(500, 0, 500, 100);

    int a = model.getPlayerArrowCount();

    g2d.setFont(new Font("", Font.BOLD, 15));

    g2d.drawString("INVENTORY:", 1, 13);

    g2d.setFont(new Font("", Font.BOLD, 12));


    Map<Treasure, Integer> m = model.getPlayerTreasures();


    g2d.setFont(new Font("", Font.BOLD, 15));

    if (model.isTunnel()) {
      g2d.drawString("Location: Tunnel", 503, 13);
    } else {
      g2d.drawString("Location: Cave", 503, 13);
    }


    g2d.setFont(new Font("", Font.BOLD, 12));


    BufferedImage arr = null;

    try {
      arr = ImageIO.read(getClass().getResource("/arrow-black.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2d.drawImage(arr, 503, 20, null);
    g2d.drawString(String.valueOf(model.getArrowCount()), 560, 30);

    Map<Treasure, Integer> m1 = model.getTreasure();


    BufferedImage im = null;
    try {
      im = ImageIO.read(getClass().getResource("/ruby.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2d.drawImage(im, 503, 30, null);
    g2d.drawString(String.valueOf(m1.get(Treasure.RUBY)), 530, 50);


    BufferedImage im2 = null;
    try {
      im2 = ImageIO.read(getClass().getResource("/emerald.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2d.drawImage(im2, 503, 60, null);
    g2d.drawString(String.valueOf(m1.get(Treasure.SAPPHIRE)), 535, 80);


    BufferedImage im3 = null;
    try {
      im3 = ImageIO.read(getClass().getResource("/diamond.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    g2d.drawImage(im3, 570, 30, null);
    g2d.drawString(String.valueOf(m1.get(Treasure.DIAMOND)), 610, 50);


    g2d.drawImage(im, 1, 30, null);
    g2d.drawString(String.valueOf(m.get(Treasure.RUBY)), 35, 45);


    g2d.drawImage(im2, 1, 60, null);
    g2d.drawString(String.valueOf(m.get(Treasure.SAPPHIRE)), 35, 80);

    g2d.drawImage(im3, 70, 30, null);
    g2d.drawString(String.valueOf(m.get(Treasure.RUBY)), 105, 50);

    g2d.drawImage(arr, 1, 20, null);
    g2d.drawString(String.valueOf(model.getPlayerArrowCount()), 55, 28);


    g2d.drawString("Status: ", 320, 13);
    if (model.isPlayerAlive()) {
      g2d.setColor(Color.BLUE);
      g2d.drawString("Alive", 363, 13);
    } else {
      g2d.setColor(Color.RED);
      g2d.drawString("Dead", 363, 13);
    }

    g2d.setColor(Color.BLACK);

  }

  //shows the message to the player
  void message(String message) {
    label.setText(message);
  }

  //sets the model for this panel
  void setModel(ReadonlyDungeonModel m) {
    this.model = m;
  }


}
