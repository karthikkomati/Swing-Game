package game;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

/**
 * An options frame that opens when the user wants to change the dungeon settings.
 */
public class OptionsFrame extends JFrame {

  private final ViewController c;
  private final JSpinner js;
  private final JSpinner js2;
  private final JSpinner js3;
  private final JSpinner js4;
  private final JSpinner js5;
  private final JSpinner js6;
  private final JComboBox jc;
  private final JComboBox jc2;

  /**
   * Creates the frame and adds all the components to it.
   *
   * @param c the controller
   */
  public OptionsFrame(ViewController c) {
    super("Options");

    this.c = c;

    this.setSize(400, 400);
    this.setLayout(new GridLayout(5, 4));

    js = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    js2 = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    js3 = new JSpinner(new SpinnerNumberModel(0, 0, 2000, 1));
    js4 = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    js5 = new JSpinner(new SpinnerNumberModel(1, 1, 1000000, 1));
    js6 = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));

    String[] a = {"True", "False"};
    jc = new JComboBox(a);
    jc2 = new JComboBox(a);


    JLabel l1 = new JLabel("Wrapping");
    JLabel l2 = new JLabel("Rows");
    JLabel l3 = new JLabel("Columns");
    JLabel l4 = new JLabel("Connectivity");
    JLabel l5 = new JLabel("Treasure Percent");
    JLabel l6 = new JLabel("Monsters");
    JLabel l7 = new JLabel("Traps");
    JLabel l8 = new JLabel("Thief");

    JPanel p1 = new JPanel();
    p1.setLayout(new FlowLayout());
    p1.add(l1);
    p1.add(jc);
    add(p1);


    JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout());
    p2.add(l2);
    p2.add(js);
    add(p2);

    JPanel p3 = new JPanel();
    p3.setLayout(new FlowLayout());
    p3.add(l3);
    p3.add(js2);
    add(p3);

    JPanel p4 = new JPanel();
    p4.setLayout(new FlowLayout());
    p4.add(l4);
    p4.add(js3);
    add(p4);

    JPanel p5 = new JPanel();
    p5.setLayout(new FlowLayout());
    p5.add(l5);
    p5.add(js4);
    add(p5);

    JPanel p6 = new JPanel();
    p6.setLayout(new FlowLayout());
    p6.add(l6);
    p6.add(js5);
    add(p6);

    JPanel p7 = new JPanel();
    p7.setLayout(new FlowLayout());
    p7.add(l7);
    p7.add(js6);
    add(p7);

    JPanel p8 = new JPanel();
    p8.setLayout(new FlowLayout());
    p8.add(l8);
    p8.add(jc2);
    add(p8);


    JButton b = new JButton("Confirm");
    b.addActionListener(new OptionListener());

    add(b);


    this.setResizable(false);

    setVisible(true);
  }

  private class OptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      boolean wrapping = String.valueOf(jc.getSelectedItem()).equals("True");

      int rows = (Integer) js.getValue();
      int cols = (Integer) js2.getValue();
      int connectivity = (Integer) js3.getValue();
      int treasurePercent = (Integer) js4.getValue();
      int monsters = (Integer) js5.getValue();
      int traps = (Integer) js6.getValue();


      boolean thief = String.valueOf(jc2.getSelectedItem()).equals("True");


      c.play(wrapping, rows, cols, connectivity, treasurePercent, monsters, traps, thief);

      setVisible(false);
      dispose();

    }
  }
}
