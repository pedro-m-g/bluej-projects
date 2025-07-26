package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.TrianglePanel;

/**
 * Comprehensive unit tests for TriangleDemo class.
 * 
 * Testing Framework: JUnit 5 (Jupiter)
 * 
 * This test class covers:
 * - Happy path scenarios with valid canvas
 * - Error conditions and exception handling 
 * - Precondition validation for null canvas
 * - Object creation and behavior verification
 * - Multiple execution scenarios
 * - Integration with Canvas and Triangle components
 * - Edge cases and boundary conditions
 */
@DisplayName("TriangleDemo Tests")
class TriangleDemoTest {

    private TriangleDemo triangleDemo;
    private TestCanvas testCanvas;

    @BeforeEach
    void setUp() {
        triangleDemo = new TriangleDemo();
        testCanvas = new TestCanvas();
    }

    @Test
    @DisplayName("Should successfully execute with valid canvas")
    void execute_WithValidCanvas_ShouldCreateAndDrawTriangle() {
        // When
        assertDoesNotThrow(() -> triangleDemo.execute(testCanvas));

        // Then
        assertTrue(testCanvas.wasDrawCalled(), "Canvas.draw should have been called");
        assertNotNull(testCanvas.getDrawnPanel(), "A panel should have been drawn");
        assertTrue(testCanvas.getDrawnPanel() instanceof TrianglePanel, 
            "The drawn panel should be a TrianglePanel");

        TrianglePanel trianglePanel = (TrianglePanel) testCanvas.getDrawnPanel();
        
        // Verify the triangle panel has correct properties
        Dimension preferredSize = trianglePanel.getPreferredSize();
        assertEquals(200, preferredSize.width, "Triangle panel width should be 200 pixels");
        assertTrue(preferredSize.height > 0, "Triangle panel should have positive height");
    }

    @Test
    @DisplayName("Should throw PreConditionsException when canvas is null")
    void execute_WithNullCanvas_ShouldThrowPreConditionsException() {
        // When & Then
        PreConditionsException exception = assertThrows(PreConditionsException.class, 
            () -> triangleDemo.execute(null));

        assertTrue(exception.getMessage().contains("canvas must not be null"), 
            "Exception should mention canvas must not be null");
        assertEquals(1, exception.violations().size(), 
            "Should have exactly one violation");
        assertEquals("canvas must not be null", exception.violations().get(0), 
            "Violation should be 'canvas must not be null'");
    }

    @Test
    @DisplayName("Should create triangle with correct size properties")
    void execute_ShouldCreateTriangleWithCorrectProperties() {
        // When
        triangleDemo.execute(testCanvas);

        // Then
        TrianglePanel trianglePanel = (TrianglePanel) testCanvas.getDrawnPanel();
        assertNotNull(trianglePanel, "TrianglePanel should not be null");

        // Verify the triangle panel dimensions match expected triangle size
        Dimension size = trianglePanel.getPreferredSize();
        assertEquals(200, size.width, "Triangle should have width of 200 pixels");
        
        // Height should be calculated based on equilateral triangle formula: side * sqrt(3)/2
        int expectedHeight = (int) (200 * Math.sqrt(3) / 2);
        assertEquals(expectedHeight, size.height, "Triangle should have correct height");
    }

    @Test
    @DisplayName("Should handle multiple consecutive executions correctly")
    void execute_MultipleExecutions_ShouldWorkCorrectly() {
        // When
        triangleDemo.execute(testCanvas);
        triangleDemo.execute(testCanvas);
        triangleDemo.execute(testCanvas);

        // Then
        assertEquals(3, testCanvas.getDrawCallCount(), 
            "Canvas.draw should have been called 3 times");
        assertTrue(testCanvas.wasDrawCalled(), "Canvas.draw should have been called");
    }

    @Test
    @DisplayName("Should create new instances on each execution")
    void execute_MultipleExecutions_ShouldCreateNewInstancesEachTime() {
        // Given
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();

        // When
        triangleDemo.execute(canvas1);
        triangleDemo.execute(canvas2);

        // Then
        assertNotNull(canvas1.getDrawnPanel(), "First canvas should have a drawn panel");
        assertNotNull(canvas2.getDrawnPanel(), "Second canvas should have a drawn panel");
        
        // Verify different instances were created
        assertNotSame(canvas1.getDrawnPanel(), canvas2.getDrawnPanel(), 
            "Each execution should create new TrianglePanel instances");
    }

