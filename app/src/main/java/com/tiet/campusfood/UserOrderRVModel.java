package com.tiet.campusfood;

public class UserOrderRVModel {

    private String name,description,total;

    public UserOrderRVModel(String name, String description, String total) {
        this.name = name;
        this.description = description;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
