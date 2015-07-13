package com.sskgg.gg.entity;

import java.io.Serializable;

/**
 * 消息实体
 * @author 杀死凯 QQ565204031
 *
 */
public class MessageInfo implements Serializable{

	//本地数据id
	private long id;
	//消息内容
	private String body;
	//接收or发送 1接收，2发送
	private int mode;
	//消息类型
	private String type;
	//是否已读
	private boolean read;
	//时间
	private String time;
	//消息列表id
	private long listid;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getListid() {
		return listid;
	}
	public void setListid(long listid) {
		this.listid = listid;
	}
	
	
}
