package com.example.tient.spa.Model;

public class Phong {
    public int getTbl_dichvu_ma_dichvu() {
        return tbl_dichvu_ma_dichvu;
    }

    public void setTbl_dichvu_ma_dichvu(int tbl_dichvu_ma_dichvu) {
        this.tbl_dichvu_ma_dichvu = tbl_dichvu_ma_dichvu;
    }

    private int tbl_dichvu_ma_dichvu;

    public Phong(int tbl_dichvu_ma_dichvu, String tbl_phong_maphong) {
        this.tbl_dichvu_ma_dichvu = tbl_dichvu_ma_dichvu;
        this.tbl_phong_maphong = tbl_phong_maphong;
    }

    public String getTbl_phong_maphong() {
        return tbl_phong_maphong;
    }

    public void setTbl_phong_maphong(String tbl_phong_maphong) {
        this.tbl_phong_maphong = tbl_phong_maphong;
    }

    private String tbl_phong_maphong;

    @Override
    public String toString() {
        return this.tbl_phong_maphong;
    }

}
