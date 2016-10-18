package com.example.openfireapp.broadcastreceiver;

import com.example.openfireapp.ChatActivity;
import com.example.openfireapp.R;
import com.example.openfireapp.utils.ConUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyChatBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		NotificationManager updateNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		String action = intent.getAction();
		if(action.equals(ConUtils.chatBroadcastReceiverReserve)){
			/*Notification notification = new Notification();
			notification.icon = R.drawable.ic_launcher;
			notification.tickerText = "你有一条新消息";
			//intentChatActivity
			Intent intentChatActivity = new Intent(context, ChatActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intentChatActivity, 0);  
	        // 指定内容意图  
			//notification.contentIntent = contentIntent;
			notification.setLatestEventInfo(context, "你有一条新消息", "点击查看详细内容", contentIntent);  

			NotificationManager updateNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			updateNotificationManager.notify(0, notification);*/
			
			Intent intentChatActivity = new Intent(context, ChatActivity.class);
			intentChatActivity.putExtra("RosterEntry", intent.getStringExtra("RosterEntry"));
			//intentChatActivity.putExtra("chatBody", intent.getStringExtra("chatBody"));
			intentChatActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			PendingIntent contentIntent = PendingIntent.getActivity(context, ConUtils.ChatNotificationId, intentChatActivity, PendingIntent.FLAG_UPDATE_CURRENT); 
			
			Notification notification = new Notification.Builder(context)    
			         .setAutoCancel(true)    
			         .setContentTitle("你有一条新消息")    
			         .setContentText("点击查看详细内容")    
			         .setContentIntent(contentIntent)    
			         .setSmallIcon(R.drawable.ic_launcher)    
			         .setWhen(System.currentTimeMillis())    
			         .build();  
			
			updateNotificationManager.notify(ConUtils.ChatNotificationId, notification);
			
		}else if(action.equals(ConUtils.chatBroadcastReceiverReserve)){
			updateNotificationManager.cancel(ConUtils.ChatNotificationId);
		}
	}

}
