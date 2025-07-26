package com.pedromg.bluej.shapes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.pedromg.bluej.shapes.preconditions.PreConditionsException;

@DisplayName("CommandRequest Tests")
class CommandRequestTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create CommandRequest with valid parameters")
        void shouldCreateCommandRequestWithValidParameters() {
            // Given
            String action = "draw";
            List<String> params = List.of("circle", "10");
            Set<String> flags = Set.of("verbose", "color");

            // When
            CommandRequest request = new CommandRequest(action, params, flags);

            // Then
            assertEquals(action, request.action());
            assertEquals(params, request.params());
            assertEquals(flags, request.flags());
        }

        @Test
        @DisplayName("Should create CommandRequest with null action")
        void shouldCreateCommandRequestWithNullAction() {
            // Given
            List<String> params = List.of("param1");
            Set<String> flags = Set.of("flag1");

            // When
            CommandRequest request = new CommandRequest(null, params, flags);

            // Then
            assertNull(request.action());
            assertEquals(params, request.params());
            assertEquals(flags, request.flags());
        }

        @Test
        @DisplayName("Should create CommandRequest with null params")
        void shouldCreateCommandRequestWithNullParams() {
            // Given
            String action = "test";
            Set<String> flags = Set.of("flag1");

            // When
            CommandRequest request = new CommandRequest(action, null, flags);

            // Then
            assertEquals(action, request.action());
            assertNull(request.params());
            assertEquals(flags, request.flags());
        }

        @Test
        @DisplayName("Should create CommandRequest with null flags")
        void shouldCreateCommandRequestWithNullFlags() {
            // Given
            String action = "test";
            List<String> params = List.of("param1");

            // When
            CommandRequest request = new CommandRequest(action, params, null);

            // Then
            assertEquals(action, request.action());
            assertEquals(params, request.params());
            assertNull(request.flags());
        }

        @Test
        @DisplayName("Should create CommandRequest with empty collections")
        void shouldCreateCommandRequestWithEmptyCollections() {
            // Given
            String action = "test";
            List<String> params = Collections.emptyList();
            Set<String> flags = Collections.emptySet();

            // When
            CommandRequest request = new CommandRequest(action, params, flags);

            // Then
            assertEquals(action, request.action());
            assertTrue(request.params().isEmpty());
            assertTrue(request.flags().isEmpty());
        }
    }

    @Nested
    @DisplayName("hasFlag() Method Tests")
    class HasFlagTests {

        @Test
        @DisplayName("Should return true when flag exists (exact case)")
        void shouldReturnTrueWhenFlagExistsExactCase() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose", "debug"));

            // When & Then
            assertTrue(request.hasFlag("verbose"));
            assertTrue(request.hasFlag("debug"));
        }

        @Test
        @DisplayName("Should return true when flag exists (case insensitive)")
        void shouldReturnTrueWhenFlagExistsCaseInsensitive() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose", "debug"));

            // When & Then
            assertTrue(request.hasFlag("VERBOSE"));
            assertTrue(request.hasFlag("Verbose"));
            assertTrue(request.hasFlag("DEBUG"));
            assertTrue(request.hasFlag("Debug"));
            assertTrue(request.hasFlag("DeBuG"));
        }

        @Test
        @DisplayName("Should return false when flag does not exist")
        void shouldReturnFalseWhenFlagDoesNotExist() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose", "debug"));

            // When & Then
            assertFalse(request.hasFlag("nonexistent"));
            assertFalse(request.hasFlag("help"));
            assertFalse(request.hasFlag("color"));
        }

        @Test
        @DisplayName("Should return false when flags set is empty")
        void shouldReturnFalseWhenFlagsSetIsEmpty() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of());

            // When & Then
            assertFalse(request.hasFlag("verbose"));
        }

        @Test
        @DisplayName("Should handle flags with special characters")
        void shouldHandleFlagsWithSpecialCharacters() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("flag-with-dash", "flag_with_underscore", "flag.with.dot"));

            // When & Then
            assertTrue(request.hasFlag("flag-with-dash"));
            assertTrue(request.hasFlag("FLAG-WITH-DASH"));
            assertTrue(request.hasFlag("flag_with_underscore"));
            assertTrue(request.hasFlag("FLAG_WITH_UNDERSCORE"));
            assertTrue(request.hasFlag("flag.with.dot"));
            assertTrue(request.hasFlag("FLAG.WITH.DOT"));
        }

        @Test
        @DisplayName("Should handle flags with numbers")
        void shouldHandleFlagsWithNumbers() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("flag1", "flag2", "version3"));

            // When & Then
            assertTrue(request.hasFlag("flag1"));
            assertTrue(request.hasFlag("FLAG1"));
            assertTrue(request.hasFlag("version3"));
            assertTrue(request.hasFlag("VERSION3"));
        }

        @Test
        @DisplayName("Should handle single character flags")
        void shouldHandleSingleCharacterFlags() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("v", "d", "h"));

            // When & Then
            assertTrue(request.hasFlag("v"));
            assertTrue(request.hasFlag("V"));
            assertTrue(request.hasFlag("d"));
            assertTrue(request.hasFlag("D"));
            assertTrue(request.hasFlag("h"));
            assertTrue(request.hasFlag("H"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should throw PreConditionsException for null or empty flag name")
        void shouldThrowPreConditionsExceptionForNullOrEmptyFlagName(String flagName) {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            assertThrows(PreConditionsException.class, () -> request.hasFlag(flagName));
        }

        @ParameterizedTest
        @ValueSource(strings = {" ", "  ", "\t", "\n", "\r", " \t \n "})
        @DisplayName("Should throw PreConditionsException for blank flag name")
        void shouldThrowPreConditionsExceptionForBlankFlagName(String flagName) {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            assertThrows(PreConditionsException.class, () -> request.hasFlag(flagName));
        }

        @Test
        @DisplayName("Should handle flag name with leading/trailing spaces after trim")
        void shouldHandleFlagNameWithLeadingTrailingSpaces() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            assertTrue(request.hasFlag(" verbose "));
            assertTrue(request.hasFlag("  verbose  "));
            assertTrue(request.hasFlag("\tverbose\t"));
        }

        @Test
        @DisplayName("Should throw NullPointerException when flags set is null")
        void shouldThrowNullPointerExceptionWhenFlagsSetIsNull() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), null);

            // When & Then
            assertThrows(NullPointerException.class, () -> request.hasFlag("verbose"));
        }

        @Test
        @DisplayName("Should handle edge case where flag exists in mixed case")
        void shouldHandleEdgeCaseWhereFlagExistsInMixedCase() {
            // Given - flags stored in mixed case
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("Verbose", "DEBUG", "hElP"));

            // When & Then - should find flags regardless of input case
            assertTrue(request.hasFlag("verbose"));
            assertTrue(request.hasFlag("VERBOSE"));
            assertTrue(request.hasFlag("debug"));
            assertTrue(request.hasFlag("DEBUG"));
            assertTrue(request.hasFlag("help"));
            assertTrue(request.hasFlag("HELP"));
            assertTrue(request.hasFlag("Verbose"));
            assertTrue(request.hasFlag("hElP"));
        }
    }

    @Nested
    @DisplayName("Record Equality and Hash Code Tests")
    class RecordEqualityTests {

        @Test
        @DisplayName("Should be equal when all fields are equal")
        void shouldBeEqualWhenAllFieldsAreEqual() {
            // Given
            CommandRequest request1 = new CommandRequest("test", List.of("param1"), Set.of("flag1"));
            CommandRequest request2 = new CommandRequest("test", List.of("param1"), Set.of("flag1"));

            // When & Then
            assertEquals(request1, request2);
            assertEquals(request1.hashCode(), request2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when actions differ")
        void shouldNotBeEqualWhenActionsDiffer() {
            // Given
            CommandRequest request1 = new CommandRequest("test1", List.of("param1"), Set.of("flag1"));
            CommandRequest request2 = new CommandRequest("test2", List.of("param1"), Set.of("flag1"));

            // When & Then
            assertNotEquals(request1, request2);
        }

        @Test
        @DisplayName("Should not be equal when params differ")
        void shouldNotBeEqualWhenParamsDiffer() {
            // Given
            CommandRequest request1 = new CommandRequest("test", List.of("param1"), Set.of("flag1"));
            CommandRequest request2 = new CommandRequest("test", List.of("param2"), Set.of("flag1"));

            // When & Then
            assertNotEquals(request1, request2);
        }

        @Test
        @DisplayName("Should not be equal when flags differ")
        void shouldNotBeEqualWhenFlagsDiffer() {
            // Given
            CommandRequest request1 = new CommandRequest("test", List.of("param1"), Set.of("flag1"));
            CommandRequest request2 = new CommandRequest("test", List.of("param1"), Set.of("flag2"));

            // When & Then
            assertNotEquals(request1, request2);
        }

        @Test
        @DisplayName("Should be equal when all fields are null")
        void shouldBeEqualWhenAllFieldsAreNull() {
            // Given
            CommandRequest request1 = new CommandRequest(null, null, null);
            CommandRequest request2 = new CommandRequest(null, null, null);

            // When & Then
            assertEquals(request1, request2);
            assertEquals(request1.hashCode(), request2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of());

            // When & Then
            assertNotEquals(request, null);
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of());
            String differentObject = "test";

            // When & Then
            assertNotEquals(request, differentObject);
        }
    }

    @Nested
    @DisplayName("toString() Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should generate meaningful toString representation")
        void shouldGenerateMeaningfulToStringRepresentation() {
            // Given
            CommandRequest request = new CommandRequest("draw", List.of("circle", "10"), Set.of("verbose", "color"));

            // When
            String result = request.toString();

            // Then
            assertNotNull(result);
            assertTrue(result.contains("CommandRequest"));
            assertTrue(result.contains("draw"));
            assertTrue(result.contains("circle"));
            assertTrue(result.contains("10"));
            assertTrue(result.contains("verbose"));
            assertTrue(result.contains("color"));
        }

        @Test
        @DisplayName("Should handle null values in toString")
        void shouldHandleNullValuesInToString() {
            // Given
            CommandRequest request = new CommandRequest(null, null, null);

            // When
            String result = request.toString();

            // Then
            assertNotNull(result);
            assertTrue(result.contains("CommandRequest"));
        }

        @Test
        @DisplayName("Should handle empty collections in toString")
        void shouldHandleEmptyCollectionsInToString() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of());

            // When
            String result = request.toString();

            // Then
            assertNotNull(result);
            assertTrue(result.contains("CommandRequest"));
            assertTrue(result.contains("test"));
        }
    }

    @Nested
    @DisplayName("Edge Case and Stress Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle very long flag names")
        void shouldHandleVeryLongFlagNames() {
            // Given
            String longFlag = "a".repeat(1000);
            CommandRequest request = new CommandRequest("test", List.of(), Set.of(longFlag));

            // When & Then
            assertTrue(request.hasFlag(longFlag));
            assertTrue(request.hasFlag(longFlag.toUpperCase()));
        }

        @Test
        @DisplayName("Should handle large number of flags")
        void shouldHandleLargeNumberOfFlags() {
            // Given
            Set<String> manyFlags = Set.of("flag1", "flag2", "flag3", "flag4", "flag5", 
                                         "flag6", "flag7", "flag8", "flag9", "flag10",
                                         "verbose", "debug", "help", "version", "quiet",
                                         "force", "dry-run", "interactive", "recursive", "all");
            CommandRequest request = new CommandRequest("test", List.of(), manyFlags);

            // When & Then
            assertTrue(request.hasFlag("flag1"));
            assertTrue(request.hasFlag("flag10"));
            assertTrue(request.hasFlag("verbose"));
            assertTrue(request.hasFlag("all"));
            assertFalse(request.hasFlag("nonexistent"));
        }

        @Test
        @DisplayName("Should handle Unicode characters in flag names")
        void shouldHandleUnicodeCharactersInFlagNames() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("flagé", "флаг", "フラグ"));

            // When & Then
            assertTrue(request.hasFlag("flagé"));
            assertTrue(request.hasFlag("флаг"));
            assertTrue(request.hasFlag("フラグ"));
        }

        @Test
        @DisplayName("Should handle flag names with only whitespace that become empty after trim")
        void shouldHandleFlagNamesWithOnlyWhitespaceThatBecomeEmptyAfterTrim() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then - these should throw PreConditionsException as they become empty after trim
            assertThrows(PreConditionsException.class, () -> request.hasFlag("   "));
            assertThrows(PreConditionsException.class, () -> request.hasFlag("\t\t"));
            assertThrows(PreConditionsException.class, () -> request.hasFlag("\n\n"));
        }

        @Test
        @DisplayName("Should handle complex flag names with mixed special characters")
        void shouldHandleComplexFlagNamesWithMixedSpecialCharacters() {
            // Given
            Set<String> complexFlags = Set.of("flag-1_test.v2", "COMPLEX_FLAG-123.final", "mix3d-Ch4rs_t3st.v1");
            CommandRequest request = new CommandRequest("test", List.of(), complexFlags);

            // When & Then
            assertTrue(request.hasFlag("flag-1_test.v2"));
            assertTrue(request.hasFlag("FLAG-1_TEST.V2"));
            assertTrue(request.hasFlag("complex_flag-123.final"));
            assertTrue(request.hasFlag("COMPLEX_FLAG-123.FINAL"));
            assertTrue(request.hasFlag("mix3d-ch4rs_t3st.v1"));
            assertTrue(request.hasFlag("MIX3D-CH4RS_T3ST.V1"));
        }

        @Test
        @DisplayName("Should handle performance with repeated flag checks")
        void shouldHandlePerformanceWithRepeatedFlagChecks() {
            // Given
            Set<String> flags = Set.of("performance", "test", "flag");
            CommandRequest request = new CommandRequest("test", List.of(), flags);

            // When & Then - perform multiple checks to ensure consistent behavior
            for (int i = 0; i < 1000; i++) {
                assertTrue(request.hasFlag("performance"));
                assertTrue(request.hasFlag("PERFORMANCE"));
                assertFalse(request.hasFlag("nonexistent"));
            }
        }
    }

    @Nested
    @DisplayName("Integration with PreConditions Tests")
    class PreConditionsIntegrationTests {

        @Test
        @DisplayName("Should use PreConditions for null check validation")
        void shouldUsePreConditionsForNullCheckValidation() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, 
                () -> request.hasFlag(null));
            
            // Verify that the exception message comes from PreConditions
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("Flag name must not be null"));
        }

        @Test
        @DisplayName("Should use PreConditions for blank check validation")
        void shouldUsePreConditionsForBlankCheckValidation() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, 
                () -> request.hasFlag(""));
            
            // Verify that the exception message comes from PreConditions
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("Flag name must not be blank"));
        }

        @Test
        @DisplayName("Should use PreConditions for whitespace-only string validation")
        void shouldUsePreConditionsForWhitespaceOnlyStringValidation() {
            // Given
            CommandRequest request = new CommandRequest("test", List.of(), Set.of("verbose"));

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, 
                () -> request.hasFlag("   "));
            
            // Verify that the exception message comes from PreConditions
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains("Flag name must not be blank"));
        }
    }
}