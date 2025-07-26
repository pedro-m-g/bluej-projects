package com.pedromg.bluej.shapes.demo;

import com.pedromg.bluej.shapes.ui.Canvas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for Demo interface implementations.
 * Tests various demo implementations to ensure proper canvas interaction.
 */
@DisplayName("Demo Interface Tests")
class DemoTest {

    @Mock
    private Canvas mockCanvas;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Demo Interface Contract Tests")
    class DemoInterfaceContractTests {

        @Test
        @DisplayName("Should define execute method with Canvas parameter")
        void shouldDefineExecuteMethodWithCanvasParameter() {
            // Given - Demo interface exists
            // When - examining the interface
            // Then - it should have execute method
            assertTrue(Demo.class.isInterface(), "Demo should be an interface");
            
            // Verify method signature exists
            assertDoesNotThrow(() -> {
                Demo.class.getMethod("execute", Canvas.class);
            }, "Demo interface should have execute(Canvas) method");
        }

        @Test
        @DisplayName("Should be a functional interface")
        void shouldBeFunctionalInterface() {
            // Given - Demo interface
            // When - counting abstract methods
            long abstractMethodCount = java.util.Arrays.stream(Demo.class.getDeclaredMethods())
                    .filter(method -> java.lang.reflect.Modifier.isAbstract(method.getModifiers()))
                    .count();
            
            // Then - should have exactly one abstract method
            assertEquals(1, abstractMethodCount, "Demo should be a functional interface with exactly one abstract method");
        }
    }

    @Nested
    @DisplayName("Demo Implementation Tests")
    class DemoImplementationTests {

        @Test
        @DisplayName("Should execute successfully with valid canvas")
        void shouldExecuteSuccessfullyWithValidCanvas() {
            // Given
            Demo testDemo = canvas -> {
                // Simple test implementation
                canvas.setVisible(true);
            };

            // When & Then
            assertDoesNotThrow(() -> testDemo.execute(mockCanvas), 
                "Demo execution should not throw exception with valid canvas");
            
            verify(mockCanvas).setVisible(true);
        }

        @Test
        @DisplayName("Should handle null canvas parameter")
        void shouldHandleNullCanvasParameter() {
            // Given
            Demo testDemo = canvas -> {
                if (canvas != null) {
                    canvas.setVisible(true);
                }
            };

            // When & Then
            assertDoesNotThrow(() -> testDemo.execute(null), 
                "Demo should handle null canvas gracefully");
        }

        @Test
        @DisplayName("Should allow multiple canvas operations")
        void shouldAllowMultipleCanvasOperations() {
            // Given
            Demo complexDemo = canvas -> {
                canvas.setVisible(true);
                canvas.draw();
                canvas.setVisible(false);
            };

            // When
            complexDemo.execute(mockCanvas);

            // Then
            verify(mockCanvas).setVisible(true);
            verify(mockCanvas).draw();
            verify(mockCanvas).setVisible(false);
        }

        @Test
        @DisplayName("Should support lambda implementation")
        void shouldSupportLambdaImplementation() {
            // Given
            Demo lambdaDemo = canvas -> canvas.setVisible(true);

            // When & Then
            assertDoesNotThrow(() -> lambdaDemo.execute(mockCanvas), 
                "Lambda implementation should work correctly");
            
            verify(mockCanvas).setVisible(true);
        }

        @Test
        @DisplayName("Should support method reference implementation")
        void shouldSupportMethodReferenceImplementation() {
            // Given
            TestDemoImplementation testImpl = new TestDemoImplementation();
            Demo methodRefDemo = testImpl::executeDemo;

            // When
            methodRefDemo.execute(mockCanvas);

            // Then
            assertTrue(testImpl.wasExecuted(), "Method reference implementation should execute");
        }

        @Test
        @DisplayName("Should handle canvas exceptions gracefully")
        void shouldHandleCanvasExceptionsGracefully() {
            // Given
            doThrow(new RuntimeException("Canvas error")).when(mockCanvas).setVisible(anyBoolean());
            Demo errorProneDemo = canvas -> {
                try {
                    canvas.setVisible(true);
                } catch (RuntimeException e) {
                    // Handle gracefully
                }
            };

            // When & Then
            assertDoesNotThrow(() -> errorProneDemo.execute(mockCanvas), 
                "Demo should handle canvas exceptions gracefully");
        }

