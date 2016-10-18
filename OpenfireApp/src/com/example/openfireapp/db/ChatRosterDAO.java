package com.example.openfireapp.db;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.TApplication;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.utils.MethUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChatRosterDAO extends MyBaseDAO{

	private static ChatRosterDAO instance;

	private ChatRosterDAO(Context context) {
		super(context);
	}

	public static ChatRosterDAO getInstance(Context context){
		if(instance == null){
			instance = new ChatRosterDAO(context);
		}
		return instance;
	}
	
	public void saveChatRoster(RosterEntry rosterEntry) {
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		ContentValues values =new ContentValues();
		values.put("user", MethUtils.subUserToName(rosterEntry.getUser()));
		if(rosterEntry.getName()==null||rosterEntry.getName().equals("")){
			values.put("name", MethUtils.subUserToName(rosterEntry.getUser()));
		}else{
			values.put("name", rosterEntry.getName());
		}
		values.put("type", 0);
		db.insert("chatRosterTable", null, values);
		db.close();
	}
	
	public void saveChatRoster(List<RosterEntry> list) {
		LogUtils.log("saveChatRoster", "saveChatRosterList");
		List<MyRosterEntry> askList = askChatRoster(false);
		if(askList != null && askList.size() != 0){
			for(RosterEntry r : list){
				boolean isHere = false;
				for(MyRosterEntry mR : askList){
					if(mR.getName().equals(MethUtils.subUserToName(r.getUser()))){
						isHere = true;
						continue;
					}
				}
				if(!isHere){
					saveChatRoster(r);
				}
			}
			return;
		}
		
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		LogUtils.log("saveChatRoster", "saveChatRoster:"+list.size());
		for(int i=0;i<list.size();i++){
			ContentValues values =new ContentValues();
			values.put("user", MethUtils.subUserToName(list.get(i).getUser()));
			if(list.get(i).getName()==null||list.get(i).getName().equals("")){
				values.put("name", MethUtils.subUserToName(list.get(i).getUser()));
			}else{
				values.put("name", list.get(i).getName());
			}
			values.put("type", 0);
			db.insert("chatRosterTable", null, values);
		}
		db.close();
	}
	
	public List<MyRosterEntry> askChatRoster(boolean isConversation) {
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		//Cursor c = db.query("chatMsgTable", new String[]{"_id","msgFrom","mesgTo","msgBody","type","kind"}, null, null, null, null, null);
		String sql;
		if(isConversation){
			sql = "select * from chatRosterTable where type = 1";
		}else{
			sql = "select * from chatRosterTable where type != 2";
		}
		Cursor c = db.rawQuery(sql, null);
		List<MyRosterEntry> lists = new ArrayList<MyRosterEntry>();
		while(c.moveToNext()){
			MyRosterEntry ce = new MyRosterEntry();
			LogUtils.log("askChatRoster", c.getString(1));
			ce.setUser(c.getString(1));
			ce.setName(c.getString(2));
			ce.setIcon(c.getString(3));
			ce.setSex(c.getInt(4));
			lists.add(ce);
		}
		c.close();
		db.close();
		return lists;
	}
	
	/*public void updateChatRoster(ContentValues values,String whereClause,String[] whereArgs){
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		db.update("chatRosterTable", values, whereClause, whereArgs);
		db.close();
	}*/
	
	public void updateChatRoster(String user,int type){
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("type", type);
		String[] args = {user};
		int i = db.update("chatRosterTable", values, "user = ?", args);
		LogUtils.log("updateChatRoster", i+"");
		db.close();
	}
	
	public void deleteChatRoster(String id){
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		db.delete("chatMsgTable","user = ?",new String[]{id});
		db.close();
	}
	
	public void deleteChatRoster(){
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		db.delete("chatMsgTable",null,null);
		db.close();
	}
	
}
