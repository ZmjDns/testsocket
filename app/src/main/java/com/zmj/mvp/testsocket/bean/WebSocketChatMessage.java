package com.zmj.mvp.testsocket.bean;

/**
 * @author Zmj
 * @date 2018/11/22
 */
public class WebSocketChatMessage {
    private long time;			//消息发送的时间        可以作为表的PrimiaryKey
    private String fromUser;	//消息的发送者
    private String toUser;		//消息的接收者
    private String content;		//消息内容
    private int issended;		//是否发送    1：发送     0：没有发送

    public WebSocketChatMessage(long time, String fromUser, String toUser, String content,int issended) {
        this.time = time;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.issended = issended;
    }

    public WebSocketChatMessage() {

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIssended() {
        return issended;
    }

    public void setIssended(int issended) {
        this.issended = issended;
    }
}
