package com.pedromg.bluej.shapes.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame {

  private static final String TITLE = "Shapes Application";
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  private JFrame frame;

  public MainFrame() {
    frame = new JFrame(TITLE);
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public void open() {
    frame.setVisible(true);
  }

}
