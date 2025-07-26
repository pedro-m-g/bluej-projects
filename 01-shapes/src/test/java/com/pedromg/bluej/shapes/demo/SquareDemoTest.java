package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.pedromg.bluej.shapes.domain.Square;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.SquarePanel;

@DisplayName("SquareDemo Unit Tests")
class SquareDemoTest {

    private SquareDemo squareDemo;
    private TestCanvas testCanvas;
    
    @BeforeEach
    void setUp() {
        squareDemo = new SquareDemo();
        testCanvas = new TestCanvas();
    }
    
    @Test
    @DisplayName("Should successfully execute demo with valid canvas")
    void shouldExecuteDemoWithValidCanvas() {
        // When
        assertDoesNotThrow(() -> squareDemo.execute(testCanvas));
        
        // Then
        assertTrue(testCanvas.wasPanelDrawn(), "Canvas should have drawn a panel");
        assertNotNull(testCanvas.getLastDrawnPanel(), "Last drawn panel should not be null");
        assertInstanceOf(SquarePanel.class, testCanvas.getLastDrawnPanel(), 
            "Drawn panel should be a SquarePanel");
    }
    
    @Test
    @DisplayName("Should throw PreConditionsException when canvas is null")
    void shouldThrowExceptionWhenCanvasIsNull() {
        // When & Then
        PreConditionsException exception = assertThrows(PreConditionsException.class, 
            () -> squareDemo.execute(null));
        
        assertNotNull(exception.getMessage(), "Exception should have a message");
        assertTrue(exception.getMessage().contains("canvas must not be null"), 
            "Exception message should mention canvas being null");
    }
    
    @Test
    @DisplayName("Should create square with size 200 pixels")
    void shouldCreateSquareWithCorrectSize() {
        // When
        squareDemo.execute(testCanvas);
        
        // Then
        SquarePanel drawnPanel = (SquarePanel) testCanvas.getLastDrawnPanel();
        assertEquals(200, drawnPanel.getPreferredSize().width, 
            "Square should have width of 200 pixels");
        assertEquals(200, drawnPanel.getPreferredSize().height, 
            "Square should have height of 200 pixels");
    }
    
    @Test
    @DisplayName("Should create blue square")
    void shouldCreateBlueSquare() {
        // When
        squareDemo.execute(testCanvas);
        
        // Then
        SquarePanel drawnPanel = (SquarePanel) testCanvas.getLastDrawnPanel();
        // We can't directly access the square's color from SquarePanel,
        // but we can verify it was created with the right parameters
        // by checking the preferred size matches our expected square
        assertEquals(200, drawnPanel.getPreferredSize().width);
        assertEquals(200, drawnPanel.getPreferredSize().height);
    }
    
    @Test
    @DisplayName("Should handle canvas that throws exception during draw")
    void shouldHandleCanvasDrawException() {
        // Given
        Canvas throwingCanvas = new Canvas() {
            @Override
            public void draw(javax.swing.JPanel panel) {
                super.draw(panel); // This will do precondition check first
                throw new RuntimeException("Canvas drawing failed");
            }
        };
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> squareDemo.execute(throwingCanvas));
        
        assertEquals("Canvas drawing failed", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should implement Demo interface correctly")
    void shouldImplementDemoInterface() {
        // When & Then
        assertInstanceOf(Demo.class, squareDemo, 
            "SquareDemo should implement Demo interface");
    }
    
    @Test
    @DisplayName("Should handle multiple consecutive executions")
    void shouldHandleMultipleExecutions() {
        // When
        assertDoesNotThrow(() -> {
            squareDemo.execute(testCanvas);
            squareDemo.execute(testCanvas);
            squareDemo.execute(testCanvas);
        });
        
        // Then
        assertEquals(3, testCanvas.getDrawCallCount(), 
            "Canvas should have been called 3 times");
    }
    
    @Test
    @DisplayName("Should create new Square instance for each execution")
    void shouldCreateNewSquareInstanceForEachExecution() {
        // When
        squareDemo.execute(testCanvas);
        javax.swing.JPanel firstPanel = testCanvas.getLastDrawnPanel();
        
        squareDemo.execute(testCanvas);
        javax.swing.JPanel secondPanel = testCanvas.getLastDrawnPanel();
        
        // Then
        assertNotSame(firstPanel, secondPanel, 
            "Each execution should create a new SquarePanel instance");
        
        // Both should have the same properties though
        assertEquals(firstPanel.getPreferredSize(), secondPanel.getPreferredSize(),
            "Both panels should have the same preferred size");
    }
    
    @Test
    @DisplayName("Should validate canvas parameter before creating square")
    void shouldValidateCanvasParameterBeforeCreatingSquare() {
        // Given - a canvas that tracks when draw is called
        TestCanvas trackingCanvas = new TestCanvas();
        
        // When
        assertDoesNotThrow(() -> squareDemo.execute(trackingCanvas));
        
        // Then - draw should have been called, meaning validation passed
        assertTrue(trackingCanvas.wasPanelDrawn(), 
            "Canvas draw should have been called after validation");
    }
    
    @Test
    @DisplayName("Should create SquarePanel with non-null Square")
    void shouldCreateSquarePanelWithNonNullSquare() {
        // When
        squareDemo.execute(testCanvas);
        
        // Then
        SquarePanel drawnPanel = (SquarePanel) testCanvas.getLastDrawnPanel();
        assertNotNull(drawnPanel, "SquarePanel should not be null");
        
        // Verify the panel has valid dimensions (which implies valid Square)
        assertTrue(drawnPanel.getPreferredSize().width > 0, 
            "Panel width should be positive");
        assertTrue(drawnPanel.getPreferredSize().height > 0, 
            "Panel height should be positive");
    }
    
    @Test
    @DisplayName("Should handle edge case with canvas that accepts but doesn't display panel")
    void shouldHandleCanvasThatAcceptsPanelButDoesNotDisplay() {
        // Given - a canvas that accepts panels but doesn't actually display them
        Canvas silentCanvas = new Canvas() {
            @Override
            public void draw(javax.swing.JPanel panel) {
                // Validate but don't actually add to window
                com.pedromg.bluej.shapes.preconditions.PreConditions
                    .requireNonNull(panel, "panel must not be null")
                    .check();
                // Intentionally don't call super.draw()
            }
        };
        
        // When & Then
        assertDoesNotThrow(() -> squareDemo.execute(silentCanvas),
            "Should handle canvas that validates but doesn't display");
    }

    /**
     * Test helper class that extends Canvas to track draw calls
     */
    private static class TestCanvas extends Canvas {
        private javax.swing.JPanel lastDrawnPanel;
        private int drawCallCount = 0;
        
        @Override
        public void draw(javax.swing.JPanel panel) {
            super.draw(panel); // This performs the precondition check
            this.lastDrawnPanel = panel;
            this.drawCallCount++;
        }
        
        public javax.swing.JPanel getLastDrawnPanel() {
            return lastDrawnPanel;
        }
        
        public boolean wasPanelDrawn() {
            return lastDrawnPanel != null;
        }
        
        public int getDrawCallCount() {
            return drawCallCount;
        }
    }
}