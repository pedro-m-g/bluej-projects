package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.domain.Circle;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.CirclePanel;
import com.pedromg.bluej.shapes.ui.Canvas;

class CircleDemoTest {

    private CircleDemo circleDemo;

    @BeforeEach
    void setUp() {
        circleDemo = new CircleDemo();
    }

    @Test
    void testExecuteWithValidCanvas() {
        // Given a valid canvas
        Canvas canvas = new Canvas();
        
        // When executing the demo
        // Then it should not throw any exception
        assertDoesNotThrow(() -> circleDemo.execute(canvas));
    }

    @Test
    void testExecuteWithNullCanvasThrowsPreConditionsException() {
        // Given a null canvas
        Canvas nullCanvas = null;
        
        // When executing the demo with null canvas
        // Then it should throw PreConditionsException
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> circleDemo.execute(nullCanvas)
        );
        
        // And the exception should contain the expected message
        assertTrue(exception.getViolations().contains("canvas must not be null"));
    }

    @Test
    void testExecuteCreatesCorrectCirclePanel() {
        // Given a test canvas that captures the panel
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        circleDemo.execute(testCanvas);
        
        // Then a CirclePanel should be drawn
        JPanel drawnPanel = testCanvas.getLastDrawnPanel();
        assertNotNull(drawnPanel);
        assertTrue(drawnPanel instanceof CirclePanel);
        
        // And the panel should have the correct preferred size for a circle with radius 100
        CirclePanel circlePanel = (CirclePanel) drawnPanel;
        assertEquals(200, circlePanel.getPreferredSize().width); // diameter = 2 * radius = 2 * 100
        assertEquals(200, circlePanel.getPreferredSize().height);
    }

    @Test
    void testExecuteMultipleTimes() {
        // Given a test canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo multiple times
        circleDemo.execute(testCanvas);
        circleDemo.execute(testCanvas);
        circleDemo.execute(testCanvas);
        
        // Then each execution should draw a panel
        assertEquals(3, testCanvas.getDrawCallCount());
    }

    @Test
    void testCircleDemoImplementsDemoInterface() {
        // Given a CircleDemo instance
        // When checking its type
        // Then it should implement the Demo interface
        assertTrue(circleDemo instanceof Demo);
    }

    @Test
    void testExecuteWithDifferentCanvasInstances() {
        // Given different canvas instances
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();
        
        // When executing on different canvases
        circleDemo.execute(canvas1);
        circleDemo.execute(canvas2);
        
        // Then both should receive draw calls
        assertEquals(1, canvas1.getDrawCallCount());
        assertEquals(1, canvas2.getDrawCallCount());
    }

    @Test
    void testCircleCreationProperties() {
        // Given the hardcoded values used in CircleDemo.execute()
        int expectedRadius = 100;
        Color expectedColor = Color.RED;
        
        // When creating the same circle as the demo does
        Circle testCircle = new Circle(expectedRadius, expectedColor);
        
        // Then the circle should have the expected properties
        assertEquals(expectedRadius, testCircle.radiusInPixels());
        assertEquals(expectedColor, testCircle.color());
        assertEquals(expectedRadius * 2, testCircle.diameterInPixels());
        
        // And when executing the demo
        TestCanvas testCanvas = new TestCanvas();
        circleDemo.execute(testCanvas);
        
        // The resulting panel should match these dimensions
        JPanel drawnPanel = testCanvas.getLastDrawnPanel();
        assertEquals(testCircle.diameterInPixels(), drawnPanel.getPreferredSize().width);
        assertEquals(testCircle.diameterInPixels(), drawnPanel.getPreferredSize().height);
    }

    @Test
    void testPreConditionValidationMessage() {
        // Given a null canvas
        Canvas nullCanvas = null;
        
        // When executing with null canvas
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> circleDemo.execute(nullCanvas)
        );
        
        // Then the exception should contain exactly one violation
        assertEquals(1, exception.getViolations().size());
        assertEquals("canvas must not be null", exception.getViolations().get(0));
    }

    @Test
    void testExecuteCreatesNewInstancesEachTime() {
        // Given a test canvas
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the same demo multiple times
        circleDemo.execute(testCanvas);
        JPanel firstPanel = testCanvas.getLastDrawnPanel();
        
        circleDemo.execute(testCanvas);
        JPanel secondPanel = testCanvas.getLastDrawnPanel();
        
        // Then each execution should create a new panel instance
        assertNotSame(firstPanel, secondPanel);
        
        // But both should have the same properties
        assertEquals(firstPanel.getPreferredSize(), secondPanel.getPreferredSize());
        assertEquals(firstPanel.getClass(), secondPanel.getClass());
    }

    @Test
    void testExecuteWithCanvasDrawMethodCalled() {
        // Given a test canvas that tracks method calls
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        circleDemo.execute(testCanvas);
        
        // Then the draw method should be called exactly once
        assertEquals(1, testCanvas.getDrawCallCount());
        
        // And the panel passed should be a CirclePanel
        JPanel drawnPanel = testCanvas.getLastDrawnPanel();
        assertNotNull(drawnPanel);
        assertEquals(CirclePanel.class, drawnPanel.getClass());
    }

    @Test
    void testCirclePanelConstructorWithValidCircle() {
        // Given a valid circle
        Circle circle = new Circle(100, Color.RED);
        
        // When creating a CirclePanel with this circle
        CirclePanel panel = new CirclePanel(circle);
        
        // Then the panel should be created successfully
        assertNotNull(panel);
        assertEquals(200, panel.getPreferredSize().width);
        assertEquals(200, panel.getPreferredSize().height);
    }

    @Test
    void testDemoExecutionFlow() {
        // Given a test canvas that captures operations
        TestCanvas testCanvas = new TestCanvas();
        
        // When executing the demo
        circleDemo.execute(testCanvas);
        
        // Then the exact sequence should be:
        // 1. Canvas null check passes
        // 2. Circle is created with radius=100, color=RED
        // 3. CirclePanel is created with the circle
        // 4. Canvas.draw() is called with the panel
        
        assertEquals(1, testCanvas.getDrawCallCount());
        JPanel drawnPanel = testCanvas.getLastDrawnPanel();
        assertTrue(drawnPanel instanceof CirclePanel);
        
        // Verify the panel dimensions match a circle with radius 100
        assertEquals(200, drawnPanel.getPreferredSize().width);
        assertEquals(200, drawnPanel.getPreferredSize().height);
    }

    @Test
    void testCircleValidationInDemo() {
        // Given we know the demo creates a Circle with radius=100 and Color.RED
        // When creating the same circle directly
        Circle testCircle = assertDoesNotThrow(() -> new Circle(100, Color.RED));
        
        // Then it should have the expected properties
        assertEquals(100, testCircle.radiusInPixels());
        assertEquals(Color.RED, testCircle.color());
        assertEquals(200, testCircle.diameterInPixels());
    }

    // Helper test class to capture draw operations without GUI
    private static class TestCanvas extends Canvas {
        private JPanel lastDrawnPanel;
        private int drawCallCount = 0;

        @Override
        public void draw(JPanel panel) {
            // Perform the same precondition check as the real Canvas
            if (panel == null) {
                throw new PreConditionsException(java.util.List.of("panel must not be null"));
            }
            
            this.lastDrawnPanel = panel;
            this.drawCallCount++;
            // Don't call super.draw() to avoid GUI operations in tests
        }

        public JPanel getLastDrawnPanel() {
            return lastDrawnPanel;
        }

        public int getDrawCallCount() {
            return drawCallCount;
        }
    }
}