package com.example.openfireapp.adapter;

import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.R;
import com.example.openfireapp.entity.MyRosterEntry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConversationAdapter extends MyBaseAdapter{
	
	private List<MyRosterEntry> lists;
	
	public ConversationAdapter(Context context, List<MyRosterEntry> lists) {
		super(context);
		this.lists = lists;
	}

	@Override
	public int getCount() {
		if(lists == null){
			return 0;
		}
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_listview_contacts, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv1);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(lists.get(position).getName()==null){
			String fromUser = lists.get(position).getUser();
			int index=fromUser.indexOf('@');
	    	if(index>0)
	    		fromUser=fromUser.substring(0,index);
			viewHolder.name.setText(fromUser);
		}else{
			viewHolder.name.setText(lists.get(position).getName());
		}
		
		return convertView;
	}

	class ViewHolder{
		TextView name;
	}
	
}
