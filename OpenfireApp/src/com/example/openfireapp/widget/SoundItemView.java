package com.example.openfireapp.widget;

import com.example.openfireapp.R;
import com.example.openfireapp.entity.ChatEntity;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SoundItemView extends BaseItemView {

	private Button sound;
	
	public SoundItemView(Context context, ChatEntity chatEntity,boolean isMyself) {
		super(context, chatEntity, isMyself);
	}

	@Override
	public void initView() {
		convertView = View.inflate(context, isMyself?R.layout.item_message_reserve:R.layout.item_message_send, null);
		sound = (Button) convertView.findViewById(R.id.tv2);
	}

	@Override
	public View setUpView(String body) {
		sound.setText("SOUND");
		//
		return convertView;
	}

}
