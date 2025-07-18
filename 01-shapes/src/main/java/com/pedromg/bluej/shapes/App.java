package com.pedromg.bluej.shapes;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.cli.CommandLineRunner;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static final Level LOG_LEVEL = Level.WARNING;

    /**
     * Application entry point that initializes and displays the main user interface window.
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
            LOGGER.log(
                Level.SEVERE,
                "An error occurred during application startup",
                e);
        }
    }

    /**
     * Sets up logging configuration to ensure all log messages are captured.
     */
    private static void setUpLogging() {
        Locale.setDefault(Locale.ENGLISH);

        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(LOG_LEVEL);
        }
    }

}
