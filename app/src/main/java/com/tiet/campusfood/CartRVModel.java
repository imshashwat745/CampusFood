package com.tiet.campusfood;

import android.content.Intent;

public class CartRVModel {
    private int image;
    private String name,quantity,price,total;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
//        On quantity change this function is called from parent so update total too
        this.total=String.valueOf(Integer.parseInt(price)*Integer.parseInt(quantity));
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public CartRVModel(int image, String name, String quantity, String price) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total=String.valueOf(Integer.parseInt(price)*Integer.parseInt(quantity));
    }

}
