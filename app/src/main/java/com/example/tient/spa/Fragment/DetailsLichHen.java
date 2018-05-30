package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tient.spa.Model.DatLich;
import com.example.tient.spa.Model.NhanVien;
import com.example.tient.spa.R;
import com.example.tient.spa.Util.api.BaseApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsLichHen extends AppCompatActivity {
    @BindView(R.id.tvhvtkh)
    TextView hvtkh;
    @BindView(R.id.tvngh)
    TextView tvngh;
    @BindView(R.id.tvkg)
    TextView kg;
    @BindView(R.id.tvtdv)
    TextView tdv;
    @BindView(R.id.tvp)
    TextView p;
    @BindView(R.id.tvsdtkh)
    TextView sdtkh;
    @BindView(R.id.tvnv)
    TextView tvnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_lich_hen);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("BundleLichHen");
        tdv.setText(bundle.getString("TenDichVu") + "");
        tvngh.setText(bundle.getString("NgayHen") + "");
        kg.setText(bundle.getString("KhungGio") + "");
        p.setText(bundle.getString("Phong") + "");
        sdtkh.setText(bundle.getString("SDT") + "");
        hvtkh.setText(bundle.getString("TenKhach") + "");
        tvnv.setText(bundle.getString("TenNhanVien") + "");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LichSuDatLich.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("FLAG_LUU_ARRAY", true);
        bundle.putString("SDT", sdtkh.getText().toString());
        intent.putExtra("BUNDLE_FLAG_LUU_ARRAY", bundle);
        startActivity(intent);
        finish();
    }
}
