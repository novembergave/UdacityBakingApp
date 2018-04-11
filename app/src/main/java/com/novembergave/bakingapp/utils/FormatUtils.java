package com.novembergave.bakingapp.utils;


import android.text.TextUtils;

public class FormatUtils {

  public static String removeStepNumber(String stringWithStepInFront) {
    String[] splitString = stringWithStepInFront.split("\\d*\\.\\s");
    // we're expecting 2 strings, one with the number, and the rest the remainder.
    if (splitString.length == 2) {
      return splitString[1];
    } else {
      // fallback to the original string
      return stringWithStepInFront;
    }
  }

  public static String removeStepNumber(String stringWithStepInFront, long stepId) {
    long actualStep = stepId+1;
    String stepString = String.valueOf(actualStep);
    if (containsStepNumberInFront(stringWithStepInFront)) {
      int positionToSplitAt = stepString.length() + 2; // dot and space
      return stringWithStepInFront.substring(positionToSplitAt);
    } else {
      // fallback to the original string
      return stringWithStepInFront;
    }
  }

  private static boolean containsStepNumberInFront(String stringWithStepInFront) {
    String substring = stringWithStepInFront.substring(0, 1);
    return TextUtils.isDigitsOnly(substring);
  }
}
