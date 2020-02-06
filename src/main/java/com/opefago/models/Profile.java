package com.opefago.models;

import java.util.Objects;

public class Profile {
    public Profile(long id, String hairColor, String height, String weight) {
        this.id = id;
        this.hairColor = hairColor;
        this.height = height;
        this.weight = weight;
    }

    private long id;
    private String hairColor;
    private String height;
    private String weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id &&
                Objects.equals(hairColor, profile.hairColor) &&
                Objects.equals(height, profile.height) &&
                Objects.equals(weight, profile.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hairColor, height, weight);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", hairColor='" + hairColor + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
