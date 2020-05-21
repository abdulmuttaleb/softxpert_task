package com.ahmad.softxperttask.model;

public class Car {
    String brand;
    String imageUrl;
    Boolean isUsed;
    String constructionYear;

    public Car(String brand, String imageUrl, Boolean isUsed, String constructionYear) {
        this.brand = brand;
        this.imageUrl = imageUrl;
        this.isUsed = isUsed;
        this.constructionYear = constructionYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        this.constructionYear = constructionYear;
    }
}
