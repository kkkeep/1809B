package com.jy.small.training.banner;

import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.base.IBasePresenter;
import com.jy.small.training.base.IBaseView;
import com.jy.small.training.repository.entity.Banner;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2019/5/5
 **/
public interface BannerConstract {


    public interface IBannerView extends IBaseView<IBannerPresenter>{

        void onSuccess(List<Banner> datas);
        void onFail(String msg);
    }

    public interface IBannerPresenter extends IBasePresenter<IBannerView> {

        void getBanner();
    }

    public interface IBannerSource{
        void getBanner(RxAppCompatActivity activity,IBaseCallBack<List<Banner>> callBack);
    }


}
