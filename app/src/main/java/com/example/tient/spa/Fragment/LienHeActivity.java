package com.example.tient.spa.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tient.spa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LienHeActivity extends AppCompatActivity {
    @BindView(R.id.lh_sdt_1)
    TextView lhSDT1;
    @BindView(R.id.lh_sdt_2)
    TextView lhSDT2;
    @BindView(R.id.emailcs1)
    TextView emailcs1;
    @BindView(R.id.emailcs2)
    TextView emailcs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.lh_sdt_1)
    public void CallSDT1() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + lhSDT1.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
            return;
        }
        startActivity(callIntent);
    }

    @OnClick(R.id.lh_sdt_2)
    public void CallSDT2() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + lhSDT2.getText().toString()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
            return;
        }
        startActivity(callIntent);
    }

    @OnClick(R.id.emailcs1)
    public void SendEmail1() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailcs1.getText().toString(), null));
        startActivity(Intent.createChooser(intent, "Chọn ứng dụng gửi Email :"));
    }

    @OnClick(R.id.emailcs2)
    public void SendEmail2() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailcs2.getText().toString(), null));
        startActivity(Intent.createChooser(intent, "Chọn ứng dụng gửi Email :"));
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
