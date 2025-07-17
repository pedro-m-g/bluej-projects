package com.pedromg.bluej.shapes;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.ui.MainFrame;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Entry point for the application; initializes and displays the main user interface window on the Swing event dispatch thread.
     *
     * If an exception occurs during UI initialization, logs a severe error and prints the stack trace.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame mainFrame = new MainFrame();
                mainFrame.open();
            } catch (Exception e) {
                LOGGER.severe("An error occurred while starting the application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
