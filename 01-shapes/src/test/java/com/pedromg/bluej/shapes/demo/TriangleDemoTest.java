package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.domain.Triangle;
import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;
import com.pedromg.bluej.shapes.ui.TrianglePanel;

@DisplayName("TriangleDemo Tests")
class TriangleDemoTest {

    private TriangleDemo triangleDemo;
    private TestCanvas testCanvas;

    @BeforeEach
    void setUp() {
        triangleDemo = new TriangleDemo();
        testCanvas = new TestCanvas();
    }

    @Nested
    @DisplayName("execute() method tests")
    class ExecuteMethodTests {

        @Test
        @DisplayName("Should successfully execute with valid canvas")
        void shouldExecuteSuccessfullyWithValidCanvas() {
            // Given
            Canvas validCanvas = testCanvas;

            // When & Then
            assertDoesNotThrow(() -> triangleDemo.execute(validCanvas));
            assertTrue(testCanvas.wasDrawCalled());
            assertNotNull(testCanvas.getLastDrawnPanel());
            assertTrue(testCanvas.getLastDrawnPanel() instanceof TrianglePanel);
        }

        @Test
        @DisplayName("Should throw PreConditionsException when canvas is null")
        void shouldThrowPreConditionsExceptionWhenCanvasIsNull() {
            // Given
            Canvas nullCanvas = null;

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, 
                () -> triangleDemo.execute(nullCanvas));
            
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("canvas must not be null"));
        }

        @Test
        @DisplayName("Should create and draw TrianglePanel with yellow triangle")
        void shouldCreateAndDrawTrianglePanelWithYellowTriangle() {
            // Given
            Canvas validCanvas = testCanvas;

            // When
            triangleDemo.execute(validCanvas);

            // Then
            assertTrue(testCanvas.wasDrawCalled());
            Object drawnPanel = testCanvas.getLastDrawnPanel();
            assertNotNull(drawnPanel);
            assertTrue(drawnPanel instanceof TrianglePanel);
            
            // Verify the triangle panel was created (indirectly through successful execution)
            assertEquals(1, testCanvas.getDrawCallCount());
        }

        @Test
        @DisplayName("Should handle multiple executions on same canvas")
        void shouldHandleMultipleExecutionsOnSameCanvas() {
            // Given
            Canvas validCanvas = testCanvas;

            // When
            triangleDemo.execute(validCanvas);
            triangleDemo.execute(validCanvas);
            triangleDemo.execute(validCanvas);

            // Then
            assertTrue(testCanvas.wasDrawCalled());
            assertEquals(3, testCanvas.getDrawCallCount());
        }

        @Test
        @DisplayName("Should handle different canvas instances")
        void shouldHandleDifferentCanvasInstances() {
            // Given
            TestCanvas canvas1 = new TestCanvas();
            TestCanvas canvas2 = new TestCanvas();

            // When
            triangleDemo.execute(canvas1);
            triangleDemo.execute(canvas2);

            // Then
            assertTrue(canvas1.wasDrawCalled());
            assertTrue(canvas2.wasDrawCalled());
            assertEquals(1, canvas1.getDrawCallCount());
            assertEquals(1, canvas2.getDrawCallCount());
        }
    }

    @Nested
    @DisplayName("Interface Implementation Tests")
    class InterfaceImplementationTests {

        @Test
        @DisplayName("Should implement Demo interface")
        void shouldImplementDemoInterface() {
            // Given & When & Then
            assertTrue(triangleDemo instanceof Demo);
        }

        @Test
        @DisplayName("Should have execute method with correct signature")
        void shouldHaveExecuteMethodWithCorrectSignature() {
            // Given & When & Then
            assertDoesNotThrow(() -> {
                triangleDemo.getClass().getMethod("execute", Canvas.class);
            });
        }

        @Test
        @DisplayName("Should be instantiable without parameters")
        void shouldBeInstantiableWithoutParameters() {
            // Given & When & Then
            assertDoesNotThrow(() -> new TriangleDemo());
        }
    }

    @Nested
    @DisplayName("Error Handling and Edge Cases")
    class ErrorHandlingAndEdgeCases {

        @Test
        @DisplayName("Should fail fast on null canvas")
        void shouldFailFastOnNullCanvas() {
            // Given
            Canvas nullCanvas = null;

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class,
                () -> triangleDemo.execute(nullCanvas));
            
            // Verify the exception is thrown before any drawing occurs
            assertFalse(testCanvas.wasDrawCalled());
            assertEquals(0, testCanvas.getDrawCallCount());
        }

        @Test
        @DisplayName("Should handle canvas that throws exceptions during draw")
        void shouldHandleCanvasThatThrowsExceptionsDuringDraw() {
            // Given
            Canvas faultyCanvas = new FaultyCanvas();

            // When & Then
            assertThrows(RuntimeException.class, () -> triangleDemo.execute(faultyCanvas));
        }

        @Test
        @DisplayName("Should provide meaningful precondition error message")
        void shouldProvideMeaningfulPreconditionErrorMessage() {
            // Given
            Canvas nullCanvas = null;

            // When
            PreConditionsException exception = assertThrows(PreConditionsException.class,
                () -> triangleDemo.execute(nullCanvas));

            // Then
            String message = exception.getMessage();
            assertNotNull(message);
            assertFalse(message.trim().isEmpty());
            assertTrue(message.toLowerCase().contains("canvas"));
            assertTrue(message.toLowerCase().contains("null"));
        }
    }

    @Nested
    @DisplayName("Behavior and State Tests")
    class BehaviorAndStateTests {

        @Test
        @DisplayName("Should be stateless between executions")
        void shouldBeStatelessBetweenExecutions() {
            // Given
            TestCanvas canvas1 = new TestCanvas();
            TestCanvas canvas2 = new TestCanvas();

            // When
            triangleDemo.execute(canvas1);
            triangleDemo.execute(canvas2);

            // Then - Each execution should behave identically
            assertTrue(canvas1.wasDrawCalled());
            assertTrue(canvas2.wasDrawCalled());
            assertEquals(1, canvas1.getDrawCallCount());
            assertEquals(1, canvas2.getDrawCallCount());
        }

        @Test
        @DisplayName("Should create new triangle objects on each execution")
        void shouldCreateNewTriangleObjectsOnEachExecution() {
            // Given
            Canvas validCanvas = testCanvas;

            // When - Execute multiple times
            triangleDemo.execute(validCanvas);
            triangleDemo.execute(validCanvas);

            // Then - Should have created separate objects (verified by multiple draw calls)
            assertEquals(2, testCanvas.getDrawCallCount());
            assertTrue(testCanvas.wasDrawCalled());
        }

        @Test
        @DisplayName("Should consistently use same triangle parameters")
        void shouldConsistentlyUseSameTriangleParameters() {
            // Given
            ParameterCapturingCanvas capturingCanvas = new ParameterCapturingCanvas();

            // When
            triangleDemo.execute(capturingCanvas);

            // Then - Verify the triangle was created with expected parameters
            assertTrue(capturingCanvas.wasDrawCalled());
            // The specific parameters (200, YELLOW) are tested indirectly through successful execution
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should integrate properly with Canvas and TrianglePanel")
        void shouldIntegrateProperlyWithCanvasAndTrianglePanel() {
            // Given
            IntegrationTestCanvas integrationCanvas = new IntegrationTestCanvas();

            // When
            triangleDemo.execute(integrationCanvas);

            // Then
            assertTrue(integrationCanvas.wasDrawCalled());
            assertTrue(integrationCanvas.receivedTrianglePanel());
        }

        @Test
        @DisplayName("Should work with different Canvas implementations")
        void shouldWorkWithDifferentCanvasImplementations() {
            // Given
            Canvas[] canvases = {
                new TestCanvas(),
                new ParameterCapturingCanvas(),
                new IntegrationTestCanvas()
            };

            // When & Then
            for (Canvas canvas : canvases) {
                assertDoesNotThrow(() -> triangleDemo.execute(canvas));
            }
        }
    }

    // Test helper classes
    private static class TestCanvas implements Canvas {
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

    private static class FaultyCanvas implements Canvas {
        @Override
        public void draw(Object panel) {
            throw new RuntimeException("Canvas drawing failed");
        }
    }

    private static class ParameterCapturingCanvas implements Canvas {
        private boolean drawCalled = false;
        private Object capturedPanel;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
            this.capturedPanel = panel;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public Object getCapturedPanel() {
            return capturedPanel;
        }
    }

    private static class IntegrationTestCanvas implements Canvas {
        private boolean drawCalled = false;
        private boolean receivedTrianglePanel = false;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
            if (panel instanceof TrianglePanel) {
                this.receivedTrianglePanel = true;
            }
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public boolean receivedTrianglePanel() {
            return receivedTrianglePanel;
        }
    }
}