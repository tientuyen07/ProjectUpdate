package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.tient.spa.Model.KhachHang;
import com.example.tient.spa.R;
import com.example.tient.spa.SharedPrefManager;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    String tbl_taikhoan_username;
    BaseApiService mApiService;
    KhachHang khachHang = new KhachHang();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPrefManager = new SharedPrefManager(this);

        mApiService = UtilsApi.getApiService();
        tbl_taikhoan_username = sharedPrefManager.getSPUsername();
        mApiService.ThongTinKH(tbl_taikhoan_username).enqueue(new Callback<List<KhachHang>>() {
            @Override
            public void onResponse(Call<List<KhachHang>> call, Response<List<KhachHang>> response) {
                List<KhachHang> list = response.body();
                if (list.size() > 0) {
                    khachHang.setHoten(list.get(0).getHoten() + "");
                    khachHang.setDiachi(list.get(0).getDiachi() + "");
                    khachHang.setGioitinh(list.get(0).getGioitinh() + "");
                    khachHang.setSdt(list.get(0).getSdt() + "");
                    khachHang.setNgaysinh(list.get(0).getNgaysinh() + "");
                }

            }

            @Override
            public void onFailure(Call<List<KhachHang>> call, Throwable t) {

            }
        });


        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.rela_cap_nhat)
    public void CapNhatTT() {
        Bundle bundle = new Bundle();
        bundle.putString("hoten", khachHang.getHoten());
        bundle.putString("ngaysinh", khachHang.getNgaysinh());
        bundle.putString("gioitinh", khachHang.getGioitinh());
        bundle.putString("diachi", khachHang.getDiachi());
        bundle.putString("sdt", khachHang.getSdt());

        Intent intent = new Intent(AccountActivity.this, CapNhatTTActivity.class);
        intent.putExtra("AccountActivitySend", bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rela_doi_matkhau)
    public void DoiMatKhau() {
        Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rela_logout)
    public void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SPA PROJECT");
        builder.setMessage("Bạn có muốn đăng xuất khỏi tài khoản không?");
        builder.setCancelable(false);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                sharedPrefManager.saveSPBoolean("LOGIN_SUCCESS", false);
                sharedPrefManager.saveSPString("username", "");
                startActivity(intent);
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

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
