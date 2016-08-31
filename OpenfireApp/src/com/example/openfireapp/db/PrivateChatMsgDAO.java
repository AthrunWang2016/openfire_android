package com.example.openfireapp.db;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;

import com.example.openfireapp.TApplication;
import com.example.openfireapp.entity.ChatEntity;
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
	
	public void savePrivateChatMsg(Message msg) {
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		ContentValues values =new ContentValues();
		values.put("msgFrom", MethUtils.subUserToName(msg.getFrom()));
		values.put("msgTo", MethUtils.subUserToName(msg.getTo()));
		values.put("msgBody", msg.getBody());
		values.put("type", 0);
		values.put("kind", 0);
		db.insert("chatMsgTable", null, values);
		db.close();
	}
	
	public List<ChatEntity> askPrivateChatMsg(String from) {
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
	}
	
	public void deletePrivateChatMsg(String id){
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		db.delete("chatMsgTable","msgTo = ?",new String[]{id});
		db.close();
	}
	
}
