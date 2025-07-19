package com.pedromg.bluej.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

public class CustomAssertions {

  /**
   * Asserts the scoped code throws the expected exception
   *
   * @param expectedException the expected exception
   * @param scope the code that is exprected to throw the given exception
   */
  public static void assertExceptionThrown(
      Class<? extends Exception> expectedException,
      Runnable scope) {

    Exception actualException = null;

    try {
      scope.run();
    } catch (Exception e) {
      actualException = e;
    }

    assertNotNull(actualException);
    assertTrue(expectedException.isInstance(actualException));
  }

  /**
   * Asserts the actualItems and expectedItems collections are effectively the same
   *
   * @param <T> collection items type
   * @param expectedItems the expected items collection
   * @param actualItems the actual items collection
   */
  public static <T> void assertCollectionEquals(
    Collection<T> expectedItems,
    Collection<T> actualItems) {

      assertEquals(expectedItems.size(), actualItems.size());
      for (T expectedItem : expectedItems) {
        assertTrue(actualItems.contains(expectedItem));
      }
  }

  /**
   * Asserts the collection is empty
   *
   * @param collection the collection to evaluate
   */
  public static void assertCollectionEmpty(Collection<?> collection) {
    assertEquals(0, collection.size());
  }

}
