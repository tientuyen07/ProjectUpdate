package com.example.tient.spa.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tient.spa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DichVuArrayAdapter extends ArrayAdapter<DichVu> {
    Activity context = null;
    ArrayList<DichVu> myArray = null;
    int layoutId;

    public DichVuArrayAdapter(Activity context, int layoutId, ArrayList<DichVu> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    public View getView(int position, View convertView, ViewGroup paren) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dich_vu_custom_layout, paren, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTenDichVu = (TextView) convertView.findViewById(R.id.tv_ten_dich_vu);
            viewHolder.tvGiaDichVu = (TextView) convertView.findViewById(R.id.tv_gia_dich_vu);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_dich_vu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DichVu dichVu = myArray.get(position);
        viewHolder.tvTenDichVu.setText(dichVu.getTendichvu());
        viewHolder.tvGiaDichVu.setText(dichVu.getGiadichvu() + " VNĐ");
        if (!dichVu.getIcon_dichvu().equals("")) {
            Picasso.with(context).load(dichVu.getIcon_dichvu()).resize(220, 220).into(viewHolder.imageView);
        }

        return convertView;
    }

    public class ViewHolder {
        TextView tvTenDichVu, tvGiaDichVu;
        ImageView imageView;
    }


}
