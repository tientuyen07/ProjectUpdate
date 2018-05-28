package com.example.tient.spa.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tient.spa.R;
import com.example.tient.spa.SharedPrefManager;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapNhatTTActivity extends AppCompatActivity {
    @BindView(R.id.spinner_gt)
    Spinner spinnerGioiTinh;
    @BindView(R.id.edit1)
    EditText editUpdateHT;
    @BindView(R.id.edit2)
    EditText editUpdatNS;
    @BindView(R.id.edit4)
    EditText editUpdateSDT;
    @BindView(R.id.edit5)
    EditText editUpdateDiaChi;
    String gioiTinh;
    String tbl_taikhoan_username;

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_tt);
        ButterKnife.bind(this);
        mContext = CapNhatTTActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);
        tbl_taikhoan_username = sharedPrefManager.getSPUsername();
        Log.i("DEBUG", tbl_taikhoan_username);

        String[] listGT = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapterGT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGT);
        spinnerGioiTinh.setAdapter(adapterGT);

        gioiTinh = spinnerGioiTinh.getSelectedItem().toString();
    }

    @OnClick(R.id.btn_cap_nhat)
    public void CapNhat() {
        mApiService.CapNhatThongTin(editUpdateHT.getText().toString(), editUpdatNS.getText().toString(),
                gioiTinh, editUpdateSDT.getText().toString(), editUpdateDiaChi.getText().toString(), tbl_taikhoan_username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AccountActivity.class));
        finish();
    }
}
