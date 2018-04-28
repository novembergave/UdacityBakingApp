package com.novembergave.bakingapp.utils;


import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

public class FormatUtils {

  public static String removeStepNumber(String stringWithStepInFront, long stepId) {
    String stepString = String.valueOf(stepId);
    if (containsStepNumberInFront(stringWithStepInFront)) {
      int positionToSplitAt = stepString.length() + 2; // dot and space
      return stringWithStepInFront.substring(positionToSplitAt);
    } else {
      // fallback to the original string
      return stringWithStepInFront;
    }
  }

  @VisibleForTesting
  static boolean containsStepNumberInFront(String stringWithStepInFront) {
    String substring = stringWithStepInFront.substring(0, 1);
    return TextUtils.isDigitsOnly(substring);
  }

  public static boolean isEmpty(String string) {
    if (string == null) {
      return true;
    }
    return string.isEmpty();
  }
}
