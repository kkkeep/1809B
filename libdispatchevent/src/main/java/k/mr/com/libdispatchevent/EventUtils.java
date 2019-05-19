package k.mr.com.libdispatchevent;

import android.view.MotionEvent;

/*
 * created by taofu on 2019/3/15
 **/
public class EventUtils {

    public static String getTouchEventName(MotionEvent v){

        switch (v.getAction()){

            case MotionEvent.ACTION_DOWN:{
                return "Down";
            }
            case MotionEvent.ACTION_MOVE:{
                return "Move";
            }
            case MotionEvent.ACTION_UP:{
                return "UP";
            }
        }
        return "unknown";
    }
}
