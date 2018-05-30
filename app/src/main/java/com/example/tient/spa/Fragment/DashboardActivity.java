package com.example.tient.spa.Fragment;

import android.content.Context;
import android.content.Intent;
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

        // Slider Layout
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("intro1", R.drawable.intro1);
        file_maps.put("intro2", R.drawable.intro2);
        file_maps.put("intro3", R.drawable.intro3);

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
        startActivity(new Intent(mContext, LichSuDatLich.class));
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
        android.os.Process.killProcess(android.os.Process.myPid());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(1);
    }

}
