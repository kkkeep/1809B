package jy.com.libumengsharelogin;

import android.content.Context;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/*
 * created by taofu on 2019/5/7
 **/
public class MyUmengUtils {


    public  static void initUmeng(Context context){
        UMConfigure.init(context,"5cd0fb773fc195e60d000d3c","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        // 下面所有ID 都得替换成你自己公司在相应创建应用时获得的id
        PlatformConfig.setWeixin("wx61cd0cb45a23724e", "9e0264a8467d2e83805f677c6cf0cd6f");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }
}
