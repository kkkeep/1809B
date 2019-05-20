package com.jy.small.training.base;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
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
public class BaseRepository {


    public static <T, R> void observer(RxAppCompatActivity activity, Observable<T> observable, Function<T, ObservableSource<R>> function, final IBaseCallBack<R> callBack) {

        observable.flatMap(function)
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.<R>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(R r) {
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public static <T, R> void observer(RxFragment fragment, Observable<T> observable, Function<T, ObservableSource<R>> function, final IBaseCallBack<R> callBack) {

        observable.flatMap(function).observeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<R>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(R r) {
                        callBack.onSuccess(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
