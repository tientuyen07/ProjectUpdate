package com.example.tient.spa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tient.spa.Fragment.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.btn_skip)
    Button mBtnSkip;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.layoutDots)
    LinearLayout mDotsLayout;
    private IntroViewPagerAdapter mPagerAdapter;
    private TextView[] mDots;
    private int[] mLayouts;
    private SharedPrefManager sharedPrefManager;
    private static final String FIRST_USER = "IsFirstUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager = new SharedPrefManager(this);

        // Nếu không phải là lần sử dụng đầu tiên thì vào luôn màn hình đăng nhập
        if (sharedPrefManager.getSPFirstUser()) {
            launchLoginRegisterScreen();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Mảng lưu các id của các intro lúc mới sử dụng phần mềm
        mLayouts = new int[]{R.layout.welcome_slide1, R.layout.welcome_slide2, R.layout.welcome_slide3, R.layout.welcome_slide4};

        addBottomDots(0);
        changeStatusBarColor();

        mPagerAdapter = new IntroViewPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(mViewPagerChangeListener);

    }

    @OnClick(R.id.btn_next)
    public void nextClick() {
        int current = getItem(1);
        if (current < mLayouts.length) {
            mViewPager.setCurrentItem(current);
        } else {
            launchLoginRegisterScreen();
        }
    }

    @OnClick(R.id.btn_skip)
    public void skipClick() {
        launchLoginRegisterScreen();
    }

    // Sự kiện khi load các Intro
    private ViewPager.OnPageChangeListener mViewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            //Change the next button text 'NEXT'/'GOT IT'
            if (position == mLayouts.length - 1) {
                mBtnNext.setText(getString(R.string.start));
                mBtnSkip.setVisibility(View.GONE);
            } else {
                //Still pages are left
                mBtnNext.setText(getString(R.string.next));
                mBtnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // Thêm các dấu chấm khi chuyển tiếp giữa các intro
    public void addBottomDots(int currentPage) {
        mDots = new TextView[mLayouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dot_inactive);
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("•"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(colorsInActive[currentPage]);
            mDotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[currentPage].setTextColor(colorsActive[currentPage]);
        }

    }

    /*
     * Làm cho thanh thông báo trong suốt
     */
    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    private void launchLoginRegisterScreen() {
        /*
        Khi đã chuyển sang màn hình đăng nhập
        Tức là đã từng sử dụng phần mềm => Bỏ qua intro
        */
        sharedPrefManager.saveSPBoolean(FIRST_USER, true);
        Log.i("DEBUG", "Đã set sử dụng lần đầu!!!");

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("DoiMatKhau", false);
        intent.putExtra("BundleFlagCapNhat", bundle);
//        mSharedPrefManager.setFirsttimeLauch(true);
        startActivity(intent);
        finish();
    }


    // Adapter cho IntroViewPager
    public class IntroViewPagerAdapter extends PagerAdapter {
        private LayoutInflater mInflater;

        public IntroViewPagerAdapter() {
            super();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(mLayouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
