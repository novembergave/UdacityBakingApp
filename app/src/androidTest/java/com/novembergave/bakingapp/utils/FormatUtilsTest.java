package com.novembergave.bakingapp.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormatUtilsTest {

  @Test
  public void testRemoveStepNumber() {
    String firstString = "9. Cut and serve.";
    String firstExpected = "Cut and serve.";
    String firstFormatted = FormatUtils.removeStepNumber(firstString, 9);

    String secondString = "9. Bake the cheesecake on a middle rack of the oven above the roasting pan full of water for 50 minutes. ";
    String secondExpected = "Bake the cheesecake on a middle rack of the oven above the roasting pan full of water for 50 minutes. ";
    String secondFormatted = FormatUtils.removeStepNumber(secondString, 9);

    String thirdString = "12. Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!";
    String thirdExpected = "Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!";
    String thirdFormatted = FormatUtils.removeStepNumber(thirdString, 12);

    assertEquals(firstExpected, firstFormatted);
    assertEquals(secondExpected, secondFormatted);
    assertEquals(thirdExpected, thirdFormatted);
  }

  @Test
  public void testContainsStepNumberInFront() {
    String firstString = "9. Cut and serve.";
    boolean firstFormatted = FormatUtils.containsStepNumberInFront(firstString);

    String secondString = "3. Fill a large roasting pan with a few inches of hot water and place it on the bottom rack of the oven.";
    boolean secondFormatted = FormatUtils.containsStepNumberInFront(secondString);

    String thirdString = "Cover the cheesecake with plastic wrap, not allowing the plastic to touch the top of the cake, and refrigerate it for at least 8 hours. Then it's ready to serve!";
    boolean thirdFormatted = FormatUtils.containsStepNumberInFront(thirdString);

    assertTrue(firstFormatted);
    assertTrue(secondFormatted);
    assertFalse(thirdFormatted);
  }

  @Test
  public void testIsEmpty() {
    String firstString = "";
    boolean firstFormatted = FormatUtils.isEmpty(firstString);

    boolean secondFormatted = FormatUtils.isEmpty(null);

    String thirdString = "9. 1. Preheat the oven to 350\\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. ";
    boolean thirdFormatted = FormatUtils.isEmpty(thirdString);

    assertTrue(firstFormatted);
    assertTrue(secondFormatted);
    assertFalse(thirdFormatted);
  }
}
