package com.example.openfireapp.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.R;
import com.example.openfireapp.TApplication;
import com.example.openfireapp.db.MyselfInfoDAO;
import com.example.openfireapp.entity.ChatEntity;
import com.example.openfireapp.entity.MyRosterEntry;
import com.example.openfireapp.utils.LogUtils;
import com.example.openfireapp.widget.BaseItemView;
import com.example.openfireapp.widget.ImageItemView;
import com.example.openfireapp.widget.SoundItemView;
import com.example.openfireapp.widget.TextItemView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends MyBaseAdapter{
	
	private final String TAG = "ChatListAdapter";
	
	private List<ChatEntity> lists;
	//private BaseItemView baseItemView;
	private String currentUserName;
	
	/**
	 * 聊天记录表
	 * type:0.文字，1.语音，2。图片，3.视频聊天，4.语音聊天
	 * kind:0.私聊，1.群聊，2，聊天室
	 */
	private static final int TYPE_MESSAGE = 0;
	private static final int TYPE_IMG = 2;
	private static final int TYPE_SOUND = 1;
	private static final int TYPE_VIDEO = 3;
	private static final int TYPE_AUDEO = 4;
	
	int item_send[] = new int[]{R.layout.item_message_send,R.layout.item_img_send,R.layout.item_sound_send};
	int item_reserve[] = new int[]{R.layout.item_message_reserve,R.layout.item_img_reserve,R.layout.item_sound_reserve};
	
	public ChatListAdapter(Context context, List<ChatEntity> lists) {
		super(context);
		Collections.reverse(lists);
		this.lists = lists;
		currentUserName = MyselfInfoDAO.getInstance(context).getMyInfoMsg().getName();
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
	public int getItemViewType(int position) {
		return lists.get(position).getType();
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		/*if(convertView == null){
			convertView = getConvertView(getItemViewType(position), lists.get(position), lists.get(position).getFrom().equals(currentUserName));
		}*/
		convertView = getConvertView(convertView, getItemViewType(position), lists.get(position), lists.get(position).getFrom().equals(currentUserName));
		return convertView;
	}
	
	public void addData(ChatEntity myRosterEntry){
		lists.add(myRosterEntry);
		notifyDataSetChanged();
	}
	
	public void addData(List<ChatEntity> myRosterEntry){
		
		if(myRosterEntry == null || myRosterEntry.size() == 0){
			return;
		}
		
		List<ChatEntity> newList = new ArrayList<ChatEntity>();
		Collections.reverse(myRosterEntry);
		newList.addAll(myRosterEntry);
		newList.addAll(lists);
		lists = new ArrayList<ChatEntity>();
		lists.addAll(newList);
		
		//lists.addAll(myRosterEntry);
		//Collections.reverse(lists);
		notifyDataSetChanged();
	}
	
	private View getConvertView(View convertView, int type,ChatEntity myRosterEntry,boolean isMyself){
		BaseItemView baseItemView = null;
		switch (type) {
			case TYPE_MESSAGE:
				LogUtils.log(TAG, "TYPE_MESSAGE"+":"+myRosterEntry.getType());
				if(convertView == null){
					baseItemView = new TextItemView(context, myRosterEntry, isMyself); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
					convertView.setTag(baseItemView);
				}else{
					baseItemView = (TextItemView) convertView.getTag(); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
				}
				//baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_IMG:
				LogUtils.log(TAG, "TYPE_IMG"+":"+myRosterEntry.getType());
				if(convertView == null){
					baseItemView = new ImageItemView(context, myRosterEntry, isMyself); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
					convertView.setTag(baseItemView);
				}else{
					baseItemView = (ImageItemView) convertView.getTag(); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
				}
				//baseItemView = new ImageItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_AUDEO:
				LogUtils.log(TAG, "TYPE_AUDEO"+":"+myRosterEntry.getType());
				myRosterEntry.setBody("AUDEO");
				if(convertView == null){
					baseItemView = new TextItemView(context, myRosterEntry, isMyself); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
					convertView.setTag(baseItemView);
				}else{
					baseItemView = (TextItemView) convertView.getTag(); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
				}
				//baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_VIDEO:
				LogUtils.log(TAG, "TYPE_VIDEO"+":"+myRosterEntry.getType());
				myRosterEntry.setBody("VIDEO");
				if(convertView == null){
					baseItemView = new TextItemView(context, myRosterEntry, isMyself); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
					convertView.setTag(baseItemView);
				}else{
					baseItemView = (TextItemView) convertView.getTag(); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
				}
				//baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_SOUND:
				LogUtils.log(TAG, "TYPE_SOUND"+":"+myRosterEntry.getType());
				if(convertView == null){
					baseItemView = new SoundItemView(context, myRosterEntry, isMyself); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
					convertView.setTag(baseItemView);
				}else{
					baseItemView = (SoundItemView) convertView.getTag(); 
					convertView = baseItemView.setUpView(myRosterEntry.getBody());
				}
				//baseItemView = new SoundItemView(context, myRosterEntry, isMyself);
				break;
		}
		
		return convertView;
	}
	
	/*private View getConvertView(int type,ChatEntity myRosterEntry,boolean isMyself){
		BaseItemView baseItemView = null;
		switch (type) {
			case TYPE_MESSAGE:
				LogUtils.log(TAG, "TYPE_MESSAGE"+":"+myRosterEntry.getType());
				baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_IMG:
				LogUtils.log(TAG, "TYPE_IMG"+":"+myRosterEntry.getType());
				baseItemView = new ImageItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_AUDEO:
				LogUtils.log(TAG, "TYPE_AUDEO"+":"+myRosterEntry.getType());
				myRosterEntry.setBody("AUDEO");
				baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_VIDEO:
				LogUtils.log(TAG, "TYPE_VIDEO"+":"+myRosterEntry.getType());
				myRosterEntry.setBody("VIDEO");
				baseItemView = new TextItemView(context, myRosterEntry, isMyself);
				break;
			case TYPE_SOUND:
				LogUtils.log(TAG, "TYPE_SOUND"+":"+myRosterEntry.getType());
				baseItemView = new SoundItemView(context, myRosterEntry, isMyself);
				break;
		}
		
		return baseItemView.setUpView();
	}*/
	
}
