package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

class DemoCommandTest {

    private DemoCommand demoCommand;

    @BeforeEach
    void setUp() {
        demoCommand = new DemoCommand();
    }

    @Test
    void testExecuteCircleDemo() {
        // Given a demo command with circle parameter
        CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteSquareDemo() {
        // Given a demo command with square parameter
        CommandRequest request = new CommandRequest("demo", List.of("square"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteTriangleDemo() {
        // Given a demo command with triangle parameter
        CommandRequest request = new CommandRequest("demo", List.of("triangle"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithCaseInsensitiveShapeName() {
        // Given a demo command with uppercase shape name
        CommandRequest request = new CommandRequest("demo", List.of("CIRCLE"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception (case insensitive)
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithMixedCaseShapeName() {
        // Given a demo command with mixed case shape name
        CommandRequest request = new CommandRequest("demo", List.of("TrIaNgLe"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception (case insensitive)
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithNoParameters() {
        // Given a demo command with no parameters
        CommandRequest request = new CommandRequest("demo", Collections.emptyList(), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithTooManyParameters() {
        // Given a demo command with multiple parameters
        CommandRequest request = new CommandRequest("demo", Arrays.asList("circle", "square"), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithNullParameter() {
        // Given a demo command with null parameter
        CommandRequest request = new CommandRequest("demo", Arrays.asList((String) null), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithBlankParameter() {
        // Given a demo command with blank parameter
        CommandRequest request = new CommandRequest("demo", List.of("   "), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithEmptyStringParameter() {
        // Given a demo command with empty string parameter
        CommandRequest request = new CommandRequest("demo", List.of(""), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithTabOnlyParameter() {
        // Given a demo command with tab-only parameter
        CommandRequest request = new CommandRequest("demo", List.of("\t\t\t"), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithMixedWhitespaceParameter() {
        // Given a demo command with mixed whitespace parameter
        CommandRequest request = new CommandRequest("demo", List.of(" \t \n "), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException with usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testExecuteWithUnknownShape() {
        // Given a demo command with unknown shape
        CommandRequest request = new CommandRequest("demo", List.of("pentagon"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException with proper error message
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        assertTrue(exception.getMessage().contains("Unknown shape: pentagon"));
        assertTrue(exception.getMessage().contains("Available shapes:"));
        assertTrue(exception.getMessage().contains("circle"));
        assertTrue(exception.getMessage().contains("square"));
        assertTrue(exception.getMessage().contains("triangle"));
    }

    @Test
    void testExecuteWithNumericShapeName() {
        // Given a demo command with numeric shape name
        CommandRequest request = new CommandRequest("demo", List.of("123"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: 123"));
    }

    @Test
    void testExecuteWithSpecialCharactersShapeName() {
        // Given a demo command with special characters shape name
        CommandRequest request = new CommandRequest("demo", List.of("@#$"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: @#$"));
    }

    @Test
    void testExecuteWithVeryLongInvalidShapeName() {
        // Given a demo command with very long invalid shape name
        String longShapeName = "a".repeat(1000);
        CommandRequest request = new CommandRequest("demo", List.of(longShapeName), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: " + longShapeName));
    }

    @Test
    void testExecuteWithUnicodeCharactersShapeName() {
        // Given a demo command with unicode characters shape name
        CommandRequest request = new CommandRequest("demo", List.of("círculo"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: círculo"));
    }

    @Test
    void testExecuteWithEmojiShapeName() {
        // Given a demo command with emoji shape name
        CommandRequest request = new CommandRequest("demo", List.of("🔴"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: 🔴"));
    }

    @Test
    void testExecuteWithShapeNameContainingNumbers() {
        // Given a demo command with shape name containing numbers
        CommandRequest request = new CommandRequest("demo", List.of("circle123"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: circle123"));
    }

    @Test
    void testExecuteAllValidShapesUppercase() {
        // Given demo commands with all valid shapes in uppercase
        List<String> shapes = Arrays.asList("CIRCLE", "SQUARE", "TRIANGLE");
        
        // When executing each demo command
        // Then none should throw exceptions
        for (String shape : shapes) {
            CommandRequest request = new CommandRequest("demo", List.of(shape), Set.of());
            assertDoesNotThrow(() -> demoCommand.execute(request),
                "Should handle uppercase " + shape);
        }
    }

    @Test
    void testExecuteAllValidShapesMixedCase() {
        // Given demo commands with all valid shapes in mixed case
        List<String> shapes = Arrays.asList("Circle", "sQuArE", "TriAnGlE");
        
        // When executing each demo command
        // Then none should throw exceptions
        for (String shape : shapes) {
            CommandRequest request = new CommandRequest("demo", List.of(shape), Set.of());
            assertDoesNotThrow(() -> demoCommand.execute(request),
                "Should handle mixed case " + shape);
        }
    }

    @Test
    void testExecuteMultipleTimesStateless() {
        // Given multiple demo command executions
        CommandRequest circleRequest = new CommandRequest("demo", List.of("circle"), Set.of());
        CommandRequest squareRequest = new CommandRequest("demo", List.of("square"), Set.of());
        CommandRequest triangleRequest = new CommandRequest("demo", List.of("triangle"), Set.of());

        // When executing commands multiple times
        // Then all should succeed (stateless behavior)
        assertDoesNotThrow(() -> demoCommand.execute(circleRequest));
        assertDoesNotThrow(() -> demoCommand.execute(squareRequest));
        assertDoesNotThrow(() -> demoCommand.execute(triangleRequest));
    }

    @Test
    void testExecuteRapidSuccessiveExecutions() {
        // Given a demo command for rapid execution
        CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of());
        
        // When executing the command rapidly multiple times
        // Then all executions should succeed
        for (int i = 0; i < 5; i++) {
            assertDoesNotThrow(() -> demoCommand.execute(request),
                "Should handle execution #" + (i + 1));
        }
    }

    @Test
    void testUsageMessageFormat() {
        // Given a demo command with no parameters
        CommandRequest request = new CommandRequest("demo", Collections.emptyList(), Set.of());

        // When executing the demo command
        // Then it should throw exception with correct usage message
        PreConditionsException exception = assertThrows(
            PreConditionsException.class,
            () -> demoCommand.execute(request)
        );
        assertEquals("Usage: java -jar 01-shapes.jar demo <shape>", exception.getMessage());
    }

    @Test
    void testErrorMessageContainsAllAvailableShapes() {
        // Given a demo command with invalid shape
        CommandRequest request = new CommandRequest("demo", List.of("invalid"), Set.of());

        // When executing the demo command
        // Then error message should contain all available shapes
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        String message = exception.getMessage();
        assertTrue(message.contains("circle"), "Error message should contain 'circle'");
        assertTrue(message.contains("square"), "Error message should contain 'square'");
        assertTrue(message.contains("triangle"), "Error message should contain 'triangle'");
        assertTrue(message.contains("Available shapes:"), "Error message should contain 'Available shapes:'");
    }

    @Test
    void testUnknownShapeErrorMessageFormat() {
        // Given a demo command with unknown shape "oval"
        CommandRequest request = new CommandRequest("demo", List.of("oval"), Set.of());

        // When executing the demo command
        // Then error message should have correct format
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        
        String message = exception.getMessage();
        assertTrue(message.startsWith("Unknown shape: oval"));
        assertTrue(message.contains("Available shapes:"));
    }

    @Test
    void testExecuteWithExactlyTwoParameters() {
        // Given a demo command with exactly two parameters
        CommandRequest request = new CommandRequest("demo", Arrays.asList("circle", "square"), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException
        assertThrows(PreConditionsException.class, () -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithThreeParameters() {
        // Given a demo command with three parameters
        CommandRequest request = new CommandRequest("demo", Arrays.asList("circle", "square", "triangle"), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException
        assertThrows(PreConditionsException.class, () -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithManyParameters() {
        // Given a demo command with many parameters
        CommandRequest request = new CommandRequest("demo", Arrays.asList("circle", "square", "triangle", "pentagon", "hexagon"), Set.of());

        // When executing the demo command
        // Then it should throw PreConditionsException
        assertThrows(PreConditionsException.class, () -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithFlags() {
        // Given a demo command with flags (flags should be ignored)
        CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of("verbose", "debug"));

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithSingleFlag() {
        // Given a demo command with single flag
        CommandRequest request = new CommandRequest("demo", List.of("square"), Set.of("test"));

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteWithEmptyFlagSet() {
        // Given a demo command with empty flag set
        CommandRequest request = new CommandRequest("demo", List.of("triangle"), Set.of());

        // When executing the demo command
        // Then it should not throw any exception
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }

    @Test
    void testExecuteIgnoresActionParameter() {
        // Given demo commands with different action values
        List<String> actions = Arrays.asList("demo", "DEMO", "Demo", "other");
        
        // When executing commands with different actions
        // Then all should succeed (action parameter is ignored)
        for (String action : actions) {
            CommandRequest request = new CommandRequest(action, List.of("circle"), Set.of());
            assertDoesNotThrow(() -> demoCommand.execute(request),
                "Should execute with action: " + action);
        }
    }

    @Test
    void testExecuteWithHexagonShape() {
        // Given a demo command with hexagon shape (not supported)
        CommandRequest request = new CommandRequest("demo", List.of("hexagon"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: hexagon"));
    }

    @Test
    void testExecuteWithRectangleShape() {
        // Given a demo command with rectangle shape (not supported)
        CommandRequest request = new CommandRequest("demo", List.of("rectangle"), Set.of());

        // When executing the demo command
        // Then it should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> demoCommand.execute(request)
        );
        assertTrue(exception.getMessage().contains("Unknown shape: rectangle"));
    }

    @Test
    void testExecuteWithValidShapeHasCorrectFlow() {
        // Given a demo command with valid shape
        CommandRequest request = new CommandRequest("demo", List.of("circle"), Set.of());

        // When executing the demo command
        // Then it should complete successfully (Canvas creation and demo execution)
        assertDoesNotThrow(() -> demoCommand.execute(request));
    }
}