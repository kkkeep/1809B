package com.jy.small.training.repository.entity;

/*
 * created by taofu on 2019/5/5
 **/
public class HttpResult<T> {
    public T data;
    public int  errorCode;
    public String errorMsg;
}
