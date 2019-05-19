package jy.com.libcustomview.contactlist;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * created by taofu on 2019-05-15
 **/
public class SliderView extends View {

    private static final String[] CONTENT = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    // private static final String [] CONTENT =  {"A","B","C","D","E","F"};


    private WindowManager mWindowManager;
    private TextView mPopView;
    private WindowManager.LayoutParams mWindowParams;
    private Paint mCharPaint; // 画 27 个字符需要用的画笔
    private Paint mFillPaint; // 话圆角背景的画笔
    private Paint mStrokePaint; // 画 圆角矩形边框的画笔
    private Paint mSelectedCharPaint;// 画选中字体的画笔
    private ArrayList<Char> mCharArrayList;
    private RectF mRounderRect = new RectF(); // 换圆角矩形背景需要用到

    private OnSelectedCharListener mOnSelectedCharListener;

    private int mWidth; // 控件的宽度
    private int mHeight; // 控件的高度
    private int mDefaultTextSize; // 默认 27个字符的大小
    private int mGridHeight, mGridWidth; //27 个格子的宽高
    private int mCharWidth, mCharHeight; // 每一个字符（可见区域）的宽高
    private int mLeftPadding, mRightPadding; // 格子内，字符距离格子左右的边距
    private int mOffset; // 换圆角矩形和圆角背景需要的偏移量。因为画圆角矩形时 线的宽度有一半不在mRounderRect 区域内
    private int mLocationInAppX,mLocationInAppY;// 我们滑动条在 app 窗口（非屏幕）中的位置
    private int mStatusBarHeight;
    private boolean mIsPopShow;


    public SliderView(Context context) {
        super(context);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


    public void setOnSelectedCharListener(OnSelectedCharListener onSelectedCharListener) {
        this.mOnSelectedCharListener = onSelectedCharListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (mWidth != 0) { // 50
            int width = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
            setMeasuredDimension(width, heightMeasureSpec);// 设置控件自身的宽高
        } else {
            int width = MeasureSpec.makeMeasureSpec(1, MeasureSpec.EXACTLY);
            setMeasuredDimension(width, heightMeasureSpec);
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mHeight == 0) {
            init();
        } else {
            // 画圆角矩形背景
            canvas.drawRoundRect(mRounderRect, mRounderRect.width() / 2, mRounderRect.width() / 2, mFillPaint);
            // 画圆角矩形边框
            canvas.drawRoundRect(mRounderRect, mRounderRect.width() / 2, mRounderRect.width() / 2, mStrokePaint);



            // 循环画27个字符
            for (Char c : mCharArrayList) {

                if(c.selected){
                    canvas.drawCircle( c.rect.width() /2, c.rect.top + c.rect.height() /2, Math.min(c.rect.width() / 2, c.rect.height() /2), mSelectedCharPaint);
                }
                canvas.drawText(c.value, c.x, c.y, mCharPaint); // 画文字
            }


        }

    }


    private void init() {

        mHeight = getHeight();
        mGridHeight = mHeight / CONTENT.length; // 每一个格子的高度

        mDefaultTextSize = mGridHeight / 2; // 默认字体的高度为格子的高度的1/2
        mCharPaint = new Paint();
        mCharPaint.setTextSize(mDefaultTextSize);
        mCharPaint.setColor(Color.parseColor("#A6A6A6"));
        mCharPaint.setAntiAlias(true);
        mCharPaint.setTextAlign(Paint.Align.CENTER);
        mOffset = 1;


        mFillPaint = new Paint();
        mFillPaint.setColor(Color.WHITE);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setStyle(Paint.Style.FILL); // 画圆角矩形背景为填充
        mFillPaint.setStrokeWidth(mOffset * 2);


        mStrokePaint = new Paint();
        mStrokePaint.setColor(Color.parseColor("#E1E1E1"));
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mOffset * 2);



        mSelectedCharPaint = new Paint();
        mSelectedCharPaint.setAntiAlias(true);
        mSelectedCharPaint.setStyle(Paint.Style.FILL);
        mSelectedCharPaint.setStrokeWidth(1);
        mSelectedCharPaint.setColor(Color.parseColor("#F6E6D7"));


        mCharWidth = (int) mCharPaint.measureText("A");// 求字体的宽度

        mLeftPadding = mRightPadding = (mCharWidth); // 格子内字体左右两边的padding 为 字体的 1/2
        mGridWidth = (mLeftPadding + mRightPadding + mCharWidth); // 格子的宽度为 字体的宽度加上左右两边的边距

        mWidth = mGridWidth; // 我们滑动条控件的宽度和格子的宽度一样

        // 用于画圆角矩形背景和边框的rect
        mRounderRect.left = mOffset;
        mRounderRect.top = mOffset;
        mRounderRect.right = mWidth - mOffset;
        mRounderRect.bottom = mHeight - mOffset;


        mStatusBarHeight = getStatusBarHeight();


        int [] location = new int[2];
        // 获取滑动条在屏幕的位置，这个位置的y 值是包含了状态栏的高度
        getLocationInWindow(location);

        mLocationInAppX = location[0];
        mLocationInAppY = location[1] - mStatusBarHeight;



        requestLayout();// 会让我们控件重新计算大小，

        initContent();


    }