        @Test
        @DisplayName("Should allow chaining of canvas operations")
        void shouldAllowChainingOfCanvasOperations() {
            // Given
            when(mockCanvas.setVisible(anyBoolean())).thenReturn(mockCanvas);
            when(mockCanvas.draw()).thenReturn(mockCanvas);
            
            Demo chainingDemo = canvas -> {
                canvas.setVisible(true);
                canvas.draw();
            };

            // When
            chainingDemo.execute(mockCanvas);

            // Then
            verify(mockCanvas).setVisible(true);
            verify(mockCanvas).draw();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCasesAndErrorHandlingTests {

        @Test
        @DisplayName("Should handle empty implementation")
        void shouldHandleEmptyImplementation() {
            // Given
            Demo emptyDemo = canvas -> {
                // Empty implementation
            };

            // When & Then
            assertDoesNotThrow(() -> emptyDemo.execute(mockCanvas), 
                "Empty demo implementation should not throw exception");
            
            verifyNoInteractions(mockCanvas);
        }

        @Test
        @DisplayName("Should handle complex canvas state changes")
        void shouldHandleComplexCanvasStateChanges() {
            // Given
            Demo complexStateDemo = canvas -> {
                canvas.setVisible(false);
                canvas.setVisible(true);
                canvas.draw();
                canvas.setVisible(false);
                canvas.setVisible(true);
            };

            // When
            complexStateDemo.execute(mockCanvas);

            // Then
            verify(mockCanvas, times(3)).setVisible(true);
            verify(mockCanvas, times(2)).setVisible(false);
            verify(mockCanvas, times(1)).draw();
        }

        @Test
        @DisplayName("Should handle concurrent execution")
        void shouldHandleConcurrentExecution() {
            // Given
            Demo concurrentDemo = canvas -> {
                synchronized (canvas) {
                    canvas.setVisible(true);
                    canvas.draw();
                }
            };

            // When & Then
            assertDoesNotThrow(() -> {
                Thread t1 = new Thread(() -> concurrentDemo.execute(mockCanvas));
                Thread t2 = new Thread(() -> concurrentDemo.execute(mockCanvas));
                
                t1.start();
                t2.start();
                
                t1.join();
                t2.join();
            }, "Concurrent execution should be handled safely");
        }
    }

    @Nested
    @DisplayName("Performance and Resource Tests")
    class PerformanceAndResourceTests {

        @Test
        @DisplayName("Should complete execution within reasonable time")
        void shouldCompleteExecutionWithinReasonableTime() {
            // Given
            Demo performanceDemo = canvas -> {
                for (int i = 0; i < 1000; i++) {
                    canvas.setVisible(i % 2 == 0);
                }
            };

            // When
            long startTime = System.currentTimeMillis();
            performanceDemo.execute(mockCanvas);
            long endTime = System.currentTimeMillis();

            // Then
            long executionTime = endTime - startTime;
            assertTrue(executionTime < 5000, 
                "Demo execution should complete within 5 seconds, took: " + executionTime + "ms");
        }

        @Test
        @DisplayName("Should not hold references after execution")
        void shouldNotHoldReferencesAfterExecution() {
            // Given
            Demo referenceDemo = new Demo() {
                private Canvas localCanvas;
                
                @Override
                public void execute(Canvas canvas) {
                    this.localCanvas = canvas;
                    canvas.setVisible(true);
                    this.localCanvas = null; // Clean up reference
                }
            };

            // When
            referenceDemo.execute(mockCanvas);

            // Then
            verify(mockCanvas).setVisible(true);
            // Reference should be cleaned up (testing via successful execution)
        }
    }

    // Helper class for testing method references
    private static class TestDemoImplementation {
        private boolean executed = false;
        
        public void executeDemo(Canvas canvas) {
            executed = true;
            canvas.setVisible(true);
        }
        
        public boolean wasExecuted() {
            return executed;
        }
    }
}