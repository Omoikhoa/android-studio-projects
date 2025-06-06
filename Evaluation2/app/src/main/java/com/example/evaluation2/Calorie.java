package com.example.evaluation2;

import java.io.Serializable;

public class Calorie implements Serializable {
    private String gender;
    private String weight;
    private String height;
    private String age;
    private String activityLevel;

    public Calorie(String gender, String weight, String height, String age, String activityLevel) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.activityLevel = activityLevel;
    }

    // Getters and setters
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
}