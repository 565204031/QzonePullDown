package com.sskgg.gg.entity;

import java.io.Serializable;

public class MessageList implements Serializable{

	private long id;
	//发信人
	private String username;
	//收信人
	private String to;
	//是否移除消息列表
	private boolean isremove;
	//最后消息
	private String body;
	//最后时间
	private String time;
	//未读条数
	private long number;
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public boolean isIsremove() {
		return isremove;
	}
	public void setIsremove(boolean isremove) {
		this.isremove = isremove;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	
}
