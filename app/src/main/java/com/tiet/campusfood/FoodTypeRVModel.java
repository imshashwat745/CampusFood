package com.tiet.campusfood;

public class FoodTypeRVModel {
//    Detais class for horizontal home rec view
    private int image;
    private String text;

    public int getImage() {
        return image;
    }

    public FoodTypeRVModel(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
