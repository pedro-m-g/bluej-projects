package com.pedromg.bluej.shapes;

import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.pedromg.bluej.shapes.cli.CommandParser;
import com.pedromg.bluej.shapes.cli.CommandRequest;
import com.pedromg.bluej.shapes.cli.CommandRunner;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * Application entry point that initializes and displays the main user interface
     * window.
     *
     * Schedules UI initialization and demo execution on the Swing event dispatch
     * thread.
     * Logs and prints the stack trace if an exception occurs during startup.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> run(args));
    }

    /**
     * Runs the application by setting up logging and executing the command line
     * runner.
     *
     * @param args command-line arguments
     */
    private static void run(String[] args) {
        try {
            CommandParser parser = new CommandParser();
            CommandRequest request = parser.parse(args);

            configureLogging(request);
            LOGGER.info("Starting the application...");
            new CommandRunner().run(request);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            LOGGER.log(
                Level.SEVERE,
                "An error occurred during application startup",
                e);
        }
    }

    /**
     * Configures logging based on request flags
     *
     * @param request the command line request
     */
    private static void configureLogging(CommandRequest request) {
        Locale.setDefault(Locale.ENGLISH);
        Level logLevel = request.hasFlag("verbose")
            ? Level.ALL
            : Level.WARNING;

        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(logLevel);
        }
    }

}
