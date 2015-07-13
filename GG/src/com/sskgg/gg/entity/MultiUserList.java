package com.sskgg.gg.entity;

/**
 * 会议室列表
 * @author 杀死凯 QQ565204031
 *
 */
public class MultiUserList {

	private long id;
	private String room;
	private String ownership;
	private String time;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
