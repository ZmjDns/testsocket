package com.zmj.mvp.testsocket.bean;

import java.io.Serializable;

/**
 * @author Zmj
 * @date 2018/11/19
 */
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 2636286243487805487L;

    private String content;//消息内容
    private String fromUser;	//消息发送者         消息的发送者和接收者到底是用对象还是用String       暂时用Sting
    private String toUser;	//消息接收者
    private long time;		//发送时间
    private int issended;		//是否发送    1：发送     0：没有发送

    public ChatMessage(String content, String fromUser, String toUser,int issended) {
        this.content = content;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.time = System.currentTimeMillis();
        this.issended = issended;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setToUser(User String) {
        this.toUser = toUser;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
