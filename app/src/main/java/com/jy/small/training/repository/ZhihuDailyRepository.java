package com.jy.small.training.repository;

import com.jy.small.training.base.BaseRepository;
import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.home.zhihu.ZhihuConstract;
import com.jy.small.training.repository.entity.ZhihuDailyData;
import com.jy.small.training.repository.remote.ok.DataService;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhihuDailyRepository extends BaseRepository implements ZhihuConstract.IZhihuDailyRepository {

    @Override
    public void getDailyContent(RxFragment rxFragment, String url, IBaseCallBack<ZhihuDailyData> listIBaseCallBack) {

        observer(rxFragment,  DataService.getService().getZhihuDailyData(url), new Function<ZhihuDailyData, ObservableSource<ZhihuDailyData>>() {
            @Override
            public ObservableSource<ZhihuDailyData> apply(ZhihuDailyData zhihuDailyData) throws Exception {
                if((zhihuDailyData.getStories() == null || zhihuDailyData.getStories().size() == 0) && (zhihuDailyData.getTop_stories() == null|| zhihuDailyData.getTop_stories().size() == 0)){
                    return Observable.error(new NullPointerException("返回数据为空"));
                }
                return Observable.just(zhihuDailyData);
            }
        }, listIBaseCallBack);


    }


}
