package com.example.tient.spa.Model;

import android.widget.ImageView;

public class DichVu {
    private int ma_dichvu;

    public String getIcon_dichvu() {
        return icon_dichvu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    private String mota;
    public void setIcon_dichvu(String icon_dichvu) {
        this.icon_dichvu = icon_dichvu;
    }

    private String icon_dichvu;
    public DichVu() {

    }

    public DichVu(int ma_dichvu, String tendichvu, String giadichvu) {
        this.ma_dichvu = ma_dichvu;
        this.tendichvu = tendichvu;
        this.giadichvu = giadichvu;
    }

    public int getMa_dichvu() {
        return ma_dichvu;
    }

    public void setMa_dichvu(int ma_dichvu) {
        this.ma_dichvu = ma_dichvu;
    }

    public String getTendichvu() {
        return tendichvu;
    }

    public void setTendichvu(String tendichvu) {
        this.tendichvu = tendichvu;
    }

    public String getGiadichvu() {
        return giadichvu;
    }

    public void setGiadichvu(String giadichvu) {
        this.giadichvu = giadichvu;
    }

    private String tendichvu;
    private String giadichvu;

    @Override
    public String toString() {
        return this.tendichvu;
    }
}
