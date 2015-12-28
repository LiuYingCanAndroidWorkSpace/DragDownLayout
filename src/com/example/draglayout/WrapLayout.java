package com.example.draglayout;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.draglayout.interfac.DragDownListener;

/**
 * Created by yingcan.liu on 2015/12/24.
 */
public class WrapLayout extends FrameLayout implements DragDownListener {

    private AboveLayout aboveLayout;
    private Context mContext;
    private View onShowView;                     //下拉时被展示的View

    public WrapLayout(Context context, View view, int maxHeader, AboveLayout layout) {
        super(context);
        mContext = context;
        aboveLayout = layout;
        initView(view, maxHeader);
    }

    private void initView(View view, int maxHeader) {
        if (view == null)
            return;
        onShowView = view;
        onShowView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0));
        addView(onShowView);

        aboveLayout.setDragDownListener(this);                                                                  //设置拖拉进度的回调
        addView(aboveLayout);
    }

    @Override
    public void onDrag(int scrollY) {
        onShowView.getLayoutParams().height = Math.abs(scrollY);
        onShowView.requestLayout();
    }

	@Override
	public int getHeaderHeight() {
		return onShowView.getLayoutParams().height;
	}
}
