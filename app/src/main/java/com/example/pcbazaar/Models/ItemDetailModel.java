package com.example.pcbazaar.Models;

public class ItemDetailModel {
    private String itemId, itemName, itemDescription, itemRating, itemPrice, itemComments, itemImagelink;

    public ItemDetailModel() {
    }

    public ItemDetailModel(String itemId, String itemName, String itemDescription, String itemRating, String itemPrice, String itemComments, String itemImagelink) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemRating = itemRating;
        this.itemPrice = itemPrice;
        this.itemComments = itemComments;
        this.itemImagelink = itemImagelink;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemRating() {
        return itemRating;
    }

    public void setItemRating(String itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemComments() {
        return itemComments;
    }

    public void setItemComments(String itemComments) {
        this.itemComments = itemComments;
    }

    public String getItemImagelink() {
        return itemImagelink;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemImagelink(String itemImagelink) {
        this.itemImagelink = itemImagelink;
    }
}