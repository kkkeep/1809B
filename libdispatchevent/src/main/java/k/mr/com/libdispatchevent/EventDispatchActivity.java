package k.mr.com.libdispatchevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

/*
 * created by taofu on 2019/3/15
 **/
public class EventDispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_dispatch);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.d("Test", getClass().getSimpleName() + " -> dispatchTouchEvent -> " + EventUtils.getTouchEventName(ev));
        boolean isProcess =  super.dispatchTouchEvent(ev);
         return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Test", getClass().getSimpleName() +  "-> onTouchEvent -> " + EventUtils.getTouchEventName(event));
        return super.onTouchEvent(event);
    }
}
