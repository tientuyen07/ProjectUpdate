package com.example.tient.spa.Model;

import java.util.Date;

public class DatLich {
    public int getId_lichhen() {
        return id_lichhen;
    }

    public void setId_lichhen(int id_lichhen) {
        this.id_lichhen = id_lichhen;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    private String hoten;
    public String getTendichvu() {
        return tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        this.tendichvu = tendichvu;
    }

    private String tendichvu;
    private int id_lichhen;
    private String ngayhen;
    private String khunggio;
    private String sdt_datlich;
    private String hoten_datlich;
    private int tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu;
    private int tbl_nhanvien_id_nhanvien;

    public DatLich() {

    }

    public DatLich(String ngayhen, String khunggio, String sdt_datlich, String hoten_datlich, int tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu, int tbl_nhanvien_id_nhanvien, String tbl_dichvu_has_tbl_phong_tbl_phong_maphong, int xacnhan) {

        this.ngayhen = ngayhen;
        this.khunggio = khunggio;
        this.sdt_datlich = sdt_datlich;
        this.hoten_datlich = hoten_datlich;
        this.tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu = tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu;
        this.tbl_nhanvien_id_nhanvien = tbl_nhanvien_id_nhanvien;
        this.tbl_dichvu_has_tbl_phong_tbl_phong_maphong = tbl_dichvu_has_tbl_phong_tbl_phong_maphong;
        this.xacnhan = xacnhan;
    }

    public String getNgayhen() {
        return ngayhen;
    }

    public void setNgayhen(String ngayhen) {
        this.ngayhen = ngayhen;
    }

    public String getKhunggio() {
        return khunggio;
    }

    public void setKhunggio(String khunggio) {
        this.khunggio = khunggio;
    }

    public String getSdt_datlich() {
        return sdt_datlich;
    }

    public void setSdt_datlich(String sdt_datlich) {
        this.sdt_datlich = sdt_datlich;
    }

    public String getHoten_datlich() {
        return hoten_datlich;
    }

    public void setHoten_datlich(String hoten_datlich) {
        this.hoten_datlich = hoten_datlich;
    }

    public int getTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu() {
        return tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu;
    }

    public void setTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu(int tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu) {
        this.tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu = tbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu;
    }

    public int getTbl_nhanvien_id_nhanvien() {
        return tbl_nhanvien_id_nhanvien;
    }

    public void setTbl_nhanvien_id_nhanvien(int tbl_nhanvien_id_nhanvien) {
        this.tbl_nhanvien_id_nhanvien = tbl_nhanvien_id_nhanvien;
    }

    public String getTbl_dichvu_has_tbl_phong_tbl_phong_maphong() {
        return tbl_dichvu_has_tbl_phong_tbl_phong_maphong;
    }

    public void setTbl_dichvu_has_tbl_phong_tbl_phong_maphong(String tbl_dichvu_has_tbl_phong_tbl_phong_maphong) {
        this.tbl_dichvu_has_tbl_phong_tbl_phong_maphong = tbl_dichvu_has_tbl_phong_tbl_phong_maphong;
    }

    public int getXacnhan() {
        return xacnhan;
    }

    public void setXacnhan(int xacnhan) {
        this.xacnhan = xacnhan;
    }

    private String tbl_dichvu_has_tbl_phong_tbl_phong_maphong;
    private int xacnhan;

}
