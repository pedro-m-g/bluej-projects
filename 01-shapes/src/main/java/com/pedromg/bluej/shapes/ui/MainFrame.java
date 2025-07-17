package com.pedromg.bluej.shapes.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame {

  private static final String TITLE = "Shapes Application";

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private static final int MIN_WIDTH = 400;
  private static final int MIN_HEIGHT = 300;

  private JFrame frame;

  /**
   * Constructs the main application window with the specified title, initial size, minimum size, and close operation.
   *
   * The window is centered on the screen and is resizable.
   */
  public MainFrame() {
    frame = new JFrame(TITLE);
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null); // Center the frame on the screen
    frame.setResizable(true);
    frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    frame.setLayout(new FlowLayout());
  }

  /**
   * Displays the main application window by making the frame visible.
   */
  public void open() {
    frame.setVisible(true);
  }

  public void add(JPanel panel) {
    if (panel == null) {
      throw new IllegalArgumentException("Panel cannot be null");
    }
    
    frame.add(panel);
    frame.revalidate();
    frame.repaint();
  }

}
