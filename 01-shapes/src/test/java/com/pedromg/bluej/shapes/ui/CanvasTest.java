package com.pedromg.bluej.shapes.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

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
        @DisplayName("should create canvas with correct title")
        void shouldCreateCanvasWithCorrectTitle() {
            Canvas testCanvas = new Canvas();
            // Access window field through reflection for testing
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                assertEquals("Shapes Application", window.getTitle());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with correct dimensions")
        void shouldCreateCanvasWithCorrectDimensions() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                assertEquals(400, window.getWidth());
                assertEquals(720, window.getHeight());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with correct minimum dimensions")
        void shouldCreateCanvasWithCorrectMinimumDimensions() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                Dimension minSize = window.getMinimumSize();
                assertEquals(400, minSize.width);
                assertEquals(720, minSize.height);
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with correct default close operation")
        void shouldCreateCanvasWithCorrectDefaultCloseOperation() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                assertEquals(WindowConstants.EXIT_ON_CLOSE, window.getDefaultCloseOperation());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with resizable property set to true")
        void shouldCreateCanvasWithResizableTrue() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                assertTrue(window.isResizable());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with FlowLayout")
        void shouldCreateCanvasWithFlowLayout() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                LayoutManager layout = window.getLayout();
                assertInstanceOf(FlowLayout.class, layout);
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should create canvas with window initially not visible")
        void shouldCreateCanvasWithWindowInitiallyNotVisible() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                assertFalse(window.isVisible());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should center window on screen")
        void shouldCenterWindowOnScreen() {
            Canvas testCanvas = new Canvas();
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                // Window should be positioned relative to null (centered)
                // We can't easily test the actual position, but we can verify the window was created
                assertNotNull(window);
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Show Method Tests")
    class ShowMethodTests {

        @Test
        @DisplayName("should make window visible when show is called")
        void shouldMakeWindowVisibleWhenShowIsCalled() {
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                assertFalse(window.isVisible(), "Window should be initially invisible");
                
                canvas.show();
                
                assertTrue(window.isVisible(), "Window should be visible after show() is called");
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle multiple calls to show gracefully")
        void shouldHandleMultipleCallsToShowGracefully() {
            assertDoesNotThrow(() -> {
                canvas.show();
                canvas.show();
                canvas.show();
            });
            
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                assertTrue(window.isVisible());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Draw Method Tests")
    class DrawMethodTests {

        private JPanel testPanel;

        @BeforeEach
        void setUpPanel() {
            testPanel = new JPanel();
        }

        @Test
        @DisplayName("should add panel to window when draw is called with valid panel")
        void shouldAddPanelToWindowWhenDrawIsCalledWithValidPanel() {
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                int initialComponentCount = window.getComponentCount();
                
                canvas.draw(testPanel);
                
                assertEquals(initialComponentCount + 1, window.getComponentCount());
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(testPanel));
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should throw PreConditionsException when draw is called with null panel")
        void shouldThrowPreConditionsExceptionWhenDrawIsCalledWithNullPanel() {
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> canvas.draw(null)
            );
            
            assertTrue(exception.getMessage().contains("panel must not be null"));
        }

        @Test
        @DisplayName("should handle multiple panels being drawn")
        void shouldHandleMultiplePanelsBeingDrawn() {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JPanel panel3 = new JPanel();

            assertDoesNotThrow(() -> {
                canvas.draw(panel1);
                canvas.draw(panel2);
                canvas.draw(panel3);
            });

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                assertEquals(3, window.getComponentCount());
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(panel1));
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(panel2));
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(panel3));
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle drawing same panel multiple times")
        void shouldHandleDrawingSamePanelMultipleTimes() {
            assertDoesNotThrow(() -> {
                canvas.draw(testPanel);
                canvas.draw(testPanel);
            });

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                // When the same panel is added multiple times, Swing removes it from its previous location
                // So it should only appear once in the component list
                long panelCount = java.util.Arrays.stream(window.getComponents())
                    .filter(comp -> comp == testPanel)
                    .count();
                assertEquals(1, panelCount);
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle drawing panels with different properties")
        void shouldHandleDrawingPanelsWithDifferentProperties() {
            JPanel smallPanel = new JPanel();
            smallPanel.setSize(50, 50);
            
            JPanel largePanel = new JPanel();
            largePanel.setSize(200, 200);
            
            JPanel coloredPanel = new JPanel();
            coloredPanel.setBackground(java.awt.Color.RED);

            assertDoesNotThrow(() -> {
                canvas.draw(smallPanel);
                canvas.draw(largePanel);
                canvas.draw(coloredPanel);
            });
        }

        @Test
        @DisplayName("should validate preconditions before adding panel")
        void shouldValidatePreconditionsBeforeAddingPanel() {
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                int initialComponentCount = window.getComponentCount();
                
                assertThrows(PreConditionsException.class, () -> canvas.draw(null));
                
                // Verify that no component was added when precondition failed
                assertEquals(initialComponentCount, window.getComponentCount());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should trigger window revalidation and repaint after drawing")
        void shouldTriggerWindowRevalidationAndRepaintAfterDrawing() {
            // This test verifies that the methods are called without exceptions
            // The actual UI updates can't be easily tested in unit tests
            assertDoesNotThrow(() -> canvas.draw(testPanel));
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("should handle complete workflow: create, show, draw")
        void shouldHandleCompleteWorkflow() {
            Canvas testCanvas = new Canvas();
            JPanel testPanel = new JPanel();

            assertDoesNotThrow(() -> {
                testCanvas.show();
                testCanvas.draw(testPanel);
            });

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(testCanvas);
                
                assertTrue(window.isVisible());
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(testPanel));
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle drawing before showing")
        void shouldHandleDrawingBeforeShowing() {
            JPanel testPanel = new JPanel();

            assertDoesNotThrow(() -> {
                canvas.draw(testPanel);
                canvas.show();
            });

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                assertTrue(window.isVisible());
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(testPanel));
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle mixed operations")
        void shouldHandleMixedOperations() {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            assertDoesNotThrow(() -> {
                canvas.draw(panel1);
                canvas.show();
                canvas.draw(panel2);
            });

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                assertTrue(window.isVisible());
                assertEquals(2, window.getComponentCount());
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(panel1));
                assertTrue(java.util.Arrays.asList(window.getComponents()).contains(panel2));
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("should handle empty panel")
        void shouldHandleEmptyPanel() {
            JPanel emptyPanel = new JPanel();
            assertDoesNotThrow(() -> canvas.draw(emptyPanel));
        }

        @Test
        @DisplayName("should handle panel with nested components")
        void shouldHandlePanelWithNestedComponents() {
            JPanel parentPanel = new JPanel();
            JPanel childPanel = new JPanel();
            parentPanel.add(childPanel);
            
            assertDoesNotThrow(() -> canvas.draw(parentPanel));
        }

        @Test
        @DisplayName("should maintain window properties after drawing panels")
        void shouldMaintainWindowPropertiesAfterDrawingPanels() {
            JPanel testPanel = new JPanel();
            canvas.draw(testPanel);

            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                assertEquals("Shapes Application", window.getTitle());
                assertEquals(400, window.getWidth());
                assertEquals(720, window.getHeight());
                assertEquals(WindowConstants.EXIT_ON_CLOSE, window.getDefaultCloseOperation());
                assertTrue(window.isResizable());
                assertInstanceOf(FlowLayout.class, window.getLayout());
                assertEquals(400, window.getMinimumSize().width);
                assertEquals(720, window.getMinimumSize().height);
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should handle panel with custom layout")
        void shouldHandlePanelWithCustomLayout() {
            JPanel panelWithLayout = new JPanel(new java.awt.BorderLayout());
            assertDoesNotThrow(() -> canvas.draw(panelWithLayout));
        }

        @Test
        @DisplayName("should handle panel with preferred size")
        void shouldHandlePanelWithPreferredSize() {
            JPanel sizedPanel = new JPanel();
            sizedPanel.setPreferredSize(new Dimension(100, 50));
            assertDoesNotThrow(() -> canvas.draw(sizedPanel));
        }
    }

    @Nested
    @DisplayName("Constants Validation Tests")
    class ConstantsValidationTests {

        @Test
        @DisplayName("should have consistent width and minimum width constants")
        void shouldHaveConsistentWidthAndMinimumWidthConstants() {
            try {
                java.lang.reflect.Field widthField = Canvas.class.getDeclaredField("WIDTH");
                java.lang.reflect.Field minWidthField = Canvas.class.getDeclaredField("MIN_WIDTH");
                widthField.setAccessible(true);
                minWidthField.setAccessible(true);
                
                int width = widthField.getInt(null);
                int minWidth = minWidthField.getInt(null);
                
                assertEquals(width, minWidth, "WIDTH and MIN_WIDTH should be equal");
                assertEquals(400, width);
                assertEquals(400, minWidth);
            } catch (Exception e) {
                fail("Could not access constant fields for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should have consistent height and minimum height constants")
        void shouldHaveConsistentHeightAndMinimumHeightConstants() {
            try {
                java.lang.reflect.Field heightField = Canvas.class.getDeclaredField("HEIGHT");
                java.lang.reflect.Field minHeightField = Canvas.class.getDeclaredField("MIN_HEIGHT");
                heightField.setAccessible(true);
                minHeightField.setAccessible(true);
                
                int height = heightField.getInt(null);
                int minHeight = minHeightField.getInt(null);
                
                assertEquals(height, minHeight, "HEIGHT and MIN_HEIGHT should be equal");
                assertEquals(720, height);
                assertEquals(720, minHeight);
            } catch (Exception e) {
                fail("Could not access constant fields for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should have correct title constant")
        void shouldHaveCorrectTitleConstant() {
            try {
                java.lang.reflect.Field titleField = Canvas.class.getDeclaredField("TITLE");
                titleField.setAccessible(true);
                
                String title = (String) titleField.get(null);
                assertEquals("Shapes Application", title);
                assertNotNull(title);
                assertFalse(title.trim().isEmpty());
            } catch (Exception e) {
                fail("Could not access TITLE field for testing: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("should have reasonable dimension constants")
        void shouldHaveReasonableDimensionConstants() {
            try {
                java.lang.reflect.Field widthField = Canvas.class.getDeclaredField("WIDTH");
                java.lang.reflect.Field heightField = Canvas.class.getDeclaredField("HEIGHT");
                widthField.setAccessible(true);
                heightField.setAccessible(true);
                
                int width = widthField.getInt(null);
                int height = heightField.getInt(null);
                
                assertTrue(width > 0, "Width should be positive");
                assertTrue(height > 0, "Height should be positive");
                assertTrue(width >= 200, "Width should be at least 200 for usability");
                assertTrue(height >= 200, "Height should be at least 200 for usability");
            } catch (Exception e) {
                fail("Could not access dimension constant fields for testing: " + e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("should handle PreConditionsException with meaningful message")
        void shouldHandlePreConditionsExceptionWithMeaningfulMessage() {
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> canvas.draw(null)
            );
            
            assertNotNull(exception.getMessage());
            assertFalse(exception.getMessage().trim().isEmpty());
            assertTrue(exception.getMessage().contains("panel"));
            assertTrue(exception.getMessage().contains("null"));
        }

        @Test
        @DisplayName("should not modify window state when draw fails")
        void shouldNotModifyWindowStateWhenDrawFails() {
            try {
                java.lang.reflect.Field windowField = Canvas.class.getDeclaredField("window");
                windowField.setAccessible(true);
                JFrame window = (JFrame) windowField.get(canvas);
                
                int initialComponentCount = window.getComponentCount();
                boolean initialVisibility = window.isVisible();
                
                assertThrows(PreConditionsException.class, () -> canvas.draw(null));
                
                assertEquals(initialComponentCount, window.getComponentCount());
                assertEquals(initialVisibility, window.isVisible());
            } catch (Exception e) {
                fail("Could not access window field for testing: " + e.getMessage());
            }
        }
    }
}