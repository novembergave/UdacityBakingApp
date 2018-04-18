package com.novembergave.bakingapp.pojo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Step implements Parcelable {

  private long id;
  private String shortDescription;
  private String description;
  private String videoURL;
  private String thumbnailURL;

  public Step() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVideoURL() {
    return videoURL;
  }

  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Step step = (Step) o;
    return id == step.id &&
        Objects.equals(shortDescription, step.shortDescription) &&
        Objects.equals(description, step.description) &&
        Objects.equals(videoURL, step.videoURL) &&
        Objects.equals(thumbnailURL, step.thumbnailURL);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, shortDescription, description, videoURL, thumbnailURL);
  }

  @Override
  public String toString() {
    return "Step{" +
        "id=" + id +
        ", shortDescription='" + shortDescription + '\'' +
        ", description='" + description + '\'' +
        ", videoURL='" + videoURL + '\'' +
        ", thumbnailURL='" + thumbnailURL + '\'' +
        '}';
  }

  protected Step(Parcel in) {
    id = in.readLong();
    shortDescription = in.readString();
    description = in.readString();
    videoURL = in.readString();
    thumbnailURL = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(shortDescription);
    dest.writeString(description);
    dest.writeString(videoURL);
    dest.writeString(thumbnailURL);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
    @Override
    public Step createFromParcel(Parcel in) {
      return new Step(in);
    }

    @Override
    public Step[] newArray(int size) {
      return new Step[size];
    }
  };
}
