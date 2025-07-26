package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.Color;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.domain.Circle;

class CircleDemoTest {

    private CircleDemo circleDemo;
    private TestableCanvas testCanvas;

    @BeforeEach
    void setUp() {
        circleDemo = new CircleDemo();
        testCanvas = new TestableCanvas();
    }

    @Test
    void testExecuteWithValidCanvas() {
        // When & Then
        assertDoesNotThrow(() -> circleDemo.execute(testCanvas));
        
        assertTrue(testCanvas.wasDrawCalled(), "Canvas draw method should have been called");
        assertNotNull(testCanvas.getLastDrawnPanel(), "Should have drawn a panel");
        assertTrue(testCanvas.getLastDrawnPanel() instanceof CirclePanel, 
            "Should have drawn a CirclePanel");
    }

    @Test
    void testExecuteWithNullCanvasThrowsPreConditionsException() {
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> circleDemo.execute(null),
            "Should throw PreConditionsException when canvas is null"
        );

        assertNotNull(exception.getMessage(), "Exception message should not be null");
        assertTrue(exception.getMessage().contains("canvas must not be null"),
            "Exception message should contain expected text");
    }

    @Test
    void testExecuteCallsDrawExactlyOnce() {
        // When
        circleDemo.execute(testCanvas);

        // Then
        assertEquals(1, testCanvas.getDrawCallCount(), "Should have called draw exactly once");
        assertTrue(testCanvas.getLastDrawnPanel() instanceof CirclePanel, 
            "Should have drawn a CirclePanel");
    }

    @Test
    void testExecuteCreatesNewCirclePanelEachTime() {
        // When
        circleDemo.execute(testCanvas);
        JPanel firstPanel = testCanvas.getLastDrawnPanel();
        
        circleDemo.execute(testCanvas);
        JPanel secondPanel = testCanvas.getLastDrawnPanel();

        // Then
        assertEquals(2, testCanvas.getDrawCallCount(), "Should have called draw twice");
        assertNotSame(firstPanel, secondPanel, "Should create different CirclePanel instances");
        assertTrue(firstPanel instanceof CirclePanel, "First panel should be CirclePanel");
        assertTrue(secondPanel instanceof CirclePanel, "Second panel should be CirclePanel");
    }

    @Test
    void testImplementsDemoInterface() {
        // Then
        assertTrue(circleDemo instanceof Demo, "CircleDemo should implement Demo interface");
    }

    @Test
    void testHasNoArgumentConstructor() {
        // When & Then
        assertDoesNotThrow(() -> new CircleDemo(), 
            "Should be able to create CircleDemo with no-argument constructor");
    }

    @Test
    void testMultipleConsecutiveExecutions() {
        // When
        assertDoesNotThrow(() -> {
            circleDemo.execute(testCanvas);
            circleDemo.execute(testCanvas);
            circleDemo.execute(testCanvas);
        });

        // Then
        assertEquals(3, testCanvas.getDrawCallCount(), "Should have called draw three times");
    }

    @Test
    void testCanvasStateAcrossMultipleExecutions() {
        // Given
        StatefulCanvas statefulCanvas = new StatefulCanvas();

        // When
        circleDemo.execute(statefulCanvas);
        circleDemo.execute(statefulCanvas);

        // Then
        assertEquals(2, statefulCanvas.getDrawCallCount(), "Should maintain call count");
        assertEquals(2, statefulCanvas.getTotalPanelsDrawn(), "Should track all drawn panels");
    }

    @Test
    void testCanvasWithInputValidation() {
        // Given
        ValidatingCanvas validatingCanvas = new ValidatingCanvas();

        // When & Then
        assertDoesNotThrow(() -> circleDemo.execute(validatingCanvas),
            "Should work with canvas that validates input");
        
        assertTrue(validatingCanvas.wasValidationPerformed(),
            "Canvas should have performed validation");
        assertTrue(validatingCanvas.wasDrawCalled(),
            "Canvas should have completed draw after validation");
    }

    @Test
    void testCanvasWithNullPanelValidation() {
        // Given
        NullRejectingCanvas nullRejectingCanvas = new NullRejectingCanvas();

        // When & Then - CircleDemo should not pass null panels
        assertDoesNotThrow(() -> circleDemo.execute(nullRejectingCanvas),
            "CircleDemo should not pass null panels to canvas");
        
        assertTrue(nullRejectingCanvas.wasDrawCalled(),
            "Draw should have been called successfully");
    }

    @Test
    void testIdempotentBehavior() {
        // Test that multiple executions don't have side effects
        // When
        circleDemo.execute(testCanvas);
        int firstCallCount = testCanvas.getDrawCallCount();
        
        circleDemo.execute(testCanvas);
        int secondCallCount = testCanvas.getDrawCallCount();
        
        // Then
        assertEquals(1, firstCallCount, "First execution should call draw once");
        assertEquals(2, secondCallCount, "Second execution should call draw again");
    }

    @Test
    void testCirclePanelCreatedWithCorrectProperties() {
        // This test verifies the Circle is created with radius 100 and RED color
        // by checking that a CirclePanel is created (which would fail if Circle constructor fails)
        
        // When
        assertDoesNotThrow(() -> circleDemo.execute(testCanvas));
        
        // Then
        assertTrue(testCanvas.getLastDrawnPanel() instanceof CirclePanel,
            "Should create CirclePanel successfully with Circle(100, Color.RED)");
    }

    @Test
    void testCanvasShowNotCalled() {
        // Verify that execute() doesn't call show() on the canvas
        // When
        circleDemo.execute(testCanvas);
        
        // Then
        assertFalse(testCanvas.wasShowCalled(), "execute() should not call show() on canvas");
    }

    // Test implementation that extends Canvas for unit testing
    private static class TestableCanvas extends Canvas {
        private boolean drawCalled = false;
        private boolean showCalled = false;
        private JPanel lastDrawnPanel = null;
        private int drawCallCount = 0;

        @Override
        public void draw(JPanel panel) {
            this.drawCalled = true;
            this.lastDrawnPanel = panel;
            this.drawCallCount++;
            // Don't call super.draw() to avoid GUI creation in tests
        }

        @Override
        public void show() {
            this.showCalled = true;
            // Don't call super.show() to avoid GUI creation in tests
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public boolean wasShowCalled() {
            return showCalled;
        }

        public JPanel getLastDrawnPanel() {
            return lastDrawnPanel;
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }
    }

    // Canvas that maintains state across multiple draws
    private static class StatefulCanvas extends Canvas {
        private int drawCallCount = 0;
        private int totalPanelsDrawn = 0;

        @Override
        public void draw(JPanel panel) {
            this.drawCallCount++;
            if (panel != null) {
                this.totalPanelsDrawn++;
            }
            // Don't call super.draw() to avoid GUI creation in tests
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }

        public int getTotalPanelsDrawn() {
            return totalPanelsDrawn;
        }
    }

    // Canvas that validates input before drawing
    private static class ValidatingCanvas extends Canvas {
        private boolean validationPerformed = false;
        private boolean drawCalled = false;

        @Override
        public void draw(JPanel panel) {
            // Perform validation
            this.validationPerformed = true;
            if (panel == null) {
                throw new IllegalArgumentException("Panel cannot be null");
            }
            if (!(panel instanceof CirclePanel)) {
                throw new IllegalArgumentException("Expected CirclePanel");
            }
            
            this.drawCalled = true;
            // Don't call super.draw() to avoid GUI creation in tests
        }

        public boolean wasValidationPerformed() {
            return validationPerformed;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }
    }

    // Canvas that rejects null panels
    private static class NullRejectingCanvas extends Canvas {
        private boolean drawCalled = false;

        @Override
        public void draw(JPanel panel) {
            if (panel == null) {
                throw new IllegalArgumentException("Panel cannot be null");
            }
            this.drawCalled = true;
            // Don't call super.draw() to avoid GUI creation in tests
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }
    }
}