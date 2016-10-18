package com.example.openfireapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.adapter.ChatListAdapter;
import com.example.openfireapp.chatutils.ChatUtils;
import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.db.PrivateChatMsgDAO;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.ConUtils;
import com.example.openfireapp.utils.FileToStringUtils;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.utils.MethUtils;
import com.example.openfireapp.widget.MyRefreshListView;
import com.example.openfireapp.widget.MyRefreshListView.IReflashListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	private String TAG = "ChatActivity";
	
	//RosterEntry rosterEntry;
	//private MyRosterEntry myRosterEntry;
	private String chatUser;
	
	private TextView tv;
	private TextView tv_back;
	private TextView tv02;
	private EditText ev;
	private RelativeLayout relativeLayout3;
	
	private MyRefreshListView listView01;
    
    private ChatManager chatmanager = ChatManager.getInstanceFor(TApplication.connection);
    private Chat chat;
    
    private List<ChatEntity> lists;
    private ChatListAdapter chatListAdapter;
    
    private int page = 0;
    
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(ConUtils.chatBroadcastReceiverReserve)){
				chatListAdapter.addData(new ChatEntity(intent.getStringExtra("RosterEntry"),intent.getStringExtra("chatBody")));
				//滑至底部
				listView01.setSelection(listView01.getBottom());
				Intent broadcastintent = new Intent();
				broadcastintent.setAction(ConUtils.chatBroadcastReceiverRead);
				sendBroadcast(broadcastintent);
			}
		}
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		//TApplication.isOnChatActivity = true;
		//myRosterEntry = (MyRosterEntry) getIntent().getSerializableExtra("RosterEntry");
		chatUser = getIntent().getStringExtra("RosterEntry");
		
		//ChatRosterDAO.getInstance(ChatActivity.this).updateChatRoster(chatUser,1);
		
		setView();
		setListener();
		setListViewData();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConUtils.chatBroadcastReceiverReserve);
		registerReceiver(myBroadcastReceiver, filter );
		
	}

	private void setView() {
		tv = (TextView) findViewById(R.id.tv);
		tv02 = (TextView) findViewById(R.id.tv02);
		tv_back = (TextView) findViewById(R.id.tv_back);
		ev = (EditText) findViewById(R.id.editText1);
		relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
		listView01 = (MyRefreshListView) findViewById(R.id.listView01);
        
		tv.setText(chatUser);
		
		chat = chatmanager.createChat(chatUser+"@"+ConUtils.SERVICENAME);
	}

	private void setListener() {
		
		tv_back.setOnClickListener(new MyClickListener());
		tv02.setOnClickListener(new MyClickListener());
		relativeLayout3.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if(!arg1){
					relativeLayout3.setVisibility(View.GONE);
				}
			}
		});
		
		listView01.setIReflashListener(new IReflashListener() {
			@Override
			public void onReflash() {
				page = page + 1;
				List<ChatEntity> chatLists = PrivateChatMsgDAO.getInstance(ChatActivity.this).askPrivateChatMsgByPage(chatUser,page);
				if(chatLists != null && chatLists.size() != 0){
					chatListAdapter.addData(chatLists);
				}
				listView01.finishRefresh();
			}
			@Override
			public void onLoadmore() {
				
			}
		});
		
		/*chatmanager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat cc, boolean bb) {
                //当收到来自对方的消息时触发监听函数
                cc.addMessageListener(new ChatMessageListener() {
                    @Override
                    public void processMessage(Chat cc, Message mm) {
                    	if(mm.getBody()==null||mm.getBody().equals("")){
                    		return;
                    	}
                    	chatListAdapter.addData(new ChatEntity(mm.getFrom(),mm.getBody()));
                    }
                });
            }
        });*/
	}

	private void setListViewData() {
		//List<ChatEntity> chatLists = PrivateChatMsgDAO.getInstance(ChatActivity.this).askPrivateChatMsg(chatUser);
		List<ChatEntity> chatLists = PrivateChatMsgDAO.getInstance(ChatActivity.this).askPrivateChatMsgByPage(chatUser,page);
		if(chatLists == null){
			lists = new ArrayList<ChatEntity>();
		}else{
			lists = chatLists;
		}
		chatListAdapter = new ChatListAdapter(ChatActivity.this, lists);
		
		listView01.setAdapter(chatListAdapter);
		//滑至底部
		//listView01.setSelection(listView01.getBottom());
		//滑至底部
		//listView01.setSelection(listView01.getBottom());
		/*String chatBody = getIntent().getStringExtra("chatBody");
		if(!(chatBody==null||chatBody.equals(""))){
			chatListAdapter.addData(new ChatEntity(chatUser, chatBody));
			//滑至底部
				listView01.setSelection(listView01.getBottom());
		}*/
	}

	public void doClick(View v){
		switch (v.getId()) {
			case R.id.button1:
				String evStr = ev.getText().toString();
				if(TextUtils.isEmpty(evStr)){
					Toast.makeText(ChatActivity.this, "消息不能为空", 0).show();
					return;
				}
				try {
					sendMsg(ChatUtils.MSG_TYPE_TXT+":"+evStr);
					//chatListAdapter.addData(new ChatEntity(TApplication.currentUser.getName(),evStr));
					ev.setText("");
					//ev.setFocusable(false);
				} catch (NotConnectedException e) {
					e.printStackTrace();
					Toast.makeText(ChatActivity.this, "消息发送失败", 0).show();
				}
				break;
			//发送图片
			case R.id.button2:
				setImgDialog();
				break;
			//视频聊天
			case R.id.button3:
				setVideoAudeoDialog(ChatUtils.MSG_TYPE_VIDEO,"video");
				break;
			//音频聊天
			case R.id.button4:
				setVideoAudeoDialog(ChatUtils.MSG_TYPE_AUDEO,"Audeo");
				break;
		}
	}
	
	private void sendMsg(String body) throws NotConnectedException{
		chat.sendMessage(body);
		Message msg = new Message();
		msg.setFrom(TApplication.currentUser.getName());
        msg.setBody(body);
        msg.setTo(chatUser);
        
        chatListAdapter.addData(new ChatEntity(TApplication.currentUser.getName(), body));
        //滑至底部
		listView01.setSelection(listView01.getBottom());
        PrivateChatMsgDAO.getInstance(ChatActivity.this).savePrivateChatMsg(msg);
        
        ChatRosterDAO.getInstance(ChatActivity.this).updateChatRoster(chatUser,1);
	}
	
	class MyClickListener implements OnClickListener {
		boolean isRelativeLayoutVisible = false;
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_back:
					finish();
					break;
				case R.id.tv02:
					if(isRelativeLayoutVisible){
						relativeLayout3.setVisibility(View.GONE);
						isRelativeLayoutVisible = false;
					}else{
						relativeLayout3.setVisibility(View.VISIBLE);
						isRelativeLayoutVisible = true;
					}
					break;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//TApplication.isOnChatActivity = false;
		unregisterReceiver(myBroadcastReceiver);
		if(TApplication.lists.size()==0){
			startActivity(new Intent(ChatActivity.this,MainActivity.class));
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 20:
				if (resultCode == RESULT_OK) {
					// 获取相机返回的数据，并转换为Bitmap图片格式
					//bitmap = data.getParcelableExtra("data");
					Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp.jpg");
					if(bitmap == null){
						Toast.makeText(this, "FileNotFound", 0).show();
						return;
					}else{
						try {
							String imgMsg = FileToStringUtils.getInstance().imageBitmapToString(bitmap);
							sendMsg(ChatUtils.MSG_TYPE_IMG+":"+imgMsg);
						} catch (NotConnectedException e) {
							Toast.makeText(this, "ServerConnectedError", 0).show();
							e.printStackTrace();
						}
					}
				}
				break;
			case 40:
				if ( resultCode == RESULT_OK && null != data) {
					Uri selectedImage = data.getData();
					try {
						Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
						String imgMsg = FileToStringUtils.getInstance().imageBitmapToString(bitmap);
						sendMsg(ChatUtils.MSG_TYPE_IMG+":"+imgMsg);
					} catch (FileNotFoundException e) {
						Toast.makeText(this, "FileNotFound", 0).show();
						e.printStackTrace();
					} catch (IOException e) {
						Toast.makeText(this, "FileError", 0).show();
						e.printStackTrace();
					}catch (NotConnectedException e) {
						Toast.makeText(this, "ServerConnectedError", 0).show();
						e.printStackTrace();
					}
				}
				break;
			/*case 30:
				//if (resultCode == getActivity().RESULT_OK && data != null) {
				if (resultCode == RESULT_OK ) {
					//bitmap = BitmapFactory.decodeFile("/mnt/sdcard/temp.jpg");
					//iv.setImageBitmap(bitmap);
					startActivity(new Intent(MainFragmentActivity.this,PhotoSettingActivity.class));
				}
				break;*/
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setVideoAudeoDialog(final String body,String videoOrAudeo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
		builder.setMessage("Is want to "+videoOrAudeo+"online?");
		builder.setPositiveButton("cancle", null);
		builder.setNegativeButton("sure", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				try {
					sendMsg(body+":"+body);
					/*chat.sendMessage(body+":"+body);
					Message msg = new Message();
					msg.setFrom(TApplication.currentUser.getName());
			        msg.setBody(body+":"+body);
			        msg.setTo(chatUser);
			        
			        chatListAdapter.addData(new ChatEntity(TApplication.currentUser.getName(), body+":"+body));
			        
			        PrivateChatMsgDAO.getInstance(ChatActivity.this).savePrivateChatMsg(msg);*/
				} catch (NotConnectedException e) {
					e.printStackTrace();
				}
			}
		});
		builder.create().show();
	}
	
	private String picName;
	
	private void setImgDialog() {
		final String[] items = new String[] {  "拍照", "图库", "取消" };
		 AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
		 //builder.setTitle("Hard").setCancelable(false);
		 builder.setItems(items,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				switch (arg1) {
					case 0:
						picName = "/" + MethUtils.getAppName(ChatActivity.this) + "/" +System.currentTimeMillis()+".jpg";
						LogUtils.log(TAG, Environment.getExternalStorageDirectory().getAbsolutePath() + picName);
						//从拍照获取图片
						//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						//若设置这句，则data返回Null，否则直接保存图片
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + picName)));
						startActivityForResult(intent, 20);
						break;
					case 1:
						//打开图库
						Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, 40);
						break;
					case 2:
						;
						break;
				}
			}
		});
		 builder.create().show();
	}
	
}
