package com.tiet.campusfood;

public class RestrauntRVModel {

    private int image;
    private String name,location,rating,cost;

    public RestrauntRVModel(int image, String name, String location, String rating, String cost) {
        this.image = image;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.cost = cost;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }


}
