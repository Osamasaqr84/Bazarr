package com.bazar.bazars.Items;

public class Chat_item {

    private String chat_right,message_time;
     private boolean isMine;

    public String getChat_right() {
        return chat_right;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public void setChat_right(String chat_right) {
        this.chat_right = chat_right;
    }


    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
