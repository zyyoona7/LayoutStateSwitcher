package com.zyyoona7.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zyyoona7 on 2017/9/7.
 *
 * 布局多状态切换View
 */

public class ViewStateSwitcher extends View {

    public ViewStateSwitcher(Context context) {
        super(context);
    }

    public ViewStateSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewStateSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewStateSwitcher, defStyleAttr, 0);

        setId(a.getResourceId(R.styleable.ViewStateSwitcher_android_id, NO_ID));
        a.recycle();

        setVisibility(GONE);
        setWillNotDraw(true);
    }
}
