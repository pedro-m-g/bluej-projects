package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.ui.Canvas;

/**
 * Interface for demo implementations that can execute on a Canvas.
 */
public interface Demo {
    /**
     * Executes the demo on the provided canvas.
     * 
     * @param canvas the canvas to execute the demo on
     */
    void execute(Canvas canvas);
}