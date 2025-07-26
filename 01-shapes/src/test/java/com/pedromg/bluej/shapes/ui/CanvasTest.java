package com.pedromg.bluej.shapes.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

class CanvasTest {

    private Canvas canvas;

    @BeforeEach
    void setUp() {
        canvas = new Canvas();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create canvas without throwing exceptions")
        void shouldCreateCanvasWithoutExceptions() {
            // Given & When
            Canvas newCanvas = new Canvas();
            
            // Then
            assertNotNull(newCanvas);
        }

        @Test
        @DisplayName("Should create multiple canvas instances independently")
        void shouldCreateMultipleCanvasInstancesIndependently() {
            // Given & When
            Canvas canvas1 = new Canvas();
            Canvas canvas2 = new Canvas();
            
            // Then
            assertNotNull(canvas1);
            assertNotNull(canvas2);
            assertNotSame(canvas1, canvas2);
        }

        @Test
        @DisplayName("Should initialize canvas with proper state")
        void shouldInitializeCanvasWithProperState() {
            // Given & When
            Canvas newCanvas = new Canvas();
            
            // Then - Canvas should be created successfully and be ready for operations
            assertNotNull(newCanvas);
            assertDoesNotThrow(() -> newCanvas.show());
        }

        @Test
        @DisplayName("Should create canvas with default configuration")
        void shouldCreateCanvasWithDefaultConfiguration() {
            // Given & When
            Canvas newCanvas = new Canvas();
            
            // Then - Canvas should be properly initialized
            assertNotNull(newCanvas);
            // Verify it can perform basic operations without errors
            assertDoesNotThrow(() -> {
                JPanel testPanel = new JPanel();
                newCanvas.draw(testPanel);
            });
        }
    }

    @Nested
    @DisplayName("Show Method Tests")
    class ShowMethodTests {

        @Test
        @DisplayName("Should show canvas without throwing exceptions")
        void shouldShowCanvasWithoutExceptions() {
            // Given
            Canvas testCanvas = new Canvas();
            
            // When & Then
            assertDoesNotThrow(() -> testCanvas.show());
        }

        @Test
        @DisplayName("Should allow multiple show calls")
        void shouldAllowMultipleShowCalls() {
            // Given
            Canvas testCanvas = new Canvas();
            
            // When & Then
            assertDoesNotThrow(() -> {
                testCanvas.show();
                testCanvas.show();
                testCanvas.show();
            });
        }

        @Test
        @DisplayName("Should show canvas immediately after creation")
        void shouldShowCanvasImmediatelyAfterCreation() {
            // Given
            Canvas newCanvas = new Canvas();
            
            // When & Then
            assertDoesNotThrow(() -> newCanvas.show());
        }

        @Test
        @DisplayName("Should handle show operation on fresh canvas instance")
        void shouldHandleShowOperationOnFreshCanvasInstance() {
            // Given & When & Then
            assertDoesNotThrow(() -> new Canvas().show());
        }

