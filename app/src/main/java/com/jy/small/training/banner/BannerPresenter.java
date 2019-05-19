package com.jy.small.training.banner;

import android.app.Activity;
import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.base.IBasePresenter;
import com.jy.small.training.base.IBaseView;
import com.jy.small.training.repository.BannerRepository;
import com.jy.small.training.repository.entity.Banner;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2019/5/5
 **/
public class BannerPresenter implements BannerConstract.IBannerPresenter {


    private BannerConstract.IBannerView mBannerView;

    private BannerConstract.IBannerSource mIBannerSource;

    public BannerPresenter() {
        mIBannerSource = new BannerRepository();
    }

    @Override
    public void getBanner() {
        if(mIBannerSource != null){
            mIBannerSource.getBanner((RxAppCompatActivity) mBannerView, new IBaseCallBack<List<Banner>>() {
                @Override
                public void onSuccess(List<Banner> data) {
                    if(mBannerView != null){
                        mBannerView.onSuccess(data);
                    }
                }

                @Override
                public void onFail(String msg) {
                    if(mBannerView != null){
                        mBannerView.onFail(msg);
                    }
                }
            });
        }
    }

    @Override
    public void attachView(BannerConstract.IBannerView view) {
        mBannerView = view;
    }

    @Override
    public void detachView(BannerConstract.IBannerView view) {
        mBannerView = null;
    }
}
