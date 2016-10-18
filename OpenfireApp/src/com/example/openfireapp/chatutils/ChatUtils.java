package com.example.openfireapp.chatutils;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.ChatActivity;
import com.example.openfireapp.TApplication;
import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.db.MyselfInfoDAO;
import com.example.openfireapp.db.PrivateChatMsgDAO;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.ConUtils;

import android.content.Context;

public class ChatUtils {

	public static String MSG_TYPE_TXT = "MSG_TYPE_TXT";
	public static String MSG_TYPE_IMG = "MSG_TYPE_IMG";
	public static String MSG_TYPE_SOUND = "MSG_TYPE_SOUND";
	public static String MSG_TYPE_VIDEO = "MSG_TYPE_VIDEO";
	public static String MSG_TYPE_AUDEO = "MSG_TYPE_AUDEO";
	
	public static String getUserName(RosterEntry rosterEntry){
		if(rosterEntry.getName() == null){
			return rosterEntry.getUser();
		}
		return rosterEntry.getName();
	}
	
	/**
	 * 聊天记录表
	 * type:0.文字，1.语音，2。图片，3.视频，4.语音聊天
	 * kind:0.私聊，1.群聊，2，聊天室
	 */
	public static int getMsgType(String text){
		int index = text.indexOf(':');
    	if(index>0)
    		text = text.substring(0,index);
    	if(MSG_TYPE_IMG.equals(text)){
    		return 2;
    	}else if(MSG_TYPE_SOUND.equals(text)){
    		return 1;
    	}else if(MSG_TYPE_VIDEO.equals(text)){
    		return 3;
    	}else if(MSG_TYPE_AUDEO.equals(text)){
    		return 4;
    	}else {
    		return 0;
    	}
		
	}
	
	public static String getMsgBody(String text){
		int index = text.indexOf(':');
    	if(index > 0)
    		text = text.substring(index + 1,text.length());
		return text;
	}
	
	public static void sendMsg(String body, String toUser, Context context) throws NotConnectedException{
		ChatManager chatmanager = ChatManager.getInstanceFor(TApplication.connection);
		Chat chat = chatmanager.createChat(toUser+"@"+ConUtils.SERVICENAME);
		chat.sendMessage(body);
		Message msg = new Message();
		msg.setFrom(MyselfInfoDAO.getInstance(context).getMyInfoMsg().getName());
        msg.setBody(body);
        msg.setTo(toUser);
        
        PrivateChatMsgDAO.getInstance(context).savePrivateChatMsg(msg);
        
        ChatRosterDAO.getInstance(context).updateChatRoster(toUser,1);
	}
	
	/*public static String getUserName(MyRosterEntry rosterEntry){
		if(rosterEntry.getName() == null){
			return rosterEntry.getUser();
		}
		return rosterEntry.getName();
	}*/
	
}
