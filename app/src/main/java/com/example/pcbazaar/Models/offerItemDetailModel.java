package com.example.pcbazaar.Models;

import android.net.Uri;

public class offerItemDetailModel {
    Uri bannerUrl;
    String discount, itemName;

    public offerItemDetailModel() {
    }

    public offerItemDetailModel(Uri bannerUrl, String discount, String itemName) {
        this.bannerUrl = bannerUrl;
        this.discount = discount;
        this.itemName = itemName;
    }

    public Uri getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(Uri bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
