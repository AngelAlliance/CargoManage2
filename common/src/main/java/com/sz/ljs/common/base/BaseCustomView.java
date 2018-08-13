package com.sz.ljs.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by 刘劲松 on 2018/8/8.
 * 自定义视图基础类
 */

public class BaseCustomView extends LinearLayout {
    public BaseCustomView(Context context) {
        this(context, null, 0);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
