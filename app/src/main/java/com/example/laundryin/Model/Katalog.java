package com.example.laundryin.Model;

public class Katalog {
        private String Name, Image, Description, Price, Discount, KatalogID;

        public Katalog() {
        }

    public Katalog(String name, String image, String description, String price, String discount, String katalogId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        KatalogID = katalogId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) { Discount = discount; }

    public String getKatalogId() {
        return KatalogID;
    }

    public void setKatalogId(String katalogId) {
        KatalogID = katalogId;
    }
}
