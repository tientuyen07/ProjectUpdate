package com.example.tient.spa.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.tient.spa.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.daimajia_slider)
    SliderLayout sliderLayout;
    @BindView(R.id.rela_table_1)
    RelativeLayout rela_dichvu;
    @BindView(R.id.rela_table_2)
    RelativeLayout rela_uudaispa;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        mContext = DashboardActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        // Slider Layout
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Không gian lý tưởng", R.drawable.intro1);
        file_maps.put("Các dịch vụ đa dạng", R.drawable.intro2);
        file_maps.put("Đội ngũ nhân viên chuyên nghiệp", R.drawable.intro3);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(mContext);
            textSliderView.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());

    }

    @OnClick(R.id.rela_table_1)
    void DichVuFunc() {
        startActivity(new Intent(mContext, DichVuActivity.class));
    }


    @OnClick(R.id.rela_table_2)
    void DatLichFunc() {
        Intent intent = new Intent(mContext, DatLichActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("FlagUpdate", false);
        intent.putExtra("BundleFlagUpdate", bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rela_table_3)
    void QuanLyLichHen() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, LichSuDatLich.class);
        bundle.putBoolean("FLAG_LUU_ARRAY", true);
        intent.putExtra("BUNDLE_FLAG_LUU_ARRAY", bundle);
        startActivity(intent);
    }

    @OnClick(R.id.rela_table_4)
    void LienHe() {
        Intent intent = new Intent(mContext, LienHeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rela_table_5)
    void Setting() {
        Intent intent = new Intent(mContext, AccountActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rela_table_6)
    void Exit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Process.killProcess(Process.myPid());
        System.exit(0);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(1);
    }
}
