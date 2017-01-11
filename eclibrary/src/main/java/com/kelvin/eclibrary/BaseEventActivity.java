package com.kelvin.eclibrary;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseEventActivity extends Activity {

    /** 具体逻辑留给子类实现 */
    protected abstract void onViewClickEvent(View view);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case KeyEvent.ACTION_UP: {
                ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
                View view = getClickView(rootView, ev);
                if (null != view) {
                    // 找到匹配的View，且有点击事件
                    onViewClickEvent(view);
                }
            }
            break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private View getClickView(View rootView, MotionEvent event) {
        if (eventInView(rootView, event)) {
            // 判断是否是在这个View范围内
            if (rootView instanceof ViewGroup) {
                // 如果有子View的话，优先一个个去子View是否匹配
                ViewGroup viewGroup = (ViewGroup) rootView;
                final int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    // 递归去找
                    View view = getClickView(viewGroup.getChildAt(i), event);
                    if (null != view) {
                        // 找到了
                        return view;
                    }
                }
            }
            // 子类没有的话就看下自己有没有点击事件
            if (null != EventUtil.getOnClickListener(rootView)) {
                // 找到了
                return rootView;
            }
        }
        return null;
    }

    private boolean eventInView(View view, MotionEvent event) {
        if (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE) {
            return false;
        }
        int clickX = (int) event.getRawX();
        int clickY = (int) event.getRawY();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();
        if (clickX > x && clickX < (x + width) &&
                clickY > y && clickY < (y + height)) {
            return true;
        }
        return false;
    }
}
