package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tient.spa.R;
import com.example.tient.spa.SharedPrefManager;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    @BindView(R.id.mkc)
    EditText matKhauCu;
    @BindView(R.id.mkm)
    EditText matKhauMoi;
    @BindView(R.id.rp_mk)
    EditText nhapLaiMK;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);
    }

    @OnClick(R.id.btn_doi_mk)
    public void DoiMatKhau() {
        if (matKhauMoi.getText().toString().equals("")) {
            Toast.makeText(this, "Mật khẩu mới không được để trống!", Toast.LENGTH_SHORT).show();
        } else {
            if (matKhauMoi.getText().toString().equals(nhapLaiMK.getText().toString())) {
                mApiService.DoiMatKhau(sharedPrefManager.getSPUsername(), matKhauMoi.getText().toString(), matKhauCu.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());

                                // Nếu error = false -----> Success!!!!!
                                if (jsonObject.getString("error").equals("false")) {
                                    Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toast.makeText(ChangePasswordActivity.this, error_message, Toast.LENGTH_SHORT).show();
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
            } else {
                Toast.makeText(this, "Mật khẩu không trùng khớp!!!", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
        finish();
    }
}
