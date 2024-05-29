package com.example.pcbazaar.Models;

public class UserModel {
    String Name,Email,MobileNumber,Address,PostalCode,BirthDate;

    public UserModel() {
    }

    public UserModel(String name, String email, String mobileNumber, String address, String postalCode, String birthDate) {
        Name = name;
        Email = email;
        MobileNumber = mobileNumber;
        Address = address;
        PostalCode = postalCode;
        BirthDate = birthDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }
}
