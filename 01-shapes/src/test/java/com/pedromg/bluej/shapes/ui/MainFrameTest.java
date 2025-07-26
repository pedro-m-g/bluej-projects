package com.pedromg.bluej.shapes.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive unit tests for MainFrame class.
 * Testing framework: JUnit 5 (Jupiter) with Mockito for mocking.
 */
class MainFrameTest {

    private MainFrame mainFrame;

    @BeforeEach
    void setUp() {
        // Initialize MainFrame for testing
        mainFrame = new MainFrame();
    }

    @AfterEach
    void tearDown() {
        // Clean up resources after each test
        if (mainFrame != null) {
            mainFrame.dispose();
        }
        mainFrame = null;
    }

    @Nested
    @DisplayName("Constructor and Initialization Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create MainFrame successfully")
        void shouldCreateMainFrameSuccessfully() {
            MainFrame frame = new MainFrame();
            assertNotNull(frame, "MainFrame should be created successfully");
            frame.dispose();
        }

        @Test
        @DisplayName("Should initialize with correct title")
        void shouldInitializeWithCorrectTitle() {
            assertEquals("Shapes Application", mainFrame.getTitle(), "Frame should have correct title");
        }

        @Test
        @DisplayName("Should set correct default close operation")
        void shouldSetCorrectDefaultCloseOperation() {
            assertEquals(JFrame.EXIT_ON_CLOSE, mainFrame.getDefaultCloseOperation(), 
                        "Frame should have correct close operation");
        }

        @Test
        @DisplayName("Should be visible by default")
        void shouldBeVisibleByDefault() {
            assertTrue(mainFrame.isVisible(), "Frame should be visible by default");
        }

        @Test
        @DisplayName("Should have proper initial size")
        void shouldHaveProperInitialSize() {
            Dimension size = mainFrame.getSize();
            assertTrue(size.width > 0, "Frame width should be positive");
            assertTrue(size.height > 0, "Frame height should be positive");
            assertEquals(800, size.width, "Default width should be 800");
            assertEquals(600, size.height, "Default height should be 600");
        }

        @Test
        @DisplayName("Should not be resizable")
        void shouldNotBeResizable() {
            assertFalse(mainFrame.isResizable(), "Frame should not be resizable");
        }

