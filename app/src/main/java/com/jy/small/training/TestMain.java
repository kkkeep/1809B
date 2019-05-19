package com.jy.small.training;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * created by taofu on 2019/5/13
 **/
public class TestMain {

    private static int count = 0;

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String args []){
        System.out.println("ssss");
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "中国联通话费10元");
            jsonObject.put("stockCount", 200);
            jsonObject.put("sellCount", 15);
            jsonObject.put("price", 10);
            jsonObject.put("pic", "http://bpic.588ku.com/element_origin_min_pic/16/08/09/1357a970e9d51c9.jpg");

            jsonArray.put(jsonObject);
            jsonObject.put("name", "中国联通话费20元");
            jsonObject.put("stockCount", 150);
            jsonObject.put("sellCount", 10);
            jsonObject.put("price", 20);
            jsonObject.put("pic", "http://bpic.588ku.com/element_origin_min_pic/16/08/09/1357a970e9d51c9.jpg");
            jsonArray.put(jsonObject);


            jsonObject.put("name", "中国电信50元");
            jsonObject.put("stockCount", 50);
            jsonObject.put("sellCount", 40);
            jsonObject.put("price", 50);
            jsonObject.put("pic", "http://img1.imgtn.bdimg.com/it/u=3465488602,253756323&fm=26&gp=0.jpg");
            jsonArray.put(jsonObject);

            jsonObject.put("name", "中国移动100元");
            jsonObject.put("stockCount", 0);
            jsonObject.put("sellCount", 40);
            jsonObject.put("price", 100);
            jsonObject.put("pic", "http://img0.pconline.com.cn/pconline/1309/27/3550817_1111-1_thumb.jpg");
            jsonArray.put(jsonObject);



            JSONObject jsonObject1 = new JSONObject();

            jsonObject1.put("list", jsonArray);
            jsonObject1.put("service", 2);

            JSONObject result = new JSONObject();

            result.put("code", 0);
            result.put("message", "成功");
            result.put("data", jsonObject1);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
