package com.example.draglayout;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by yingcan.liu on 2015/12/28.
 */
public class DragDownListViewLayout extends AboveLayout {

	private ListView mListView;
	
    public DragDownListViewLayout(Context context, int maxHeader, int contentHeight, final ListView contentView) {
        super(context, maxHeader, contentHeight, contentView);
        mListView = contentView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	boolean deliver = false;
    	switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			break;
		case MotionEvent.ACTION_MOVE:
			if (mListener.getHeaderHeight() > 0) {
				deliver = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		default:
			break;
		}
    	return deliver;
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
    	if (getListViewScrollY() != 0) {
			interceptContentScroll = true;
		} else {
			interceptContentScroll = false;
		}
    	return super.dispatchTouchEvent(ev);
    }
    
    
    public int getListViewScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null ) {
            return 0;
        }
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
   }

}
