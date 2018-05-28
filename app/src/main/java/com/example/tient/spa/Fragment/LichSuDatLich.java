package com.example.tient.spa.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tient.spa.Model.DatLich;
import com.example.tient.spa.Model.LichSuArrayAdapter;
import com.example.tient.spa.R;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuDatLich extends AppCompatActivity {
    @BindView(R.id.lv_qllh)
    ListView lvQLLH;
    @BindView(R.id.btn_xem_lich_su)
    Button btnXemLichSu;
    @BindView(R.id.edit_lich_su_sdt)
    EditText editLichSuSdt;

    ArrayList<DatLich> arrLichSu = new ArrayList<DatLich>();
    LichSuArrayAdapter adapter = null;
    Context mContext;
    BaseApiService mApiService;
    DatLich luuDatLich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lich_hen);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = LichSuDatLich.this;
        mApiService = UtilsApi.getApiService();

        arrLichSu = new ArrayList<DatLich>();
        adapter = new LichSuArrayAdapter(this, R.layout.layout_custom_lich_su, arrLichSu);
        lvQLLH.setAdapter(adapter);

        lvQLLH.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                luuDatLich = arrLichSu.get(position);
                return false;
            }
        });

        registerForContextMenu(lvQLLH);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listview_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_xemlh:
                Intent intent = new Intent(this, DetailsLichHen.class);
                Bundle bundle = new Bundle();
                bundle.putString("TenDichVu", luuDatLich.getTendichvu() + "");
                bundle.putString("NgayHen", luuDatLich.getNgayhen() + "");
                bundle.putString("KhungGio", luuDatLich.getKhunggio() + "");
                bundle.putString("Phong", luuDatLich.getTbl_dichvu_has_tbl_phong_tbl_phong_maphong() + "");
                bundle.putString("TenKhach", luuDatLich.getHoten_datlich() + "");
                bundle.putString("SDT", luuDatLich.getSdt_datlich() + "");
                bundle.putString("TenNhanVien", luuDatLich.getHoten() + "");
                intent.putExtra("BundleLichHen", bundle);
                startActivity(intent);
                break;
            case R.id.item_sualh:
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 0);
                Date day = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String now = dateFormat.format(day);
                String ngay_lichhen = luuDatLich.getNgayhen() + "";
                if (now.compareTo(ngay_lichhen) <= 0) {
                    Intent intent1 = new Intent(this, DatLichActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("FlagUpdate", true);
                    bundle1.putString("Ngay", luuDatLich.getNgayhen() + "");

                    Log.i("DEBUG", luuDatLich.getId_lichhen() + "  " + luuDatLich.getTbl_nhanvien_id_nhanvien() + "  " + luuDatLich.getTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu());
                    bundle1.putInt("ID", luuDatLich.getId_lichhen());
                    bundle1.putString("Gio", luuDatLich.getKhunggio() + "");
                    bundle1.putString("KhungGio", luuDatLich.getKhunggio() + "");
                    bundle1.putInt("IDDV", luuDatLich.getTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu());
                    bundle1.putString("DichVu", luuDatLich.getTendichvu() + "");
                    bundle1.putInt("IDNV", luuDatLich.getTbl_nhanvien_id_nhanvien());
                    bundle1.putString("NhanVien", luuDatLich.getHoten() + "");
                    bundle1.putString("Phong", luuDatLich.getTbl_dichvu_has_tbl_phong_tbl_phong_maphong() + "");
                    bundle1.putString("HoTen", luuDatLich.getHoten_datlich() + "");
                    bundle1.putString("SDT", luuDatLich.getSdt_datlich() + "");
                    intent1.putExtra("BundleFlagUpdate", bundle1);
                    startActivity(intent1);
                }
                if (now.compareTo(ngay_lichhen) > 0) {
                    Toast.makeText(this, "Lịch hẹn không thể thay đổi! Xin vui lòng đặt lịch mới!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return super.onContextItemSelected(item);
    }

    @OnClick(R.id.btn_xem_lich_su)
    public void XuLyNhap() {
        if (editLichSuSdt.getText().toString().equals("")) {
            Toast.makeText(this, "Xin vui lòng nhập số điện thoại!!!", Toast.LENGTH_SHORT).show();
        } else {
            arrLichSu.clear();
            mApiService.LichSuDatLich(editLichSuSdt.getText().toString()).enqueue(new Callback<List<DatLich>>() {
                @Override
                public void onResponse(Call<List<DatLich>> call, Response<List<DatLich>> response) {
                    List<DatLich> list = response.body();
                    for (int i = 0; i < list.size(); i++) {
                        DatLich datLich = new DatLich();
                        datLich.setKhunggio(list.get(i).getKhunggio() + "");
                        datLich.setNgayhen(list.get(i).getNgayhen() + "");
                        datLich.setTendichvu(list.get(i).getTendichvu() + "");
                        datLich.setId_lichhen(list.get(i).getId_lichhen());
                        datLich.setTbl_nhanvien_id_nhanvien(list.get(i).getTbl_nhanvien_id_nhanvien());
                        datLich.setSdt_datlich(list.get(i).getSdt_datlich() + "");
                        datLich.setHoten(list.get(i).getHoten() + "");
                        datLich.setTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu(list.get(i).getTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu());
                        datLich.setHoten_datlich(list.get(i).getHoten_datlich() + "");
                        datLich.setTbl_dichvu_has_tbl_phong_tbl_phong_maphong(list.get(i).getTbl_dichvu_has_tbl_phong_tbl_phong_maphong() + "");
                        arrLichSu.add(datLich);
                        adapter.notifyDataSetChanged();
                    }
                    if (list.size() == 0) {
                        Toast.makeText(mContext, "Số điện thoại sai hoặc bạn chưa từng đặt lịch", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<DatLich>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
