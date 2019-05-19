package k.mr.com.libdispatchevent.mock;

/*
 * created by taofu on 2019-05-14
 **/
public class ViewGroup1 extends ViewGroup {


    @Override
    public boolean dispatchOnTouchEvent() {
        System.out.println(getClass().getSimpleName() + " dispatchOnTouchEvent ");
        return super.dispatchOnTouchEvent();
    }


    @Override
    public boolean onInterceptTouchEvent() {
        System.out.println(getClass().getSimpleName() + " onInterceptTouchEvent ");
        return super.onInterceptTouchEvent();
    }


    @Override
    public boolean onTouchEvent() {
        System.out.println(getClass().getSimpleName() + " onTouchEvent ");
        return super.onTouchEvent();
    }

    @Override
    public ViewGroup getChildView() {
        return new ViewGroup2();
    }
}
