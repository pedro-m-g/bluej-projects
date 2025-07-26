package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.SquarePanel;

class SquareDemoIntegrationTest {

    private SquareDemo squareDemo;

    @BeforeEach
    void setUp() {
        squareDemo = new SquareDemo();
    }

    @Test
    void testCompleteSquareDemoWorkflow() {
        // Given an integration test canvas
        IntegrationTestCanvas canvas = new IntegrationTestCanvas();
        
        // When executing the demo
        squareDemo.execute(canvas);
        
        // Then the complete workflow should succeed
        assertTrue(canvas.wasDrawCalled());
        assertNotNull(canvas.getLastDrawnPanel());
        assertTrue(canvas.getLastDrawnPanel() instanceof SquarePanel);
        
        // Verify the workflow created the expected objects
        SquarePanel panel = (SquarePanel) canvas.getLastDrawnPanel();
        assertNotNull(panel);
        
        // The panel should have the expected size (200x200 based on implementation)
        Dimension preferredSize = panel.getPreferredSize();
        assertEquals(200, preferredSize.width);
        assertEquals(200, preferredSize.height);
    }

    @Test
    void testWorkflowWithRealCanvasImplementation() {
        // Given a real canvas implementation
        Canvas realCanvas = new Canvas();
        
        // When executing the demo
        // Then it should complete without throwing exceptions
        assertDoesNotThrow(() -> squareDemo.execute(realCanvas));
    }

    @Test
    void testEndToEndSquareCreationAndDisplay() {
        // Given a verifying canvas
        VerifyingCanvas canvas = new VerifyingCanvas();
        
        // When executing the demo
        squareDemo.execute(canvas);
        
        // Then the end-to-end process should work correctly
        assertTrue(canvas.receivedCorrectSquarePanel());
        assertEquals(1, canvas.getDrawInvocationCount());
    }

    @Test
    void testIntegrationWithMultipleExecutions() {
        // Given a comprehensive test canvas
        ComprehensiveTestCanvas canvas = new ComprehensiveTestCanvas();
        
        // When executing the demo multiple times
        squareDemo.execute(canvas);
        squareDemo.execute(canvas);
        squareDemo.execute(canvas);
        
        // Then all executions should succeed and create consistent results
        assertEquals(3, canvas.getExecutionCount());
        assertTrue(canvas.allExecutionsSucceeded());
        assertTrue(canvas.allPanelsWereSquarePanels());
    }

    // Integration test helper classes
    private static class IntegrationTestCanvas extends Canvas {
        private boolean drawCalled = false;
        private Object lastDrawnPanel;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
            this.lastDrawnPanel = panel;
            
            // Perform integration validation
            if (panel instanceof SquarePanel) {
                SquarePanel squarePanel = (SquarePanel) panel;
                // Additional integration checks
                assertNotNull(squarePanel);
                assertNotNull(squarePanel.getPreferredSize());
            }
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public Object getLastDrawnPanel() {
            return lastDrawnPanel;
        }
    }

    private static class VerifyingCanvas extends Canvas {
        private boolean receivedSquarePanel = false;
        private int drawInvocationCount = 0;

        @Override
        public void draw(Object panel) {
            drawInvocationCount++;
            
            if (panel instanceof SquarePanel) {
                receivedSquarePanel = true;
                
                // Verify the complete object chain
                SquarePanel squarePanel = (SquarePanel) panel;
                assertNotNull(squarePanel);
                
                // Verify panel properties
                Dimension size = squarePanel.getPreferredSize();
                assertTrue(size.width > 0);
                assertTrue(size.height > 0);
            }
        }

        public boolean receivedCorrectSquarePanel() {
            return receivedSquarePanel;
        }

        public int getDrawInvocationCount() {
            return drawInvocationCount;
        }
    }

    private static class ComprehensiveTestCanvas extends Canvas {
        private int executionCount = 0;
        private boolean allSucceeded = true;
        private boolean allWereSquarePanels = true;

        @Override
        public void draw(Object panel) {
            executionCount++;
            
            try {
                assertNotNull(panel);
                if (!(panel instanceof SquarePanel)) {
                    allWereSquarePanels = false;
                }
                
                if (panel instanceof SquarePanel) {
                    SquarePanel squarePanel = (SquarePanel) panel;
                    Dimension size = squarePanel.getPreferredSize();
                    if (size.width <= 0 || size.height <= 0) {
                        allSucceeded = false;
                    }
                }
            } catch (Exception e) {
                allSucceeded = false;
            }
        }

        public int getExecutionCount() {
            return executionCount;
        }

        public boolean allExecutionsSucceeded() {
            return allSucceeded;
        }

        public boolean allPanelsWereSquarePanels() {
            return allWereSquarePanels;
        }
    }
}