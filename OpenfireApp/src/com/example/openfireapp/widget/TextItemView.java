package com.example.openfireapp.widget;

import com.example.openfireapp.R;
import com.example.openfireapp.entity.ChatEntity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TextItemView extends BaseItemView {

	private TextView text;
	
	public TextItemView(Context context, ChatEntity chatEntity,boolean isMyself) {
		super(context, chatEntity, isMyself);
	}

	@Override
	public void initView() {
		convertView = View.inflate(context, isMyself?R.layout.item_message_reserve:R.layout.item_message_send, null);
		text = (TextView) convertView.findViewById(R.id.tv2);
	}

	@Override
	public View setUpView(String body) {
		text.setText(body);
		return convertView;
	}

}
