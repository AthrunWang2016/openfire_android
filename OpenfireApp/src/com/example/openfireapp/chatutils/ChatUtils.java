package com.example.openfireapp.chatutils;

import org.jivesoftware.smack.roster.RosterEntry;

import com.example.openfireapp.entity.MyRosterEntry;

public class ChatUtils {

	public static String getUserName(RosterEntry rosterEntry){
		if(rosterEntry.getName() == null){
			return rosterEntry.getUser();
		}
		return rosterEntry.getName();
	}
	
	/*public static String getUserName(MyRosterEntry rosterEntry){
		if(rosterEntry.getName() == null){
			return rosterEntry.getUser();
		}
		return rosterEntry.getName();
	}*/
	
}
