package com.example.openfireapp.entity;

import java.io.Serializable;

public class CurrentUser implements Serializable{

	protected String name;
	protected String user;
	private String password;
	private int sex;
	private String icon;
	
	public CurrentUser() {
		// TODO Auto-generated constructor stub
	}
	
	public CurrentUser(String name, String user, String password, int sex, String icon) {
		super();
		this.name = name;
		this.user = user;
		this.password = password;
		this.sex = sex;
		this.icon = icon;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		if(name == null){
			String fromUser = user;
			int index=fromUser.indexOf('@');
	    	if(index>0)
	    		fromUser=fromUser.substring(0,index);
			return fromUser;
		}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