        @Test
        @DisplayName("Should be centered on screen")
        void shouldBeCenteredOnScreen() {
            Point location = mainFrame.getLocation();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = mainFrame.getSize();
            
            int expectedX = (screenSize.width - frameSize.width) / 2;
            int expectedY = (screenSize.height - frameSize.height) / 2;
            
            assertEquals(expectedX, location.x, "Frame should be horizontally centered");
            assertEquals(expectedY, location.y, "Frame should be vertically centered");
        }
    }

    @Nested
    @DisplayName("Component Initialization Tests")
    class ComponentInitializationTests {

        @Test
        @DisplayName("Should initialize menu bar")
        void shouldInitializeMenuBar() {
            JMenuBar menuBar = mainFrame.getJMenuBar();
            assertNotNull(menuBar, "Menu bar should be initialized");
            assertTrue(menuBar.getMenuCount() > 0, "Menu bar should have menus");
        }

        @Test
        @DisplayName("Should have File menu with proper items")
        void shouldHaveFileMenuWithProperItems() {
            JMenuBar menuBar = mainFrame.getJMenuBar();
            assertNotNull(menuBar, "Menu bar should exist");
            
            if (menuBar.getMenuCount() > 0) {
                JMenu fileMenu = menuBar.getMenu(0);
                assertEquals("File", fileMenu.getText(), "First menu should be File menu");
                assertTrue(fileMenu.getItemCount() > 0, "File menu should have items");
            }
        }

        @Test
        @DisplayName("Should initialize toolbar")
        void shouldInitializeToolbar() throws Exception {
            // Use reflection to access private toolbar field
            Field toolbarField = MainFrame.class.getDeclaredField("toolbar");
            toolbarField.setAccessible(true);
            JToolBar toolbar = (JToolBar) toolbarField.get(mainFrame);
            
            assertNotNull(toolbar, "Toolbar should be initialized");
            assertTrue(toolbar.getComponentCount() > 0, "Toolbar should have components");
        }

        @Test
        @DisplayName("Should initialize drawing panel")
        void shouldInitializeDrawingPanel() throws Exception {
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertNotNull(drawingPanel, "Drawing panel should be initialized");
            assertTrue(drawingPanel.isOpaque(), "Drawing panel should be opaque");
            assertEquals(Color.WHITE, drawingPanel.getBackground(), "Drawing panel should have white background");
        }

        @Test
        @DisplayName("Should initialize status bar")
        void shouldInitializeStatusBar() throws Exception {
            Field statusBarField = MainFrame.class.getDeclaredField("statusBar");
            statusBarField.setAccessible(true);
            JLabel statusBar = (JLabel) statusBarField.get(mainFrame);
            
            assertNotNull(statusBar, "Status bar should be initialized");
            assertEquals("Ready", statusBar.getText(), "Status bar should show 'Ready' initially");
        }

        @Test
        @DisplayName("Should use BorderLayout")
        void shouldUseBorderLayout() {
            Container contentPane = mainFrame.getContentPane();
            LayoutManager layout = contentPane.getLayout();
            assertTrue(layout instanceof BorderLayout, "Should use BorderLayout");
        }
    }

    @Nested
    @DisplayName("Menu Action Tests")
    class MenuActionTests {

        @Test
        @DisplayName("Should handle new file action")
        void shouldHandleNewFileAction() throws Exception {
            Method newFileMethod = MainFrame.class.getDeclaredMethod("newFile");
            newFileMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> newFileMethod.invoke(mainFrame), 
                              "New file action should not throw exception");
        }

        @Test
        @DisplayName("Should clear drawing panel on new file")
        void shouldClearDrawingPanelOnNewFile() throws Exception {
            Method newFileMethod = MainFrame.class.getDeclaredMethod("newFile");
            newFileMethod.setAccessible(true);
            
            newFileMethod.invoke(mainFrame);
            
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertEquals(0, drawingPanel.getComponentCount(), 
                        "Drawing panel should be cleared after new file");
        }

        @Test
        @DisplayName("Should handle save file action")
        void shouldHandleSaveFileAction() throws Exception {
            Method saveFileMethod = MainFrame.class.getDeclaredMethod("saveFile");
            saveFileMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> saveFileMethod.invoke(mainFrame), 
                              "Save file action should not throw exception");
        }

        @Test
        @DisplayName("Should handle open file action")
        void shouldHandleOpenFileAction() throws Exception {
            Method openFileMethod = MainFrame.class.getDeclaredMethod("openFile");
            openFileMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> openFileMethod.invoke(mainFrame), 
                              "Open file action should not throw exception");
        }

        @Test
        @DisplayName("Should handle exit action")
        void shouldHandleExitAction() throws Exception {
            Method exitMethod = MainFrame.class.getDeclaredMethod("exitApplication");
            exitMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> exitMethod.invoke(mainFrame), 
                              "Exit action should not throw exception");
        }

        @Test
        @DisplayName("Should show about dialog")
        void shouldShowAboutDialog() throws Exception {
            Method aboutMethod = MainFrame.class.getDeclaredMethod("showAboutDialog");
            aboutMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> aboutMethod.invoke(mainFrame), 
                              "About dialog should not throw exception");
        }
    }

    @Nested
    @DisplayName("Shape Creation Tests")
    class ShapeCreationTests {

        @Test
        @DisplayName("Should create rectangle shape")
        void shouldCreateRectangleShape() throws Exception {
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            createRectangleMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> createRectangleMethod.invoke(mainFrame), 
                              "Rectangle creation should not throw exception");
        }

        @Test
        @DisplayName("Should add rectangle to drawing panel")
        void shouldAddRectangleToDrawingPanel() throws Exception {
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            createRectangleMethod.setAccessible(true);
            
            createRectangleMethod.invoke(mainFrame);
            
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertTrue(drawingPanel.getComponentCount() > 0, 
                      "Rectangle should be added to drawing panel");
        }

        @Test
        @DisplayName("Should create circle shape")
        void shouldCreateCircleShape() throws Exception {
            Method createCircleMethod = MainFrame.class.getDeclaredMethod("createCircle");
            createCircleMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> createCircleMethod.invoke(mainFrame), 
                              "Circle creation should not throw exception");
        }

        @Test
        @DisplayName("Should create triangle shape")
        void shouldCreateTriangleShape() throws Exception {
            Method createTriangleMethod = MainFrame.class.getDeclaredMethod("createTriangle");
            createTriangleMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> createTriangleMethod.invoke(mainFrame), 
                              "Triangle creation should not throw exception");
        }

        @Test
        @DisplayName("Should handle multiple shape creation")
        void shouldHandleMultipleShapeCreation() throws Exception {
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            Method createCircleMethod = MainFrame.class.getDeclaredMethod("createCircle");
            createRectangleMethod.setAccessible(true);
            createCircleMethod.setAccessible(true);
            
            createRectangleMethod.invoke(mainFrame);
            createCircleMethod.invoke(mainFrame);
            
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertEquals(2, drawingPanel.getComponentCount(), 
                        "Should have multiple shapes in drawing panel");
        }

        @ParameterizedTest
        @ValueSource(strings = {"RECTANGLE", "CIRCLE", "TRIANGLE"})
        @DisplayName("Should create shapes by type")
        void shouldCreateShapesByType(String shapeType) throws Exception {
            Method createShapeMethod = MainFrame.class.getDeclaredMethod("createShape", String.class);
            createShapeMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> createShapeMethod.invoke(mainFrame, shapeType), 
                              "Shape creation should not throw exception for " + shapeType);
        }
    }

    @Nested
    @DisplayName("Status Bar Tests")
    class StatusBarTests {

        @Test
        @DisplayName("Should update status message")
        void shouldUpdateStatusMessage() throws Exception {
            Method updateStatusMethod = MainFrame.class.getDeclaredMethod("updateStatus", String.class);
            updateStatusMethod.setAccessible(true);
            
            String testMessage = "Test status message";
            updateStatusMethod.invoke(mainFrame, testMessage);
            
            Field statusBarField = MainFrame.class.getDeclaredField("statusBar");
            statusBarField.setAccessible(true);
            JLabel statusBar = (JLabel) statusBarField.get(mainFrame);
            
            assertEquals(testMessage, statusBar.getText(), "Status bar should display the test message");
        }

        @Test
        @DisplayName("Should handle null status message")
        void shouldHandleNullStatusMessage() throws Exception {
            Method updateStatusMethod = MainFrame.class.getDeclaredMethod("updateStatus", String.class);
            updateStatusMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> updateStatusMethod.invoke(mainFrame, (String) null), 
                              "Should handle null status message gracefully");
            
            Field statusBarField = MainFrame.class.getDeclaredField("statusBar");
            statusBarField.setAccessible(true);
            JLabel statusBar = (JLabel) statusBarField.get(mainFrame);
            
            assertEquals("", statusBar.getText(), "Status bar should show empty string for null input");
        }

        @ParameterizedTest
        @CsvSource({
            "'', ''",
            "'Ready', 'Ready'",
            "'Shape created', 'Shape created'",
            "'File saved successfully', 'File saved successfully'"
        })
        @DisplayName("Should handle various status messages")
        void shouldHandleVariousStatusMessages(String input, String expected) throws Exception {
            Method updateStatusMethod = MainFrame.class.getDeclaredMethod("updateStatus", String.class);
            updateStatusMethod.setAccessible(true);
            
            updateStatusMethod.invoke(mainFrame, input);
            
            Field statusBarField = MainFrame.class.getDeclaredField("statusBar");
            statusBarField.setAccessible(true);
            JLabel statusBar = (JLabel) statusBarField.get(mainFrame);
            
            assertEquals(expected, statusBar.getText(), "Status bar should display the expected message");
        }
    }

    @Nested
    @DisplayName("Window Event Tests")
    class WindowEventTests {

        @Test
        @DisplayName("Should handle window closing event")
        void shouldHandleWindowClosingEvent() {
            WindowEvent closingEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING);
            
            assertDoesNotThrow(() -> mainFrame.processWindowEvent(closingEvent), 
                              "Window closing event should not throw exception");
        }

        @Test
        @DisplayName("Should handle window iconified event")
        void shouldHandleWindowIconifiedEvent() {
            WindowEvent iconifiedEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_ICONIFIED);
            
            assertDoesNotThrow(() -> mainFrame.processWindowEvent(iconifiedEvent), 
                              "Window iconified event should not throw exception");
        }

        @Test
        @DisplayName("Should handle window deiconified event")
        void shouldHandleWindowDeiconifiedEvent() {
            WindowEvent deiconifiedEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_DEICONIFIED);
            
            assertDoesNotThrow(() -> mainFrame.processWindowEvent(deiconifiedEvent), 
                              "Window deiconified event should not throw exception");
        }

        @Test
        @DisplayName("Should handle window opened event")
        void shouldHandleWindowOpenedEvent() {
            WindowEvent openedEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_OPENED);
            
            assertDoesNotThrow(() -> mainFrame.processWindowEvent(openedEvent), 
                              "Window opened event should not throw exception");
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle invalid shape creation gracefully")
        void shouldHandleInvalidShapeCreationGracefully() throws Exception {
            Method createShapeMethod = MainFrame.class.getDeclaredMethod("createShape", String.class);
            createShapeMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> createShapeMethod.invoke(mainFrame, "INVALID_SHAPE"), 
                              "Invalid shape creation should be handled gracefully");
        }

        @Test
        @DisplayName("Should handle file operation errors gracefully")
        void shouldHandleFileOperationErrorsGracefully() throws Exception {
            Method saveToFileMethod = MainFrame.class.getDeclaredMethod("saveToFile", String.class);
            saveToFileMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> saveToFileMethod.invoke(mainFrame, "/invalid/path/file.txt"), 
                              "Invalid file operations should be handled gracefully");
        }

        @Test
        @DisplayName("Should handle null action events gracefully")
        void shouldHandleNullActionEventsGracefully() throws Exception {
            Method actionPerformedMethod = MainFrame.class.getDeclaredMethod("actionPerformed", ActionEvent.class);
            actionPerformedMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> actionPerformedMethod.invoke(mainFrame, (ActionEvent) null), 
                              "Null action events should be handled gracefully");
        }

        @Test
        @DisplayName("Should handle keyboard shortcuts gracefully")
        void shouldHandleKeyboardShortcutsGracefully() throws Exception {
            Method handleKeyPressMethod = MainFrame.class.getDeclaredMethod("handleKeyPress", KeyEvent.class);
            handleKeyPressMethod.setAccessible(true);
            
            KeyEvent keyEvent = new KeyEvent(mainFrame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 
                                           KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_S, 'S');
            
            assertDoesNotThrow(() -> handleKeyPressMethod.invoke(mainFrame, keyEvent), 
                              "Keyboard shortcuts should be handled gracefully");
        }
    }

    @Nested
    @DisplayName("UI State Tests")
    class UIStateTests {

        @Test
        @DisplayName("Should maintain proper frame state")
        void shouldMaintainProperFrameState() {
            assertEquals(Frame.NORMAL, mainFrame.getState(), "Frame should be in normal state");
        }

        @Test
        @DisplayName("Should not be always on top")
        void shouldNotBeAlwaysOnTop() {
            assertFalse(mainFrame.isAlwaysOnTop(), "Frame should not be always on top");
        }

        @Test
        @DisplayName("Should be enabled by default")
        void shouldBeEnabledByDefault() {
            assertTrue(mainFrame.isEnabled(), "Frame should be enabled by default");
        }

        @Test
        @DisplayName("Should be valid and showing")
        void shouldBeValidAndShowing() {
            assertTrue(mainFrame.isValid(), "Frame should be valid");
            assertTrue(mainFrame.isShowing(), "Frame should be showing");
        }

        @Test
        @DisplayName("Should be opaque")
        void shouldBeOpaque() {
            assertTrue(mainFrame.isOpaque(), "Frame should be opaque");
        }

        @Test
        @DisplayName("Should have proper color scheme")
        void shouldHaveProperColorScheme() throws Exception {
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertEquals(Color.WHITE, drawingPanel.getBackground(), 
                        "Drawing panel should have white background");
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        @DisplayName("Should create frame within time limit")
        void shouldCreateFrameWithinTimeLimit() {
            assertDoesNotThrow(() -> {
                MainFrame testFrame = new MainFrame();
                testFrame.dispose();
            }, "Frame creation should complete within time limit");
        }

        @RepeatedTest(10)
        @DisplayName("Should handle rapid shape creation")
        void shouldHandleRapidShapeCreation() throws Exception {
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            createRectangleMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 10; i++) {
                    createRectangleMethod.invoke(mainFrame);
                }
            }, "Rapid shape creation should not cause issues");
        }

        @Test
        @DisplayName("Should properly dispose resources")
        void shouldProperlyDisposeResources() {
            MainFrame testFrame = new MainFrame();
            assertTrue(testFrame.isDisplayable(), "Frame should be displayable before dispose");
            
            testFrame.dispose();
            assertFalse(testFrame.isDisplayable(), "Frame should not be displayable after dispose");
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should integrate all components properly")
        void shouldIntegrateAllComponentsProperly() {
            assertNotNull(mainFrame.getJMenuBar(), "Menu bar should be integrated");
            assertNotNull(mainFrame.getContentPane(), "Content pane should be integrated");
            assertTrue(mainFrame.isVisible(), "Frame should be visible");
            assertEquals("Shapes Application", mainFrame.getTitle(), "Title should be set correctly");
        }

        @Test
        @DisplayName("Should maintain state consistency")
        void shouldMaintainStateConsistency() throws Exception {
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            Method newFileMethod = MainFrame.class.getDeclaredMethod("newFile");
            createRectangleMethod.setAccessible(true);
            newFileMethod.setAccessible(true);
            
            // Create shape, then clear
            createRectangleMethod.invoke(mainFrame);
            newFileMethod.invoke(mainFrame);
            
            Field drawingPanelField = MainFrame.class.getDeclaredField("drawingPanel");
            drawingPanelField.setAccessible(true);
            JPanel drawingPanel = (JPanel) drawingPanelField.get(mainFrame);
            
            assertEquals(0, drawingPanel.getComponentCount(), 
                        "Drawing panel should be cleared after new file operation");
        }

        @Test
        @DisplayName("Should handle complete workflow")
        void shouldHandleCompleteWorkflow() throws Exception {
            // Test a complete workflow: create shapes, save, new file, open
            Method createRectangleMethod = MainFrame.class.getDeclaredMethod("createRectangle");
            Method createCircleMethod = MainFrame.class.getDeclaredMethod("createCircle");
            Method saveFileMethod = MainFrame.class.getDeclaredMethod("saveFile");
            Method newFileMethod = MainFrame.class.getDeclaredMethod("newFile");
            Method openFileMethod = MainFrame.class.getDeclaredMethod("openFile");
            
            createRectangleMethod.setAccessible(true);
            createCircleMethod.setAccessible(true);
            saveFileMethod.setAccessible(true);
            newFileMethod.setAccessible(true);
            openFileMethod.setAccessible(true);
            
            assertDoesNotThrow(() -> {
                createRectangleMethod.invoke(mainFrame);
                createCircleMethod.invoke(mainFrame);
                saveFileMethod.invoke(mainFrame);
                newFileMethod.invoke(mainFrame);
                openFileMethod.invoke(mainFrame);
            }, "Complete workflow should execute without exceptions");
        }
    }
}