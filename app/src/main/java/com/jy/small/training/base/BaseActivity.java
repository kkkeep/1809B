package com.jy.small.training.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;
import com.jy.small.training.R;
import com.jy.small.training.utils.Logger;
import com.jy.small.training.utils.Logger1;
import com.jy.small.training.widget.view.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2019/5/5
 **/
public abstract class BaseActivity extends RxAppCompatActivity {

    private static final String TAG = "BaseActivity";

    private LoadingPage mLoadingPage;


    protected void addFragment(FragmentManager manager,Class<? extends BaseFragment> aClass,int containerId,Bundle args){

        String tag = aClass.getName();

        Logger.d("%s add fragment %s",TAG,aClass.getSimpleName());

        /**
         * 先查询fragmentManager 所在的activitiy 中是否已经添加了这个fragment
         *  第一步 先从一个mAdded 的一个ArrayList遍历查找，如果找不到再从 一个 叫 mActive 的 SparseArray的一个map
         *  里面查找。
         *
         *
         * 注意：
         * 1.一个 fragment 被 remove 掉后，只会从 mAdded 里面删除，不会从 mActive 里面删除，只有当这个fragment 所在的 transaction
         * 从回退栈里面移除后才会 从mActive 删除
         * 2. 当我们add 一个fragment时 会把我们的fragment 添加到 mAdded 里面，不会添加到 mActive。
         * 3. 只有当我们把 transaction 添加到回退栈的时候，才会把我们的 fragment 添加到 mActive 里面。
         *
         *
         * 所以我们通过 findFragmentByTag 方法查找出来的 fragment 不一定是被添加到我们的 activity 中。
         */

        Fragment fragment =  manager.findFragmentByTag(tag);


        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if(fragment == null){// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例

                BaseFragment baseFragment = (BaseFragment) fragment; // 强壮成我们base fragment

                // 设置 fragment 进入，退出， 弹进，弹出的动画
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, fragment);

                if(baseFragment.isNeedToAddBackStack()){ // 判断是否需要加入回退栈
                    transaction.addToBackStack(tag); // 加入回退栈时制定一个tag，以便在找到指定的事务
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            if(fragment.isAdded()){
                if(fragment.isHidden()){
                    transaction.show(fragment);
                }
            }else{
                transaction.add(containerId, fragment);
            }
        }

        if(fragment != null){
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }


    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager,FragmentTransaction transaction,Fragment currentFragment){

        List<Fragment> fragments = manager.getFragments();

        for(Fragment fragment : fragments){
            if(fragment != currentFragment && !fragment.isHidden()){
                transaction.hide(fragment);
            }
        }
    }

    protected void showLoadingPage(int type) {
        showLoadingPage(android.R.id.content, type);
    }

    protected void showLoadingPage(int containerId, int type) {
        showLoadingPage((ViewGroup) findViewById(containerId), type);
    }

    protected void showLoadingPage(ViewGroup group, int type) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.layout_laoding_page, null, false);
        }
        group.addView(mLoadingPage);
    }

    protected void dismiss() {
        if (mLoadingPage != null) {
            ViewParent parent = mLoadingPage.getParent();
            if(parent != null){
                ViewGroup group = (ViewGroup) parent;
                group.removeView(mLoadingPage);
            }
        }
    }



    protected void onError(String msg){
        onError(null,msg);
    }

    protected void onError(LoadingPage.OnReloadCallBack callBack,String msg){
        if(mLoadingPage != null){
            mLoadingPage.onError(callBack, msg);
        }
    }

    protected void onError(){
        if(mLoadingPage != null){
            mLoadingPage.onError();
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }
}
