package com.example.openfireapp;

import com.example.openfireapp.LoginActivity.MyOnClickListener;
import com.example.openfireapp.fragment.ChatroomFragment;
import com.example.openfireapp.fragment.ContactsFragment;
import com.example.openfireapp.fragment.ConversationFragment;
import com.example.openfireapp.fragment.SettingFragment;
import com.example.openfireapp.utils.MethUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	
	private int which = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setView();
		setListener();

		tv1.setTextColor(Color.BLACK);
		tv2.setTextColor(Color.WHITE);
		tv3.setTextColor(Color.WHITE);
		tv4.setTextColor(Color.WHITE);
		ConversationFragment conversationFragment = new ConversationFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//把当前Fragment替换成其他Fragment
		transaction.add(R.id.linearLayout2, conversationFragment);
		transaction.show(conversationFragment);
		//transaction.addToBackStack(null);
		transaction.commit();
		
	}

	private void setView() {
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
	}

	private void setListener() {
		tv1.setOnClickListener(new MyOnClickListener());
		tv2.setOnClickListener(new MyOnClickListener());
		tv3.setOnClickListener(new MyOnClickListener());
		tv4.setOnClickListener(new MyOnClickListener());
	}

	private void selectWhich(){
		switch (which) {
			case 0:
				//Conversation
				tv1.setTextColor(Color.BLACK);
				tv2.setTextColor(Color.WHITE);
				tv3.setTextColor(Color.WHITE);
				tv4.setTextColor(Color.WHITE);
				MethUtils.setupView(R.id.linearLayout2, new ConversationFragment(), MainActivity.this);
				break;
			case 1:
				//Contacts
				tv1.setTextColor(Color.WHITE);
				tv2.setTextColor(Color.BLACK);
				tv3.setTextColor(Color.WHITE);
				tv4.setTextColor(Color.WHITE);
				MethUtils.setupView(R.id.linearLayout2, new ContactsFragment(), MainActivity.this);
				break;
			case 2:
				//Chatroom
				tv1.setTextColor(Color.WHITE);
				tv2.setTextColor(Color.WHITE);
				tv3.setTextColor(Color.BLACK);
				tv4.setTextColor(Color.WHITE);
				MethUtils.setupView(R.id.linearLayout2, new ChatroomFragment(), MainActivity.this);
				break;
			case 3:
				//Setting
				tv1.setTextColor(Color.WHITE);
				tv2.setTextColor(Color.WHITE);
				tv3.setTextColor(Color.WHITE);
				tv4.setTextColor(Color.BLACK);
				MethUtils.setupView(R.id.linearLayout2, new SettingFragment(), MainActivity.this);
				break;
		}
	}
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv1:
					which = 0;
					break;
				case R.id.tv2:
					which = 1;
					break;
				case R.id.tv3:
					which = 2;
					break;
				case R.id.tv4:
					which = 3;
					break;
			}
			selectWhich();
		}
	}
	
}
