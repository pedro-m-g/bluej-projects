package com.pedromg.bluej.shapes.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainFrame {

  private static final String TITLE = "Shapes Application";

  private static final int WIDTH = 400;
  private static final int HEIGHT = 720;

  private static final int MIN_WIDTH = 400;
  private static final int MIN_HEIGHT = 720;

  private JFrame frame;

  /**
   * Initializes the main application window with a title, size, minimum size, centered position, and a flow layout.
   *
   * The window is configured to exit the application on close and is resizable.
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
   * Makes the main application window visible to the user.
   */
  public void open() {
    frame.setVisible(true);
  }

  /**
   * Adds the specified JPanel to the main application window.
   *
   * @param panel the JPanel to add; must not be null
   * 
   * @throws NullPointerException if the panel is null
   */
  public void add(JPanel panel) {
    Objects.requireNonNull(panel, "Panel cannot be null");

    frame.add(panel);
    frame.revalidate();
    frame.repaint();
  }

}
