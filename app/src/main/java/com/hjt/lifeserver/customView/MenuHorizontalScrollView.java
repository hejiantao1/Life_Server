package com.hjt.lifeserver.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.hjt.lifeserver.R;
import com.hjt.lifeserver.util.ConvertUtils;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by John on 2016/6/2.
 */
public class MenuHorizontalScrollView extends HorizontalScrollView{
    private LinearLayout mLinearLayout;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int menuPaddingRight;
    private int menuWidth;
    private int contentWidth;
    private int screenWidth;
    private boolean isMeasure=false;
    private boolean open=false;
    public MenuHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MenuHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
/**
 * 初始化
 * */
    private void init(Context context,AttributeSet attrs,int defStyleAttr) {
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;
        //获取自定义属性
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.MenuHorizontalScrollView,defStyleAttr,0);
        int index=a.getIndexCount();
        for(int i=0;i<index;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.MenuHorizontalScrollView_menuPaddingRight:
                    menuPaddingRight=a.getDimensionPixelSize(attr, ConvertUtils.dp2px(context,50));
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isMeasure) {
            mLinearLayout = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mLinearLayout.getChildAt(0);
            mContent = (ViewGroup) mLinearLayout.getChildAt(1);
            menuWidth = screenWidth - menuPaddingRight;
            contentWidth = screenWidth;
            mMenu.getLayoutParams().width = menuWidth;
            mContent.getLayoutParams().width = contentWidth;
            isMeasure=true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     * 通过设置偏移量将滚动条隐藏
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            scrollTo(menuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
           case  MotionEvent.ACTION_UP:
               int scrollX=getScrollX();//隐藏在左边的宽度
               if(scrollX<menuWidth/2){//将菜单显示
                    smoothScrollTo(0,0);
                   open=true;
               }else {//将菜单隐藏
                   smoothScrollTo(menuWidth,0);
                   open=false;
               }
                 return true;

        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //通过属性动画实现抽屉式菜单
        float scale=l*1.0f/menuWidth;
        ViewHelper.setTranslationX(mMenu,scale*menuWidth);

    }
    /**
     * 打开菜单
     * */
    public void openMenu(){
        if(open){//是打开就返回
            open=true;
        }else {//是关闭的
            open=true;
            smoothScrollTo(0,0);
        }
    }
    /**
     * 关闭菜单
     * */
    public void closeMenu(){
        if(open){
            open=false;
            smoothScrollTo(menuWidth,0);
        }
    }
    /**
     * 切换菜单
     * */
    public void toggle(){
        if(open){//是打开的
            closeMenu();
        }else{//是关闭的
            openMenu();
        }
    }
}
