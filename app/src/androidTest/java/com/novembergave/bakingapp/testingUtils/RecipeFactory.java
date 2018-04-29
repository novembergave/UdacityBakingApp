package com.novembergave.bakingapp.testingUtils;

import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeFactory {

  public static Recipe generateDummyRecipe(int numberOfIngredients, int numberOfSteps) {
    Recipe recipe = new Recipe();
    recipe.setIngredients(generateDummyIngredientList(numberOfIngredients));
    recipe.setSteps(generateDummyStepsList(numberOfSteps));
    return recipe;
  }

  private static List<Ingredient> generateDummyIngredientList(int numberOfIngredients) {
    List<Ingredient> ingredients = new ArrayList<>();
    for (int i = 0; i < numberOfIngredients; i++) {
      ingredients.add(createIngredient(i));
    }
    return ingredients;
  }

  private static Ingredient createIngredient(int i) {
    Ingredient ingredient = new Ingredient();
    ingredient.setIngredient("Random Ingredient");
    ingredient.setMeasure("cup");
    ingredient.setQuantity(i);
    return ingredient;
  }

  private static List<Step> generateDummyStepsList(int numberOfSteps) {
    List<Step> steps = new ArrayList<>();
    for (int i = 0; i < numberOfSteps; i++) {
      steps.add(createStep(i));
    }
    return steps;
  }

  private static Step createStep(int i) {
    Step step = new Step();
    step.setId(i);
    String randomDescription = String.valueOf(i+1) + ". " + UUID.randomUUID().toString();
    step.setDescription(randomDescription);
    step.setShortDescription(UUID.randomUUID().toString());
    step.setThumbnailURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4");
    return step;
  }
}
