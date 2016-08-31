package com.example.openfireapp.fragment;

import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;

import com.example.openfireapp.ChatActivity;
import com.example.openfireapp.R;
import com.example.openfireapp.TApplication;
import com.example.openfireapp.adapter.ContactsListAdapter;
import com.example.openfireapp.adapter.ConversationAdapter;
import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.db.PrivateChatMsgDAO;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.fragment.ContactsFragment.MyClickListener;
import com.example.openfireapp.fragment.ContactsFragment.MyRosterListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ConversationFragment extends BaseFragment{

	private ListView listView1;
	private ArrayList<MyRosterEntry> lists;
	private ConversationAdapter arrayAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragmnet_conversation, null);
		
		setView();
		setListener();
		setListViewData();
		
		return view;
	}
	
	private void setView() {
		listView1 = (ListView) view.findViewById(R.id.listView1);
	}

	private void setListener() {
		listView1.setOnItemClickListener(new MyClickListener());
		listView1.setOnItemLongClickListener(new MyClickListener());
	}
	
	private void setListViewData(){
		lists = new ArrayList<MyRosterEntry>();
		lists.addAll(ChatRosterDAO.getInstance(getActivity()).askChatRoster(true));
		arrayAdapter = new ConversationAdapter(getActivity(), lists);
		listView1.setAdapter(arrayAdapter);
		
	}
	
	
	class MyClickListener implements OnClickListener,OnItemClickListener,OnItemLongClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(),ChatActivity.class);
			MyRosterEntry rosterEntry= (MyRosterEntry)parent.getItemAtPosition(position);
			//intent.putExtra("RosterEntry", new MyRosterEntry(rosterEntry));
			//intent.putExtra("RosterEntry", rosterEntry);
			intent.putExtra("RosterEntry", rosterEntry.getName());
			startActivity(intent);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			MyRosterEntry rosterEntry= (MyRosterEntry)parent.getItemAtPosition(position);
			setDialog(rosterEntry);
			return true;
		}
	}
	
	private void setDialog(final MyRosterEntry rosterEntry){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.ic_launcher);
        //builder.setTitle("");
        final String[] cities = {"删除", "取消"};
        builder.setItems(cities, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            	switch (which) {
					case 0:
						ChatRosterDAO.getInstance(getActivity()).updateChatRoster(rosterEntry.getName(),0);
						PrivateChatMsgDAO.getInstance(getActivity()).deletePrivateChatMsg(rosterEntry.getName());
						//刷新界面,
						//arrayAdapter.notifyDataSetChanged();
						setListViewData();
						break;
					case 1:
						
						break;	
				}
            }
        });
        builder.show();
	}
	
}
