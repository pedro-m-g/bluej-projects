package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Circle;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {

    private final Circle circle;

    /**
     * Creates a panel that displays the given Circle.
     *
     * @param circle the Circle to render; must not be null
     * @throws NullPointerException if the provided Circle is null
     */
    public CirclePanel(Circle circle) {
        Objects.requireNonNull(circle, "Circle must not be null");

        this.circle = circle;
    }

    /**
     * Returns the preferred size of this panel, with both width and height set to the circle's diameter in pixels.
     *
     * @return a {@code Dimension} where width and height equal the diameter of the circle in pixels
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
            circle.getDiameterInPixels(),
            circle.getDiameterInPixels());
    }

    /**
     * Renders the associated circle onto the panel with anti-aliasing and the circle's color.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(circle.getColor());
        g.fillOval(
            0,
            0,
            circle.getDiameterInPixels(),
            circle.getDiameterInPixels());
    }

}
