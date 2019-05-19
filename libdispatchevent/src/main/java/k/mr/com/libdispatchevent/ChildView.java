package k.mr.com.libdispatchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*
 * created by taofu on 2019/3/15
 **/
public class ChildView extends View {
    public ChildView(Context context) {
        super(context);
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d("Test", getClass().getSimpleName() + " -> dispatchTouchEvent -> " + EventUtils.getTouchEventName(ev));
        return super.dispatchTouchEvent(ev);
        //return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Test", getClass().getSimpleName() +  " -> onTouchEvent -> " + EventUtils.getTouchEventName(event));
        return super.onTouchEvent(event);
    }

}
