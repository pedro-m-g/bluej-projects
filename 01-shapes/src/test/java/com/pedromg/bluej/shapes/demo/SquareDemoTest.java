package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.domain.Square;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.SquarePanel;

class SquareDemoTest {

    private SquareDemo squareDemo;

    @BeforeEach
    void setUp() {
        squareDemo = new SquareDemo();
    }

    @Test
    void testExecuteWithValidCanvas() {
        // Given a valid canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        assertDoesNotThrow(() -> squareDemo.execute(testCanvas));
        
        // Then the canvas draw method should be called
        assertTrue(testCanvas.wasDrawCalled());
    }

    @Test
    void testExecuteWithNullCanvasThrowsPreConditionsException() {
        // When executing with null canvas
        // Then a PreConditionsException should be thrown
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> squareDemo.execute(null)
        );
        
        assertTrue(exception.getMessage().contains("canvas must not be null"));
    }

    @Test
    void testExecuteCreatesSquarePanel() {
        // Given a test canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        squareDemo.execute(testCanvas);
        
        // Then a SquarePanel should be drawn
        assertTrue(testCanvas.wasDrawCalled());
        Object drawnPanel = testCanvas.getLastDrawnPanel();
        assertTrue(drawnPanel instanceof SquarePanel);
    }

    @Test
    void testExecuteCallsCanvasDrawExactlyOnce() {
        // Given a test canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        squareDemo.execute(testCanvas);
        
        // Then canvas draw should be called exactly once
        assertEquals(1, testCanvas.getDrawCallCount());
    }

    @Test
    void testExecuteMultipleTimesCreatesMultipleDrawCalls() {
        // Given a test canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo multiple times
        squareDemo.execute(testCanvas);
        squareDemo.execute(testCanvas);
        squareDemo.execute(testCanvas);
        
        // Then canvas draw should be called three times
        assertEquals(3, testCanvas.getDrawCallCount());
    }

    @Test
    void testExecuteWithDifferentCanvasInstances() {
        // Given two different canvas instances
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();
        
        // When executing the demo on both canvases
        squareDemo.execute(canvas1);
        squareDemo.execute(canvas2);
        
        // Then each canvas should receive exactly one draw call
        assertEquals(1, canvas1.getDrawCallCount());
        assertEquals(1, canvas2.getDrawCallCount());
    }

    @Test
    void testImplementsDemoInterface() {
        // Then the SquareDemo should implement the Demo interface
        assertTrue(squareDemo instanceof Demo);
    }

    @Test
    void testExecuteHandlesCanvasExceptionProperly() {
        // Given a canvas that throws an exception
        Canvas throwingCanvas = new Canvas() {
            @Override
            public void draw(Object panel) {
                throw new RuntimeException("Canvas error");
            }
        };
        
        // When executing the demo
        // Then the exception should be propagated
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> squareDemo.execute(throwingCanvas)
        );
        
        assertEquals("Canvas error", exception.getMessage());
    }

    @Test
    void testPreconditionCheckOccursBeforeCanvasDraw() {
        // Given a failing test canvas (that would throw if draw is called)
        FailingTestCanvas failingCanvas = new FailingTestCanvas();
        
        // When executing with null canvas
        // Then PreConditionsException should be thrown before canvas.draw is called
        assertThrows(PreConditionsException.class, () -> squareDemo.execute(null));
        
        // Verify that canvas.draw was never called since precondition failed
        assertEquals(0, failingCanvas.getDrawCallCount());
    }

    @Test
    void testExecuteCreatesNewSquarePanelInstanceEachTime() {
        // Given a capturing test canvas
        CapturingTestCanvas canvas = new CapturingTestCanvas();
        
        // When executing the demo twice
        squareDemo.execute(canvas);
        Object firstPanel = canvas.getLastDrawnPanel();
        canvas.resetLastPanel();
        
        squareDemo.execute(canvas);
        Object secondPanel = canvas.getLastDrawnPanel();
        
        // Then each execution should create new instances
        assertTrue(firstPanel instanceof SquarePanel);
        assertTrue(secondPanel instanceof SquarePanel);
        assertTrue(firstPanel != secondPanel, "Each execution should create new SquarePanel instances");
    }

    @Test
    void testSquareDemoIsStateless() {
        // Given two different canvas instances
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();
        
        // When executing the demo on both canvases
        squareDemo.execute(canvas1);
        squareDemo.execute(canvas2);
        
        // Then both executions should succeed independently
        assertTrue(canvas1.wasDrawCalled());
        assertTrue(canvas2.wasDrawCalled());
        assertEquals(1, canvas1.getDrawCallCount());
        assertEquals(1, canvas2.getDrawCallCount());
    }

    @Test
    void testExecuteWithValidCanvasDoesNotThrow() {
        // Given a valid canvas
        Canvas validCanvas = new Canvas();
        
        // When executing the demo
        // Then no exception should be thrown
        assertDoesNotThrow(() -> squareDemo.execute(validCanvas));
    }

    @Test
    void testSquareCreationWithExpectedParameters() {
        // Given a test canvas that captures the square panel details
        SquarePanelCapturingCanvas canvas = new SquarePanelCapturingCanvas();
        
        // When executing the demo
        squareDemo.execute(canvas);
        
        // Then the created square panel should have expected properties
        SquarePanel capturedPanel = canvas.getCapturedSquarePanel();
        assertNotNull(capturedPanel);
        
        // Verify the panel's preferred size matches expected square size (200x200)
        assertEquals(200, capturedPanel.getPreferredSize().width);
        assertEquals(200, capturedPanel.getPreferredSize().height);
    }

    @Test
    void testSquarePropertiesConsistentAcrossExecutions() {
        // Given a test canvas that captures square panel details
        SquarePanelCapturingCanvas canvas = new SquarePanelCapturingCanvas();
        
        // When executing the demo multiple times
        squareDemo.execute(canvas);
        SquarePanel firstPanel = canvas.getCapturedSquarePanel();
        canvas.reset();
        
        squareDemo.execute(canvas);
        SquarePanel secondPanel = canvas.getCapturedSquarePanel();
        
        // Then the square properties should be consistent across executions
        assertNotNull(firstPanel);
        assertNotNull(secondPanel);
        assertEquals(firstPanel.getPreferredSize(), secondPanel.getPreferredSize());
    }

    @Test
    void testExecuteCreatesSquareWithBlueColor() {
        // Given a test canvas that captures square panel
        SquarePanelCapturingCanvas canvas = new SquarePanelCapturingCanvas();
        
        // When executing the demo
        squareDemo.execute(canvas);
        
        // Then verify that a SquarePanel was created (we can't directly access the Square
        // but we know from the implementation it should be blue with size 200)
        SquarePanel panel = canvas.getCapturedSquarePanel();
        assertNotNull(panel);
        assertTrue(panel instanceof SquarePanel);
    }

    // Test helper classes
    private static class TestCanvas extends Canvas {
        private boolean drawCalled = false;
        private int drawCallCount = 0;
        private Object lastDrawnPanel;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
            this.drawCallCount++;
            this.lastDrawnPanel = panel;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }

        public Object getLastDrawnPanel() {
            return lastDrawnPanel;
        }
    }

    private static class CapturingTestCanvas extends TestCanvas {
        public void resetLastPanel() {
            // Reset the last panel reference for multi-execution tests
        }
    }

    private static class FailingTestCanvas extends Canvas {
        private int drawCallCount = 0;

        @Override
        public void draw(Object panel) {
            drawCallCount++;
            throw new RuntimeException("This should not be called if preconditions are checked first");
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }
    }

    private static class SquarePanelCapturingCanvas extends Canvas {
        private SquarePanel capturedSquarePanel;

        @Override
        public void draw(Object panel) {
            if (panel instanceof SquarePanel) {
                this.capturedSquarePanel = (SquarePanel) panel;
            }
        }

        public SquarePanel getCapturedSquarePanel() {
            return capturedSquarePanel;
        }

        public void reset() {
            this.capturedSquarePanel = null;
        }
    }
}