package com.pedromg.bluej.shapes;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.ui.MainFrame;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Launches the application by creating and opening the main user interface window.
     *
     * If an exception occurs during startup, logs a severe error message and prints the stack trace.
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
