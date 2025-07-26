package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;

class SquareDemoEdgeCaseTest {

    private SquareDemo squareDemo;

    @BeforeEach
    void setUp() {
        squareDemo = new SquareDemo();
    }

    @Test
    void testExecuteWithCanvasThatThrowsRuntimeException() {
        // Given a canvas that throws RuntimeException
        Canvas exceptionCanvas = new Canvas() {
            @Override
            public void draw(Object panel) {
                throw new RuntimeException("Unexpected canvas error");
            }
        };
        
        // When executing the demo
        // Then the RuntimeException should be propagated
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> squareDemo.execute(exceptionCanvas)
        );
        
        assertEquals("Unexpected canvas error", exception.getMessage());
    }

    @Test
    void testExecuteWithCanvasThatThrowsError() {
        // Given a canvas that throws Error
        Canvas errorCanvas = new Canvas() {
            @Override
            public void draw(Object panel) {
                throw new OutOfMemoryError("Simulated memory error");
            }
        };
        
        // When executing the demo
        // Then the Error should be propagated
        OutOfMemoryError error = assertThrows(
            OutOfMemoryError.class,
            () -> squareDemo.execute(errorCanvas)
        );
        
        assertEquals("Simulated memory error", error.getMessage());
    }

    @Test
    void testPreConditionMessageContainsExpectedText() {
        // When executing with null canvas
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> squareDemo.execute(null)
        );
        
        // Then the exception message should contain the expected text
        String message = exception.getMessage();
        assertTrue(message.contains("canvas must not be null"));
    }

    @Test
    void testExecuteWithCanvasSubclass() {
        // Given a canvas subclass
        CanvasSubclass canvasSubclass = new CanvasSubclass();
        
        // When executing the demo
        // Then it should work without issues
        assertDoesNotThrow(() -> squareDemo.execute(canvasSubclass));
        assertTrue(canvasSubclass.wasDrawCalled());
    }

    @Test
    void testConcurrentExecution() throws InterruptedException {
        // Given multiple canvas instances
        TestCanvas canvas1 = new TestCanvas();
        TestCanvas canvas2 = new TestCanvas();
        
        // When executing concurrently (simulated)
        Thread thread1 = new Thread(() -> squareDemo.execute(canvas1));
        Thread thread2 = new Thread(() -> squareDemo.execute(canvas2));
        
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        
        // Then both should complete without interference
        assertTrue(canvas1.wasDrawCalled());
        assertTrue(canvas2.wasDrawCalled());
    }

    @Test
    void testExecuteAfterException() {
        // Given a canvas that initially throws an exception
        ExceptionThenSuccessCanvas canvas = new ExceptionThenSuccessCanvas();
        
        // When executing the demo first time (should throw)
        assertThrows(RuntimeException.class, () -> squareDemo.execute(canvas));
        
        // And executing again (should succeed)
        canvas.disableException();
        assertDoesNotThrow(() -> squareDemo.execute(canvas));
        assertTrue(canvas.wasDrawCalled());
    }

    @Test
    void testExecuteWithNullCanvasMultipleTimes() {
        // When executing with null canvas multiple times
        // Then each call should throw PreConditionsException
        assertThrows(PreConditionsException.class, () -> squareDemo.execute(null));
        assertThrows(PreConditionsException.class, () -> squareDemo.execute(null));
        assertThrows(PreConditionsException.class, () -> squareDemo.execute(null));
    }

    @Test
    void testExecuteDoesNotModifyCanvas() {
        // Given a canvas that tracks modifications
        ModificationTrackingCanvas canvas = new ModificationTrackingCanvas();
        
        // When executing the demo
        squareDemo.execute(canvas);
        
        // Then the canvas should only have draw called, no other modifications
        assertTrue(canvas.wasDrawCalled());
        assertEquals(1, canvas.getMethodCallCount());
    }

    // Test helper classes
    private static class CanvasSubclass extends Canvas {
        private boolean drawCalled = false;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }
    }

    private static class TestCanvas extends Canvas {
        private boolean drawCalled = false;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }
    }

    private static class ExceptionThenSuccessCanvas extends Canvas {
        private boolean shouldThrowException = true;
        private boolean drawCalled = false;

        @Override
        public void draw(Object panel) {
            if (shouldThrowException) {
                throw new RuntimeException("First call exception");
            }
            this.drawCalled = true;
        }

        public void disableException() {
            this.shouldThrowException = false;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }
    }

    private static class ModificationTrackingCanvas extends Canvas {
        private boolean drawCalled = false;
        private int methodCallCount = 0;

        @Override
        public void draw(Object panel) {
            this.drawCalled = true;
            this.methodCallCount++;
        }

        public boolean wasDrawCalled() {
            return drawCalled;
        }

        public int getMethodCallCount() {
            return methodCallCount;
        }
    }
}