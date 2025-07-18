package com.pedromg.bluej.shapes;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.cli.CommandLineRunner;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Application entry point that initializes and displays the mainuser interface window.
     *
     * Schedules UI initialization and demo execution on the Swing event dispatch thread.
     * Logs and prints the stack trace if an exception occurs during startup.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> run(args));
    }

    /**
     * Runs the application by setting up logging and executing the command line runner.
     *
     * @param args command-line arguments
     */
    private static void run(String[] args) {
        try {
            setUpLogging();
            LOGGER.info("Starting the application...");
            CommandLineRunner commandLineRunner = new CommandLineRunner();
            commandLineRunner.run(args);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Sets up logging configuration to ensure all log messages are captured.
     */
    private static void setUpLogging() {
        Locale.setDefault(Locale.ENGLISH);
        
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.ALL);
        }
    }

}
