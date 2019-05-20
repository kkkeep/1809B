package com.jy.small.training.repository;

import com.jy.small.training.banner.BannerConstract;
import com.jy.small.training.base.BaseRepository;
import com.jy.small.training.base.IBaseCallBack;
import com.jy.small.training.repository.entity.Banner;
import com.jy.small.training.repository.entity.HttpResult;
import com.jy.small.training.repository.remote.ok.DataService;

import java.util.List;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2019/5/5
 **/
public class BannerRepository extends BaseRepository implements BannerConstract.IBannerSource {


    @Override
    public void getBanner(RxAppCompatActivity activity, final IBaseCallBack<List<Banner>> callBack) {



        observer(activity, DataService.getService().getBanners(), new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
            @Override
            public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                if (listHttpResult != null && listHttpResult.errorCode == 0 && listHttpResult.data != null && listHttpResult.data.size() > 0) {
                    return Observable.just(listHttpResult.data);
                }

                return Observable.error(new NullPointerException("返回数据为空或者异常 : " + (listHttpResult == null ? "" : listHttpResult.errorMsg)));
            }
        }, callBack);
    }

}
