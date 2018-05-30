package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tient.spa.R;
import com.example.tient.spa.SharedPrefManager;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.edit1)
    EditText edit_user;
    @BindView(R.id.edit2)
    EditText edit_pass;

    BaseApiService mApiService;
    Context mContext;
    SharedPrefManager sharedPrefManager; // Dùng để lưu trạng thái đăng nhập và lưu tài khoản người dùng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefManager = new SharedPrefManager(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // Nếu đã đăng nhập thành công lúc trước thì tự động vào màn hình chính
        if (sharedPrefManager.getSPLoginSuccess()) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        mApiService = UtilsApi.getApiService();
        mContext = LoginActivity.this;
    }

    @OnClick(R.id.btn_register)
    void RegisterAccountFunc() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)
    void LoginAccountFunc() {
        mApiService.loginRequest(edit_user.getText().toString(), edit_pass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.i("DEBUG", "response --------> Success!!!!!!!!");
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("error").equals("false") && (jsonObject.getInt("level") == 2)) {
                                    Log.i("DEBUG", "NGƯỜI DÙNG    =====>    Đăng nhập thành công!!!!!!!!");


                                    // Lưu lại username và set đã đăng nhập thành công
                                    Intent intent = new Intent(mContext, DashboardActivity.class);
                                    sharedPrefManager.saveSPString("username", edit_user.getText().toString());
                                    sharedPrefManager.saveSPBoolean("LOGIN_SUCCESS", true);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(mContext, jsonObject.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                    Log.i("DEBUG", jsonObject.getString("error_msg"));
                                    Log.i("DEBUG", "" + jsonObject.getInt("phanquyen"));
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
                        Toast.makeText(mContext, "Đăng nhập thất bại! Xin kiểm tra lại kết nối mạng!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
