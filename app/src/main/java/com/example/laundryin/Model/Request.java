package com.example.laundryin.Model;

import java.util.List;

public class Request {

    private String phone;
    private String name;
    private String alamat;
    private String total;
    private String status;
    private List<Pesan> katalog;

    public Request() {

    }

    public Request(String phone, String name, String alamat, String total, List<Pesan> katalog) {
        this.phone = phone;
        this.name = name;
        this.alamat = alamat;
        this.total = total;
        this.katalog = katalog;
        this.status = "0";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Pesan> getKatalog() {
        return katalog;
    }

    public void setKatalog(List<Pesan> katalog) { this.katalog = katalog; }
}
