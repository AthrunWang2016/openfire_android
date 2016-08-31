package com.example.openfireapp;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.openfireapp.adapter.ChatListAdapter;
import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.db.PrivateChatMsgDAO;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.ConUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	//RosterEntry rosterEntry;
	//private MyRosterEntry myRosterEntry;
	private String chatUser;
	
	private TextView tv;
	private TextView tv_back;
	private EditText ev;
	
	private ListView listView01;
    private MaterialRefreshLayout refresh;
    
    private ChatManager chatmanager = ChatManager.getInstanceFor(TApplication.connection);
    private Chat chat;
    
    private List<ChatEntity> lists;
    private ChatListAdapter chatListAdapter;
    
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(ConUtils.chatBroadcastReceiverReserve)){
				chatListAdapter.addData(new ChatEntity(intent.getStringExtra("RosterEntry"),intent.getStringExtra("chatBody")));
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
		tv_back = (TextView) findViewById(R.id.tv_back);
		ev = (EditText) findViewById(R.id.editText1);
		listView01 = (ListView) findViewById(R.id.listView01);
        refresh = (MaterialRefreshLayout) findViewById(R.id.refresh);
        refresh.setIsOverLay(false);
        refresh.setWaveShow(false);
        refresh.setLoadMore(true);
        
		tv.setText(chatUser);
		
		chat = chatmanager.createChat(chatUser+"@"+ConUtils.SERVICENAME);
	}

	private void setListener() {
		
		tv_back.setOnClickListener(new MyClickListener());
		
		refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
			@Override
			public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
				//下拉刷新...
				refresh.finishRefresh();
			}
		   @Override
	       public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
	          //上拉加载更多...
			   refresh.finishRefreshLoadMore();
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
		List<ChatEntity> chatLists = PrivateChatMsgDAO.getInstance(ChatActivity.this).askPrivateChatMsg(chatUser);
		if(chatLists == null){
			lists = new ArrayList<ChatEntity>();
		}else{
			lists = chatLists;
		}
		chatListAdapter = new ChatListAdapter(ChatActivity.this, lists);
		
		listView01.setAdapter(chatListAdapter);
		String chatBody = getIntent().getStringExtra("chatBody");
		if(!(chatBody==null||chatBody.equals(""))){
			chatListAdapter.addData(new ChatEntity(chatUser, chatBody));
		}
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
					senfMsg(evStr);
					chatListAdapter.addData(new ChatEntity(TApplication.currentUser.getName(),evStr));
					ev.setText("");
					ev.setFocusable(false);
				} catch (NotConnectedException e) {
					e.printStackTrace();
					Toast.makeText(ChatActivity.this, "消息发送失败", 0).show();
				}
				break;
		}
	}
	
	private void senfMsg(String body) throws NotConnectedException{
		chat.sendMessage(body);
		Message msg = new Message();
		msg.setFrom(TApplication.currentUser.getName());
        msg.setBody(body);
        msg.setTo(chatUser);
        PrivateChatMsgDAO.getInstance(ChatActivity.this).savePrivateChatMsg(msg);
        //先查询Type再修改
        ChatRosterDAO.getInstance(ChatActivity.this).updateChatRoster(chatUser,1);
	}
	
	class MyClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_back:
					finish();
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
	
}
