package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Circle;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class CirclePanel extends JPanel {

    private final Circle circle;

    public CirclePanel(Circle circle) {
        if (circle == null) {
            throw new IllegalArgumentException("Circle cannot be null");
        }

        this.circle = circle;
        setPreferredSize(
            new Dimension(
                circle.getDiameterInPixels(),
                circle.getDiameterInPixels()));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(circle.getColor());
        g.fillOval(
            0,
            0,
            circle.getDiameterInPixels(),
            circle.getDiameterInPixels());
    }

}
