package com.sz.ljs.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by Administrator on 2018/8/17.
 */

public class NoscrollExpandableListView extends ExpandableListView {
    public NoscrollExpandableListView(Context context) {
        super(context);
    }

    public NoscrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoscrollExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
