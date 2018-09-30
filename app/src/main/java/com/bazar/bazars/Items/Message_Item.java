package com.bazar.bazars.Items;

import java.util.ArrayList;

/**
 * Created by AG on 4/9/2017.
 */

public class Message_Item {

    private String message_title;
    private String sender_name;
    private String time_send,isReaded;
    private String code_id;
    private int from,to;
    private ArrayList<Message_Itema> messeges;

    public ArrayList<Message_Itema> getMesseges() {
        return messeges;
    }

    public void setMesseges(ArrayList<Message_Itema> messeges) {
        this.messeges = messeges;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getTime_send() {
        return time_send;
    }

    public void setTime_send(String time_send) {
        this.time_send = time_send;
    }


    public String getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }


}
