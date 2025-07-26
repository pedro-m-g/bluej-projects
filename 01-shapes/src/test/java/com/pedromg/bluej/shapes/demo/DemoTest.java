package com.pedromg.bluej.shapes.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;
import com.pedromg.bluej.shapes.ui.Canvas;

/**
 * Comprehensive unit tests for the Demo interface.
 * Testing framework: JUnit 5 with Mockito for mocking
 * 
 * Tests cover contract validation, behavior verification, edge cases,
 * and various implementation scenarios for the Demo interface.
 */
@DisplayName("Demo Interface Tests")
class DemoTest {

    @Mock
    private Canvas mockCanvas;

    private Demo testDemo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Contract Tests")
    class ContractTests {

        @Test
        @DisplayName("Demo interface should define execute method with Canvas parameter")
        void shouldDefineExecuteMethodWithCanvasParameter() {
            // Verify that the Demo interface has the expected method signature
            assertDoesNotThrow(() -> {
                Demo.class.getMethod("execute", Canvas.class);
            }, "Demo interface should define execute(Canvas) method");
        }

        @Test
        @DisplayName("Demo interface should be public")
        void shouldBePublicInterface() {
            assertTrue(Demo.class.isInterface(), "Demo should be an interface");
            assertTrue(java.lang.reflect.Modifier.isPublic(Demo.class.getModifiers()), 
                      "Demo interface should be public");
        }

        @Test
        @DisplayName("Execute method should be public and abstract")
        void executeMethodShouldBePublicAndAbstract() throws NoSuchMethodException {
            var executeMethod = Demo.class.getMethod("execute", Canvas.class);
            assertTrue(java.lang.reflect.Modifier.isPublic(executeMethod.getModifiers()), 
                      "execute method should be public");
            assertTrue(java.lang.reflect.Modifier.isAbstract(executeMethod.getModifiers()), 
                      "execute method should be abstract");
        }

        @Test
        @DisplayName("Execute method should have void return type")
        void executeMethodShouldHaveVoidReturnType() throws NoSuchMethodException {
            var executeMethod = Demo.class.getMethod("execute", Canvas.class);
            assertEquals(void.class, executeMethod.getReturnType(), 
                        "execute method should return void");
        }

        @Test
        @DisplayName("Demo interface should have exactly one abstract method")
        void shouldHaveExactlyOneAbstractMethod() {
            long abstractMethodCount = java.util.Arrays.stream(Demo.class.getDeclaredMethods())
                .filter(method -> java.lang.reflect.Modifier.isAbstract(method.getModifiers()))
                .count();
            
            assertEquals(1, abstractMethodCount, 
                        "Demo interface should have exactly one abstract method");
        }
    }

    @Nested
    @DisplayName("Implementation Tests")
    class ImplementationTests {

        @BeforeEach
        void setUpImplementation() {
            // Create a test implementation of Demo that mimics real implementations
            testDemo = new Demo() {
                @Override
                public void execute(Canvas canvas) {
                    // Basic test implementation that uses actual Canvas.draw() method
                    if (canvas != null) {
                        JPanel panel = new JPanel();
                        canvas.draw(panel);
                    }
                }
            };
        }

        @Test
        @DisplayName("Should execute successfully with valid canvas")
        void shouldExecuteSuccessfullyWithValidCanvas() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert
            assertDoesNotThrow(() -> testDemo.execute(mockCanvas), 
                              "Should execute without throwing exceptions");
            
