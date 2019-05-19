package jy.com.libjpush;

import android.content.Context;
import cn.jpush.android.api.JPushInterface;

/*
 * created by taofu on 2019/5/9
 **/
public class JPushUtils {


    public static void init(Context context){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
    }
}
