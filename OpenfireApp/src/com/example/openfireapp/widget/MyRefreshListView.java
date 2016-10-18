package com.example.openfireapp.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MyRefreshListView extends ListView {

	private boolean isLoading = false;
	private ProgressBar progressBar;
	private IReflashListener reflashListener;
	
	public MyRefreshListView(Context context) {
		this(context, null);
	}

	public MyRefreshListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public MyRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		progressBar = new ProgressBar(context);
		progressBar.setIndeterminate(false);
		
		//设置渐变
        /*ClipDrawable d = new ClipDrawable(new ColorDrawable(Color.YELLOW), Gravity.LEFT, ClipDrawable.HORIZONTAL);
		progressBar.setProgressDrawable(new ColorDrawable(Color.YELLOW));//设置ProgressBar
        progressBar.setIndeterminateDrawable(d);*///设置背景颜色
		
	}

	public void finishRefresh(){
		removeHeaderView(progressBar);
		isLoading = false;
	}
	
	public void finishLoadmore(){
		removeFooterView(progressBar);
		isLoading = false;
	}
	
	private float sRawY = 0, eRawY = 0;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.i("onTouchEvent", "onTouchEvent");
		int action = event.getAction();
		switch (action){
			case MotionEvent.ACTION_DOWN:
				//Log.i("ACTION_DOWN", "ACTION_DOWN");
				sRawY = event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				//Log.i("eRawX", "ACTION_MOVE:"+(eRawY - sRawY));
				break;
			case MotionEvent.ACTION_UP:
				//Log.i("eRawY - sRawY", "ACTION_UP:"+(eRawY - sRawY));
				
				if(isLoading){
					if (eRawY - sRawY > 0){
						removeHeaderView(progressBar);
						isLoading = false;
						//return true;
						break;
					}else if(eRawY - sRawY < 0){
						removeFooterView(progressBar);
						isLoading = false;
						//return true;
						break;
					}
				}
				
				eRawY = event.getRawY();
				/*if (eRawY - sRawY < 0 && getLastVisiblePosition() == (getCount() - 1)){
					//上拉加载
					addFooterView(progressBar);
					if(reflashListener!=null){
					     reflashListener.onLoadmore();
					}
					isLoading = true;
					//return true;
				} else */if(eRawY - sRawY > 0 && getFirstVisiblePosition() == 0){
					//下拉刷新
					addHeaderView(progressBar);
					if(reflashListener!=null){
						reflashListener.onReflash();
					}
					isLoading = true;
					//return true;
				}
				break;
		}
		
		return super.onTouchEvent(event);
	}
	
	public void setIReflashListener(IReflashListener iReflashListener){
		this.reflashListener = iReflashListener;
	}
	
	public interface IReflashListener{
		public void onReflash();
		public void onLoadmore();
	}
	
}
