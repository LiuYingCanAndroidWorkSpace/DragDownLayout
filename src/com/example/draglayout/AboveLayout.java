package com.example.draglayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.draglayout.interfac.DragDownListener;


/**
 * Created by yingcan.liu on 2015/12/24.
 */
public class AboveLayout extends RelativeLayout {

	protected int maxHeaderHeight;
	protected boolean interceptContentScroll = false;
	private Thread reFloatThread;
	private boolean isPause = false;
	protected DragDownListener mListener;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (getScrollY() + msg.what > 0){
				scrollToY(0);
			} else {
				scrollToY(getScrollY() + msg.what);
			}
		}
	};

	public AboveLayout(Context context, int maxHeader, int contentHeight, View contentView) {
		super(context);
		setClickable(true);
		setBackgroundColor(Color.TRANSPARENT);

		maxHeaderHeight = maxHeader;

		if (contentView == null) {
			contentView = new View(context);                        //demo
			contentView.setBackgroundColor(Color.YELLOW);
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, contentHeight);
		contentView.setLayoutParams(params);
		addView(contentView);
	}

	protected void initView(){

	}

	public void setDragDownListener(DragDownListener listener){
		mListener = listener;
	}

	private PointF previousPoint = new PointF();
	private PointF nowPoint = new PointF();
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (!interceptContentScroll) {
			switch (ev.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				previousPoint.x = ev.getX();
				previousPoint.y = ev.getY();
				isPause = true;
				break;
			case MotionEvent.ACTION_MOVE:
				nowPoint.x = ev.getX();
				nowPoint.y = ev.getY();
				int distanceY = (int) (nowPoint.y - previousPoint.y);
				int traY = getScrollY();
				distanceY = -(distanceY - traY);
				if (distanceY > 0){                             //如果是向上滑的则无效
					scrollToY(0);
				return super.dispatchTouchEvent(ev);
				} else if(Math.abs(distanceY) > maxHeaderHeight){
					scrollToY(-maxHeaderHeight);
					return super.dispatchTouchEvent(ev);
				}
				scrollToY(distanceY);
				previousPoint.x = ev.getX();
				previousPoint.y = ev.getY();
				break;
			case MotionEvent.ACTION_UP:
				isPause = false;
				if (reFloatThread == null || !reFloatThread.isAlive()){
					reFloatThread = new Thread(){
						@Override
						public void run() {
							int a = getScrollY();
							while (a < 0){
								if (isPause)
									continue;
								try {
									a = getScrollY();
									mHandler.sendEmptyMessage(10);
									sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}

						}
					};
					reFloatThread.start();
				}
				break;
			default:
				break;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	private void scrollToY(int y){
		scrollTo(0, y);
		mListener.onDrag(y);
	}
}
