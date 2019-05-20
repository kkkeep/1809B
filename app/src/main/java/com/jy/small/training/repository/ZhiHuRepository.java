package com.jy.small.training.repository;

import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.home.zhihu.ZhihuConstract;
import com.jy.small.training.repository.entity.ZhiHuTab;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhiHuRepository implements ZhihuConstract.IZhihuRepository {



    @Override
    public void getTabContent(RxFragment rxFragment, int count, IBaseCallBack<ArrayList<ZhiHuTab>> listIBaseCallBack) {

        ArrayList<ZhiHuTab> arrayList = new ArrayList<>();
        ZhiHuTab tab = new ZhiHuTab("日报", "/stories/latest");
        arrayList.add(tab);

        tab = new ZhiHuTab("专栏", "/sections ");

        arrayList.add(tab);

        tab = new ZhiHuTab("热门", "/news/hot");

        arrayList.add(tab);

        if(listIBaseCallBack != null){
            listIBaseCallBack.onSuccess(arrayList);
        }


    }


}
