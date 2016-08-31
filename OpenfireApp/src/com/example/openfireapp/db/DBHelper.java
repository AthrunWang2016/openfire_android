package com.example.openfireapp.db;

import com.example.openfireapp.utils.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存聊天记录和联系人
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper instance;
	
	/**
	 * 聊天记录表
	 * type:0.文字，1.语音，2。图片，3.视频
	 * king:0.私聊，1.群聊，2，聊天室
	 */
	private static String ChatMsgTable = "create table if not exists chatMsgTable ("
			+ "_id integer primary key autoincrement,"
			+ "msgFrom text,"
			+ "msgTo text,"
			+ "msgBody text,"
			+ "kind integer,"
			+ "type integer );";
	
	/**
	 * 联系人表
	 * type：0.没有会话，1.有会话
	 */
	private static String ChatRosterTable = "create table if not exists chatRosterTable ("
			+ "_id integer primary key autoincrement,"
			+ "user text,"
			+ "name text,"
			+ "icon text,"
			+ "sex integer,"
			+ "type integer );";
	
	//private static String ChatTable = "";
	
	public static DBHelper getInstance(Context context){
		if(instance == null){
			instance = new DBHelper(context);
		}
		return instance;
	}
	
	private DBHelper(Context context){
		super(context, "MyXmppDemo.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ChatMsgTable);
		db.execSQL(ChatRosterTable);
		LogUtils.log("SQLiteDatabase", "SQLiteDatabase");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
