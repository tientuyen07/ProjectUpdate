package com.example.tient.spa.Model;

public class KhungGio {
    public String getKhunggio() {
        return khunggio;
    }

    public void setKhunggio(String khunggio) {
        this.khunggio = khunggio;
    }

    private String khunggio;


    public String getTrangthaiNV() {
        return trangthaiNV;
    }

    public void setTrangthaiNV(String trangthaiNV) {
        this.trangthaiNV = trangthaiNV;
    }

    public String getTrangthaiPhong() {
        return trangthaiPhong;
    }

    public void setTrangthaiPhong(String trangthaiPhong) {
        this.trangthaiPhong = trangthaiPhong;
    }

    private String trangthaiNV;
    private String trangthaiPhong;

    public KhungGio(String gio, String trangthaiNV, String trangthaiPhong) {
        this.khunggio = gio;
        this.trangthaiNV = trangthaiNV;
        this.trangthaiPhong = trangthaiPhong;
    }
}
