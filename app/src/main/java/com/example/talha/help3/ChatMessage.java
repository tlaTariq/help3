package com.example.talha.help3;

/**
 * Created by talha on 1/4/2018.
 */

public class ChatMessage {

    public String body, For;
    public String Date, Time;
    public Integer msgid;
    public boolean isMine;// Did I send the message.

    public ChatMessage() {


    }

    public ChatMessage(Integer id, String with, String messageString, boolean isMINE) {
        msgid = id;
        body = messageString;
        isMine = isMINE;
        For = with;

    }

    public String getBody(){return body;}
    public String getDate(){return Date;}
    public String getTime(){return body;}
    public String getFor(){return For;}
    public Integer getId(){return msgid;}
    public boolean getIsMine(){return isMine;}

    public void setBody(String text){body = text;}
    public void setDate(String date){Date = date;}
    public void setTime(String time){Time = time;}
    public void setFor(String with){For = with;}
    public void setId(Integer id){msgid = id;}
    public void setIsMine(boolean flag){isMine = flag;}


//    public void setMsgID() {
//
//        msgid += "-" + String.format("%02d", new Random().nextInt(100));
//        ;
//    }
}