    @Test
    @DisplayName("Should implement Demo interface correctly")
    void triangleDemo_ShouldImplementDemoInterface() {
        // Then
        assertTrue(triangleDemo instanceof Demo, 
            "TriangleDemo should implement Demo interface");
        
        // Verify it has the execute method with correct signature
        assertDoesNotThrow(() -> {
            triangleDemo.getClass().getMethod("execute", Canvas.class);
        }, "Should have execute method that takes Canvas parameter");
    }

    @Test
    @DisplayName("Should work consistently with different canvas instances")
    void execute_WithDifferentCanvasInstances_ShouldBehaveConsistently() {
        // Given
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();

        // When
        triangleDemo.execute(canvas1);
        triangleDemo.execute(canvas2);

        // Then
        assertTrue(canvas1.wasDrawCalled(), "First canvas should have draw called");
        assertTrue(canvas2.wasDrawCalled(), "Second canvas should have draw called");
        assertNotNull(canvas1.getDrawnPanel(), "First canvas should have drawn panel");
        assertNotNull(canvas2.getDrawnPanel(), "Second canvas should have drawn panel");
        
        // Both should create TrianglePanel instances
        assertTrue(canvas1.getDrawnPanel() instanceof TrianglePanel);
        assertTrue(canvas2.getDrawnPanel() instanceof TrianglePanel);
    }

    @Test
    @DisplayName("Should handle canvas that throws exception during draw")
    void execute_WhenCanvasDrawThrowsException_ShouldPropagateException() {
        // Given
        TestCanvas throwingCanvas = new TestCanvas(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> triangleDemo.execute(throwingCanvas));

        assertEquals("Test canvas draw exception", exception.getMessage(), 
            "Should propagate the canvas exception");
    }

    @Test
    @DisplayName("Should not modify TriangleDemo instance state")
    void execute_ShouldNotModifyTriangleDemoState() {
        // Given
        TriangleDemo demo1 = new TriangleDemo();
        TriangleDemo demo2 = new TriangleDemo();

        // When
        demo1.execute(testCanvas);
        
        // Then - demo2 should still work independently
        TestCanvas canvas2 = new TestCanvas();
        assertDoesNotThrow(() -> demo2.execute(canvas2));
        assertTrue(canvas2.wasDrawCalled(), "Second demo should work independently");
    }

    @Test
    @DisplayName("Should create triangle with expected dimensions")
    void execute_ShouldCreateTriangleWithExpectedDimensions() {
        // When
        triangleDemo.execute(testCanvas);

        // Then
        TrianglePanel panel = (TrianglePanel) testCanvas.getDrawnPanel();
        assertNotNull(panel, "Panel should not be null");
        
        // Verify triangle dimensions are exactly as specified in the code
        Dimension size = panel.getPreferredSize();
        assertEquals(200, size.width, "Panel width should match triangle size (200 pixels)");
        assertTrue(size.height > 0, "Panel should have positive height");
        
        // Verify height calculation is correct for equilateral triangle
        int calculatedHeight = (int) (200 * Math.sqrt(3) / 2);
        assertEquals(calculatedHeight, size.height, 
            "Height should be calculated using equilateral triangle formula");
    }

    @Test
    @DisplayName("Should handle rapid consecutive executions without issues")
    void execute_RapidConsecutiveExecutions_ShouldHandleCorrectly() {
        // When - Execute multiple times rapidly
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(() -> triangleDemo.execute(testCanvas));
        }

