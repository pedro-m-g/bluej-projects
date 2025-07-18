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
     * Launches the application, initializes the main user interface window, and runs the CircleDemo, SquareDemo, and TriangleDemo in sequence.
     *
     * Schedules all UI initialization and demo execution on the Swing event dispatch thread. If an exception occurs during startup, logs the error and prints the stack trace.
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
