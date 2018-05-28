package com.example.tient.spa.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tient.spa.R;

import java.util.ArrayList;

public class LichSuArrayAdapter extends ArrayAdapter<DatLich> {
    Activity context = null;
    ArrayList<DatLich> arrayList = null;
    int layoutId;

    public LichSuArrayAdapter(Activity context, int layoutId, ArrayList<DatLich> arrayList) {
        super(context, layoutId, arrayList);
        this.context = context;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    public View getView(int position, View convertView, ViewGroup pare) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_custom_lich_su, pare, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNgayHen = (TextView) convertView.findViewById(R.id.tv_ngay_hen);
            viewHolder.tvGioHen = (TextView) convertView.findViewById(R.id.tv_khung_gio_hen);
            viewHolder.tvDichVu = (TextView) convertView.findViewById(R.id.tv_dich_vu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DatLich datLich = arrayList.get(position);
        viewHolder.tvNgayHen.setText(datLich.getNgayhen());
        viewHolder.tvGioHen.setText(datLich.getKhunggio());
        viewHolder.tvDichVu.setText(datLich.getTendichvu());
        return convertView;
    }

    public class ViewHolder {
        TextView tvNgayHen, tvGioHen, tvDichVu;
    }

}
