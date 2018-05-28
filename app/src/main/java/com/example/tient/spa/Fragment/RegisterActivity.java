package com.example.tient.spa.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tient.spa.R;
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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edt_sdt)
    EditText username;
    @BindView(R.id.edt_mk)
    EditText password;
    @BindView(R.id.edt_repeat_mk)
    EditText repeat_password;

    Context mContext;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this); //Bind a view's children into fields

        mContext = this;
        mApiService = UtilsApi.getApiService();
    }

    @OnClick(R.id.btn_dangky)
    void DangKyAccount() {
//        loading = ProgressDialog.show(mContext, null, "Please wait...", true, false);
        if (password.getText().toString().equals(repeat_password.getText().toString())){
            mApiService.registerRequest(username.getText().toString(),
                    password.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.i("DEBUG", ""+response.message());
                            if (response.isSuccessful()) {
                                // Gửi đăng ký thành công
                                Log.i("DEBUG", "onResponse: Success!!!!!!");
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());

                                    // Nếu error = false -----> Success!!!!!
                                    if (jsonObject.getString("error").equals("false")) {
                                        Toast.makeText(mContext, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String error_message = jsonObject.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else{
//                            loading.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Đăng ký lỗi! Xin vui lòng kiểm tra lại kết nối mạng!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(mContext, "Mật khẩu không trùng khớp!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
