package k.mr.com.libdispatchevent.mock;

import android.view.View;

/*
 * created by taofu on 2019-05-14
 **/
public class TestTouch {


    public static void main(String args []){

        ViewGroup1 group1 = new ViewGroup1();

       boolean b = group1.dispatchOnTouchEvent();

       System.out.println(b);
    }
}
