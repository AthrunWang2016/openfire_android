package com.example.openfireapp.db;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.TApplication;
import com.example.openfireapp.chatutils.ChatUtils;
import com.example.openfireapp.entity.CurrentUser;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.utils.MethUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyselfInfoDAO extends MyBaseDAO{
	
	private static MyselfInfoDAO instance;
	private Context context;
	
	private MyselfInfoDAO(Context context) {
		super(context);
		this.context = context;
	}

	public static MyselfInfoDAO getInstance(Context context){
		if(instance == null){
			instance = new MyselfInfoDAO(context);
		}
		return instance;
	}

	public void saveMyInfoMsg(CurrentUser currentUser) {
		//ChatRosterDAO.getInstance(context).saveChatRoster(rosterEntry);
		TApplication.currentUser = new CurrentUser();
		TApplication.currentUser.setUser(currentUser.getUser());
		TApplication.currentUser.setName(currentUser.getName());
		TApplication.currentUser.setPassword(currentUser.getPassword());
		   
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		ContentValues values =new ContentValues();
		values.put("user", MethUtils.subUserToName(currentUser.getUser()));
		if(currentUser.getName()==null||currentUser.getName().equals("")){
			values.put("name", MethUtils.subUserToName(currentUser.getUser()));
		}else{
			values.put("name", currentUser.getName());
		}
		values.put("type", 2);
		db.insert("chatRosterTable", null, values);
		db.close();
	}
	
	public CurrentUser getMyInfoMsg(){
		if(TApplication.currentUser !=null ){
			return TApplication.currentUser;
		}else{
			SQLiteDatabase db = dBHelper.getReadableDatabase();
			//Cursor c = db.query("chatMsgTable", new String[]{"_id","msgFrom","mesgTo","msgBody","type","kind"}, null, null, null, null, null);
			String sql = "select * from chatRosterTable where type = 2";
			Cursor c = db.rawQuery(sql, null);
			CurrentUser ce = new CurrentUser();
			while(c.moveToNext()){
				LogUtils.log("askChatRoster", c.getString(1));
				ce.setUser(c.getString(1));
				ce.setName(c.getString(2));
				ce.setIcon(c.getString(3));
				ce.setSex(c.getInt(4));
			}
			c.close();
			db.close();
			return ce;
		}
	}
	
}
