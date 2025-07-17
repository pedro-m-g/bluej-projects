package com.pedromg.bluej.shapes.ui;

import com.pedromg.bluej.shapes.domain.Circle;
import java.awt.Dimension;
import javax.swing.JPanel;

public class CirclePanel extends JPanel {

    private final Circle circle;

    public CirclePanel(Circle circle) {
        this.circle = circle;
        setPreferredSize(
            new Dimension(
                circle.getDiameterInPixels(),
                circle.getDiameterInPixels()));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(circle.getColor());
        g.fillOval(
            0,
            0,
            circle.getDiameterInPixels(),
            circle.getDiameterInPixels());
    }

}
