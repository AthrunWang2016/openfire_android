package com.example.openfireapp.adapter;

import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.R;
import com.example.openfireapp.TApplication;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatListAdapter extends MyBaseAdapter{
	
	private List<ChatEntity> lists;
	
	public ChatListAdapter(Context context, List<ChatEntity> lists) {
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

	public void addData(ChatEntity myRosterEntry){
		lists.add(myRosterEntry);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolderSend = null;
		ViewHolder viewHolderReserve = null;
		
		View viewSend = null;
		View viewReserve = null;
		
		if(!lists.get(position).getFrom().equals(TApplication.currentUser.getName())){
			//convertView = View.inflate(context, R.layout.item_message_send, null);
			convertView = getConvertView(viewHolderSend, viewSend, R.layout.item_message_send,lists.get(position));
		}else{
			//convertView = View.inflate(context, R.layout.item_message_reserve, null);
			convertView = getConvertView(viewHolderReserve, viewReserve, R.layout.item_message_reserve,lists.get(position));
		}
		
		/*if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_message_send, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv1);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}*/
		
		return convertView;
	}

	private View getConvertView(ViewHolder viewHolder,View convertView,int layout,ChatEntity myRosterEntry){
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, layout, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv);
			viewHolder.body = (TextView) convertView.findViewById(R.id.tv2);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		/*if(myRosterEntry.getFrom()==null){
			String fromUser = myRosterEntry.getUser();
			int index=fromUser.indexOf('@');
	    	if(index>0)
	    		fromUser=fromUser.substring(0,index);
			viewHolder.name.setText(fromUser);
		}else{
			viewHolder.name.setText(myRosterEntry.getName());
		}*/
		String fromUser = myRosterEntry.getFrom();
		int index=fromUser.indexOf('@');
    	if(index>0)
    		fromUser=fromUser.substring(0,index);
		viewHolder.name.setText(fromUser);
		//viewHolder.name.setText(myRosterEntry.getFrom());
		viewHolder.body.setText(myRosterEntry.getBody());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView name;
		TextView body;
	}
	
}
