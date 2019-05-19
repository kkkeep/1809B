package k.mr.com.libdispatchevent.mock;

/*
 * created by taofu on 2019-05-14
 **/
public class ViewGroup {


    public boolean dispatchOnTouchEvent(){

        boolean isIntercept = onInterceptTouchEvent();
        boolean childIsProcess = false;
        if(!isIntercept){

            ViewGroup child = getChildView();


            if(child != null){
                childIsProcess =  child.dispatchOnTouchEvent();
            }else{
                childIsProcess = false;
            }

        }
        if(!childIsProcess){
            return onTouchEvent();
        }

        return false;
    }


    public boolean onInterceptTouchEvent(){

        return false;
    }


    public boolean onTouchEvent(){

            return false;
    }


    public ViewGroup getChildView(){
        return null;
    }
}
