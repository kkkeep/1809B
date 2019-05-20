package com.jy.small.training.home.zhihu;

import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.repository.ZhiHuRepository;
import com.jy.small.training.repository.ZhihuDailyRepository;
import com.jy.small.training.repository.entity.ZhiHuTab;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhihuPresenter  implements ZhihuConstract.IZhihuPresenter {

    private ZhihuConstract.IZhihuRepository mRepository;

    private ZhihuConstract.IZhihuView mView;


    public ZhihuPresenter() {
        this.mRepository = new ZhiHuRepository();
    }

    @Override
    public void getTab(int count) {

        mRepository.getTabContent((RxFragment) mView, count, new IBaseCallBack<ArrayList<ZhiHuTab>>() {
            @Override
            public void onSuccess(ArrayList<ZhiHuTab> data) {
                if(mView != null){
                    mView.onTabSuccess(data);
                }
            }

            @Override
            public void onFail(String msg) {
                mView.onTabFail(msg);
            }
        });
    }

    @Override
    public void attachView(ZhihuConstract.IZhihuView view) {
            mView = view;
    }

    @Override
    public void detachView(ZhihuConstract.IZhihuView view) {
        mView = null;
    }
}
