package com.pedromg.bluej.shapes;

import java.util.logging.Logger;

import com.pedromg.bluej.shapes.ui.MainFrame;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            MainFrame mainFrame = new MainFrame();
            mainFrame.open();
        } catch (Exception e) {
            LOGGER.severe("An error occurred while starting the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