        @Test
        @DisplayName("Should maintain show state across operations")
        void shouldMaintainShowStateAcrossOperations() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                testCanvas.show();
                testCanvas.draw(panel);
                testCanvas.show(); // Show again after drawing
            });
        }
    }

    @Nested
    @DisplayName("Draw Method Tests")
    class DrawMethodTests {

        @Test
        @DisplayName("Should draw valid JPanel successfully")
        void shouldDrawValidJPanelSuccessfully() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should throw PreConditionsException when panel is null")
        void shouldThrowPreConditionsExceptionWhenPanelIsNull() {
            // Given
            JPanel nullPanel = null;
            
            // When & Then
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> canvas.draw(nullPanel)
            );
            
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("panel must not be null"));
        }

        @Test
        @DisplayName("Should draw multiple panels sequentially")
        void shouldDrawMultiplePanelsSequentially() {
            // Given
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(panel1);
                canvas.draw(panel2);
                canvas.draw(panel3);
            });
        }

        @Test
        @DisplayName("Should draw same panel multiple times")
        void shouldDrawSamePanelMultipleTimes() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(panel);
                canvas.draw(panel);
                canvas.draw(panel);
            });
        }

        @Test
        @DisplayName("Should draw panel with custom layout")
        void shouldDrawPanelWithCustomLayout() {
            // Given
            JPanel panel = new JPanel(new FlowLayout());
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should draw panel with custom size")
        void shouldDrawPanelWithCustomSize() {
            // Given
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(100, 50));
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should draw empty panel")
        void shouldDrawEmptyPanel() {
            // Given
            JPanel emptyPanel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(emptyPanel));
        }

        @Test
        @DisplayName("Should draw panel after showing canvas")
        void shouldDrawPanelAfterShowingCanvas() {
            // Given
            JPanel panel = new JPanel();
            canvas.show();
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should draw panel before showing canvas")
        void shouldDrawPanelBeforeShowingCanvas() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(panel);
                canvas.show();
            });
        }

        @Test
        @DisplayName("Should handle panel with nested components")
        void shouldHandlePanelWithNestedComponents() {
            // Given
            JPanel outerPanel = new JPanel();
            JPanel innerPanel = new JPanel();
            outerPanel.add(innerPanel);
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(outerPanel));
        }

        @Test
        @DisplayName("Should handle drawing panel with different layout managers")
        void shouldHandleDrawingPanelWithDifferentLayoutManagers() {
            // Given
            JPanel flowLayoutPanel = new JPanel(new FlowLayout());
            JPanel nullLayoutPanel = new JPanel();
            nullLayoutPanel.setLayout(null);
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(flowLayoutPanel);
                canvas.draw(nullLayoutPanel);
            });
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should handle complete workflow: create, draw, show")
        void shouldHandleCompleteWorkflow() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                testCanvas.draw(panel1);
                testCanvas.draw(panel2);
                testCanvas.show();
            });
        }

        @Test
        @DisplayName("Should handle workflow: create, show, draw")
        void shouldHandleWorkflowCreateShowDraw() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                testCanvas.show();
                testCanvas.draw(panel);
            });
        }

        @Test
        @DisplayName("Should handle mixed valid and invalid operations")
        void shouldHandleMixedValidAndInvalidOperations() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel validPanel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> testCanvas.draw(validPanel));
            assertThrows(PreConditionsException.class, () -> testCanvas.draw(null));
            assertDoesNotThrow(() -> testCanvas.show());
        }

        @Test
        @DisplayName("Should support full lifecycle operations")
        void shouldSupportFullLifecycleOperations() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            
            // When & Then - Complete lifecycle
            assertDoesNotThrow(() -> {
                // Initial draw operations
                testCanvas.draw(panel1);
                testCanvas.draw(panel2);
                
                // Show the canvas
                testCanvas.show();
                
                // Additional draw operations after showing
                testCanvas.draw(panel3);
                
                // Show again (should be idempotent)
                testCanvas.show();
            });
        }

        @Test
        @DisplayName("Should handle complex interaction patterns")
        void shouldHandleComplexInteractionPatterns() {
            // Given
            Canvas testCanvas = new Canvas();
            JPanel[] panels = new JPanel[5];
            for (int i = 0; i < panels.length; i++) {
                panels[i] = new JPanel();
            }
            
            // When & Then - Complex interaction pattern
            assertDoesNotThrow(() -> {
                testCanvas.draw(panels[0]);
                testCanvas.show();
                testCanvas.draw(panels[1]);
                testCanvas.draw(panels[2]);
                testCanvas.show();
                testCanvas.draw(panels[3]);
                testCanvas.draw(panels[4]);
            });
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle rapid successive draw calls")
        void shouldHandleRapidSuccessiveDrawCalls() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 50; i++) {
                    canvas.draw(panel);
                }
            });
        }

        @Test
        @DisplayName("Should handle rapid successive show calls")
        void shouldHandleRapidSuccessiveShowCalls() {
            // When & Then
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 10; i++) {
                    canvas.show();
                }
            });
        }

        @Test
        @DisplayName("Should handle panel with zero dimensions")
        void shouldHandlePanelWithZeroDimensions() {
            // Given
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(0, 0));
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should handle panel with large dimensions")
        void shouldHandlePanelWithLargeDimensions() {
            // Given
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(10000, 10000));
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should handle alternating draw and show operations")
        void shouldHandleAlternatingDrawAndShowOperations() {
            // Given
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(panel1);
                canvas.show();
                canvas.draw(panel2);
                canvas.show();
                canvas.draw(panel3);
                canvas.show();
            });
        }

        @Test
        @DisplayName("Should handle panel with negative dimensions")
        void shouldHandlePanelWithNegativeDimensions() {
            // Given
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(-10, -20));
            
            // When & Then - Should not throw exception even with negative dimensions
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should handle drawing many different panels")
        void shouldHandleDrawingManyDifferentPanels() {
            // Given & When & Then
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 30; i++) {
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(i * 10, i * 5));
                    canvas.draw(panel);
                }
            });
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should provide meaningful error message for null panel")
        void shouldProvideMeaningfulErrorMessageForNullPanel() {
            // When & Then
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> canvas.draw(null)
            );
            
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("panel must not be null"));
        }

        @Test
        @DisplayName("Should handle error and continue normal operation")
        void shouldHandleErrorAndContinueNormalOperation() {
            // Given
            JPanel validPanel = new JPanel();
            
            // When & Then
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            assertDoesNotThrow(() -> canvas.draw(validPanel));
            assertDoesNotThrow(() -> canvas.show());
        }

        @Test
        @DisplayName("Should throw exception consistently for null parameter")
        void shouldThrowExceptionConsistentlyForNullParameter() {
            // When & Then - Multiple calls should all throw the same exception
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
        }

        @Test
        @DisplayName("Should maintain canvas state after exception")
        void shouldMaintainCanvasStateAfterException() {
            // Given
            JPanel validPanel = new JPanel();
            
            // When
            canvas.draw(validPanel); // Valid operation first
            
            // Then
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            
            // Should still be able to perform valid operations
            assertDoesNotThrow(() -> canvas.draw(validPanel));
            assertDoesNotThrow(() -> canvas.show());
        }

        @Test
        @DisplayName("Should handle multiple consecutive null panel attempts")
        void shouldHandleMultipleConsecutiveNullPanelAttempts() {
            // When & Then
            for (int i = 0; i < 5; i++) {
                assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            }
            
            // Should still work normally after multiple failures
            JPanel validPanel = new JPanel();
            assertDoesNotThrow(() -> canvas.draw(validPanel));
        }

        @Test
        @DisplayName("Should handle error recovery in mixed operations")
        void shouldHandleErrorRecoveryInMixedOperations() {
            // Given
            JPanel validPanel1 = new JPanel();
            JPanel validPanel2 = new JPanel();
            
            // When & Then - Mix of valid and invalid operations
            assertDoesNotThrow(() -> canvas.draw(validPanel1));
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            assertDoesNotThrow(() -> canvas.show());
            assertThrows(PreConditionsException.class, () -> canvas.draw(null));
            assertDoesNotThrow(() -> canvas.draw(validPanel2));
        }
    }

    @Nested
    @DisplayName("State Management Tests")
    class StateManagementTests {

        @Test
        @DisplayName("Should maintain independent state across canvas instances")
        void shouldMaintainIndependentStateAcrossCanvasInstances() {
            // Given
            Canvas canvas1 = new Canvas();
            Canvas canvas2 = new Canvas();
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            
            // When & Then - Operations on one canvas should not affect the other
            assertDoesNotThrow(() -> {
                canvas1.draw(panel1);
                canvas2.draw(panel2);
                canvas1.show();
                canvas2.show();
            });
        }

        @Test
        @DisplayName("Should handle interleaved operations correctly")
        void shouldHandleInterleavedOperationsCorrectly() {
            // Given
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> {
                canvas.draw(panel1);
                canvas.show();
                canvas.draw(panel2);
                canvas.show(); // Show again
            });
        }

        @Test
        @DisplayName("Should support operations in any order")
        void shouldSupportOperationsInAnyOrder() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then - Show first, then draw
            assertDoesNotThrow(() -> {
                canvas.show();
                canvas.draw(panel);
            });
        }

        @Test
        @DisplayName("Should handle concurrent-like operations")
        void shouldHandleConcurrentLikeOperations() {
            // Given
            Canvas canvas1 = new Canvas();
            Canvas canvas2 = new Canvas();
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            
            // When & Then - Simulate concurrent operations
            assertDoesNotThrow(() -> {
                canvas1.draw(panel1);
                canvas2.draw(panel2);
                canvas1.show();
                canvas2.draw(panel1); // Use same panel in different canvas
                canvas2.show();
                canvas1.draw(panel2); // Use same panel in different canvas
            });
        }
    }

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Should handle single panel draw operation")
        void shouldHandleSinglePanelDrawOperation() {
            // Given
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> canvas.draw(panel));
        }

        @Test
        @DisplayName("Should handle empty sequence of operations")
        void shouldHandleEmptySequenceOfOperations() {
            // Given
            Canvas newCanvas = new Canvas();
            
            // When & Then - Just create and show without drawing anything
            assertDoesNotThrow(() -> newCanvas.show());
        }

        @Test
        @DisplayName("Should handle maximum practical number of panels")
        void shouldHandleMaximumPracticalNumberOfPanels() {
            // Given
            Canvas testCanvas = new Canvas();
            
            // When & Then - Draw many panels
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 25; i++) {
                    JPanel panel = new JPanel();
                    testCanvas.draw(panel);
                }
                testCanvas.show();
            });
        }

        @Test
        @DisplayName("Should handle minimal viable canvas usage")
        void shouldHandleMinimalViableCanvasUsage() {
            // Given
            Canvas minimalCanvas = new Canvas();
            
            // When & Then - Just create, no other operations
            assertNotNull(minimalCanvas);
        }

        @Test
        @DisplayName("Should handle single operation workflows")
        void shouldHandleSingleOperationWorkflows() {
            // Given
            Canvas showOnlyCanvas = new Canvas();
            Canvas drawOnlyCanvas = new Canvas();
            JPanel panel = new JPanel();
            
            // When & Then
            assertDoesNotThrow(() -> showOnlyCanvas.show());
            assertDoesNotThrow(() -> drawOnlyCanvas.draw(panel));
        }
    }
}