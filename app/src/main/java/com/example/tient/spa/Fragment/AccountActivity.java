package com.example.tient.spa.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.tient.spa.R;
import com.example.tient.spa.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPrefManager = new SharedPrefManager(this);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.rela_cap_nhat)
    public void CapNhatTT() {
        Intent intent = new Intent(AccountActivity.this, CapNhatTTActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rela_doi_matkhau)
    public void DoiMatKhau() {
        Intent intent = new Intent(AccountActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("DoiMatKhau", true);
        intent.putExtra("BundleFlagCapNhat", bundle);
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
