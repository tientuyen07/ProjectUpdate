package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tient.spa.Model.CustomGridLayout;
import com.example.tient.spa.Model.DatLich;
import com.example.tient.spa.Model.DichVu;
import com.example.tient.spa.Model.KhungGio;
import com.example.tient.spa.Model.NhanVien;
import com.example.tient.spa.Model.Phong;
import com.example.tient.spa.R;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatLichActivity extends AppCompatActivity {
    @BindView(R.id.spinner_dv)
    Spinner spinner_dv;
    @BindView(R.id.spinner_nv)
    Spinner spinner_nv;
    @BindView(R.id.spinner_phong)
    Spinner spinner_phong;
    @BindView(R.id.spinner_ngayhen)
    Spinner spinner_ngayhen;
    @BindView(R.id.edit_sdt)
    EditText editSdt;
    @BindView(R.id.edit_hvt)
    EditText editHvt;
    @BindView(R.id.btn_datlich)
    Button btnDatLich;

    ArrayList<DichVu> arrDichVu = new ArrayList<DichVu>();
    ArrayList<NhanVien> arrNhanVien = new ArrayList<NhanVien>();
    ArrayList<KhungGio> khunggio_details = null;
    CustomGridLayout adapterGridView = null;
    //    DichVuArrayAdapter adapter = null;
    Context mContext;
    DatLich lichhen = new DatLich();
    BaseApiService mApiService;
    boolean flag_capnhat = false;
    boolean flag[] = {false, false, false, false, false, false, false, false, false};
    boolean flag_selected_kg = true;

    // Các tham số trong bảng lịch hẹn, sử dụng khi chọn chức năng cập nhật, get dữ liệu từ Bundle
    String d_v;
    String n_v;
    String p;
    int ID;
    int IDDV;
    int IDNV;
    String k_g;

    boolean flag_update_success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = DatLichActivity.this;
        mApiService = UtilsApi.getApiService();
        Bundle bundle = getIntent().getBundleExtra("BundleFlagUpdate");
        flag_capnhat = bundle.getBoolean("FlagUpdate");
        String NgayUpdate = bundle.getString("Ngay");
        d_v = bundle.getString("DichVu");
        n_v = bundle.getString("NhanVien");
        p = bundle.getString("Phong");
        ID = bundle.getInt("ID");
        k_g = bundle.getString("KhungGio");
        IDDV = bundle.getInt("IDDV");
        IDNV = bundle.getInt("IDNV");

        if (flag_capnhat) {
            editSdt.setText(bundle.getString("SDT"));
            editHvt.setText(bundle.getString("HoTen"));
            btnDatLich.setText("Thay đổi lịch");
            lichhen.setKhunggio(k_g);
            mApiService.DeleteDatLich(ID, "NULL").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.i("DEBUG", "Đã set giờ về NULL");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }

        // Load danh sách khung giờ mặc định lên để chọn
        load_khunggio();

        // Load dịch vụ và phòng tới spinner_dv và spinner_phong
        arrDichVu = new ArrayList<DichVu>();
        load_dv();

        // Load nhân viên tới Spinner
        arrNhanVien = new ArrayList<NhanVien>();
        load_nv();

        // Load 7 ngày tiếp theo lên Spinner Ngày hẹn
        String[] listDate = new String[7];
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Date day = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dayi = dateFormat.format(day);
            listDate[i] = dayi;
        }
        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listDate);
        spinner_ngayhen.setAdapter(adapterDate);

        if (flag_capnhat) {
            for (int i = 0; i < 7; i++) {
                if (listDate[i].equals(NgayUpdate)) {
                    spinner_ngayhen.setSelection(i);
                    break;
                }
            }
        }

        // Khi click vào ngày hẹn, load các khung giờ đã hết chỗ để khách có thể lựa chọn
        spinner_ngayhen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object o = spinner_ngayhen.getItemAtPosition(position);
                String ngayHen = (String) o;
                lichhen.setNgayhen(ngayHen + "");
                lichhen.setXacnhan(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Load khung giờ lên GridView
    public void load_khunggio() {
        khunggio_details = getListKhungGio();
        final GridView gridView = (GridView) findViewById(R.id.grid_view);
        adapterGridView = new CustomGridLayout(this, khunggio_details);
        gridView.setAdapter(adapterGridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag_selected_kg = false;
                Object o = gridView.getItemAtPosition(position);
                KhungGio khungGio = (KhungGio) o;
                lichhen.setKhunggio(((KhungGio) o).getKhunggio() + "");
                Log.i("DEBUG", "Selected: " + khungGio.getKhunggio());
            }
        });
    }

    // Load danh sách Dịch Vụ từ base
    public void load_dv() {
        mApiService.getDichVu().enqueue(new Callback<List<DichVu>>() {
            @Override
            public void onResponse(Call<List<DichVu>> call, Response<List<DichVu>> response) {
                final List<DichVu> list = response.body();

                for (int i = 0; i < list.size(); i++) {
                    arrDichVu.add(new DichVu(list.get(i).getMa_dichvu(), list.get(i).getTendichvu() + "", list.get(i).getGiadichvu() + ""));
                }
                ArrayAdapter<DichVu> adapter = new ArrayAdapter<DichVu>(mContext, android.R.layout.simple_spinner_item, arrDichVu);
                spinner_dv.setAdapter(adapter);

                if (flag_capnhat) {
                    for (int i = 0; i < list.size(); i++) {
                        if ((list.get(i).getTendichvu() + "").equals(d_v)) {
                            spinner_dv.setSelection(i);
                            break;
                        }
                    }
                }

                // Load danh sách phòng sau khi load dịch vụ
                spinner_dv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        flag_selected_kg = true;
                        DichVu dichVu = (DichVu) spinner_dv.getSelectedItem();
                        Log.i("DEBUG", dichVu.getMa_dichvu() + " " + dichVu.getTendichvu() + " " + dichVu.getGiadichvu());
                        load_phong(dichVu.getMa_dichvu());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<DichVu>> call, Throwable t) {

            }
        });
    }

    // Load danh sách phòng theo dịch vụ
    public void load_phong(int ma_dichvu) {
        mApiService.getDsPhong(ma_dichvu).enqueue(new Callback<List<Phong>>() {
            @Override
            public void onResponse(Call<List<Phong>> call, Response<List<Phong>> response) {
                List<Phong> list = response.body();
                ArrayList<Phong> arrPhong = new ArrayList<Phong>();
                for (int i = 0; i < list.size(); i++) {
                    arrPhong.add(new Phong(list.get(i).getTbl_dichvu_ma_dichvu(), list.get(i).getTbl_phong_maphong() + ""));
                }
                ArrayAdapter<Phong> adapter = new ArrayAdapter<Phong>(mContext, android.R.layout.simple_spinner_item, arrPhong);
                spinner_phong.setAdapter(adapter);

                if (flag_capnhat) {
                    for (int i = 0; i < list.size(); i++) {
                        if ((list.get(i).getTbl_phong_maphong() + "").equals(p)) {
                            spinner_phong.setSelection(i);
                            break;
                        }
                    }
                }

                spinner_phong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Phong phong = (Phong) spinner_phong.getSelectedItem();
                        lichhen.setTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu(phong.getTbl_dichvu_ma_dichvu());
                        lichhen.setTbl_dichvu_has_tbl_phong_tbl_phong_maphong(phong.getTbl_phong_maphong());
                        Log.i("DEBUG", "Ma dv: " + phong.getTbl_dichvu_ma_dichvu() + " Ma Phong: " + phong.getTbl_phong_maphong() + "");
                        GioTheoPhong(lichhen.getNgayhen(), lichhen.getTbl_dichvu_has_tbl_phong_tbl_phong_maphong());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Phong>> call, Throwable t) {

            }
        });
    }

    // Load danh sách nhân viên vào Spinner Nhân Viên
    public void load_nv() {
        mApiService.getNhanVien().enqueue(new Callback<List<NhanVien>>() {
            @Override
            public void onResponse(Call<List<NhanVien>> call, Response<List<NhanVien>> response) {
                ArrayAdapter<NhanVien> adapter;
                final List<NhanVien> list = response.body();
                Log.i("DEBUG", list.size() + "");
                for (int i = 0; i < list.size(); i++) {
                    arrNhanVien.add(new NhanVien(list.get(i).getId_nhanvien(), list.get(i).getTenNhanVien() + ""));
                }
                adapter = new ArrayAdapter<NhanVien>(mContext, android.R.layout.simple_spinner_item, arrNhanVien);
                spinner_nv.setAdapter(adapter);

                if (flag_capnhat) {
                    for (int i = 0; i < list.size(); i++) {
                        if ((list.get(i).getTenNhanVien() + "").equals(n_v)) {
                            spinner_nv.setSelection(i);
                            break;
                        }
                    }
                }

                spinner_nv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        NhanVien nv = (NhanVien) spinner_nv.getSelectedItem();
                        lichhen.setTbl_nhanvien_id_nhanvien(nv.getId_nhanvien());

                        GioTheoNV(lichhen.getNgayhen(), nv.getId_nhanvien());
                        Log.i("DEBUG", "Chon ID: " + nv.getId_nhanvien() + " va TenNV: " + nv.getTenNhanVien());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<NhanVien>> call, Throwable t) {

            }
        });
    }


    // Load danh sách khung giờ lên GridView
    private ArrayList<KhungGio> getListKhungGio() {
        ArrayList<KhungGio> arrayList = new ArrayList<KhungGio>();
        arrayList.add(new KhungGio("7:30 - 9:00", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("9:00 - 10:30", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("10:30 - 12:00", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("12:00 - 13:30", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("13:30 - 15:00", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("15:00 - 16:30", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("16:30 - 18:00", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("18:00 - 19:30", "NV chưa có lịch", "Còn chỗ"));
        arrayList.add(new KhungGio("19:30 - 21:00", "NV chưa có lịch", "Còn chỗ"));
        return arrayList;
    }

    // Load danh sách khung giờ mà phòng đã đầy lên
    public void GioTheoPhong(String ngayhen, String tbl_dichvu_has_tbl_phong_tbl_phong_maphong) {
        mApiService.GioTheoPhong(ngayhen, tbl_dichvu_has_tbl_phong_tbl_phong_maphong).enqueue(new Callback<List<KhungGio>>() {
            @Override
            public void onResponse(Call<List<KhungGio>> call, Response<List<KhungGio>> response) {
                List<KhungGio> list = response.body();
                for (int j = 0; j < 9; j++) {
                    khunggio_details.get(j).setTrangthaiPhong("Còn chỗ");
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < 9; j++) {
                        if (list.get(i).getKhunggio().equals(khunggio_details.get(j).getKhunggio() + "")) {
                            khunggio_details.get(j).setTrangthaiPhong("Hết chỗ");
                        }
                    }
                }
                adapterGridView.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<KhungGio>> call, Throwable t) {

            }
        });
    }

    // Load danh sách khung giờ mà nhân viên bận lên
    public void GioTheoNV(String ngayhen, int tbl_nhanvien_id_nhanvien) {
        mApiService.GioTheoNV(ngayhen, tbl_nhanvien_id_nhanvien).enqueue(new Callback<List<KhungGio>>() {
            @Override
            public void onResponse(Call<List<KhungGio>> call, Response<List<KhungGio>> response) {
                List<KhungGio> list = response.body();
                for (int j = 0; j < 9; j++) {
                    khunggio_details.get(j).setTrangthaiNV("NV chưa có lịch");
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < 9; j++) {
                        if (list.get(i).getKhunggio().equals(khunggio_details.get(j).getKhunggio() + "")) {
                            khunggio_details.get(j).setTrangthaiNV("NV đã có lịch");
                        }
                    }
                }
                adapterGridView.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<KhungGio>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btn_datlich)
    void DatLich() {
        boolean flag_sdt = true;
        boolean flag_hvt = true;
        boolean flag_khunggio = true;

        // Kiểm tra đã nhập số điện thoại chưa
        if (editSdt.getText().toString().equals("")) {
            flag_sdt = false;
            Toast.makeText(mContext, "Bạn chưa nhập Số Điện Thoại", Toast.LENGTH_SHORT).show();
        }

        // Kiểm tra đã nhập họ và tên chưa
        if (editHvt.getText().toString().equals("")) {
            flag_hvt = false;
            Toast.makeText(mContext, "Bạn chưa nhập Họ và Tên", Toast.LENGTH_SHORT).show();
        }

        // Kiểm tra khung giờ chọn có bị trùng hay không
        for (int i = 0; i < 9; i++) {
            if ((khunggio_details.get(i).getTrangthaiNV() + "").equals("NV đã có lịch")
                    || (khunggio_details.get(i).getTrangthaiPhong() + "").equals("Hết chỗ")) {
                flag_khunggio = false;
                Toast.makeText(mContext, "Xin quý khách chọn khung giờ khác!", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        // Nếu sđt và hvt đã được điền đầy đủ
        // Nếu khung giờ không bị trùng
        if (flag_sdt && flag_hvt && flag_khunggio) {
            if (flag_capnhat == true) {
                DichVu dv = (DichVu) spinner_dv.getSelectedItem();
                NhanVien nv = (NhanVien) spinner_nv.getSelectedItem();
                Phong p = (Phong) spinner_phong.getSelectedItem();
                String ngay = (String) spinner_ngayhen.getSelectedItem();

                mApiService.UpdateLich(ID, ngay, lichhen.getKhunggio() + "", editSdt.getText().toString(), editHvt.getText().toString(),
                        dv.getMa_dichvu(), nv.getId_nhanvien(), p.getTbl_phong_maphong(), 0).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());

                                // Nếu error = false -----> Success!!!!!
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                    flag_update_success = true;
                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_LONG).show();
                                    Log.i("DEBUG", "Cập nhật lịch thất bại!!!!!!!!!!!!!");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            // Nếu đổi sang dịch vụ khác --> Khung giờ load lại.
            // Sau khi load lại mà chưa chọn khung giờ, xong gửi luôn sẽ báo khung giờ chưa chọn
            Log.i("DEBUG", "" + flag_selected_kg);
            if (flag_selected_kg) {
                Toast.makeText(mContext, "Bạn chưa chọn khung giờ!", Toast.LENGTH_SHORT).show();
            }

            // Nếu không phải là cập nhật mà là đặt lịch mới
            // Nếu khung giờ đã được chọn => Gửi đặt lịch
            // Lỗi nếu sđt đã đặt lịch cùng ngày và giờ với dữ liệu có sẵn
            if (flag_capnhat == false && flag_selected_kg == false) {
                mApiService.DatLich(lichhen.getNgayhen(),
                        lichhen.getKhunggio(),
                        editSdt.getText().toString(),
                        editHvt.getText().toString(),
                        lichhen.getTbl_dichvu_has_tbl_phong_tbl_dichvu_ma_dichvu(),
                        lichhen.getTbl_nhanvien_id_nhanvien(),
                        lichhen.getTbl_dichvu_has_tbl_phong_tbl_phong_maphong(),
                        0).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());

                                        // Nếu error = false -----> Success!!!!!
                                        if (jsonObject.getString("error").equals("false")) {
                                            Toast.makeText(mContext, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
                                            Log.i("DEBUG", "Đặt lịch thành công!!!!!!!!!!!!!");
                                        } else {
                                            String error_message = jsonObject.getString("error_msg");
                                            Toast.makeText(mContext, error_message, Toast.LENGTH_LONG).show();
                                            Log.i("DEBUG", "Đặt lịch thất bại!!!!!!!!!!!!!");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        }
                );
            }

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
        // Nếu cập nhật và không thành công
        if (flag_capnhat && flag_update_success == false) {
            mApiService.DeleteDatLich(ID, k_g).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("DEBUG", "Chưa thay đổi lịch hẹn.");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        // Nếu cập nhật
        if (flag_capnhat) {
            Intent intent = new Intent(this, LichSuDatLich.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("FLAG_LUU_ARRAY", true);
            bundle.putString("SDT", editSdt.getText().toString());
            intent.putExtra("BUNDLE_FLAG_LUU_ARRAY", bundle);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }
}
