package com.zhaodj.foo.netty;

public class UserState {
    
    private int roomId;
    private String host;
    private int port;
    
    public UserState(int roomId, String host, int port) {
        super();
        this.roomId = roomId;
        this.host = host;
        this.port = port;
    }
    
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

}
