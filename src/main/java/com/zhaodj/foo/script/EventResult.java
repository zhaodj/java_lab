package com.zhaodj.foo.script;

public class EventResult {
	
	private String userName;
	private int roomId;
	private int type;
	private int num;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	@Override
	public String toString() {
		return "EventResult ["
				+ (userName != null ? "userName=" + userName + ", " : "")
				+ "roomId=" + roomId + ", type=" + type + ", num=" + num + "]";
	}

}
