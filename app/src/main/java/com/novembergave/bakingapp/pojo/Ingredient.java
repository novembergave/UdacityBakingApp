package com.novembergave.bakingapp.pojo;


import java.util.Objects;

public class Ingredient {

  private float quantity;
  private String measure;
  private String ingredient;

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public String getIngredient() {
    return ingredient;
  }

  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ingredient that = (Ingredient) o;
    return Float.compare(that.quantity, quantity) == 0 &&
        Objects.equals(measure, that.measure) &&
        Objects.equals(ingredient, that.ingredient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity, measure, ingredient);
  }

  @Override
  public String toString() {
    return "Ingredient{" +
        "quantity=" + quantity +
        ", measure='" + measure + '\'' +
        ", ingredient='" + ingredient + '\'' +
        '}';
  }
}
