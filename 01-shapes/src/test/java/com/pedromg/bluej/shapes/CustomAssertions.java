package com.pedromg.bluej.shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

public class CustomAssertions {

  public static void assertExceptionThrown(
      Class<? extends Throwable> expectedException,
      Runnable executable) {
    try {
      executable.run();
    } catch (Exception actualException) {
      assertTrue(expectedException.isInstance(actualException));
    }
  }

  public static <T> void assertCollectionEquals(
    Collection<T> expectedItems,
    Collection<T> actualItems) {

      assertEquals(expectedItems.size(), actualItems.size());
      for (T expectedItem : expectedItems) {
        assertTrue(actualItems.contains(expectedItem));
      }
  }

  public static void assertCollectionEmpty(Collection<?> collection) {
    assertEquals(0, collection.size());
  }

}
