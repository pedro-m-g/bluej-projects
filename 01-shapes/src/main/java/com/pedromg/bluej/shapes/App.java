package com.pedromg.bluej.shapes;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.demo.CircleDemo;
import com.pedromg.bluej.shapes.demo.SquareDemo;
import com.pedromg.bluej.shapes.demo.TriangleDemo;
import com.pedromg.bluej.shapes.ui.MainFrame;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Application entry point that initializes and displays the mainuser interface window,
     * then runs these demos:
     * - CircleDemo
     * - SquareDemo
     * - TriangleDemo
     *
     * Schedules UI initialization and demo execution on the Swing event dispatch thread.
     * Logs and prints the stack trace if an exception occurs during startup.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame mainFrame = new MainFrame();
                mainFrame.open();

                CircleDemo circleDemo = new CircleDemo();
                circleDemo.run(mainFrame);

                SquareDemo squareDemo = new SquareDemo();
                squareDemo.run(mainFrame);

                TriangleDemo triangleDemo = new TriangleDemo();
                triangleDemo.run(mainFrame);
            } catch (Exception e) {
                LOGGER.severe("An error occurred while starting the application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
