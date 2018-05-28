package com.example.tient.spa.Model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NhanVienArrayAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    ArrayList<NhanVien> arrNhanVien = null;

    public NhanVienArrayAdapter(Context context, int resourceId, ArrayList<NhanVien> arr) {
        super(context, resourceId);
        this.context = context;
        this.arrNhanVien = arr;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return arrNhanVien.size();
    }
}
