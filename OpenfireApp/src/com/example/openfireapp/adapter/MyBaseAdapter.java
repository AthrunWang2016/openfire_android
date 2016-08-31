package com.example.openfireapp.adapter;

import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter{

	protected Context context;
	
	public MyBaseAdapter(Context context) {
		super();
		this.context = context;
	}

}
