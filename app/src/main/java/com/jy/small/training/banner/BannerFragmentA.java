package com.jy.small.training.banner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.small.training.R;
import com.jy.small.training.base.BaseFragment;

/*
 * created by taofu on 2019/5/5
 **/
public class BannerFragmentA extends BaseFragment {


    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }


    @Override
    public int enter() {
        return 0;
    }


    @Override
    public int popExit() {
        return 0;
    }

    @Override
    public int exit() {
        return 0;
    }

    @Override
    public int popEnter() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_a, container, false);


        return v;

    }
}
