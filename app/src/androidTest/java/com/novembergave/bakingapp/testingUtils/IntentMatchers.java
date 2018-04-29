package com.novembergave.bakingapp.testingUtils;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import org.hamcrest.Matcher;

public class IntentMatchers {

  @NonNull
  public static <T> Matcher<Intent> activityOf(Class<T> type) {
    return hasComponent(new ComponentName(getTargetContext(), type));
  }
}
