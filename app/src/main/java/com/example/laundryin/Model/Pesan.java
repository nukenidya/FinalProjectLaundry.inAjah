package com.example.laundryin.Model;

public class Pesan {
    private String ProdukId;
    private String ProdukName;
    private String Quantity;
    private String Harga;
    private String Discount;

    public Pesan() {
    }

    public Pesan(String produkId, String produkName, String quantity, String harga, String discount) {
        ProdukId = produkId;
        ProdukName = produkName;
        Quantity = quantity;
        Harga = harga;
        Discount = discount;
    }

    public String getProdukId() {
        return ProdukId;
    }

    public void setProdukId(String produkId) {
        ProdukId = produkId;
    }

    public String getProdukName() {
        return ProdukName;
    }

    public void setProdukName(String produkName) {
        ProdukName = produkName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) { Harga = harga; }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
