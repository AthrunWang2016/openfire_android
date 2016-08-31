package com.example.openfireapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MethUtils {

	public static void setupView(int rl,Fragment mainFragment,FragmentActivity fragmentActivity) {
		FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(rl, mainFragment);
		transaction.show(mainFragment);
		//transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public static String subUserToName(String fromUser){
		int index = fromUser.indexOf('@');
    	if(index>0)
    		fromUser=fromUser.substring(0,index);
    	return fromUser;
	}
	
}
