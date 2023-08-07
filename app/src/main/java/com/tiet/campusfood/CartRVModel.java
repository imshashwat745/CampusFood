package com.tiet.campusfood;

public class CartRVModel {
    private String image, imageID;

    private String name,quantity,price,total;

    public CartRVModel(String image, String imageID, String name, String quantity, String price, String total) {
        this.image = image;
        this.imageID = imageID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total=String.valueOf(Integer.parseInt(price)*Integer.parseInt(quantity));
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public CartRVModel() {
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public void setTotal() {
        this.total=String.valueOf(Integer.parseInt(price)*Integer.parseInt(quantity));
    }
}
