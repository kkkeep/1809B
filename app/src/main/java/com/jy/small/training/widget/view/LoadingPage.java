package com.jy.small.training.widget.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jy.small.training.R;

/*
 * created by taofu on 2018/11/28
 **/
public class LoadingPage extends ConstraintLayout {

    public static final int MODE_ONLY_SHOW_LOGING = 1;
    public static final int MODE_SHOW_BUTTON_AND_TEXT = 2;


    private ProgressBar mPbLoading;

    private Button mBtnAction;

    private TextView mTvMsg;


    private OnReloadCallBack mOnReloadCallBack;






    public LoadingPage(Context context) {
        super(context);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mPbLoading = findViewById(R.id.loading_pb_loading_view);
        mBtnAction = findViewById(R.id.loading_btn_reload);
        mTvMsg = findViewById(R.id.loading_tv_msg);


        mBtnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnReloadCallBack != null){
                    mOnReloadCallBack.reload();
                    startLoading(MODE_SHOW_BUTTON_AND_TEXT);
                }

            }
        });

        startLoading();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);

         return true;
    }

    public void startLoading(){
        startLoading(MODE_ONLY_SHOW_LOGING);
    }

    public void startLoading(int mode){
        if(mode == MODE_ONLY_SHOW_LOGING){
            mBtnAction.setVisibility(GONE);
            mTvMsg.setVisibility(GONE);
            mPbLoading.setVisibility(VISIBLE);
            setBackgroundColor(Color.TRANSPARENT);
        }else if(mode == MODE_SHOW_BUTTON_AND_TEXT){
            mBtnAction.setVisibility(GONE);
            mPbLoading.setVisibility(VISIBLE);
            mTvMsg.setVisibility(VISIBLE);
            mTvMsg.setText(R.string.text_loading_loading);
            setBackgroundColor(Color.WHITE);
        }
    }


    public void onError(){
        onError(getContext().getString(R.string.text_loading_error));
    }

    public void onError(String msg){
        onError(null, msg);
    }


    public void onError(OnReloadCallBack callBack,String msg){

        mTvMsg.setText(msg);
        mTvMsg.setVisibility(VISIBLE);
        mBtnAction.setVisibility(VISIBLE);
        mPbLoading.clearAnimation();
        mPbLoading.setVisibility(GONE);
        mOnReloadCallBack = callBack;

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPbLoading.clearAnimation();
    }


    public interface  OnReloadCallBack{

        void reload();
    }
}
