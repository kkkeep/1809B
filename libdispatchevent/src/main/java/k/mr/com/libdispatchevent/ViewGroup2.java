package k.mr.com.libdispatchevent;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/*
 * created by taofu on 2019/3/15
 **/
public class ViewGroup2 extends ConstraintLayout {


    public ViewGroup2(Context context) {
        super(context);
    }

    public ViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d("Test", getClass().getSimpleName() + " -> dispatchTouchEvent -> " + EventUtils.getTouchEventName(ev));
        return super.dispatchTouchEvent(ev);


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("Test", getClass().getSimpleName() +  "-> onInterceptTouchEvent -> " + EventUtils.getTouchEventName(ev));
        return super.onInterceptTouchEvent(ev);
    }



     @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Test", getClass().getSimpleName() +  "-> onTouchEvent -> " + EventUtils.getTouchEventName(event) + " y = " + event.getY());
        return true;
    }


}
