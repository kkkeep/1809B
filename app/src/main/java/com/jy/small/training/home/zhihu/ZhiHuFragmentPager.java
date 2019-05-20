package com.jy.small.training.home.zhihu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.small.training.R;
import com.jy.small.training.base.BaseFragment;
import com.jy.small.training.repository.entity.ZhiHuTab;
import com.jy.small.training.utils.Logger;

import java.util.ArrayList;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhiHuFragmentPager extends BaseFragment implements ZhihuConstract.IZhihuView {

    private TabLayout mTabLayout;

    private ZhihuConstract.IZhihuPresenter mPresenter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setPresenter(new ZhihuPresenter());

    }

    @Override
    public boolean isNeedAnimation() {
        return false;
    }



    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zhihu, container, false);
        mTabLayout = v.findViewById(R.id.tab_home_zhihu_tablayout);
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getTab(5);
    }

    @Override
    public void onTabSuccess(ArrayList<ZhiHuTab> tabs) {
        Logger.d("%s tabs = %s",getTAG(),tabs.size());

        mTabLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTabFail(String msg) {
        Logger.d("%s error = %s",getTAG(),msg);
        mTabLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPresenter(ZhihuConstract.IZhihuPresenter presenter) {
            mPresenter  = presenter;
            mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return getContext();
    }
}
