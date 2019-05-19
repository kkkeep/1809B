package com.jy.small.training.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.jy.small.training.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

/*
 * created by taofu on 2019/5/5
 **/
public class BaseFragment extends RxFragment {

    private BaseActivity mBaseActivity;



    public int enter(){
        return R.anim.common_page_right_in;
    }

    public int exit(){
        return R.anim.common_page_left_out;
    }
    public int popEnter(){
            return R.anim.common_page_left_in;
    }
    public int popExit(){
        return R.anim.common_page_right_out;
    }


    protected boolean isNeedToAddBackStack(){
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity) activity;
        }
    }


    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args){
        if(mBaseActivity != null){
            mBaseActivity.addFragment(manager, aClass, containerId, args);
        }
    }

}
