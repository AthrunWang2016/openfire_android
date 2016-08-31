package com.example.openfireapp.entity;

import java.io.Serializable;
import java.util.List;

import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.packet.RosterPacket.ItemStatus;
import org.jivesoftware.smack.roster.packet.RosterPacket.ItemType;

public class MyRosterEntry extends CurrentUser{

	/*private List<RosterGroup> rosterGroupList;
	private ItemStatus itemStatus;
	private ItemType itemType;*/
	public MyRosterEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public MyRosterEntry(RosterEntry rosterEntry) {
		super();
		name = rosterEntry.getName();
		user = rosterEntry.getUser();
		/*rosterGroupList = rosterEntry.getGroups();
		itemStatus = rosterEntry.getStatus();
		itemType = rosterEntry.getType();*/
	}

	/*public List<RosterGroup> getRosterGroupList() {
		return rosterGroupList;
	}

	public void setRosterGroupList(List<RosterGroup> rosterGroupList) {
		this.rosterGroupList = rosterGroupList;
	}

	public ItemStatus getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}*/

}
