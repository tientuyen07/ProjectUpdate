package com.example.tient.spa.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tient.spa.R;

import java.util.ArrayList;

public class MyPhamArrayAdapter extends ArrayAdapter<MyPham> {
    Activity context = null;
    ArrayList<MyPham> myArray = null;
    int layoutId;

    public MyPhamArrayAdapter(Activity context, int layoutId, ArrayList<MyPham> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    public View getView(int position, View convertView, ViewGroup paren) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        if (myArray.size() > 0 && position >= 0) {
            final TextView viewTen = (TextView) convertView.findViewById(R.id.text_view10);
            final TextView viewGia = (TextView) convertView.findViewById(R.id.text_view11);
            final TextView viewXuatXu = (TextView) convertView.findViewById(R.id.text_view12);
            final MyPham myPham = myArray.get(position);
            viewTen.setText(myPham.getTenSanPham() + "");
            viewGia.setText("Giá: " + myPham.getGiaMyPham());
            viewXuatXu.setText("Xuất xứ: " + myPham.getXuatxu());
        }
        return convertView;
    }
}
