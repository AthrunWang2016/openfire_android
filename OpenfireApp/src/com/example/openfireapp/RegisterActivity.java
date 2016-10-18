package com.example.openfireapp;

import java.util.HashMap;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smackx.iqregister.AccountManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	private TextView tv_back;
	//�˺�
	private EditText editText1;
	//����
	private EditText editText2;
	private EditText editText3;
	
	private String et1Str;
	private String et2Str;
	private String et3Str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		setView();
		setListener();
	}

	private void setView() {
		tv_back = (TextView) findViewById(R.id.tv_back);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
	}

	private void setListener() {
		tv_back.setOnClickListener(new MyOnClickListener());
	}

	public void doClick(View v){
		switch (v.getId()) {
			case R.id.bt_register:
				et1Str = editText1.getText().toString();
				et2Str = editText2.getText().toString();
				et3Str = editText3.getText().toString();
				if(TextUtils.isEmpty(et1Str)||TextUtils.isEmpty(et2Str)||TextUtils.isEmpty(et3Str)){
					Toast.makeText(this, "请填写信息", 0).show();
					break;
				}
				if(!et2Str.equals(et3Str)){
					Toast.makeText(this, "两次密码不一致", 0).show();
					break;
				}
				try {
					register();
					Toast.makeText(this, "注册成功", 0).show();
					startActivity(new Intent(this,LoginActivity.class));
				} catch (NoResponseException e) {
					Toast.makeText(this, "注册失败", 0).show();
					e.printStackTrace();
				} catch (XMPPErrorException e) {
					Toast.makeText(this, "注册失败", 0).show();
					e.printStackTrace();
				} catch (NotConnectedException e) {
					Toast.makeText(this, "注册失败", 0).show();
					e.printStackTrace();
				}
				break;
		}
	}
	
	//xmppע�᷽��
	private void register() throws NoResponseException, XMPPErrorException, NotConnectedException{
		AccountManager accountManager = AccountManager.getInstance(TApplication.connection);
		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", et1Str);*/
		accountManager.createAccount(et1Str,et2Str);
	}
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_back:
					finish();
					break;
			}
		}
	}
	
}
