package com.example.openfireapp.widget;

import com.example.openfireapp.R;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.utils.FileToStringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageItemView extends BaseItemView {

	private ImageView img;
	
	public ImageItemView(Context context, ChatEntity chatEntity,boolean isMyself) {
		super(context, chatEntity, isMyself);
	}

	@Override
	public void initView() {
		convertView = View.inflate(context, isMyself?R.layout.item_img_reserve:R.layout.item_img_send, null);
		img = (ImageView) convertView.findViewById(R.id.tv2);
	}

	@Override
	public View setUpView(String body) {
		//base64解密后缓存到本地bitmap
		Bitmap bitmap = FileToStringUtils.getInstance().imageStringToBitmap(body);
		img.setImageBitmap(bitmap);
		return convertView;
	}

}
