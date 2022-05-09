package game;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

/**
 * A Dungeon view class which represents the whole frame in which the game takes place.
 */
public class DungeonView extends JFrame implements View {

  ReadonlyDungeonModel model;
  ViewController c;

  InfoPanel i;
  DungeonPanel g;


  /**
   * Creates the dungeon view with the given model and controller.
   *
   * @param d the read only model
   * @param c the controller
   */
  public DungeonView(ReadonlyDungeonModel d, ViewController c) {

    super("Dungeon");


    this.model = d;
    this.c = c;


    this.setSize(670, 560);

    i = new InfoPanel(d);
    g = new DungeonPanel(d);
    MouseAdapter mouse = new Mouse(c);
    g.addMouseListener(mouse);
    this.addKeyListener(new Key(c));


    i.setPreferredSize(new Dimension(700, 100));


    this.addMenu();
    JScrollPane sp = new JScrollPane(g);
    sp.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
    sp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 15));
    this.add(sp, BorderLayout.CENTER);
    add(i, BorderLayout.SOUTH);


    this.setResizable(false);

    makeVisible();
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

  }

  private void addMenu() {

    JMenu m = new JMenu("Settings");
    JMenuItem m1 = new JMenuItem("Settings (Complete restart)");
    m1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new OptionsFrame(c);
      }
    });

    m.add(m1);

    JMenuItem m2 = new JMenuItem("Restart Current Dungeon");
    m2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        c.playCurrentDungeon();
      }
    });
    m.add(m2);

    JMenuItem m3 = new JMenuItem("Restart with Current Settings");
    m3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        c.playWithCurrentSettings();
      }
    });
    m.add(m3);

    JMenuItem m4 = new JMenuItem("Quit");
    m4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        DungeonView.this.setVisible(false);
        DungeonView.this.dispose();
      }
    });
    m.add(m4);


    JMenuBar b = new JMenuBar();
    b.add(m);
    this.setJMenuBar(b);
  }

  @Override
  public void setModel(ReadonlyDungeonModel m) {
    this.model = m;
    i.setModel(m);
    g.setModel(m);

  }


  @Override
  public void refresh() {
    repaint();

  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }


  @Override
  public void message(String message) {
    i.message(message);
  }


  @Override
  public void invalid(String s) {
    JOptionPane.showMessageDialog(this, s);
  }

}
