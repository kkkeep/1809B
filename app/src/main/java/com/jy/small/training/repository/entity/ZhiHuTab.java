package com.jy.small.training.repository.entity;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhiHuTab {

    private String  title;
    private String uri;


    public ZhiHuTab(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
