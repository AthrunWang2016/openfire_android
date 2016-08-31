package com.example.openfireapp;

import java.io.IOException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;

import com.example.openfireapp.entity.CurrentUser;
import com.example.openfireapp.utils.LogUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private TextView tv_back;
	private TextView tv_register;
	private EditText editText1;
	private EditText editText2;
	
	private String et1Str;
	private String et2Str;
	
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sp = getSharedPreferences("userInfo", 0);
		if(sp.getBoolean("isLogin", false)){
			et1Str = sp.getString("USER_NAME", "");
			et2Str = sp.getString("PASSWORD", "");
			login();
		}
		
		setContentView(R.layout.activity_login);
		
		setView();
		setListener();
		
	}

	private void setView() {
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_register = (TextView) findViewById(R.id.tv_register);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		if(LogUtils.isLog){
			editText1.setText("a");
			editText2.setText("123456");
		}
	}

	private void setListener() {
		tv_back.setOnClickListener(new MyOnClickListener());
		tv_register.setOnClickListener(new MyOnClickListener());
	}

	public void doClick(View v){
		switch (v.getId()) {
			case R.id.bt_login:
				et1Str = editText1.getText().toString();
				et2Str = editText2.getText().toString();
				if(TextUtils.isEmpty(et1Str)||TextUtils.isEmpty(et2Str)){
					Toast.makeText(this, "登录成功", 0).show();
					break;
				}
				/*try {
					TApplication.connection.login(et1Str, et2Str);
					Toast.makeText(this, "登录成功", 0).show();
					TApplication.currentUser = new CurrentUser();
				   TApplication.currentUser.setName(et1Str);
				   TApplication.currentUser.setPassword(et2Str);
				   //下次自动登录则通讯录只保存一次
				   TApplication.saveRoster(this);
					startActivity(new Intent(this,MainActivity.class));
					finish();
				} catch (XMPPException e) {
					Toast.makeText(this, "登录失败", 0).show();
					e.printStackTrace();
				} catch (SmackException e) {
					Toast.makeText(this, "登录失败", 0).show();
					e.printStackTrace();
				} catch (IOException e) {
					Toast.makeText(this, "登录失败", 0).show();
					e.printStackTrace();
				}*/
				login();
				break;
		}
	}
	
	private void login() {
		try {
			TApplication.connection.login(et1Str, et2Str);
			Toast.makeText(this, "登录成功", 0).show();
			TApplication.currentUser = new CurrentUser();
		   TApplication.currentUser.setName(et1Str);
		   TApplication.currentUser.setPassword(et2Str);
		   //下次自动登录则通讯录只保存一次
		   TApplication.saveRoster(this);
		   
		   Editor editor = sp.edit();
		   editor.putString("USER_NAME", et1Str);
		   editor.putString("PASSWORD", et2Str);
		   editor.putBoolean("isLogin", true);
		   editor.commit();
		   
			startActivity(new Intent(this,MainActivity.class));
			finish();
		} catch (XMPPException e) {
			Toast.makeText(this, "登录失败", 0).show();
			e.printStackTrace();
		} catch (SmackException e) {
			Toast.makeText(this, "登录失败", 0).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "登录失败", 0).show();
			e.printStackTrace();
		}
	}
	
	/*private void login(String phone, String code) throws XMPPException, SmackException, IOException {
		   //Toast.makeText(this, TApplication.connection.isConnected()?"true":"false", 0).show();
			TApplication.reConnect();
		   TApplication.connection.login(phone, code);
		   boolean isSuccess = TApplication.connection.isAuthenticated();
		   if(isSuccess){
			   Toast.makeText(this, "登录成功", 0).show();
			   TApplication.currentUser = new CurrentUser();
			   TApplication.currentUser.setName(phone);
			   TApplication.currentUser.setPassword(code);
			   startActivity(new Intent(this,MainActivity.class));
		   }else{
			   Toast.makeText(this, "登录失败", 0).show();
		   }
	}*/
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_back:
					finish();
					break;
				case R.id.tv_register:
					startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
					break;
			}
		}
	}
	
}