    private String mPreSelectedChar = "NULL";


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                String value = getValue((int) event.getX(), (int) event.getY());

                if (!TextUtils.isEmpty(value)) {

                    if(!mPreSelectedChar.equals(value)){
                        mPreSelectedChar = value;

                        addOrUpdatePopView(value, (int) event.getRawY());

                        invalidate();// 刷新界面，让我们界面重新绘制，on draw 方法会执行

                        // 把我们的值回调给 activity，通知 recycler view 滑动到指定的姓
                        if(mOnSelectedCharListener != null){
                            mOnSelectedCharListener.onSelected(value);
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                removePopView();
                break;
            }
        }
        return true;
    }






    /**
     * 获取 down 和 move 的点在 27个格子中对应的 字母
     * @param x
     * @param y
     * @return
     */
    private String getValue(int x, int y) {
        String value = null;
        for (Char c : mCharArrayList) {
            if (c.rect.contains(x, y)) {
                value = c.value;
                c.selected = true;
            } else {
                c.selected = false;

            }
        }
        return value;
    }


    private void addOrUpdatePopView(String value,int y) {

        if (TextUtils.isEmpty(value)) {
            return;
        }

        if (mWindowManager == null) {
            mWindowManager = ((Activity) getContext()).getWindowManager();

            mWindowParams = new WindowManager.LayoutParams();

            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;     // 提示类型,重要
            mWindowParams.format = PixelFormat.RGBA_8888;
            mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
            mWindowParams.flags = mWindowParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

            mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角

            int w = dp2Px(getContext(), 60);
            mWindowParams.width = w;
            mWindowParams.height = w;

            mWindowParams.x =  getLeft() - 2 * w;


        }

        if (mPopView == null) {
            mPopView = new TextView(getContext());
            mPopView.setBackgroundColor(Color.BLACK);
            mPopView.setAlpha(0.5f);
            mPopView.setGravity(Gravity.CENTER);
            mPopView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
            mPopView.setTextColor(Color.WHITE);
        }


        mWindowParams.y = Math.min(mLocationInAppY + mHeight - mWindowParams.height , y - mStatusBarHeight);
        //mWindowParams.x = 0;
        //mWindowParams.y = 0;


        if (!mIsPopShow) {
            mWindowManager.addView(mPopView, mWindowParams);
            mIsPopShow = true;
        }


        mPopView.setText(value);
        mWindowManager.updateViewLayout(mPopView, mWindowParams);

    }
    private void removePopView() {
        if (mIsPopShow) {
            for (Char c : mCharArrayList) {
                c.selected = false;
            }
            invalidate();
            mWindowManager.removeView(mPopView);
            mIsPopShow = false;
        }
    }

    private void initContent() {
        Char c;
        Rect rect;
        int sum = 0;
        mCharArrayList = new ArrayList<>();
        for (String s : CONTENT) {
            rect = new Rect(0, sum, mGridWidth, sum + mGridHeight);
            sum += mGridHeight;
            c = new Char(s, rect);
            mCharArrayList.add(c);
        }
    }

    public class Char {

        private String value; // A,B,C
        private Rect rect;
        private int x;
        private int y;
        private int baseLine;

        private boolean selected  ;


        public Char(String value, Rect rect) {
            this.value = value;
            this.rect = rect;


            x = rect.width() / 2;

            Paint.FontMetrics fontMetrics = mCharPaint.getFontMetrics();

            // 字符的可视化高度
            int cH = (int) -(fontMetrics.ascent + fontMetrics.descent);


            y = rect.top + (rect.height() / 2 + cH / 2);

            baseLine = y;
        }
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    private int getStatusBarHeight() {
        Rect rectangle = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density; //当前屏幕密度因子
        return (int) (dp * scale + 0.5f);
    }



    public interface OnSelectedCharListener {

        void onSelected(String value);

    }
}
