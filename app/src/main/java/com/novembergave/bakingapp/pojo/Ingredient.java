package com.novembergave.bakingapp.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Ingredient implements Parcelable {

  private float quantity;
  private String measure;
  private String ingredient;

  public Ingredient() {
  }

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

  protected Ingredient(Parcel in) {
    quantity = in.readFloat();
    measure = in.readString();
    ingredient = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeFloat(quantity);
    dest.writeString(measure);
    dest.writeString(ingredient);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
    @Override
    public Ingredient createFromParcel(Parcel in) {
      return new Ingredient(in);
    }

    @Override
    public Ingredient[] newArray(int size) {
      return new Ingredient[size];
    }
  };
}