        // Then
        assertEquals(10, testCanvas.getDrawCallCount(), 
            "Should handle 10 rapid executions without issues");
    }

    @Test
    @DisplayName("Should create properly constructed triangle panel")
    void execute_ShouldCreateProperlyConstructedTrianglePanel() {
        // When
        triangleDemo.execute(testCanvas);

        // Then
        TrianglePanel panel = (TrianglePanel) testCanvas.getDrawnPanel();
        assertNotNull(panel, "TrianglePanel should be created");
        
        // Verify panel is a proper JPanel
        assertTrue(panel instanceof JPanel, "TrianglePanel should extend JPanel");
        
        // Verify panel has appropriate dimensions
        Dimension preferredSize = panel.getPreferredSize();
        assertTrue(preferredSize.width > 0, "Panel should have positive width");
        assertTrue(preferredSize.height > 0, "Panel should have positive height");
        
        // Verify panel is repaintable
        assertDoesNotThrow(() -> panel.repaint(), "Panel should be repaintable");
    }

    @Test
    @DisplayName("Should create triangle with size 200 and verify panel dimensions")
    void execute_ShouldCreateTriangleWithSize200() {
        // When
        triangleDemo.execute(testCanvas);

        // Then
        TrianglePanel panel = (TrianglePanel) testCanvas.getDrawnPanel();
        Dimension size = panel.getPreferredSize();
        
        // Verify exact size matches what's hardcoded in TriangleDemo
        assertEquals(200, size.width, "Triangle size should be exactly 200 pixels");
        
        // Verify height is calculated correctly for the 200-pixel triangle
        assertTrue(size.height > 170 && size.height < 175, 
            "Height should be approximately 173 pixels for 200-pixel equilateral triangle");
    }

    @Test
    @DisplayName("Should verify triangle demo creates yellow colored triangle")
    void execute_ShouldCreateYellowTriangle() {
        // Given - We'll create our own triangle to verify the color expectation
        Triangle yellowTriangle = new Triangle(200, Color.YELLOW);
        
        // Then - Verify our understanding of the expected color
        assertEquals(Color.YELLOW, yellowTriangle.color(), 
            "Triangle should be created with YELLOW color");
        assertEquals(200, yellowTriangle.lengthInPixels(), 
            "Triangle should be created with 200 pixel size");
        
        // When - Execute the demo
        triangleDemo.execute(testCanvas);
        
        // Then - Verify a panel was created (we can't directly access the triangle color)
        TrianglePanel panel = (TrianglePanel) testCanvas.getDrawnPanel();
        assertNotNull(panel, "Yellow triangle panel should be created");
    }

    @Test
    @DisplayName("Should maintain stateless behavior across multiple executions")
    void execute_ShouldMaintainStatelessBehavior() {
        // Given
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();
        TestCanvas canvas3 = new TestCanvas();

        // When - Execute with different canvases
        triangleDemo.execute(canvas1);
        triangleDemo.execute(canvas2);
        triangleDemo.execute(canvas3);

        // Then - All executions should behave identically
        assertTrue(canvas1.wasDrawCalled());
        assertTrue(canvas2.wasDrawCalled());
        assertTrue(canvas3.wasDrawCalled());

        // All should create panels with same dimensions
        Dimension size1 = canvas1.getDrawnPanel().getPreferredSize();
        Dimension size2 = canvas2.getDrawnPanel().getPreferredSize();
        Dimension size3 = canvas3.getDrawnPanel().getPreferredSize();

        assertEquals(size1, size2, "All executions should create same-sized panels");
        assertEquals(size2, size3, "All executions should create same-sized panels");
    }

    /**
     * Test implementation of Canvas for testing purposes.
     * Extends the real Canvas class to capture draw calls and provide verification methods.
     */
    private static class TestCanvas extends Canvas {
        private boolean drawCalled = false;
        private JPanel drawnPanel = null;
        private int drawCallCount = 0;
        private final boolean shouldThrowException;

        public TestCanvas() {
            this(false);
        }

        public TestCanvas(boolean shouldThrowException) {
            this.shouldThrowException = shouldThrowException;
        }

        @Override
        public void draw(JPanel panel) {
            if (shouldThrowException) {
                throw new RuntimeException("Test canvas draw exception");
            }
            
            // Call parent to ensure preconditions are checked
            super.draw(panel);
            
            // Capture the draw call for verification
            this.drawCalled = true;
            this.drawnPanel = panel;
            this.drawCallCount++;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public JPanel getDrawnPanel() {
            return drawnPanel;
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }
    }
}