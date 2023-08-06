package com.tiet.campusfood;

import android.graphics.Bitmap;
import android.net.Uri;

public class RestrauntRVModel {

    private String image;
    private String name,location,rating,cost,documentID;

    public RestrauntRVModel(String image, String name, String location, String rating, String cost, String documentID) {
        this.image = image;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.cost = cost;
        this.documentID = documentID;
    }

    public RestrauntRVModel() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
