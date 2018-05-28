package com.example.tient.spa.Model;

public class NhanVien {
    private int id_nhanvien;
    private String luong;
    private String trangthai_nv;
    private String loainv;

    public NhanVien() {
        this.id_nhanvien = 0;
        this.hoten = "";
        this.loainv = "";
        this.luong = "";
        this.trangthai_nv = "";
    }

    public NhanVien(int id_nhanvien, String hoten) {
        this.id_nhanvien = id_nhanvien;
        this.hoten= hoten;
    }

    public String getTenNhanVien() {
        return hoten;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.hoten = tenNhanVien;
    }

    private String hoten;


    public int getId_nhanvien() {
        return id_nhanvien;
    }

    public void setId_nhanvien(int id_nhanvien) {
        this.id_nhanvien = id_nhanvien;
    }

    public String getLuong() {
        return luong;
    }

    public void setLuong(String luong) {
        this.luong = luong;
    }

    public String getTrangthai_nv() {
        return trangthai_nv;
    }

    public void setTrangthai_nv(String trangthai_nv) {
        this.trangthai_nv = trangthai_nv;
    }

    public String getLoainv() {
        return loainv;
    }

    public void setLoainv(String loainv) {
        this.loainv = loainv;
    }

    @Override
    public String toString() {
        return this.hoten;
    }
}
