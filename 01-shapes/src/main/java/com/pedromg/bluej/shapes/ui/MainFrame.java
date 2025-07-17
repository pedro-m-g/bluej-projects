package com.pedromg.bluej.shapes.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.plaf.DimensionUIResource;

public class MainFrame {

  private static final String TITLE = "Shapes Application";

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private static final int MIN_WIDTH = 400;
  private static final int MIN_HEIGHT = 300;

  private JFrame frame;

  public MainFrame() {
    frame = new JFrame(TITLE);
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null); // Center the frame on the screen
    frame.setResizable(true);
    frame.setMinimumSize(new DimensionUIResource(MIN_WIDTH, MIN_HEIGHT));
  }

  public void open() {
    frame.setVisible(true);
  }

}
