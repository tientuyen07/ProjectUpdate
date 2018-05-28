package com.example.tient.spa.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tient.spa.R;

import java.util.List;

public class CustomGridLayout extends BaseAdapter {
    private List<KhungGio> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridLayout(Context mContext, List<KhungGio> listData) {
        this.context = mContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_custom, null);
            holder = new ViewHolder();
            holder.gioView = (TextView) convertView.findViewById(R.id.txt_kg);
            holder.trangthaiNV = (TextView) convertView.findViewById(R.id.txt_trang_thai_nv);
            holder.trangthaiPhong = (TextView) convertView.findViewById(R.id.txt_trang_thai_phong);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        KhungGio khungGio = this.listData.get(position);
        holder.gioView.setText(khungGio.getKhunggio());
        holder.trangthaiNV.setText(khungGio.getTrangthaiNV());
        holder.trangthaiPhong.setText(khungGio.getTrangthaiPhong());

        return convertView;
    }

    static class ViewHolder {
        TextView gioView;
        TextView trangthaiNV;
        TextView trangthaiPhong;
    }
}
