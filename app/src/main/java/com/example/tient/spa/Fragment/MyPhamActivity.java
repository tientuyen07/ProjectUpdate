package com.example.tient.spa.Fragment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.tient.spa.Model.MyPhamArrayAdapter;
import com.example.tient.spa.Model.MyPham;
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

public class MyPhamActivity extends AppCompatActivity {
    ArrayList<MyPham> arrMyPham = new ArrayList<MyPham>();
    MyPhamArrayAdapter adapter = null;
    Context mContext;
    BaseApiService mApiService;

    @BindView(R.id.lv_my_pham)
    ListView lvMyPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pham);
        ButterKnife.bind(this);
        mContext = MyPhamActivity.this;
        mApiService = UtilsApi.getApiService();

        arrMyPham = new ArrayList<MyPham>();
        adapter = new MyPhamArrayAdapter(this, R.layout.my_pham_layout, arrMyPham);
        lvMyPham.setAdapter(adapter);

        XuLyNhap();
    }

    public void XuLyNhap() {


        mApiService.getMyPham().enqueue(new Callback<List<MyPham>>() {
            @Override
            public void onResponse(Call<List<MyPham>> call, Response<List<MyPham>> response) {
                List<MyPham> list = response.body();

                for (int i = 0; i < list.size(); i++) {
                    MyPham myPham = new MyPham();
                    myPham.setTenSanPham(list.get(i).getTenSanPham() + "");
                    myPham.setGiaMyPham(list.get(i).getGiaMyPham() + "");
                    myPham.setXuatxu(list.get(i).getXuatxu() + "");
                    arrMyPham.add(myPham);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MyPham>> call, Throwable t) {

            }
        });
    }
}
