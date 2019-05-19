package com.jy.small.training.base;

/*
 * created by taofu on 2019/5/5
 **/
public interface IBaseCallBack<T> {

    void onSuccess(T data);
    void onFail(String msg);
}