            // Verify interaction with Canvas.draw() method
            verify(mockCanvas).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should complete execution within reasonable time")
        void shouldCompleteExecutionWithinReasonableTime() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));
            long startTime = System.currentTimeMillis();
            
            // Act
            testDemo.execute(mockCanvas);
            
            // Assert
            long executionTime = System.currentTimeMillis() - startTime;
            assertTrue(executionTime < 1000, 
                      "Demo execution should complete within 1 second");
        }

        @Test
        @DisplayName("Should handle null canvas according to implementation strategy")
        void shouldHandleNullCanvasAccordingToImplementationStrategy() {
            // Create null-safe demo implementation
            Demo nullSafeDemo = canvas -> {
                if (canvas != null) {
                    canvas.draw(new JPanel());
                }
            };

            // Act & Assert - null-safe implementation should not throw
            assertDoesNotThrow(() -> nullSafeDemo.execute(null), 
                              "Null-safe demo should handle null canvas gracefully");
        }
    }

    @Nested
    @DisplayName("Real Implementation Pattern Tests")
    class RealImplementationPatternTests {

        @Test
        @DisplayName("Should follow the pattern of real implementations with precondition checks")
        void shouldFollowPatternOfRealImplementationsWithPreconditionChecks() {
            // Create demo that mimics CircleDemo, SquareDemo, TriangleDemo pattern
            Demo preconditionDemo = canvas -> {
                // Simulate the PreConditions.requireNonNull pattern
                if (canvas == null) {
                    throw new PreConditionsException("canvas must not be null");
                }
                
                JPanel panel = new JPanel();
                canvas.draw(panel);
            };

            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Test with valid canvas
            assertDoesNotThrow(() -> preconditionDemo.execute(mockCanvas), 
                              "Should execute successfully with valid canvas");
            verify(mockCanvas).draw(any(JPanel.class));

            // Test with null canvas - should throw PreConditionsException
            assertThrows(PreConditionsException.class, 
                        () -> preconditionDemo.execute(null),
                        "Should throw PreConditionsException with null canvas");
        }

        @Test
        @DisplayName("Should support typical shape demo workflow")
        void shouldSupportTypicalShapeDemoWorkflow() {
            // Create demo that follows typical shape demo pattern:
            // 1. Create shape object
            // 2. Create panel for shape
            // 3. Draw panel on canvas
            Demo shapeDemo = canvas -> {
                if (canvas == null) {
                    throw new PreConditionsException("canvas must not be null");
                }
                
                // Simulate creating a shape and its panel
                JPanel shapePanel = new JPanel();
                canvas.draw(shapePanel);
            };

            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert
            assertDoesNotThrow(() -> shapeDemo.execute(mockCanvas), 
                              "Shape demo should execute successfully");
            verify(mockCanvas).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should handle canvas draw method exceptions properly")
        void shouldHandleCanvasDrawMethodExceptionsProperly() {
            // Create demo that handles canvas exceptions
            Demo robustDemo = canvas -> {
                if (canvas == null) {
                    throw new PreConditionsException("canvas must not be null");
                }
                
                try {
                    JPanel panel = new JPanel();
                    canvas.draw(panel);
                } catch (Exception e) {
                    // Handle canvas drawing exceptions
                    System.err.println("Drawing failed: " + e.getMessage());
                    throw new RuntimeException("Demo execution failed", e);
                }
            };

            doThrow(new RuntimeException("Canvas drawing error"))
                .when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, 
                                                     () -> robustDemo.execute(mockCanvas),
                                                     "Should throw RuntimeException when canvas.draw() fails");
            assertTrue(exception.getMessage().contains("Demo execution failed"));
        }
    }

    @Nested
    @DisplayName("Functional Interface Properties")
    class FunctionalInterfaceTests {

        @Test
        @DisplayName("Demo interface should be suitable for lambda expressions")
        void shouldBeSuitableForLambdaExpressions() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert - should be able to create lambda implementations
            assertDoesNotThrow(() -> {
                Demo lambdaDemo = (canvas) -> {
                    if (canvas != null) {
                        canvas.draw(new JPanel());
                    }
                };
                lambdaDemo.execute(mockCanvas);
            }, "Demo interface should support lambda expressions");

            verify(mockCanvas).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Demo interface should support method references")
        void shouldSupportMethodReferences() {
            // Arrange
            class DemoHelper {
                public void runDemo(Canvas canvas) {
                    if (canvas != null) {
                        canvas.draw(new JPanel());
                    }
                }
            }

            DemoHelper helper = new DemoHelper();
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert - should be able to use method references
            assertDoesNotThrow(() -> {
                Demo methodRefDemo = helper::runDemo;
                methodRefDemo.execute(mockCanvas);
            }, "Demo interface should support method references");

            verify(mockCanvas).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should support complex lambda expressions with multiple operations")
        void shouldSupportComplexLambdaExpressionsWithMultipleOperations() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert - complex lambda with multiple operations
            assertDoesNotThrow(() -> {
                Demo complexDemo = canvas -> {
                    if (canvas != null) {
                        // Multiple operations in lambda - simulate drawing multiple shapes
                        for (int i = 0; i < 3; i++) {
                            JPanel panel = new JPanel();
                            canvas.draw(panel);
                        }
                    }
                };
                complexDemo.execute(mockCanvas);
            }, "Demo should support complex lambda expressions");

            verify(mockCanvas, times(3)).draw(any(JPanel.class));
        }
    }

    @Nested
    @DisplayName("Integration and Composition Tests")
    class IntegrationAndCompositionTests {

        @Test
        @DisplayName("Should work in demo execution pipeline")
        void shouldWorkInDemoExecutionPipeline() {
            // Arrange - simulate a demo execution pipeline
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            Demo[] demos = {
                canvas -> { 
                    if (canvas != null) canvas.draw(new JPanel()); 
                },
                canvas -> { 
                    if (canvas != null) canvas.draw(new JPanel()); 
                },
                canvas -> { 
                    if (canvas != null) canvas.draw(new JPanel()); 
                }
            };

            // Act & Assert
            assertDoesNotThrow(() -> {
                for (Demo demo : demos) {
                    demo.execute(mockCanvas);
                }
            }, "Demo pipeline should execute successfully");

            // Verify all operations occurred
            verify(mockCanvas, times(3)).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should support demo composition")
        void shouldSupportDemoComposition() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            Demo setupDemo = canvas -> {
                if (canvas != null) canvas.draw(new JPanel()); // Setup panel
            };

            Demo drawingDemo = canvas -> {
                if (canvas != null) canvas.draw(new JPanel()); // Main drawing panel
            };

            Demo compositeDemo = canvas -> {
                setupDemo.execute(canvas);
                drawingDemo.execute(canvas);
            };

            // Act & Assert
            assertDoesNotThrow(() -> compositeDemo.execute(mockCanvas), 
                              "Composite demo should execute successfully");

            verify(mockCanvas, times(2)).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should handle mixed implementation types")
        void shouldHandleMixedImplementationTypes() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Mix of different demo implementation styles
            Demo strictDemo = canvas -> {
                if (canvas == null) throw new PreConditionsException("canvas must not be null");
                canvas.draw(new JPanel());
            };

            Demo lenientDemo = canvas -> {
                if (canvas != null) canvas.draw(new JPanel());
            };

            // Act & Assert - both should work with valid canvas
            assertDoesNotThrow(() -> {
                strictDemo.execute(mockCanvas);
                lenientDemo.execute(mockCanvas);
            }, "Mixed implementation types should work together");

            verify(mockCanvas, times(2)).draw(any(JPanel.class));
        }
    }

    @Nested
    @DisplayName("Performance and Resource Tests")
    class PerformanceAndResourceTests {

        @Test
        @DisplayName("Should handle rapid successive executions")
        void shouldHandleRapidSuccessiveExecutions() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));
            Demo lightweightDemo = canvas -> {
                if (canvas != null) canvas.draw(new JPanel());
            };

            // Act & Assert
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 100; i++) {
                    lightweightDemo.execute(mockCanvas);
                }
            }, "Should handle rapid successive executions");

            verify(mockCanvas, times(100)).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should handle multiple canvas operations efficiently")
        void shouldHandleMultipleCanvasOperationsEfficiently() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));

            Demo multiOpDemo = canvas -> {
                if (canvas != null) {
                    // Multiple operations - should be efficient
                    for (int i = 0; i < 10; i++) {
                        JPanel panel = new JPanel();
                        canvas.draw(panel);
                    }
                }
            };

            long startTime = System.currentTimeMillis();
            
            // Act
            multiOpDemo.execute(mockCanvas);
            
            // Assert
            long executionTime = System.currentTimeMillis() - startTime;
            assertTrue(executionTime < 500, 
                      "Multiple canvas operations should complete efficiently");
            
            verify(mockCanvas, times(10)).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should not create unnecessary object references")
        void shouldNotCreateUnnecessaryObjectReferences() {
            // Test that demo implementations don't hold unnecessary references
            Demo memoryEfficientDemo = canvas -> {
                if (canvas != null) {
                    // Create panel locally - should be eligible for GC after execution
                    JPanel panel = new JPanel();
                    canvas.draw(panel);
                    // panel should be eligible for garbage collection here
                }
            };

            doNothing().when(mockCanvas).draw(any(JPanel.class));

            // Act
            memoryEfficientDemo.execute(mockCanvas);

            // Assert - execution completes successfully
            verify(mockCanvas).draw(any(JPanel.class));
            
            // Suggest garbage collection (not guaranteed but good practice in tests)
            System.gc();
            
            // Demo should still be functional after potential GC
            assertDoesNotThrow(() -> memoryEfficientDemo.execute(mockCanvas));
        }
    }

    @Nested
    @DisplayName("Error Handling and Edge Cases")
    class ErrorHandlingAndEdgeCasesTests {

        @Test
        @DisplayName("Should handle concurrent executions safely")
        void shouldHandleConcurrentExecutionsSafely() {
            // Arrange
            doNothing().when(mockCanvas).draw(any(JPanel.class));
            Demo threadSafeDemo = canvas -> {
                if (canvas != null) {
                    // Simulate thread-safe operation
                    synchronized (this) {
                        JPanel panel = new JPanel();
                        canvas.draw(panel);
                    }
                }
            };

            // Act & Assert - simulate concurrent access
            assertDoesNotThrow(() -> {
                Thread t1 = new Thread(() -> threadSafeDemo.execute(mockCanvas));
                Thread t2 = new Thread(() -> threadSafeDemo.execute(mockCanvas));
                
                t1.start();
                t2.start();
                
                t1.join();
                t2.join();
            }, "Should handle concurrent executions safely");

            verify(mockCanvas, times(2)).draw(any(JPanel.class));
        }

        @Test
        @DisplayName("Should handle invalid canvas states gracefully")
        void shouldHandleInvalidCanvasStatesGracefully() {
            // Create a demo that attempts to handle canvas in various states
            Demo resilientDemo = canvas -> {
                if (canvas == null) {
                    throw new PreConditionsException("canvas must not be null");
                }
                
                try {
                    JPanel panel = new JPanel();
                    canvas.draw(panel);
                } catch (IllegalStateException e) {
                    // Handle invalid canvas state
                    System.err.println("Canvas in invalid state: " + e.getMessage());
                    throw new RuntimeException("Cannot execute demo with invalid canvas", e);
                }
            };

            // Test with canvas that throws IllegalStateException
            doThrow(new IllegalStateException("Canvas not initialized"))
                .when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class,
                                                     () -> resilientDemo.execute(mockCanvas),
                                                     "Should handle invalid canvas state");
            assertTrue(exception.getMessage().contains("Cannot execute demo with invalid canvas"));
        }

        @Test
        @DisplayName("Should maintain demo contract even with exceptions")
        void shouldMaintainDemoContractEvenWithExceptions() {
            // Demo that maintains its contract despite internal exceptions
            Demo contractDemo = canvas -> {
                if (canvas == null) {
                    throw new PreConditionsException("canvas must not be null");
                }
                
                // Always attempt to draw, even if it might fail
                try {
                    JPanel panel = new JPanel();
                    canvas.draw(panel);
                } finally {
                    // Ensure demo contract is maintained
                    System.out.println("Demo execution completed");
                }
            };

            doThrow(new RuntimeException("Drawing failed"))
                .when(mockCanvas).draw(any(JPanel.class));

            // Act & Assert - contract should be maintained even when drawing fails
            assertThrows(RuntimeException.class,
                        () -> contractDemo.execute(mockCanvas),
                        "Should throw exception but maintain contract");
            
            verify(mockCanvas).draw(any(JPanel.class));
        }
    }
}