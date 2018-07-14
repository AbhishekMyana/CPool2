package com.example.dhanuja.cpool;

import android.text.format.DateFormat;

import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String sendTime;

    public ChatMessage(String messageText , String messageUser){
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy                                                                          hh:mm:ss a");
        Date now = new Date();
        sendTime = simpleDateFormat.format(now);

    }

    public ChatMessage(String messageText , String messageUser , String sendTime){
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.sendTime = sendTime;
    }


    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getSendTime() { return sendTime; }

    public void setSendTime(String sendTime) { this.sendTime = sendTime; }

}
