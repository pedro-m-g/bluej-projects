package com.pedromg.bluej.shapes.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.pedromg.bluej.shapes.preconditions.PreConditions;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

public class Canvas {

  private static final String TITLE = "Shapes Application";

  private static final int WIDTH = 400;
  private static final int HEIGHT = 720;

  private static final int MIN_WIDTH = 400;
  private static final int MIN_HEIGHT = 720;

  private JFrame window;

  /**
   * Initializes the canvas with a title, size, minimum size, centered
   * position, and a flow layout.
   *
   * The window is configured to exit the application on close and is
   * resizable.
   */
  public Canvas() {
    window = new JFrame(TITLE);
    window.setSize(WIDTH, HEIGHT);
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null); // Center the frame on the screen
    window.setResizable(true);
    window.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
    window.setLayout(new FlowLayout());
  }

  /**
   * Shows this canvas GUI.
   */
  public void show() {
    window.setVisible(true);
  }

  /**
   * Draws the specified {@code JPanel} into this canvas.
   *
   * @param panel the JPanel to add; must not be null
   *
   * @throws PreConditionsException if the panel is null
   */
  public void draw(JPanel panel) {
    PreConditions
        .requireNonNull(panel, "panel must not be null");

    window.add(panel);
    window.revalidate();
    window.repaint();
  }

}
