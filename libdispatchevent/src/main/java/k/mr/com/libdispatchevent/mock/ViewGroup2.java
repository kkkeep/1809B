package k.mr.com.libdispatchevent.mock;

/*
 * created by taofu on 2019-05-14
 **/
public class ViewGroup2  extends ViewGroup{

    @Override
    public boolean dispatchOnTouchEvent() {
        System.out.println(getClass().getSimpleName() + " dispatchOnTouchEvent ");
         super.dispatchOnTouchEvent();

         return false;
    }


    @Override
    public boolean onInterceptTouchEvent() {
        System.out.println(getClass().getSimpleName() + " onInterceptTouchEvent ");
        return super.onInterceptTouchEvent();
    }


    @Override
    public boolean onTouchEvent() {
        System.out.println(getClass().getSimpleName() + " onTouchEvent ");
        return true;
    }
}
