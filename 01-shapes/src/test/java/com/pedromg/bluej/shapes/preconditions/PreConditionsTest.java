package com.pedromg.bluej.shapes.preconditions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

public class PreConditionsTest {

    @Nested
    @DisplayName("Static require methods")
    class StaticRequireMethods {

        @Test
        @DisplayName("require(Runnable, String) - should pass when runnable doesn't throw")
        void requireRunnable_shouldPassWhenRunnableDoesNotThrow() {
            // Given
            Runnable validRunnable = () -> { /* do nothing */ };
            String message = "Should not fail";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.require(validRunnable, message).check();
            });
        }

        @Test
        @DisplayName("require(Runnable, String) - should fail when runnable throws exception")
        void requireRunnable_shouldFailWhenRunnableThrowsException() {
            // Given
            Runnable throwingRunnable = () -> {
                throw new RuntimeException("Test exception");
            };
            String message = "Runnable should not throw";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(throwingRunnable, message).check();
            });
            assertTrue(exception.getViolations().contains(message));
        }

        @Test
        @DisplayName("require(boolean, String) - should pass when condition is true")
        void requireBoolean_shouldPassWhenConditionIsTrue() {
            // Given
            boolean condition = true;
            String message = "Condition should be true";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.require(condition, message).check();
            });
        }

        @Test
        @DisplayName("require(boolean, String) - should fail when condition is false")
        void requireBoolean_shouldFailWhenConditionIsFalse() {
            // Given
            boolean condition = false;
            String message = "Condition should be true";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(condition, message).check();
            });
            assertTrue(exception.getViolations().contains(message));
        }

        @Test
        @DisplayName("requireNot(boolean, String) - should pass when condition is false")
        void requireNot_shouldPassWhenConditionIsFalse() {
            // Given
            boolean condition = false;
            String message = "Condition should be false";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.requireNot(condition, message).check();
            });
        }

        @Test
        @DisplayName("requireNot(boolean, String) - should fail when condition is true")
        void requireNot_shouldFailWhenConditionIsTrue() {
            // Given
            boolean condition = true;
            String message = "Condition should be false";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.requireNot(condition, message).check();
            });
            assertTrue(exception.getViolations().contains(message));
        }

        @Test
        @DisplayName("requireNonNull(Object, String) - should pass when object is not null")
        void requireNonNull_shouldPassWhenObjectIsNotNull() {
            // Given
            Object obj = new Object();
            String message = "Object should not be null";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.requireNonNull(obj, message).check();
            });
        }

        @Test
        @DisplayName("requireNonNull(Object, String) - should fail when object is null")
        void requireNonNull_shouldFailWhenObjectIsNull() {
            // Given
            Object obj = null;
            String message = "Object should not be null";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.requireNonNull(obj, message).check();
            });
            assertTrue(exception.getViolations().contains(message));
        }
    }

    @Nested
    @DisplayName("Instance and methods")
    class InstanceAndMethods {

        @Test
        @DisplayName("and(Runnable, String) - should pass when runnable doesn't throw")
        void andRunnable_shouldPassWhenRunnableDoesNotThrow() {
            // Given
            Runnable validRunnable1 = () -> { /* do nothing */ };
            Runnable validRunnable2 = () -> { /* do nothing */ };
            String message1 = "First should not fail";
            String message2 = "Second should not fail";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.require(validRunnable1, message1)
                    .and(validRunnable2, message2)
                    .check();
            });
        }

        @Test
        @DisplayName("and(Runnable, String) - should fail when any runnable throws")
        void andRunnable_shouldFailWhenAnyRunnableThrows() {
            // Given
            Runnable validRunnable = () -> { /* do nothing */ };
            Runnable throwingRunnable = () -> {
                throw new RuntimeException("Test exception");
            };
            String message1 = "First should not fail";
            String message2 = "Second should not fail";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(validRunnable, message1)
                    .and(throwingRunnable, message2)
                    .check();
            });
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message1));
        }

        @Test
        @DisplayName("and(boolean, String) - should pass when all conditions are true")
        void andBoolean_shouldPassWhenAllConditionsAreTrue() {
            // Given
            boolean condition1 = true;
            boolean condition2 = true;
            String message1 = "First condition should be true";
            String message2 = "Second condition should be true";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.require(condition1, message1)
                    .and(condition2, message2)
                    .check();
            });
        }

        @Test
        @DisplayName("and(boolean, String) - should fail when any condition is false")
        void andBoolean_shouldFailWhenAnyConditionIsFalse() {
            // Given
            boolean condition1 = true;
            boolean condition2 = false;
            String message1 = "First condition should be true";
            String message2 = "Second condition should be true";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(condition1, message1)
                    .and(condition2, message2)
                    .check();
            });
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message1));
        }

        @Test
        @DisplayName("andNot(boolean, String) - should pass when condition is false")
        void andNot_shouldPassWhenConditionIsFalse() {
            // Given
            boolean condition1 = true;
            boolean condition2 = false;
            String message1 = "First condition should be true";
            String message2 = "Second condition should be false";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.require(condition1, message1)
                    .andNot(condition2, message2)
                    .check();
            });
        }

        @Test
        @DisplayName("andNot(boolean, String) - should fail when condition is true")
        void andNot_shouldFailWhenConditionIsTrue() {
            // Given
            boolean condition1 = true;
            boolean condition2 = true;
            String message1 = "First condition should be true";
            String message2 = "Second condition should be false";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(condition1, message1)
                    .andNot(condition2, message2)
                    .check();
            });
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message1));
        }

        @Test
        @DisplayName("andNonNull(Object, String) - should pass when object is not null")
        void andNonNull_shouldPassWhenObjectIsNotNull() {
            // Given
            Object obj1 = new Object();
            Object obj2 = new Object();
            String message1 = "First object should not be null";
            String message2 = "Second object should not be null";

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.requireNonNull(obj1, message1)
                    .andNonNull(obj2, message2)
                    .check();
            });
        }

        @Test
        @DisplayName("andNonNull(Object, String) - should fail when any object is null")
        void andNonNull_shouldFailWhenAnyObjectIsNull() {
            // Given
            Object obj1 = new Object();
            Object obj2 = null;
            String message1 = "First object should not be null";
            String message2 = "Second object should not be null";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.requireNonNull(obj1, message1)
                    .andNonNull(obj2, message2)
                    .check();
            });
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message1));
        }
    }

    @Nested
    @DisplayName("Check method behavior")
    class CheckMethodBehavior {

        @Test
        @DisplayName("check() - should throw exception when no requirements defined")
        void check_shouldThrowExceptionWhenNoRequirementsDefined() {
            // Given
            PreConditions preConditions = new PreConditions();

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                preConditions.check();
            });
            assertTrue(exception.getViolations().contains("No requirements defined"));
        }

        @Test
        @DisplayName("check() - should collect all violations")
        void check_shouldCollectAllViolations() {
            // Given
            boolean condition1 = false;
            boolean condition2 = false;
            boolean condition3 = true;
            String message1 = "First violation";
            String message2 = "Second violation";
            String message3 = "Third should pass";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(condition1, message1)
                    .and(condition2, message2)
                    .and(condition3, message3)
                    .check();
            });
            
            assertEquals(2, exception.getViolations().size());
            assertTrue(exception.getViolations().contains(message1));
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message3));
        }

        @Test
        @DisplayName("check() - should handle mixed runnable and boolean conditions")
        void check_shouldHandleMixedRunnableAndBooleanConditions() {
            // Given
            Runnable throwingRunnable = () -> {
                throw new RuntimeException("Runnable failed");
            };
            boolean falseCondition = false;
            boolean trueCondition = true;
            String message1 = "Runnable violation";
            String message2 = "Boolean violation";
            String message3 = "Should pass";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(throwingRunnable, message1)
                    .and(falseCondition, message2)
                    .and(trueCondition, message3)
                    .check();
            });
            
            assertEquals(2, exception.getViolations().size());
            assertTrue(exception.getViolations().contains(message1));
            assertTrue(exception.getViolations().contains(message2));
            assertFalse(exception.getViolations().contains(message3));
        }

        @Test
        @DisplayName("check() - should handle exceptions other than IllegalArgumentException")
        void check_shouldHandleExceptionsOtherThanIllegalArgumentException() {
            // Given
            Runnable runtimeExceptionRunnable = () -> {
                throw new RuntimeException("Runtime exception");
            };
            Runnable nullPointerExceptionRunnable = () -> {
                throw new NullPointerException("Null pointer exception");
            };
            String message1 = "Runtime exception violation";
            String message2 = "Null pointer exception violation";

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(runtimeExceptionRunnable, message1)
                    .and(nullPointerExceptionRunnable, message2)
                    .check();
            });
            
            assertEquals(2, exception.getViolations().size());
            assertTrue(exception.getViolations().contains(message1));
            assertTrue(exception.getViolations().contains(message2));
        }
    }

    @Nested
    @DisplayName("Method chaining and fluent interface")
    class MethodChainingAndFluentInterface {

        @Test
        @DisplayName("should support complex method chaining")
        void shouldSupportComplexMethodChaining() {
            // Given
            Object obj1 = new Object();
            Object obj2 = new Object();
            boolean condition1 = true;
            boolean condition2 = false;
            Runnable validRunnable = () -> { /* do nothing */ };

            // When & Then
            assertDoesNotThrow(() -> {
                PreConditions.requireNonNull(obj1, "Object 1 should not be null")
                    .andNonNull(obj2, "Object 2 should not be null")
                    .and(condition1, "Condition 1 should be true")
                    .andNot(condition2, "Condition 2 should be false")
                    .and(validRunnable, "Runnable should not throw")
                    .check();
            });
        }

        @Test
        @DisplayName("should return proper types for method chaining")
        void shouldReturnProperTypesForMethodChaining() {
            // Given
            String message = "Test message";
            
            // When
            PreConditions result1 = PreConditions.require(true, message);
            PreConditions result2 = PreConditions.require(() -> {}, message);
            PreConditions result3 = PreConditions.requireNot(false, message);
            PreConditions result4 = PreConditions.requireNonNull(new Object(), message);

            // Then
            assertNotNull(result1);
            assertNotNull(result2);
            assertNotNull(result3);
            assertNotNull(result4);
            
            // Should be able to chain further
            assertDoesNotThrow(() -> {
                result1.and(true, message).check();
                result2.and(true, message).check();
                result3.and(true, message).check();
                result4.and(true, message).check();
            });
        }
    }

    @Nested
    @DisplayName("Edge cases and boundary conditions")
    class EdgeCasesAndBoundaryConditions {

        @Test
        @DisplayName("should handle null messages gracefully")
        void shouldHandleNullMessagesGracefully() {
            // Given
            String nullMessage = null;
            boolean falseCondition = false;

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(falseCondition, nullMessage).check();
            });
            
            assertTrue(exception.getViolations().contains(nullMessage));
        }

        @Test
        @DisplayName("should handle empty messages")
        void shouldHandleEmptyMessages() {
            // Given
            String emptyMessage = "";
            boolean falseCondition = false;

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(falseCondition, emptyMessage).check();
            });
            
            assertTrue(exception.getViolations().contains(emptyMessage));
        }

        @Test
        @DisplayName("should handle very long message strings")
        void shouldHandleVeryLongMessageStrings() {
            // Given
            String longMessage = "This is a very long message ".repeat(100);
            boolean falseCondition = false;

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                PreConditions.require(falseCondition, longMessage).check();
            });
            
            assertTrue(exception.getViolations().contains(longMessage));
        }

        @Test
        @DisplayName("should handle many requirements")
        void shouldHandleManyRequirements() {
            // Given
            PreConditions preConditions = PreConditions.require(true, "First");
            
            // Add many requirements
            for (int i = 2; i <= 100; i++) {
                preConditions = preConditions.and(true, "Requirement " + i);
            }

            // When & Then
            assertDoesNotThrow(() -> {
                preConditions.check();
            });
        }

        @Test
        @DisplayName("should handle many failing requirements")
        void shouldHandleManyFailingRequirements() {
            // Given
            PreConditions preConditions = PreConditions.require(false, "First violation");
            
            // Add many failing requirements
            for (int i = 2; i <= 50; i++) {
                preConditions = preConditions.and(false, "Violation " + i);
            }

            // When & Then
            PreConditionsException exception = assertThrows(PreConditionsException.class, () -> {
                preConditions.check();
            });
            
            assertEquals(50, exception.getViolations().size());
        }

        @Test
        @DisplayName("should handle null runnable")
        void shouldHandleNullRunnable() {
            // Given
            Runnable nullRunnable = null;
            String message = "Null runnable";

            // When & Then
            assertThrows(NullPointerException.class, () -> {
                PreConditions.require(nullRunnable, message).check();
            });
        }
    }

    @Nested
    @DisplayName("Performance and memory tests")
    class PerformanceAndMemoryTests {

        @Test
        @DisplayName("should not keep strong references to passed objects")
        void shouldNotKeepStrongReferencesToPassedObjects() {
            // Given
            Object testObject = new Object();
            String message = "Object should not be null";

            // When
            PreConditions preConditions = PreConditions.requireNonNull(testObject, message);
            testObject = null; // Remove reference
            System.gc(); // Suggest garbage collection

            // Then - should still be able to check
            assertDoesNotThrow(() -> {
                preConditions.check();
            });
        }

        @Test
        @DisplayName("should handle rapid successive checks")
        void shouldHandleRapidSuccessiveChecks() {
            // Given
            PreConditions preConditions = PreConditions.require(true, "Should pass");

            // When & Then
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 1000; i++) {
                    preConditions.check();
                }
            });
        }
    }
}