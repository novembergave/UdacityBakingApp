package com.novembergave.bakingapp.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe implements Parcelable {

  private long id;
  private String name;
  private List<Ingredient> ingredients;
  private List<Step> steps;
  private int servings;
  private String image;

  public Recipe() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public int getServings() {
    return servings;
  }

  public void setServings(int servings) {
    this.servings = servings;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Recipe recipe = (Recipe) o;
    return id == recipe.id &&
        servings == recipe.servings &&
        Objects.equals(name, recipe.name) &&
        Objects.equals(ingredients, recipe.ingredients) &&
        Objects.equals(steps, recipe.steps) &&
        Objects.equals(image, recipe.image);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, ingredients, steps, servings, image);
  }

  @Override
  public String toString() {
    return "Recipe{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", ingredients=" + ingredients +
        ", steps=" + steps +
        ", servings=" + servings +
        ", image='" + image + '\'' +
        '}';
  }

  protected Recipe(Parcel in) {
    id = in.readLong();
    name = in.readString();
    if (in.readByte() == 0x01) {
      ingredients = new ArrayList<Ingredient>();
      in.readList(ingredients, Ingredient.class.getClassLoader());
    } else {
      ingredients = null;
    }
    if (in.readByte() == 0x01) {
      steps = new ArrayList<Step>();
      in.readList(steps, Step.class.getClassLoader());
    } else {
      steps = null;
    }
    servings = in.readInt();
    image = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(name);
    if (ingredients == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(ingredients);
    }
    if (steps == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(steps);
    }
    dest.writeInt(servings);
    dest.writeString(image);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
    @Override
    public Recipe createFromParcel(Parcel in) {
      return new Recipe(in);
    }

    @Override
    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };
}
