package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

/**
 * Comprehensive unit tests for DemoCommand class.
 * Testing framework: JUnit Jupiter (JUnit 5)
 * 
 * These tests cover:
 * - Valid shape execution (happy path)
 * - Invalid shape handling
 * - Parameter validation
 * - Case sensitivity handling
 * - Edge cases and boundary conditions
 * - Error message formatting
 */
class DemoCommandTest {

    private DemoCommand demoCommand;
    private TestCommandRequest request;

    @BeforeEach
    void setUp() {
        demoCommand = new DemoCommand();
        request = new TestCommandRequest();
    }

    // Happy Path Tests - Valid Shapes

    @Test
    void execute_WithValidCircleShape_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("circle"));
        
        // When & Then - Should not throw exception
        // Note: This will actually show the GUI window in a real test environment
        // In a production test suite, we would need to mock the Canvas and Demo classes
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithValidSquareShape_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("square"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithValidTriangleShape_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("triangle"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    // Case Sensitivity Tests

    @Test
    void execute_WithUppercaseCircle_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("CIRCLE"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithMixedCaseSquare_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("SqUaRe"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithMixedCaseTriangle_ShouldNotThrowException() {
        // Given
        request.setParams(List.of("tRiAnGlE"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithCapitalizedShapes_ShouldNotThrowException() {
        // Test all shapes with first letter capitalized
        String[] capitalizedShapes = {"Circle", "Square", "Triangle"};
        
        for (String shape : capitalizedShapes) {
            // Given
            request.setParams(List.of(shape));
            
            // When & Then
            assertDoesNotThrow(
                () -> demoCommand.execute(request),
                "Should handle capitalized shape: " + shape
            );
        }
    }

    // Invalid Shape Tests

    @Test
    void execute_WithInvalidShapePentagon_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("pentagon"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        String message = exception.getMessage();
        assertTrue(message.contains("Unknown shape: pentagon"));
        assertTrue(message.contains("Available shapes:"));
        assertTrue(message.contains("circle"));
        assertTrue(message.contains("square"));
        assertTrue(message.contains("triangle"));
    }

    @Test
    void execute_WithInvalidShapeRectangle_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("rectangle"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: rectangle"));
    }

    @Test
    void execute_WithInvalidShapeHexagon_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("hexagon"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: hexagon"));
    }

    // Parameter Validation Tests

    @Test
    void execute_WithEmptyParams_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(Collections.emptyList());
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithTwoParams_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(Arrays.asList("circle", "square"));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithThreeParams_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(Arrays.asList("circle", "square", "triangle"));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithNullParam_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(Arrays.asList((String) null));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithBlankParam_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(List.of("   "));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithEmptyStringParam_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(List.of(""));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void execute_WithTabsAndNewlinesParam_ShouldThrowPreConditionsException() {
        // Given
        request.setParams(List.of("\t\n\r "));
        
        // When & Then
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    // Edge Case Tests

    @Test
    void execute_WithNumericShapeName_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("123"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: 123"));
    }

    @Test
    void execute_WithSpecialCharactersInShapeName_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("circle@#$"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: circle@#$"));
    }

    @Test
    void execute_WithUnicodeCharactersInShapeName_ShouldThrowIllegalArgumentException() {
        // Given
        request.setParams(List.of("círculo"));
        
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: círculo"));
    }

    @Test
    void execute_WithLeadingAndTrailingSpacesAroundValidShape_ShouldWorkCorrectly() {
        // Given
        request.setParams(List.of("  circle  "));
        
        // When & Then - should not throw exception
        // The code calls toLowerCase() and trim() logic should handle this
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void execute_WithMixedWhitespaceAroundValidShape_ShouldWorkCorrectly() {
        // Given  
        request.setParams(List.of("\t circle \n"));
        
        // When & Then
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    // Comprehensive Tests

    @Test
    void execute_WithAllSupportedShapes_ShouldExecuteEachCorrectly() {
        // Test all supported shapes to ensure they work
        String[] supportedShapes = {"circle", "square", "triangle"};
        
        for (String shape : supportedShapes) {
            // Given
            request.setParams(List.of(shape));
            
            // When & Then
            assertDoesNotThrow(
                () -> demoCommand.execute(request),
                "Should not throw exception for shape: " + shape
            );
        }
    }

    @Test
    void execute_WithAllCaseVariations_ShouldHandleCorrectly() {
        // Test various case combinations for all shapes
        String[][] caseVariations = {
            {"circle", "CIRCLE", "Circle", "cIrClE"},
            {"square", "SQUARE", "Square", "sQuArE"},
            {"triangle", "TRIANGLE", "Triangle", "tRiAnGlE"}
        };
        
        for (String[] variations : caseVariations) {
            for (String variation : variations) {
                // Given
                request.setParams(List.of(variation));
                
                // When & Then
                assertDoesNotThrow(
                    () -> demoCommand.execute(request),
                    "Should handle case variation: " + variation
                );
            }
        }
    }

    // Error Message Format Tests

    @Test
    void execute_ErrorMessageFormat_ShouldFollowExpectedFormat() {
        // Given
        request.setParams(List.of("invalidShape"));
        
        // When
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        // Then verify the error message format matches exactly
        String message = exception.getMessage();
        assertTrue(message.startsWith("Unknown shape: invalidshape. Available shapes:"));
        assertTrue(message.contains("circle"));
        assertTrue(message.contains("square"));
        assertTrue(message.contains("triangle"));
    }

    @Test
    void execute_ErrorMessage_ShouldContainAllAvailableShapes() {
        // Given
        request.setParams(List.of("unknown"));
        
        // When
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        // Then verify all available shapes are mentioned
        String message = exception.getMessage();
        assertTrue(message.contains("circle"), "Error message should contain 'circle'");
        assertTrue(message.contains("square"), "Error message should contain 'square'");
        assertTrue(message.contains("triangle"), "Error message should contain 'triangle'");
    }

    // Complex Edge Cases

    @Test
    void execute_WithComplexInvalidInputs_ShouldHandleGracefully() {
        // Test parameter edge cases that should still result in invalid shape errors
        String[] edgeCaseInputs = {
            "circle123",    // Valid shape with numbers
            "123circle",    // Numbers with valid shape
            "circle_demo",  // Underscore
            "circle-demo",  // Hyphen
            "circle.exe",   // File extension
            "circle/square", // Path separator
            "circle\\square", // Backslash
            "circle+square", // Plus sign
            "circle=square", // Equals sign
            "circle?square", // Question mark
            "circle*square"  // Asterisk
        };
        
        for (String input : edgeCaseInputs) {
            // Given
            request.setParams(List.of(input));
            
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> demoCommand.execute(request),
                "Should throw exception for input: " + input
            );
            
            assertTrue(exception.getMessage().contains("Unknown shape: " + input.toLowerCase()),
                "Error message should contain the exact input (lowercase): " + input);
        }
    }

    @Test
    void execute_WithVariousMultipleArguments_ShouldRejectAll() {
        // Test multiple argument combinations
        String[][] multipleArguments = {
            {"circle", "square"},
            {"triangle", "circle", "square"},
            {"square", "triangle", "circle", "pentagon"},
            {"", "circle"},
            {"circle", ""},
            {" ", "square"},
            {"circle", "square", "rectangle", "pentagon"}
        };
        
        for (String[] args : multipleArguments) {
            // Given
            request.setParams(Arrays.asList(args));
            
            // When & Then
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> demoCommand.execute(request),
                "Should throw PreConditionsException for args: " + Arrays.toString(args)
            );
            
            assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
        }
    }

    @Test
    void execute_WithExtremelyLongShapeName_ShouldHandleGracefully() {
        // Test extremely long shape names
        String longShapeName = "a".repeat(1000);
        request.setParams(List.of(longShapeName));
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: " + longShapeName));
    }

    @Test
    void execute_WithVariousWhitespaceInputs_ShouldRejectAllBlank() {
        // Test various whitespace combinations
        String[] whitespaceInputs = {
            " ",
            "\t",
            "\n",
            "\r",
            "  ",
            "\t\t",
            "\n\n",
            "\r\r",
            " \t\n\r ",
            "\u00A0", // Non-breaking space
            "\u2000", // En quad
            "\u2003"  // Em space
        };
        
        for (String whitespace : whitespaceInputs) {
            // Given
            request.setParams(List.of(whitespace));
            
            // When & Then
            PreConditionsException exception = assertThrows(
                PreConditionsException.class,
                () -> demoCommand.execute(request),
                "Should throw PreConditionsException for whitespace: '" + whitespace + "'"
            );
            
            assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
        }
    }

    // Null Handling Tests

    @Test
    void execute_WithNullCommandRequest_ShouldThrowNullPointerException() {
        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> demoCommand.execute(null),
            "Should throw NullPointerException when CommandRequest is null"
        );
    }

    @Test
    void execute_WithCommandRequestReturningNullParams_ShouldThrowNullPointerException() {
        // Given
        request.setParams(null);
        
        // When & Then
        assertThrows(
            NullPointerException.class,
            () -> demoCommand.execute(request),
            "Should throw NullPointerException when params() returns null"
        );
    }

    // Stress Testing

    @Test
    void execute_MultipleExecutions_ShouldHandleRepeatedCalls() {
        // Test that multiple executions don't interfere with each other
        request.setParams(List.of("circle"));
        
        // When & Then - Execute multiple times
        assertDoesNotThrow(() -> {
            demoCommand.execute(request);
            demoCommand.execute(request);
            demoCommand.execute(request);
        });
    }

    @Test
    void execute_AlternatingValidAndInvalidShapes_ShouldHandleCorrectly() {
        // Test alternating between valid and invalid shapes
        String[] shapes = {"circle", "invalid", "square", "unknown", "triangle", "pentagon"};
        
        for (int i = 0; i < shapes.length; i++) {
            request.setParams(List.of(shapes[i]));
            
            if (i % 2 == 0) { // Even indices are valid shapes
                assertDoesNotThrow(
                    () -> demoCommand.execute(request),
                    "Should not throw for valid shape: " + shapes[i]
                );
            } else { // Odd indices are invalid shapes
                assertThrows(
                    IllegalArgumentException.class,
                    () -> demoCommand.execute(request),
                    "Should throw for invalid shape: " + shapes[i]
                );
            }
        }
    }

    /**
     * Test helper class to create CommandRequest instances for testing
     */
    private static class TestCommandRequest implements CommandRequest {
        private List<String> params;
        
        public void setParams(List<String> params) {
            this.params = params;
        }
        
        @Override
        public List<String> params() {
            return params;
        }
    }
}