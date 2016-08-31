package com.example.openfireapp.db;

import android.content.Context;

public abstract class MyBaseDAO {

	protected DBHelper dBHelper;
	
	public MyBaseDAO(Context context) {
		dBHelper = DBHelper.getInstance(context);
	}
	
	/*public abstract void insert();
	
	public abstract void query();
	
	public abstract void delete();*/
	
}
