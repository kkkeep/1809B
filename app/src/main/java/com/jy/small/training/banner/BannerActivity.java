package com.jy.small.training.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jy.small.training.R;
import com.jy.small.training.base.BaseActivity;
import com.jy.small.training.repository.entity.Banner;
import com.jy.small.training.utils.Logger;
import com.jy.small.training.utils.Logger1;

import java.util.List;

/*
 * created by taofu on 2019/5/5
 **/
public class BannerActivity extends BaseActivity implements BannerConstract.IBannerView,View.OnClickListener {

    private static final String TAG = "BannerActivity";
    private Button mTabA;
    private Button mTabB;


    private BannerConstract.IBannerPresenter mBannerPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_banner);

        mTabA = findViewById(R.id.btn_tab_a);
        mTabB = findViewById(R.id.btn_tab_b);

        mTabA.setOnClickListener(this);
        mTabB.setOnClickListener(this);

        setPresenter(new BannerPresenter());
        addFragment(getSupportFragmentManager(), BannerFragmentA.class, R.id.fragment_container, null);


        Logger.d("%s packageName = %s",TAG,getPackageName());


    }

    @Override
    public void onSuccess(List<Banner> datas) {
        Logger1.d(TAG, "banners size = " + datas.size());
    }

    @Override
    public void onFail(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void setPresenter(BannerConstract.IBannerPresenter presenter) {

        mBannerPresenter = presenter;
        mBannerPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_tab_a:{
                 addFragment(getSupportFragmentManager(), BannerFragmentA.class, R.id.fragment_container, null);
                break;
            }
            case R.id.btn_tab_b:{
                addFragment(getSupportFragmentManager(), BannerFragmentB.class, R.id.fragment_container, null);
                break;
            }
        }
    }
}
