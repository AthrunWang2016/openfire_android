package com.example.openfireapp.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smack.packet.Message;

import com.example.openfireapp.TApplication;
import com.example.openfireapp.chatutils.ChatUtils;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.utils.MethUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PrivateChatMsgDAO extends MyBaseDAO{

	private static PrivateChatMsgDAO instance;

	private PrivateChatMsgDAO(Context context) {
		super(context);
	}

	public static PrivateChatMsgDAO getInstance(Context context){
		if(instance == null){
			instance = new PrivateChatMsgDAO(context);
		}
		return instance;
	}
	
	/**
	 * 聊天记录表
	 * type:0.文字，1.语音，2。图片，3.视频，4.语音聊天
	 * kind:0.私聊，1.群聊，2，聊天室
	 */
	public void savePrivateChatMsg(Message msg) {
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		ContentValues values =new ContentValues();
		values.put("msgFrom", MethUtils.subUserToName(msg.getFrom()));
		values.put("msgTo", MethUtils.subUserToName(msg.getTo()));
		values.put("msgBody", ChatUtils.getMsgBody(msg.getBody()));
		values.put("type", ChatUtils.getMsgType(msg.getBody()));
		values.put("kind", 0);
		db.insert("chatMsgTable", null, values);
		db.close();
	}
	
	/*public List<ChatEntity> askPrivateChatMsg(String from) {
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		//Cursor c = db.query("chatMsgTable", new String[]{"_id","msgFrom","msgTo","msgBody","type","kind"}, null, null, null, null, null);
		Cursor c = db.rawQuery("select * from chatMsgTable where msgFrom = '"+from+"'or (msgFrom = '"+TApplication.currentUser.getName()+"' and msgTo = '"+ from +"')", null);
		List<ChatEntity> lists = new ArrayList<ChatEntity>();
		while(c.moveToNext()){
			ChatEntity ce = new ChatEntity();
			ce.setFrom(c.getString(1));
			ce.setTo(c.getString(2));
			ce.setBody(c.getString(3));
			ce.setKind(c.getInt(4));
			ce.setType(c.getInt(5));
			lists.add(ce);
		}
		c.close();
		db.close();
		return lists;
	}*/
	
	public List<ChatEntity> askPrivateChatMsgByPage(String from,int page) {
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		int size = 20;
		//Cursor c = db.query("chatMsgTable", new String[]{"_id","msgFrom","msgTo","msgBody","type","kind"}, null, null, null, null, null);
		//跳过(size*page)条记录选出size条记录
		//Cursor c = db.rawQuery("select * from chatMsgTable where msgFrom = '"+from+"'or (msgFrom = '"+TApplication.currentUser.getName()+"' and msgTo = '"+ from +"')", null);
		Cursor c = db.rawQuery("select * from chatMsgTable where msgFrom = '"+from+"'or (msgFrom = '"+TApplication.currentUser.getName()+"' and msgTo = '"+ from +"') order by _id DESC limit " + size + " offset " + (size*page), null);
		List<ChatEntity> lists = new ArrayList<ChatEntity>();
		LogUtils.log("askPrivateChatMsgByPage", "-----------start-----------");
		while(c.moveToNext()){
			ChatEntity ce = new ChatEntity();
			LogUtils.log("askPrivateChatMsgByPage", "Id:"+c.getInt(0));
			ce.setFrom(c.getString(1));
			ce.setTo(c.getString(2));
			ce.setBody(c.getString(3));
			ce.setKind(c.getInt(4));
			ce.setType(c.getInt(5));
			lists.add(ce);
		}
		LogUtils.log("askPrivateChatMsgByPage", "-----------end-----------");
		c.close();
		db.close();
		//倒序排列
		//Collections.reverse(lists);
		return lists;
	}
	
	/*private List<ChatEntity> compositorChatMsgList(List<ChatEntity> list){
		Collections.reverse(list);
		return list;
	}*/
	
	public void deletePrivateChatMsg(String id){
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		db.delete("chatMsgTable","msgTo = ?",new String[]{id});
		db.close();
	}
	
}
