package com.jy.small.training.home.zhihu;

import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.base.IBasePresenter;
import com.jy.small.training.base.IBaseView;
import com.jy.small.training.repository.entity.ZhiHuTab;
import com.jy.small.training.repository.entity.ZhihuDailyData;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * created by taofu on 2019-05-20
 **/
public interface ZhihuConstract {


    /**
     * ViewPager 所在的 fragment 的 view
     */
    public interface IZhihuView extends IBaseView<IZhihuPresenter>{
        void onTabSuccess(ArrayList<ZhiHuTab> tabs);
        void onTabFail(String msg);

    }

    /**
     * 知乎日报 view
     */
    public interface IZhihuDailyView extends IBaseView<IZhihuDailyPresenter>{
        void onDailySuccess(ArrayList<ZhihuDailyData> datas);
        void onDailyFail(String msg);
    }

    /**
     * 专栏 view
     */
    public interface  IZhihuSelectionView extends IBaseView<IZhihuSelctionPresenter>{
        void onSelectionSuccess();
        void onSelectionFail(String msg);
    }

    /**
     * 热门 viwe
     */
    public interface  IZhihuHotView extends IBaseView<IZhihuHotPresenter>{
        void onHotSuccess();
        void onHotFail(String msg);
    }

    /**
     * ViewPager 所在的 fragment 的 p 层
     */
    public interface IZhihuPresenter extends IBasePresenter<IZhihuView>{
        void getTab(int count);

    }

    public interface IZhihuDailyPresenter extends IBasePresenter<IZhihuDailyView> {
        void getDailyData();
    }


    public interface IZhihuSelctionPresenter extends IBasePresenter<IZhihuSelectionView> {
        void getSectionData();
    }
    public interface IZhihuHotPresenter extends IBasePresenter<IZhihuHotView> {
        void getHotData();
    }





    public interface IZhihuRepository {

        void getTabContent(RxFragment rxFragment,int count, IBaseCallBack<ArrayList<ZhiHuTab>> listIBaseCallBack);


    }


    public interface IZhihuDailyRepository {

        void getDailyContent(RxFragment rxFragment,String url,IBaseCallBack<ZhihuDailyData> listIBaseCallBack);


    }
    public interface IZhihuSelectionRepository {

        void getSelectionContent(RxFragment rxFragment,String url);


    }
    public interface IZhihuHotRepository {

        void getHotContent(RxFragment rxFragment,String url);


    }
}
