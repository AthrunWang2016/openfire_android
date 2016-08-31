package com.example.openfireapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.db.PrivateChatMsgDAO;
import com.example.openfireapp.entity.CurrentUser;
import com.example.openfireapp.utils.ConUtils;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.utils.MethUtils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class TApplication extends Application{

	public static XMPPTCPConnection connection;
	public static CurrentUser currentUser;
	
	public static List<Activity> lists = new ArrayList<Activity>();
	
	private ChatManager chatmanager;
	
	//public static boolean isOnChatActivity = false; 
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		LogUtils.isLog = true;
		
		connectChatServer();
		
	}
	
	public void connectChatServer() {
		
		 XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
         //builder.setUsernameAndPassword("bb", "123");
         builder.setHost(ConUtils.HOST).setServiceName(ConUtils.SERVICENAME).setPort(ConUtils.POST);
         //不加这行会报错，因为没有证书
         builder.setSecurityMode(SecurityMode.disabled);
         //builder.setSocketFactory(SSLSocketFactory.getDefault());
         //builder.setSecurityMode(SecurityMode.ifpossible);
         //登录后，不要自动发送出席消息（等初始化其他数据后，再发）
         builder.setSendPresence(true);
         
         builder.setDebuggerEnabled(true);
         XMPPTCPConnectionConfiguration conf = builder.build();
         
		 /*XMPPTCPConnectionConfiguration conf = XMPPTCPConnectionConfiguration.builder()
				 .setHost(ConUtils.HOST).setServiceName(ConUtils.SERVICENAME).setPort(ConUtils.POST)
			     //.setUsernameAndPassword("user", "password")
			     .setCompressionEnabled(false).build();*/
         
		connection = new XMPPTCPConnection(conf);
		registerInterceptorListener();
		
		/*try {
			connection.connect();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		reConnect();  
		//saveRoster();
	}

	public static void reConnect() {
		if(!TApplication.connection.isConnected()){
			new Thread("connect thread"){  
				   @Override  
				   public void run(){  
					   try {
						   TApplication.connection.connect();
						} catch (SmackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				  }  
				}.start();
		 }
	}
	
	public static void saveRoster(Context context) {
		Roster roster = Roster.getInstanceFor(connection);
		List<RosterEntry> rosterLists = new ArrayList<RosterEntry>();
		rosterLists.addAll(roster.getEntries());
		ChatRosterDAO.getInstance(context).saveChatRoster(rosterLists);
	}
	
	private void registerInterceptorListener() {
		//让框架的接口指向实现类
		connection.addConnectionListener(new MyConnectionListener());
		
		chatmanager = ChatManager.getInstanceFor(TApplication.connection);
		chatmanager.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat arg0, boolean arg1) {
				arg0.addMessageListener(new ChatMessageListener() {
					@Override
					public void processMessage(Chat arg0, Message arg1) {
						if(arg1.getBody()==null||arg1.getBody().equals("")){
                    		return;
                    	}
						Intent intent = new Intent();
						intent.setAction(ConUtils.chatBroadcastReceiverReserve);
						/*String fromUser = arg1.getFrom();
						int index=fromUser.indexOf('@');
				    	if(index>0)
				    		fromUser=fromUser.substring(0,index);*/
						intent.putExtra("RosterEntry", MethUtils.subUserToName(arg1.getFrom()));
						//intent.putExtra("chatBody", arg1.getBody());
						PrivateChatMsgDAO.getInstance(TApplication.this).savePrivateChatMsg(arg1);
						sendBroadcast(intent);
					}
				});
			}
		});
	}
	
	class MyConnectionListener implements ConnectionListener{
		@Override
		public void authenticated(XMPPConnection arg0, boolean arg1) {
			// TODO Auto-generated method stub
			LogUtils.log("authenticated:", arg0.toString());
		}
		@Override
		public void connected(XMPPConnection arg0) {
			// TODO Auto-generated method stub
			LogUtils.log("connected:", arg0.toString());
		}
		@Override
		public void connectionClosed() {
			// TODO Auto-generated method stub
			LogUtils.log("connectionClosed:", "openfire连接关闭");
		}
		@Override
		public void connectionClosedOnError(Exception arg0) {
			// TODO Auto-generated method stub
			LogUtils.log("connectionClosedOnError:", arg0.getMessage());
			try {
			   TApplication.connection.connect();
			} catch (SmackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void reconnectingIn(int arg0) {
			// TODO Auto-generated method stub
			LogUtils.log("reconnectingIn:", arg0+"");
		}
		@Override
		public void reconnectionFailed(Exception arg0) {
			// TODO Auto-generated method stub
			LogUtils.log("reconnectionFailed:", arg0.getMessage());
		}
		@Override
		public void reconnectionSuccessful() {
			// TODO Auto-generated method stub
			LogUtils.log("reconnectionSuccessful:", "reconnectionSuccessful");
		}
	}
	
	/*public void addActivityList(Activity activity){
		lists.add(activity);
	}*/
	
	public void exitApp(){
		for(Activity a:lists){
			if(a != null){
				a.finish();
			}
		}
	}
	
}
