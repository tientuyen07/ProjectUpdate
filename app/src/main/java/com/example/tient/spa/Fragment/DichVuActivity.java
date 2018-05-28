package com.example.tient.spa.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tient.spa.Model.DichVu;
import com.example.tient.spa.Model.DichVuArrayAdapter;
import com.example.tient.spa.R;
import com.example.tient.spa.Util.api.BaseApiService;
import com.example.tient.spa.Util.api.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DichVuActivity extends AppCompatActivity {
    ArrayList<DichVu> arrDichVu = new ArrayList<DichVu>();
    DichVuArrayAdapter adapter = null;
    Context mContext;
    BaseApiService mApiService;
    @BindView(R.id.lv_dich_vu)
    ListView lvDichVu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dich_vu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        mContext = DichVuActivity.this;
        mApiService = UtilsApi.getApiService();


        arrDichVu = new ArrayList<DichVu>();
        adapter = new DichVuArrayAdapter(this, R.layout.dich_vu_custom_layout, arrDichVu);
        lvDichVu.setAdapter(adapter);
        xulynhap();
        lvDichVu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DichVu dichVu = arrDichVu.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("MoTa", dichVu.getMota());
                Intent intent = new Intent(mContext, WebViewLayout.class);
                intent.putExtra("DICHVU", bundle);
                startActivity(intent);
            }
        });
    }

    public void xulynhap() {
        Log.i("Debug", "xu ly nhap");
        mApiService.getDichVu().enqueue(new Callback<List<DichVu>>() {
            @Override
            public void onResponse(Call<List<DichVu>> call, Response<List<DichVu>> response) {
                List<DichVu> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    DichVu dichVu = new DichVu();
                    dichVu.setTendichvu(list.get(i).getTendichvu() + "");
                    dichVu.setGiadichvu(list.get(i).getGiadichvu() + "");
                    dichVu.setIcon_dichvu(list.get(i).getIcon_dichvu() + "");
                    dichVu.setMota(list.get(i).getMota() + "");
                    arrDichVu.add(dichVu);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DichVu>> call, Throwable t) {

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
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
