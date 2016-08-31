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
import com.example.openfireapp.db.ChatRosterDAO;
import com.example.openfireapp.entity.MyRosterEntry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactsFragment extends BaseFragment{

	private ListView listView1;
	private ArrayList<RosterEntry> lists;
	private ContactsListAdapter arrayAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragmnet_contacts, null);
		
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
		//数据应从数据库中取出
		Roster roster = Roster.getInstanceFor(TApplication.connection);
		roster.addRosterListener(new MyRosterListener());
		lists = new ArrayList<RosterEntry>();
		lists.addAll(roster.getEntries());
		arrayAdapter = new ContactsListAdapter(getActivity(), lists);
		listView1.setAdapter(arrayAdapter);
		
		//只能保存一次
		//ChatRosterDAO.getInstance(getActivity()).saveChatRoster(lists);
		
	}
	
	class MyRosterListener implements RosterListener{
		@Override
		public void entriesAdded(Collection<String> arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void entriesDeleted(Collection<String> arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void entriesUpdated(Collection<String> arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void presenceChanged(Presence arg0) {
			/**
			 * 
			 */
			/*String fromUser=arg0.getFrom();
	    	int index=fromUser.indexOf('@');
	    	if(index>0)
	    		fromUser=fromUser.substring(0,index);
	        if(arg0.isAvailable())
	        {
	        	boolean isExit = false;
	        	for(int i=0;i<adapter.getSize();i++){
	        		if(fromUser.compareTo(((Entity)adapter.getOBJ(i)).getname())==0)
	        			isExit = true;
	        	}
	        	if(!isExit){
					Entity entity=new Entity();
					entity.setname(fromUser);
					adapter.addObject(entity);
	        	}
	        }else
	        {
	        	for(int i=0;i<adapter.getSize();i++)
	        		if(fromUser.compareTo(((Entity)adapter.getOBJ(i)).getname())==0)
	        			adapter.removeObject(i);
	        	
	        }*/

		}
	}
	
	class MyClickListener implements OnClickListener,OnItemClickListener,OnItemLongClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(),ChatActivity.class);
			RosterEntry rosterEntry= (RosterEntry)parent.getItemAtPosition(position);
			//intent.putExtra("RosterEntry", new MyRosterEntry(rosterEntry));
			//intent.putExtra("RosterEntry", rosterEntry);
			intent.putExtra("RosterEntry", new MyRosterEntry(rosterEntry).getName());
			startActivity(intent);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			setDialog();
			return true;
		}
	}
	
	private void setDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.ic_launcher);
        //builder.setTitle("ѡ��һ������");
        final String[] cities = {"删除", "取消"};
        builder.setItems(cities, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            	switch (which) {
					case 0:
						
						break;
					case 1:
						
						break;	
				}
            }
        });
        builder.show();
	}
}
