package com.tiet.campusfood;

public class UserOrderRVModel {

    private String name,description,total,restrauntId,orderId;
    private boolean isDelivered;

    public UserOrderRVModel(String name, String description, String total, boolean isDelivered) {
        this.name = name;
        this.description = description;
        this.total = total;
        this.isDelivered = isDelivered;
    }

    public UserOrderRVModel(String name, String description, String total, String restrauntId, boolean isDelivered) {
        this.name = name;
        this.description = description;
        this.total = total;
        this.restrauntId = restrauntId;
        this.isDelivered = isDelivered;
    }

    public UserOrderRVModel() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRestrauntId() {
        return restrauntId;
    }

    public void setRestrauntId(String restrauntId) {
        this.restrauntId = restrauntId;
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

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }
}
