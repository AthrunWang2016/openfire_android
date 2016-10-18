package com.example.openfireapp.entity;

import java.io.Serializable;

import com.example.openfireapp.chatutils.ChatUtils;

public class ChatEntity implements Serializable{

	private String from;
	private String body;
	private String to;
	private int type;
	private int kind;
	
	public ChatEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public ChatEntity(String from, String msg) {
		this.from = from;
		this.body = ChatUtils.getMsgBody(msg);
		this.type = ChatUtils.getMsgType(msg);
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	
}
