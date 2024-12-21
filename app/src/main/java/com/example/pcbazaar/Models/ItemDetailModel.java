package com.example.pcbazaar.Models;

public class ItemDetailModel {
    private String itemId, itemName, itemDescription, itemRating, itemPrice, itemComments, itemImageLink, itemTotalViewer;

    public ItemDetailModel() {
    }

    public ItemDetailModel(String itemId, String itemName, String itemDescription, String itemRating, String itemPrice, String itemComments, String itemImageLink, String itemTotalViewer) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemRating = itemRating;
        this.itemPrice = itemPrice;
        this.itemComments = itemComments;
        this.itemImageLink = itemImageLink;
        this.itemTotalViewer = itemTotalViewer;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
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

    public String getItemImageLink() {
        return itemImageLink;
    }

    public void setItemImageLink(String itemImageLink) {
        this.itemImageLink = itemImageLink;
    }

    public String getItemTotalViewer() {
        return itemTotalViewer;
    }

    public void setItemTotalViewer(String itemTotalViewer) {
        this.itemTotalViewer = itemTotalViewer;
    }
}