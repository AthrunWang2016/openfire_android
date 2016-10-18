package com.example.openfireapp.widget;

import com.example.openfireapp.R;
import com.example.openfireapp.entity.ChatEntity;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseItemView {

	protected View convertView;
	protected ChatEntity chatEntity;
	protected boolean isMyself;
	protected Context context;
	
	public BaseItemView(Context context,ChatEntity chatEntity,boolean isMyself) {
		this.chatEntity = chatEntity;
		this.isMyself = isMyself;
		this.context = context;
		
		initView();
		initBaseView();
		//setUpView();
		
	}
	
	/**
	 * 初始化基类view
	 */
	private void initBaseView(){
		TextView tv_name= (TextView) convertView.findViewById(R.id.tv);
		tv_name.setText(chatEntity.getFrom());
	}
	
	/**
	 * 初始化子类view
	 */
    public abstract void initView();
    
	/**
	 * 子类view设置类容
	 */
    public abstract View setUpView(String text);
    
}
